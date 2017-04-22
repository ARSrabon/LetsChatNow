package view;

import network.ServerHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LetsChatServerGui extends JFrame {

    private static LetsChatServerGui instance = new LetsChatServerGui();
    private static ServerHandler serverHandler = ServerHandler.getInstance();
    private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("hh:mm:ss");

    public static LetsChatServerGui getInstance() {
        return instance;
    }

    private JTextField serverPort;
    private JLabel lblServerName;
    private JTextArea console;

    private LetsChatServerGui() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("LetsChatNow Server");
        setSize(new Dimension(560, 400));
        setResizable(false);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(0, 85));
        getContentPane().add(panel, BorderLayout.NORTH);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JLabel lblServerInfo = null;
        try {
            lblServerInfo = new JLabel("Address: " + Inet4Address.getLocalHost().getHostAddress() + " Port:");
        } catch (UnknownHostException e) {

        }
        panel.add(lblServerInfo);

        serverPort = new JFormattedTextField();
        serverPort.setText("9000");
        panel.add(serverPort);
        serverPort.setColumns(10);

        JButton btnStartServer = new JButton("Start Server");
        btnStartServer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (serverPort.getText().equals(null)){
                    JOptionPane.showMessageDialog(null,"Error: Please Insert a Port number.");
                }else {
                    int port = Integer.parseInt(serverPort.getText());
                    String serverName = JOptionPane.showInputDialog("Enter LetsChatNow Server Name");
                    LetsChatServerGui.getInstance().updateServerName(serverName);
                    serverHandler.initChatServer(port,serverName);
                    serverHandler.startChatServer();
                    serverPort.setEnabled(false);
                    btnStartServer.setEnabled(false);
                }
            }
        });
        panel.add(btnStartServer);

        JButton btnStopServer = new JButton("Stop Server");
        btnStopServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverHandler.stopServer();
                dispose();
            }
        });
        panel.add(btnStopServer);

        JPanel panel_1 = new JPanel();
        panel.add(panel_1);

        lblServerName = new JLabel("Server Name: qwerty");
        panel_1.add(lblServerName);

        JPanel usersList = new JPanel();
        getContentPane().add(usersList, BorderLayout.WEST);
        usersList.setLayout(new GridLayout(0, 1, 0, 0));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(120, 0));
        scrollPane.setBorder(BorderFactory.createTitledBorder("Online Users"));
        usersList.add(scrollPane);

        JList onlineUsers = new JList();
        onlineUsers.setValueIsAdjusting(true);
        scrollPane.setViewportView(onlineUsers);

        JPanel panel_2 = new JPanel();
        getContentPane().add(panel_2, BorderLayout.CENTER);
        panel_2.setLayout(new GridLayout(0, 1, 0, 0));

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setPreferredSize(new Dimension(200, 0));
        scrollPane_1.setBorder(BorderFactory.createTitledBorder("Console"));
        panel_2.add(scrollPane_1);


        console = new JTextArea();
        console.setEditable(false);
        console.setWrapStyleWord(true);
        console.setLineWrap(true);
        scrollPane_1.setViewportView(console);

        JPanel commonPanel = new JPanel();
        getContentPane().add(commonPanel, BorderLayout.SOUTH);
        commonPanel.setLayout(new BoxLayout(commonPanel, BoxLayout.X_AXIS));

        JLabel lblNewLabel_1 = new JLabel("Message: ");
        commonPanel.add(lblNewLabel_1);

        JTextArea writeBroadcastMessage = new JTextArea();
        commonPanel.add(writeBroadcastMessage);

        JButton btnBroadcast = new JButton("Broadcast");
        btnBroadcast.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        commonPanel.add(btnBroadcast);

        getWindows()[0].addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                serverHandler.stopServer();
                super.windowClosing(e);
            }
        });

        show();
    }

    public void updateServerName(String sname) {
        LetsChatServerGui.getInstance().lblServerName.setText("Server Name: " + sname);
    }

    public void updateConsole(String message){
        this.console.append(DATE_FORMAT.format(new Date()) + " -> " + message + " \n");
    }

}
