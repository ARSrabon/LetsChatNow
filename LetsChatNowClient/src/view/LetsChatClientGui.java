package view;

import network.ClientHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LetsChatClientGui extends JFrame {

    private static LetsChatClientGui instance = new LetsChatClientGui();
    private static ClientHandler clientHandler = ClientHandler.getInstance();

    public static LetsChatClientGui getInstance() {
        return instance;
    }

    private JTextArea chatMessageViewBox;
    private JTextField serverAddress;
    private JTextField serverPort;
    JList<String> onlineUsers;
    private JTextField txtWriteMessage;

    private LetsChatClientGui() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Lets Chat Now!!!");
        setSize(450, 600);
        setResizable(false);
        getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(0, 75));
        getContentPane().add(mainPanel, BorderLayout.NORTH);
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JLabel lblNewLabel = new JLabel("Server Address");
        mainPanel.add(lblNewLabel);

        serverAddress = new JTextField();
        mainPanel.add(serverAddress);
        serverAddress.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("Port");
        mainPanel.add(lblNewLabel_1);

        serverPort = new JTextField();
        mainPanel.add(serverPort);
        serverPort.setColumns(10);

        JButton btnConnectToServer = new JButton("Connect");
        btnConnectToServer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog("Enter your Username:");
                LetsChatClientGui.getInstance().updateUsername(username);
                JOptionPane.showMessageDialog(null, "Your username: " + username);

                btnConnectToServer.setEnabled(false);
            }
        });

        JLabel lblUserName = new JLabel("Username: cyborn13x");
        mainPanel.add(lblUserName);
        mainPanel.add(btnConnectToServer);

        JButton btnDisConnectFromServer = new JButton("Disconnect");
        mainPanel.add(btnDisConnectFromServer);

        JPanel userOnline = new JPanel();
        userOnline.setPreferredSize(new Dimension(150, 0));
        getContentPane().add(userOnline, BorderLayout.WEST);
        userOnline.setLayout(new GridLayout(3, 0, 0, 0));

        onlineUsers = new JList<>();

        JScrollPane onlineUsersList = new JScrollPane(onlineUsers);
        onlineUsersList.setBorder(BorderFactory.createTitledBorder("Online Users"));
        userOnline.add(onlineUsersList);

        JScrollPane activeChat = new JScrollPane();
        activeChat.setBorder(BorderFactory.createTitledBorder("Active Chats"));
        userOnline.add(activeChat);

        JList activeChats = new JList();
        activeChat.setViewportView(activeChats);

        JPanel btnHolder = new JPanel();
        userOnline.add(btnHolder);

        JButton btnStartChat = new JButton("Start Chat");
        btnStartChat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnHolder.add(btnStartChat);

        JPanel chatPanel = new JPanel();
        getContentPane().add(chatPanel, BorderLayout.CENTER);
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.X_AXIS));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(BorderFactory.createTitledBorder("Message"));
        chatPanel.add(scrollPane);

        chatMessageViewBox = new JTextArea();
        scrollPane.setViewportView(chatMessageViewBox);

        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.SOUTH);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        txtWriteMessage = new JTextField();
        txtWriteMessage.setPreferredSize(new Dimension(0, 50));
        panel.add(txtWriteMessage);
        txtWriteMessage.setColumns(10);

        JButton btnSendMessage = new JButton("Send");
        panel.add(btnSendMessage);

        show();
    }

    public void updateUsername(String username) {

    }

    public void updateMessageView(String str) {
        LetsChatClientGui.getInstance().chatMessageViewBox.append(str);
    }

}
