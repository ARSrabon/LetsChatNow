package view;

import network.ClientHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

public class LetsChatClientGui extends JFrame {

    private static LetsChatClientGui instance = new LetsChatClientGui();

    public static LetsChatClientGui getInstance() {
        return instance;
    }

    private JTextArea chatMessageViewBox;
    private JTextField serverAddress;
    private JTextField serverPort;
    private JList onlineUsers;
    private Vector<String> usersList = new Vector<>();
    private Vector<String> activeUser = new Vector<>();
    private JTextField txtWriteMessage;
    private JLabel lblUserName;

    private LetsChatClientGui() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Lets Chat Now!!!");
        setSize(450, 600);
        setResizable(false);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(0, 75));
        getContentPane().add(mainPanel, BorderLayout.NORTH);
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JLabel lblNewLabel = new JLabel("Server Address");
        mainPanel.add(lblNewLabel);

        serverAddress = new JTextField();
        serverAddress.setText("192.168.0.2");
        serverAddress.setToolTipText("Enter LetsChatNow Server Address.");
        mainPanel.add(serverAddress);
        serverAddress.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("Port");
        mainPanel.add(lblNewLabel_1);

        serverPort = new JTextField();
        serverPort.setText("9000");
        serverPort.setToolTipText("Enter LetsChatNow Server Port to connect.");
        mainPanel.add(serverPort);
        serverPort.setColumns(10);

        JButton btnConnectToServer = new JButton("Connect");
        btnConnectToServer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog("Enter your Username:");
                LetsChatClientGui.getInstance().updateUsername(username);
                ClientHandler.getInstance().initClient(serverAddress.getText(),Integer.parseInt(serverPort.getText()),username);
                ClientHandler.getInstance().connectToChatServer();
                serverAddress.setEnabled(false);
                serverPort.setEnabled(false);
                btnConnectToServer.setEnabled(false);
            }
        });


        lblUserName = new JLabel("Username: cyborn13x");
        mainPanel.add(lblUserName);
        mainPanel.add(btnConnectToServer);

        JButton btnDisConnectFromServer = new JButton("Disconnect");
        btnDisConnectFromServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientHandler.getInstance().stopClient();
                dispose();
            }
        });
        mainPanel.add(btnDisConnectFromServer);

        JPanel userOnline = new JPanel();
        userOnline.setPreferredSize(new Dimension(150, 0));
        getContentPane().add(userOnline, BorderLayout.WEST);
        userOnline.setLayout(new GridLayout(3, 0, 0, 0));

        onlineUsers = new JList(this.getUsersList());

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

        getWindows()[0].addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ClientHandler.getInstance().stopClient();
                super.windowClosing(e);
                dispose();
            }
        });
        show();
    }

    public void updateUsername(String username) {
        lblUserName.setText("Username: " + username);
    }

    public void updateMessageView(String str) {
        LetsChatClientGui.getInstance().chatMessageViewBox.append(str + "\n");
    }

    public String getUsername(String s) {
        return JOptionPane.showInputDialog(s);
    }

    public void showMessage(String message, String title, int i){
        if (i != 0){
            JOptionPane.showMessageDialog(null,message,title,JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public JList getOnlineUsers() {
        return onlineUsers;
    }

    public void setOnlineUsers(JList onlineUsers) {
        this.onlineUsers = onlineUsers;
    }

    public Vector<String> getUsersList() {
        return usersList;
    }

    public void setUsersList(Vector<String> usersList) {
        this.usersList = usersList;
    }

    public Vector<String> getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(Vector<String> activeUser) {
        this.activeUser = activeUser;
    }

    public void updateOnlineUsers(Vector<String> userList) {
        LetsChatClientGui.getInstance().getOnlineUsers().setListData(userList);
//        LetsChatClientGui.getInstance().show();
//        System.out.println("Here !!!");
        LetsChatClientGui.getInstance().revalidate();
    }
}
