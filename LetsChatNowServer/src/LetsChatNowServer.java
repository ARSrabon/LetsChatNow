import network.ServerHandler;

/**
 * Created by msrabon on 4/14/17.
 */
public class LetsChatNowServer {
    public static void main(String[] args) {
        ServerHandler serverHandler = ServerHandler.getInstance();

        serverHandler.initChatServer(9000,"Test");
        serverHandler.startChatServer();
    }
}
