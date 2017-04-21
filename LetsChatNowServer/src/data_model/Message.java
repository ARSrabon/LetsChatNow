package data_model;

/**
 * Created by msrabon on 4/14/17.
 */
public class Message {
    /**
     * @param sender indicates who is sending the message.
     */
    private String sender;
    /**
     * @param receiver indicates who will be receiving the message.
     */
    private String receiver;
    /**
     * @param opCode indicates network communication codes that represents a message.
     */
    private int opCode;
    /**
     * @param chatType indicates in which chat mode we are using.
     */
    private int chatType;
    /**
     * @param payLoad this will contain the message.
     */
    private String payLoad;

    /**
     * public constructor
     * @param sender
     * @param receiver
     * @param opCode
     * @param chatType
     * @param payLoad
     */
    public Message(String sender, String receiver, int opCode, int chatType, String payLoad) {
        this.sender = sender;
        this.receiver = receiver;
        this.opCode = opCode;
        this.chatType = chatType;
        this.payLoad = payLoad;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public int getOpCode() {
        return opCode;
    }

    public void setOpCode(int opCode) {
        this.opCode = opCode;
    }

    public int getChatType() {
        return chatType;
    }

    public void setChatType(int chatType) {
        this.chatType = chatType;
    }

    public String getPayLoad() {
        return payLoad;
    }

    public void setPayLoad(String payLoad) {
        this.payLoad = payLoad;
    }
}
