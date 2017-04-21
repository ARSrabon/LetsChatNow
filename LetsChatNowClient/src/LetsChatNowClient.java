import network.ClientHandler;

import java.util.Scanner;

/**
 * Created by msrabon on 4/14/17.
 */
public class LetsChatNowClient {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ClientHandler clientHandler = ClientHandler.getInstance();
        System.out.print("Please Enter a Username: Test");
        clientHandler.initClient("192.168.0.2",9000,"Test");
        clientHandler.connectToChatServer();
    }
}
