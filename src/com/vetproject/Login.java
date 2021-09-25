package com.vetproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Login implements ActionListener {

    JFrame frame;
    JLabel image, lock, userLabel, passwordLabel, messageLabel;
    JButton loginBtn, registerBtn, resetBtn, exitBtn;
    JTextField userTextField;
    JPasswordField passwordField;


    Login() {

        frame = new JFrame();
        frame.getContentPane().setLayout(null);
        frame.setUndecorated(true);
        frame.setSize(500,300);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(132,173,41));

        image = new JLabel(new ImageIcon("img/login.jpg"));
        lock = new JLabel(new ImageIcon("img/l.png"));
        image.setBounds(0,0,200,300);
        lock.setBounds(330,20,50,50);
        frame.add(image);
        frame.add(lock);

        loginBtn = new JButton("Login");
        loginBtn.setBackground(Color.white);
        loginBtn.setForeground(new Color(132,173,41));
        loginBtn.setFont(new Font("Ink Free",Font.BOLD,15));
        loginBtn.setBounds(220,200,120,30);
        loginBtn.addActionListener(this);
        frame.add(loginBtn);

        registerBtn = new JButton("Register");
        registerBtn.setBackground(Color.white);
        registerBtn.setForeground(new Color(132,173,41));
        registerBtn.setFont(new Font("Ink Free",Font.BOLD,15));
        registerBtn.setBounds(370,200,120,30);
        registerBtn.addActionListener(this);
        frame.add(registerBtn);

        resetBtn = new JButton("Reset password");
        resetBtn.setBackground(new Color(132,173,41));
        resetBtn.setForeground(Color.white);
        resetBtn.setFont(new Font("Ink Free",Font.BOLD,12));
        resetBtn.setBounds(250,240,200,30);
        resetBtn.addActionListener(this);
        resetBtn.setBorder(BorderFactory.createLineBorder(new Color(132,173,41)));
        frame.add(resetBtn);

        userTextField = new JTextField();
        userTextField.setBackground(new Color(132,173,41));
        userTextField.setForeground(Color.white);
        userTextField.setFont(new Font("Ink Free",Font.BOLD,12));
        userTextField.setBounds(300,100,150,20);
        frame.add(userTextField);

        passwordField = new JPasswordField();
        passwordField.setBackground(new Color(132,173,41));
        passwordField.setForeground(Color.white);
        passwordField.setFont(new Font("Ink Free",Font.BOLD,12));
        passwordField.setBounds(300,150,150,20);
        frame.add(passwordField);

        userLabel = new JLabel("ID");
        userLabel.setForeground(Color.white);
        userLabel.setFont(new Font("Ink Free",Font.BOLD,12));
        userLabel.setBounds(270, 100,100,20);
        frame.add(userLabel);

        passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.white);
        passwordLabel.setFont(new Font("Ink Free",Font.BOLD,12));
        passwordLabel.setBounds(220, 150,100,20);
        frame.add(passwordLabel);

        exitBtn = new JButton("x");
        exitBtn.setForeground(Color.white);
        exitBtn.setBackground(new Color(132,173,41));
        exitBtn.setFont(new Font("Ink Free",Font.BOLD,20));
        exitBtn.setBounds(450, 7,55,20);
        exitBtn.addActionListener(this);
        exitBtn.setBorder(BorderFactory.createLineBorder(new Color(132,173,41)));
        frame.add(exitBtn);

        messageLabel = new JLabel();
        messageLabel.setForeground (new Color (182,37,37));
        messageLabel.setFont(new Font("Ink Free",Font.BOLD,12));
        messageLabel.setBounds(220, 75,400,15);
        frame.add(messageLabel);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //checks if password's field and id's field aren't empty
        if(e.getSource() == loginBtn && userTextField.getText().isEmpty() ||
                passwordField.getPassword().length ==0) {
            messageLabel.setText("Please enter username and password");
            userTextField.setBorder(BorderFactory.createLineBorder(new Color(182,37,37)));
            passwordField.setBorder(BorderFactory.createLineBorder(new Color(182,37,37)));
        } else if(e.getSource() == loginBtn){
            //encrypts the password
            String pass_word = PasswordSecurity.encrypt(new String(passwordField.getPassword()));
            String log_in = userTextField.getText();
            //verifies if id and password exist in the database
            validateLogin(log_in,pass_word);
        }
        if(e.getSource() == exitBtn) {
            frame.dispose();
        }
        if(e.getSource() == registerBtn) {
            //on btn click calls the registration frame
            RegistrationForm r = new RegistrationForm();
            frame.dispose();
        }
        if(e.getSource() == resetBtn) {
            //on btn click calls the resetPassword frame
            ResetPassword rp = new ResetPassword();
            frame.dispose();
        }
    }

    //checks if password and login are in database
    private void validateLogin(String log_in, String pass_word) {
        DataBaseConnection connection = new DataBaseConnection();
        Connection connectDB = connection.getConnection();

        String verifyLogin = "SELECT count(1) FROM registration WHERE autogeneratedid = '" + log_in +
                "'AND password ='" + pass_word + "'";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet  queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()) {
                if(queryResult.getInt(1) == 1) {
                    Menu menu = new Menu();
                    frame.dispose();
                } else {
                    //if there isn't a correct password or id,
                    // then it prints a message and set a red border to text fields
                    messageLabel.setText("Try again! Invalid login or password!");
                    userTextField.setBorder(BorderFactory.createLineBorder(new Color(182,37,37)));
                    passwordField.setBorder(BorderFactory.createLineBorder(new Color(182,37,37)));
                }
            }
            connectDB.close();
        }catch(Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
