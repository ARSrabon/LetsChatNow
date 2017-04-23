package network;

import com.google.gson.Gson;
import data_model.Message;
import view.LetsChatClientGui;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

/**
 * Created by msrabon on 4/16/17.
 */
public class ClientHandler {
    private static ClientHandler ourInstance = new ClientHandler();

    private Socket chatServer;
    private String serverAddress;
    private int serverPort;
    private String clientUserName;
    private String chatServerName;
    private Gson gson;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;
    private boolean error;

    public static ClientHandler getInstance() {
        return ourInstance;
    }

    private ClientHandler() {
    }

    public void initClient(String serverAddress, int serverPort, String clientUserName) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.clientUserName = clientUserName;
        this.gson = new Gson();
        error = false;
    }

    public void connectToChatServer() {
        try {
            this.chatServer = new Socket(serverAddress, serverPort);
            this.dataIn = new DataInputStream(chatServer.getInputStream());
            this.dataOut = new DataOutputStream(chatServer.getOutputStream());
            dataOut.writeUTF(gson.toJson(new Message(clientUserName, "", 100, 0, "")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message;
                while (chatServer.isConnected() && !error) {
                    try {
                        message = gson.fromJson(dataIn.readUTF(), Message.class);
                        decisionMaker(message);
                    } catch (IOException e) {
                        error = true;
//                e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void decisionMaker(Message message) throws IOException {
        Socket con = ClientHandler.getInstance().getChatServer();
        DataInputStream dataIn = ClientHandler.getInstance().getDataIn();
        DataOutputStream dataOut = ClientHandler.getInstance().getDataOut();

        switch (message.getOpCode()) {
            case 101:
                if (createUserInServer(message, dataIn, dataOut)) {
//                    System.out.println("here !!!");
                    getOnlineUserList(dataIn, dataOut);
                }
                break;
            case 110:
                ClientHandler.getInstance().setClientUserName(message.getReceiver());
                ClientHandler.getInstance().setChatServerName(message.getSender());
                LetsChatClientGui.getInstance().showMessage(message.getPayLoad(), "Welcome");
                LetsChatClientGui.getInstance().updateUsername(ClientHandler.getInstance().getClientUserName());
                getOnlineUserList(dataIn, dataOut);
                break;

            case 200: // start Chat
                if (message.getChatType() == 0) {

                } else if (message.getChatType() == 1) {
                    if (LetsChatClientGui.getInstance().warnning(message.getSender() + " wants to chat with you.")) {
                        dataOut.writeUTF(gson.toJson(new Message(clientUserName, message.getSender(), 201, 1, "")));
                    } else {
                        dataOut.writeUTF(gson.toJson(new Message(clientUserName, message.getSender(), 202, 1, "")));
                    }
                }
                break;
            case 201:
                JOptionPane.showMessageDialog(null, message.getSender() + " has accepted your chat request.");
                LetsChatClientGui.getInstance().updateMessageView(message.getSender() + " is Available now.");
                break;
            case 202:
                JOptionPane.showMessageDialog(null, "Sorry," + message.getSender() + " is busy right now.");
                LetsChatClientGui.getInstance().updateMessageView(message.getSender() + " is busy.");
                break;
            case 210:
                break;
            case 300: // get Online users list.
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Vector<String> userList = gson.fromJson(message.getPayLoad(), Vector.class);
                        for (String s : userList) {
                            System.out.println(s);
                        }
                        userList.remove(clientUserName);
                        LetsChatClientGui.getInstance().setUsersList(userList);
                        LetsChatClientGui.getInstance().updateOnlineUsers(userList);
                        LetsChatClientGui.getInstance().updateMessageView(String.valueOf(userList));
                    }
                }).start();
                break;

            default:
                break;
        }
        return;
    }

    private void getOnlineUserList(DataInputStream dataIn, DataOutputStream dataOut) throws IOException {
        dataOut.writeUTF(gson.toJson(new Message(clientUserName, chatServerName, 300, 0, "")));
        return;
    }

    private boolean createUserInServer(Message message, DataInputStream dataIn, DataOutputStream dataOut) throws IOException {
        System.out.println("OpCode: " + message.getOpCode());
        if (message.getOpCode() == 101) {
            String str = LetsChatClientGui.getInstance().getUsername("Same Username exists in the chat server.\nTo Proceed ,Please enter a new Username: ");
            dataOut.writeUTF(gson.toJson(new Message(str, message.getSender(), 100, 0, "")));
            return createUserInServer(gson.fromJson(dataIn.readUTF(), Message.class), dataIn, dataOut);
        } else if (message.getOpCode() == 110) {
            ClientHandler.getInstance().setClientUserName(message.getReceiver());
            ClientHandler.getInstance().setChatServerName(message.getSender());
            LetsChatClientGui.getInstance().updateMessageView(message.getPayLoad());
            LetsChatClientGui.getInstance().updateUsername(ClientHandler.getInstance().getClientUserName());
            return true;
        }
        return false;
    }

    public Socket getChatServer() {
        return chatServer;
    }

    public void setChatServer(Socket chatServer) {
        this.chatServer = chatServer;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getClientUserName() {
        return clientUserName;
    }

    public void setClientUserName(String clientUserName) {
        this.clientUserName = clientUserName;
    }

    public DataInputStream getDataIn() {
        return dataIn;
    }

    public void setDataIn(DataInputStream dataIn) {
        this.dataIn = dataIn;
    }

    public DataOutputStream getDataOut() {
        return dataOut;
    }

    public void setDataOut(DataOutputStream dataOut) {
        this.dataOut = dataOut;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getChatServerName() {
        return chatServerName;
    }

    public void setChatServerName(String chatServerName) {
        this.chatServerName = chatServerName;
    }


    public void stopClient() {
        try {
            error = true;
            if (ClientHandler.getInstance().getChatServer().isConnected()) {
                ClientHandler.getInstance().getChatServer().close();
            }
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startChat(String targetUser) throws IOException {
        dataOut.writeUTF(gson.toJson(new Message(clientUserName, targetUser, 200, 1, "")));
    }
}
