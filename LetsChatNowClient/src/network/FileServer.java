package network;

import view.LetsChatClientGui;

import java.io.File;
import java.net.ServerSocket;
import java.util.Random;

public class FileServer {
    private static FileServer ourInstance = new FileServer();
    private ServerSocket myServer;
    private int serverPort;
    private File file;

    public static FileServer getInstance() {
        return ourInstance;
    }

    private FileServer() {
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void startServer(){
        Random random = new Random();
        while (myServer == null){
            this.serverPort = random.nextInt(65000);
            try{
                this.myServer = new ServerSocket(this.serverPort);
                LetsChatClientGui.getInstance().updateMessageView("Ready to send File: " + this.file.getName());

            }catch (Exception e){

            }
        }
    }
}
