package view;

import network.ClientHandler;
import network.FileServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class LetsChatClientGui extends JFrame {

    private static LetsChatClientGui instance = new LetsChatClientGui();
    private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("hh:mm:ss");

    public static LetsChatClientGui getInstance() {
        return instance;
    }

    private JTextArea chatMessageViewBox;
    private JTextField serverAddress;
    private JTextField serverPort;
    private JList onlineUsers;
    private JList activeChats;
    private Vector<String> usersList = new Vector<>();
    private Vector<String> activeUser = new Vector<>();
    private JTextField txtWriteMessage;
    private JLabel lblUserName;
    private JButton btnStartChat;
    private JButton btnSendFile;
    private JFileChooser fileChooser;

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
                ClientHandler.getInstance().initClient(serverAddress.getText(), Integer.parseInt(serverPort.getText()), username);
                ClientHandler.getInstance().connectToChatServer();
                if (ClientHandler.getInstance().getChatServer().isConnected()) {
                    serverAddress.setEnabled(false);
                    serverPort.setEnabled(false);
                    btnConnectToServer.setEnabled(false);
                }
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


        activeChats = new JList();
        activeChat.setViewportView(activeChats);

        JPanel btnHolder = new JPanel();
        userOnline.add(btnHolder);


        btnStartChat = new JButton("Start Chat");
        btnStartChat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String targetUser = String.valueOf(onlineUsers.getSelectedValue());
                try {
                    ClientHandler.getInstance().startChat(targetUser);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnHolder.add(btnStartChat);

        btnSendFile = new JButton("Send File");
//        btnSendFile.setEnabled(ClientHandler.getInstance().isFlag_busy());
        btnSendFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.showOpenDialog(null);
                File file = fileChooser.getSelectedFile();
                FileServer.getInstance().setFile(file);

            }
        });

        btnHolder.add(btnSendFile);

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
        txtWriteMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Enter Pressed");
                if (!txtWriteMessage.getText().equals(null)) {
                    try {
                        String msg = txtWriteMessage.getText();
                        ClientHandler.getInstance().sendMessage(msg);
                        LetsChatClientGui.getInstance().updateMessageView(ClientHandler.getInstance().getClientUserName() + ": " + msg);
                        txtWriteMessage.setText("");
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(null, e1);
                    }
                }
            }
        });
        panel.add(txtWriteMessage);
        txtWriteMessage.setColumns(10);

        JButton btnSendMessage = new JButton("Send");
        btnSendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!txtWriteMessage.getText().equals(null)) {
                    try {
                        String msg = txtWriteMessage.getText();
                        ClientHandler.getInstance().sendMessage(msg);
                        LetsChatClientGui.getInstance().updateMessageView(ClientHandler.getInstance().getClientUserName() + ": " + msg);
                        txtWriteMessage.setText("");
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(null, e1);
                    }
                }
            }
        });
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
        LetsChatClientGui.getInstance().chatMessageViewBox.append(DATE_FORMAT.format(new Date()) + " -> " + str + "\n");
    }

    public String getUsername(String s) {
        return JOptionPane.showInputDialog(s);
    }

    public void showMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
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

    public JButton getBtnStartChat() {
        return btnStartChat;
    }

    public void setActiveUser(Vector<String> activeUser) {
        this.activeUser = activeUser;
    }

    public JList getActiveChats() {
        return activeChats;
    }

    public void setActiveChats(JList activeChats) {
        this.activeChats = activeChats;
    }

    public void updateOnlineUsers(Vector<String> userList) {
        LetsChatClientGui.getInstance().getOnlineUsers().setListData(userList);
//        LetsChatClientGui.getInstance().show();
//        System.out.println("Here !!!");
        LetsChatClientGui.getInstance().revalidate();
    }

    public void updateActiveChat(Vector<String> userList) {
        LetsChatClientGui.getInstance().getActiveChats().setListData(userList);
        LetsChatClientGui.getInstance().revalidate();
    }

    public boolean warnning(String s) {
        JDialog.setDefaultLookAndFeelDecorated(true);
        int response = JOptionPane.showConfirmDialog(null, s, "Confirm",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(null, "No button clicked");
        } else if (response == JOptionPane.YES_OPTION) {
            return true;
        } else if (response == JOptionPane.CLOSED_OPTION) {
            return false;
        }

        return false;
    }
}
