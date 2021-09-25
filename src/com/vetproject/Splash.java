package com.vetproject;

import javax.swing.*;
import java.awt.*;

public class Splash {

    JFrame frame;
    JLabel image = new JLabel(new ImageIcon("img/splash.jpg"));
    JProgressBar progressBar = new JProgressBar(0, 100);


    Splash() {
        frame = new JFrame();
        frame.getContentPane().setLayout(null);
        frame.setUndecorated(true);
        frame.setSize(600, 350);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.white);

        image.setSize(600, 300);
        image.setLocation(0, 0);

        progressBar.setBounds(0, 300, 600, 50);
        progressBar.setBorderPainted(true);
        progressBar.setStringPainted(true);
        progressBar.setBackground(new Color(28, 25, 54));
        progressBar.setForeground(new Color(132, 173, 41));

        frame.add(image);
        frame.add(progressBar);
        frame.setVisible(true);
        runningBar();
    }


    public void runningBar() {

        int counter = 0;
        while(counter<=100) {

            progressBar.setValue(counter);
            try {
                Thread.sleep(100);
                counter++;
                if(counter==100) {
                    frame.dispose();
                    Login login = new Login();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


}
