package com.vetproject;



import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.Random;

public class ResetPassword implements ActionListener {

    JFrame frame;
    JPanel panel;
    JLabel title, idLabel, emailLabel, otpLabel, messageLabel;
    JTextField emailTextField, idTextField, otpTextField;
    JButton firstBtn, secondBtn,backBtn,exitBtn;
    Random random = new Random();
    String code = "";
    static String id = "";

    ResetPassword() {
        frame = new JFrame();
        frame = new JFrame();
        frame.getContentPane().setLayout(null);
        frame.setUndecorated(true);
        frame.setSize(400,320);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.white);
        frame.setLayout(null);

        title = new JLabel("Reset password");
        panel = new JPanel();
        panel.setBounds(0,0,400,40);
        panel.setBackground(Color.white);
        panel.setLayout(null);
        title.setForeground(new Color(132,173,41));
        title.setFont(new Font("Ink Free",Font.BOLD,20));
        title.setBounds(125,10,200,20);

        backBtn = new JButton(new ImageIcon("backbtn.jpg"));
        backBtn.setBounds(10,10,24,23);
        backBtn.setBorder(BorderFactory.createLineBorder(Color.white));
        backBtn.addActionListener(this);

        exitBtn = new JButton(new ImageIcon("exitbtn.jpg"));
        exitBtn.setBounds(370,10,23,23);
        exitBtn.setBorder(BorderFactory.createLineBorder(Color.white));
        exitBtn.addActionListener(this);

        panel.add(title);
        panel.add(exitBtn);
        panel.add(backBtn);
        frame.add(panel);

        idLabel = new JLabel("ID");
        idTextField = new JTextField();
        idLabel.setBounds(200,60,200,15);
        idLabel.setFont(new Font("Ink Free",Font.BOLD,15));
        idLabel.setForeground(Color.BLACK);
        idTextField.setBounds(80,80,250,18);
        idTextField.setForeground(Color.BLACK);
        frame.add(idLabel);
        frame.add(idTextField);

        emailLabel = new JLabel("Email Address");
        emailTextField = new JTextField();
        emailLabel.setBounds(150,110,200,15);
        emailLabel.setFont(new Font("Ink Free",Font.BOLD,15));
        emailLabel.setForeground(Color.BLACK);
        emailTextField.setBounds(80,130,250,18);
        emailTextField.setForeground(Color.BLACK);
        frame.add(emailLabel);
        frame.add( emailTextField);

        firstBtn = new JButton("Verify Details");
        firstBtn.setBounds(130,160,150,22);
        firstBtn.setForeground(Color.white);
        firstBtn.setBackground(new Color(132,173,41));
        firstBtn.addActionListener(this);
        frame.add(firstBtn);

        otpLabel = new JLabel("Enter Code");
        otpTextField = new JTextField();
        otpLabel.setBounds(160,200,100,15);
        otpLabel.setFont(new Font("Ink Free",Font.BOLD,15));
        otpLabel.setForeground(Color.lightGray);
        otpTextField.setBounds(80,220,250,18);
        otpTextField.setForeground(Color.BLACK);
        otpTextField.setEnabled(false);
        frame.add(otpLabel);
        frame.add(otpTextField);

        secondBtn = new JButton("Verify Code");
        secondBtn.setBounds(130,250,150,22);
        secondBtn.setForeground(Color.black);
        secondBtn.setBackground(Color.lightGray);
        secondBtn.setEnabled(false);
        secondBtn.addActionListener(this);
        frame.add(secondBtn);

        messageLabel = new JLabel();
        messageLabel.setForeground (new Color (182,37,37));
        messageLabel.setFont(new Font("Ink Free",Font.BOLD,15));
        messageLabel.setBounds(90, 40,400,15);
        frame.add(messageLabel);

        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
         if(e.getSource() == exitBtn) {
             frame.dispose();
         }
         if(e.getSource() == backBtn) {
             frame.dispose();
             Login log = new Login();
         }
         if(e.getSource() == firstBtn){
             //if id exists in the database then it sends an email with the verification code,
             // the disabled fields become enable and
             // the enabled fields become disable
             if(verifyExistingId()) {
                 id = idTextField.getText();
                 setCode();
                 sendEmail(code, emailTextField);
                 otpTextField.setEnabled(true);
                 otpLabel.setForeground(Color.black);
                 secondBtn.setEnabled(true);
                 secondBtn.setBackground(new Color(132, 173, 41));
                 secondBtn.setForeground(Color.white);
                 idLabel.setForeground(Color.lightGray);
                 emailLabel.setForeground(Color.lightGray);
                 idTextField.setEnabled(false);
                 emailTextField.setEnabled(false);
                 firstBtn.setEnabled(false);
                 firstBtn.setForeground(Color.black);
                 firstBtn.setBackground(Color.lightGray);
             }  else {
                 messageLabel.setText("Try again! Invalid email or id!");
                 emailTextField.setBorder(BorderFactory.createLineBorder(new Color(182, 37, 37)));
                 idTextField.setBorder(BorderFactory.createLineBorder(new Color(182, 37, 37)));
             }
         }

         //checks if the introduced code matches the verification code
         if(e.getSource() == secondBtn && !otpTextField.getText().equals(code)) {
             messageLabel.setText("Try again! Invalid code!");
             otpTextField.setBorder(BorderFactory.createLineBorder(new Color(182,37,37)));
         } else if (e.getSource() == secondBtn && otpTextField.getText().equals(code)) {
             frame.dispose();
             NewPassword ps = new NewPassword();
         }
    }

    //checks if id exists in the database
    public boolean verifyExistingId() {

        DataBaseConnection connection = new DataBaseConnection();
        Connection connectDB = connection.getConnection();

        String str = "SELECT count(1) FROM registration WHERE autogeneratedid = '" + idTextField.getText() +
                "' and email = '" + emailTextField.getText() + "'";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryR1 = statement.executeQuery(str);

            while(queryR1.next()) {
                if(queryR1.getInt(1) == 1) {
                    return true;
                }
            }
            connectDB.close();
        }catch(Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        return false;
    }

    // sends an email to the indicated address with the verification code
    public void sendEmail(String str, JTextField email) {
        String to = email.getText();
        String from = "email";
        String password = "password";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Password Reset E-mail");

            String msg = "Hello from Felix Pharmacy! The code to reset your password is " + str +"!";

            message.setContent(msg,"text/html");

            Transport.send(message);

            System.out.println("Mail successfully sent..");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //sets a random 4 digits code
    public void setCode() {
        int id = random.nextInt(9999);
        code = String.format("%04d", id);
    }

    public static String getID() {
        return id;

    }
}
