package user;

import data_model.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by msrabon on 4/14/17.
 */
public class ClientHandler {
    private Socket client;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;
    private String userName;

    public ClientHandler(Socket client, DataInputStream dataIn, DataOutputStream dataOut) {
        this.client = client;
        this.dataIn = dataIn;
        this.dataOut = dataOut;
    }

    public ClientHandler(Socket client) throws IOException {
        this.client = client;
        this.dataIn = new DataInputStream(this.client.getInputStream());
        this.dataOut = new DataOutputStream(this.client.getOutputStream());
    }

    public Socket getClient() {
        return client;
    }

    public void setClient(Socket client) {
        this.client = client;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void sendMessage(Message message){

    }
}
