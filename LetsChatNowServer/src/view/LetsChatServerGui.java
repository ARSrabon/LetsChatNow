package view;

import network.ServerHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

public class LetsChatServerGui extends JFrame {

    private static LetsChatServerGui instance = new LetsChatServerGui();
    private static ServerHandler serverHandler = ServerHandler.getInstance();

    public static LetsChatServerGui getInstance() {
        return instance;
    }

    private JTextField serverPort;
    private JLabel lblServerName;

    private LetsChatServerGui() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("LetsChatNow Server");
        setSize(new Dimension(350, 400));
        setResizable(false);
        getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(0, 85));
        getContentPane().add(panel, BorderLayout.NORTH);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JLabel lblServerInfo = new JLabel("Address: " + " " + " Port:");
        panel.add(lblServerInfo);

        serverPort = new JFormattedTextField(NumberFormat.getNumberInstance());
        panel.add(serverPort);
        serverPort.setColumns(10);

        JButton btnStartServer = new JButton("Start Server");
        btnStartServer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                String serverName = JOptionPane.showInputDialog(null);
//                LetsChatServerGui.getInstance().updateServerName(serverName);
                if (!serverPort.getText().toString().equals("")) JOptionPane.showMessageDialog(null,serverPort.getText());
            }
        });
        panel.add(btnStartServer);

        JButton btnStopServer = new JButton("Stop Server");
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

        JTextArea console = new JTextArea();
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

        show();
    }

    public void updateServerName(String sname) {
        LetsChatServerGui.getInstance().lblServerName.setText("Server Name: " + sname);
    }

}
