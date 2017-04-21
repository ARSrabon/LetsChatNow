package view;

/**
 * Created by msrabon on 4/16/17.
 */
public class GuiClient {
    private static GuiClient ourInstance = new GuiClient();

    public static GuiClient getInstance() {
        return ourInstance;
    }

    private GuiClient() {
    }
}
