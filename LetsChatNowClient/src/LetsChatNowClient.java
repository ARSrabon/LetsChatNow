import network.ClientHandler;
import view.LetsChatClientGui;

import javax.swing.*;
import java.util.Scanner;

/**
 * Created by msrabon on 4/14/17.
 */
public class LetsChatNowClient {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LetsChatClientGui clientGui = LetsChatClientGui.getInstance();
            }
        });
    }
}
