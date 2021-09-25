package com.vetproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FinishFrame implements ActionListener {

    JFrame frame = new JFrame();
    String id;
    JLabel image, message, message1, message2;
    JButton loginBtn;

    FinishFrame() {
        frame.getContentPane().setLayout(null);
        frame.setUndecorated(true);
        frame.setSize(400,270);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.white);

        image = new JLabel(new ImageIcon("img/success.jpg"));
        image.setBounds(150,20,100,100);
        frame.add(image);

        message1 = new JLabel("Hello,");
        message1.setFont(new Font("Amaranth",Font.BOLD,25));
        message1.setForeground(new Color(90,173,70));
        message1.setBounds(165,120,400,30);
        frame.add(message1);

        message2 = new JLabel("You have been successfully reset");
        message2.setFont(new Font("Amaranth",Font.BOLD,15));
        message2.setForeground(Color.darkGray);
        message2.setBounds(60,160,400,30);
        frame.add(message2);

        message = new JLabel("your password!");
        message.setFont(new Font("Amaranth",Font.BOLD,15));
        message.setForeground(Color.darkGray);
        message.setBounds(130,180,400,30);
        frame.add(message);

        loginBtn = new JButton("Log in");
        loginBtn.setBackground(new Color(90,173,70));
        loginBtn.setForeground(Color.white);
        loginBtn.setBounds(100,220,200,25);
        loginBtn.addActionListener(this);
        frame.add(loginBtn);

        frame.setVisible(true);

    }

    //for registration
    FinishFrame(String id) {
        frame.getContentPane().setLayout(null);
        frame.setUndecorated(true);
        frame.setSize(400,270);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.white);

        this.id = id;
        image = new JLabel(new ImageIcon("img/success.jpg"));
        image.setBounds(150,20,100,100);
        frame.add(image);

        message1 = new JLabel("Hello,");
        message1.setFont(new Font("Amaranth",Font.BOLD,25));
        message1.setForeground(new Color(90,173,70));
        message1.setBounds(165,120,400,30);
        frame.add(message1);

        message2 = new JLabel("You have been successfully registered!");
        message2.setFont(new Font("Amaranth",Font.BOLD,15));
        message2.setForeground(Color.darkGray);
        message2.setBounds(40,160,400,30);
        frame.add(message2);

        message = new JLabel("Your ID for authentication is " + id + ".");
        message.setFont(new Font("Amaranth",Font.BOLD,15));
        message.setForeground(Color.darkGray);
        message.setBounds(50,190,400,15);
        frame.add(message);

        loginBtn = new JButton("Log in");
        loginBtn.setBackground(new Color(90,173,70));
        loginBtn.setForeground(Color.white);
        loginBtn.setBounds(100,220,200,25);
        loginBtn.addActionListener(this);
        frame.add(loginBtn);

        frame.setVisible(true);


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == loginBtn) {
            frame.dispose();
            Login login = new Login();
        }
    }
}
