package view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by m.arsrabon on 08-Feb-17.
 */
public class MyProgressBar extends JPanel {

    JFrame jFrame;
    JProgressBar progressBar;
    JLabel jTextArea;
    JLabel info;

    public MyProgressBar(String title, String fileName) {

        jFrame = new JFrame();
        jFrame.setTitle(title);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = jFrame.getContentPane();
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setBackground(Color.white);
        progressBar.setForeground(Color.green);
        Border border = BorderFactory.createTitledBorder(fileName);
        progressBar.setBorder(border);
        content.add(progressBar, BorderLayout.NORTH);

        jTextArea = new JLabel();
        content.add(jTextArea);

        info = new JLabel();
        content.add(info);

        jFrame.setSize(300, 200);
        jFrame.setVisible(true);
    }

    public void setjTextArea(String text) {
        jTextArea.setText("Transfer Speed: " + text);
    }

    public void progressBarUpdater(int update) {
        progressBar.setValue(update);
    }

    public void setInfo(String text) {
        info.setText(text);
    }

    public void closeProgressbar(){
        for (int i = 5; i >=0 ; i--) {
            setInfo(String.format("Closing in %d s",i));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        jFrame.dispose();
    }

}
