package network;

import com.google.gson.Gson;
import data_model.Message;
import user.ClientHandler;
import view.LetsChatServerGui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Created by msrabon on 4/14/17.
 */
public class ServerHandler {
    private static ServerHandler ourInstance = new ServerHandler();
    private static LetsChatServerGui serverGui = LetsChatServerGui.getInstance();

    private String serverName;
    private int serverPort;
    private ServerSocket myChatServer;
    private boolean acceptClient;
    private Gson gson;

    public Map<String, ClientHandler> clientHandlerMap;

    public static ServerHandler getInstance() {
        return ourInstance;
    }

    private ServerHandler() {
    }

    public Map<String, ClientHandler> getClientHandlerMap() {
        return clientHandlerMap;
    }

    public void setClientHandlerMap(Map<String, ClientHandler> clientHandlerMap) {
        this.clientHandlerMap = clientHandlerMap;
    }

    public void initChatServer(int serverPort, String serverName) {
        this.clientHandlerMap = new HashMap<>();
        this.acceptClient = true;
        this.serverPort = serverPort;
        this.serverName = serverName;
        this.gson = new Gson();
    }

    public void startChatServer() {
        try {
            this.myChatServer = new ServerSocket(serverPort);
            serverGui.updateConsole("Server started successfully !!!");
            serverGui.updateConsole(serverName + "'s LetsChatNow Server is ready to receive clients.");
        } catch (IOException e) {
            serverGui.updateConsole("An error occurred while starting the LetsChatNow Server" +
                    "\nPlease check if the port: " + serverPort + " is taken by or not.");
            e.printStackTrace();
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (acceptClient) {
                    try {
                        Socket client = myChatServer.accept();
                        ClientHandler clientHandler = new ClientHandler(client);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                boolean error = false;
                                while (!error && clientHandler.getClient().isConnected()) {
                                    try {
                                        Message message = gson.fromJson(clientHandler.getDataIn().readUTF(), Message.class);
                                        ServerHandler.getInstance().decisionMaker(clientHandler, message);
                                    } catch (IOException e) {
//                                        e.printStackTrace();
                                        error = true;
                                        return;
                                    }
                                }
                            }
                        }).start();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
        }).start();
    }

    public void decisionMaker(ClientHandler clientHandler, Message message) throws IOException {

        switch (message.getOpCode()) {
            case 100: // register new client as Online user.
                if (checkUsername(clientHandler, message)) {
                    //Sending Confirmation message.
                    // Code : 200 means Ok/Ready
                    clientHandler.getDataOut().writeUTF(gson.toJson(new Message(serverName, clientHandler.getUserName(), 110, 0,
                            "Welcome to " + serverName + "'s LetsChat Server,\nyou are ready to chat now.")));
                }
                break;

            case 300: // get list of online users.
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean error = false;
                        while (!error) {
                            String str = gson.toJson(new Vector<String>(ServerHandler.getInstance().getClientHandlerMap().keySet()));
                            serverGui.updateConsole(str);
                            try {
                                clientHandler.getDataOut().writeUTF(gson.toJson(new Message(serverName, clientHandler.getUserName(), 300, 0, str)));
                                Thread.sleep(1000);
                            } catch (IOException e) {
                                error = true;
//                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                error = true;
//                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                break;

            default:
                break;

        }
    }

    public void stopServer() {
        this.acceptClient = false;
        try {
            this.myChatServer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkUsername(ClientHandler clientHandler, Message message) throws IOException {
//        System.out.println(message.getSender() + " " + message.getOpCode() + "  " + clientHandlerMap.containsKey(message.getSender()));
        if (clientHandlerMap.containsKey(message.getSender())) {
            clientHandler.getDataOut().writeUTF(gson.toJson(new Message(serverName, message.getSender(), 101, 0, "Error")));
            return checkUsername(clientHandler, gson.fromJson(clientHandler.getDataIn().readUTF(), Message.class));
        }
        clientHandler.setUserName(message.getSender());
        clientHandlerMap.put(message.getSender(), clientHandler);
        serverGui.updateConsole(message.getSender() + " is online now.");
        return true;
    }
}
