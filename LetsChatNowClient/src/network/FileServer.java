package network;

import java.net.ServerSocket;

public class FileServer {
    private static FileServer ourInstance = new FileServer();
    private ServerSocket myServer;
    private int serverPort;
    public static FileServer getInstance() {
        return ourInstance;
    }

    private FileServer() {
    }

    public void initFileServer(){
        
    }
}
