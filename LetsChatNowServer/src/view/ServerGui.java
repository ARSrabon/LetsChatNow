package view;

/**
 * Created by msrabon on 4/16/17.
 */
public class ServerGui {
    private static ServerGui ourInstance = new ServerGui();

    public static ServerGui getInstance() {
        return ourInstance;
    }

    private ServerGui() {
    }
}
