package com.vetproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class RegistrationForm implements ActionListener, ItemListener {

    JFrame frame;
    JPanel panel;
    JLabel titleLabel, firstnameLabel, lastnameLabel, ssnLabel, addressLabel, phoneNumberLabel, emailLabel,
            jobPositionLabel, dateBirthLabel, passwordLabel, confPasswordLabel, messageLabel;
    JTextField firstnameTextField, lastnameTextField, ssnTextField, addressTextField, phoneNumberTextField,
            emailTextField, dateBirthTextField;
    JPasswordField passwordField, confPasswordField;
    JButton backBtn, exitBtn, regBtn;

    String[] list = { "Veterinarian","Veterinary Assistant","Veterinary Technician",
            "Animal Care and Service Workers","Veterinary Pharmacist"};
    JComboBox<String> listJobPositions;
    Random random = new Random();
    static String id = "";

    RegistrationForm() {
        frame = new JFrame();
        frame.getContentPane().setLayout(null);
        frame.setUndecorated(true);
        frame.setSize(400,440);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.white);

        panel = new JPanel();
        panel.setLayout(null);
        titleLabel = new JLabel("Registration");
        titleLabel.setForeground(new Color(132,173,41));
        titleLabel.setFont(new Font("Ink Free",Font.BOLD,20));
        titleLabel.setBounds(130,15,200,25);

        backBtn = new JButton(new ImageIcon("img/backbtn.jpg"));
        backBtn.setBounds(10,10,23,23);
        backBtn.setBorder(BorderFactory.createLineBorder(Color.white));
        backBtn.addActionListener(this);

        exitBtn = new JButton(new ImageIcon("img/exitbtn.jpg"));
        exitBtn.setBounds(370,10,23,23);
        exitBtn.setBorder(BorderFactory.createLineBorder(Color.white));
        exitBtn.addActionListener(this);

        panel.setBackground(Color.white);
        panel.setBounds(0,0,400,40);
        panel.add(exitBtn);
        panel.add(backBtn);
        panel.add(titleLabel);
        frame.add(panel);

        firstnameLabel = new JLabel("First Name:");
        firstnameLabel.setForeground(Color.black);
        firstnameLabel.setFont(new Font("Ink Free",Font.BOLD,12));
        firstnameLabel.setBounds(10,60,100,15);
        firstnameTextField = new JTextField();
        firstnameTextField.setForeground(Color.BLACK);
        firstnameTextField.setBounds(10,80,180,20);
        frame.add(firstnameTextField);
        frame.add(firstnameLabel);

        lastnameLabel = new JLabel("Last Name:");
        lastnameLabel.setForeground(Color.black);
        lastnameLabel.setFont(new Font("Ink Free",Font.BOLD,12));
        lastnameLabel.setBounds(210,60,100,15);
        lastnameTextField = new JTextField();
        lastnameTextField.setForeground(Color.BLACK);
        lastnameTextField.setBounds(210,80,180,20);
        frame.add(lastnameTextField);
        frame.add(lastnameLabel);

        ssnLabel = new JLabel("Social Security Number:");
        ssnLabel.setForeground(Color.black);
        ssnLabel.setFont(new Font("Ink Free",Font.BOLD,12));
        ssnLabel.setBounds(10,110,200,15);
        ssnTextField = new JTextField();
        ssnTextField.setForeground(Color.BLACK);
        ssnTextField.setBounds(10,130,180,20);
        frame.add(ssnTextField);
        frame.add(ssnLabel);

        addressLabel = new JLabel("Address:");
        addressLabel.setForeground(Color.black);
        addressLabel.setFont(new Font("Ink Free",Font.BOLD,12));
        addressLabel.setBounds(10,160,100,15);
        addressTextField = new JTextField();
        addressTextField.setForeground(Color.BLACK);
        addressTextField.setBounds(10,180,380,20);
        frame.add(addressTextField);
        frame.add(addressLabel);

        emailLabel = new JLabel("Email Address:");
        emailLabel.setForeground(Color.black);
        emailLabel.setFont(new Font("Ink Free",Font.BOLD,12));
        emailLabel.setBounds(10,210,200,15);
        emailTextField = new JTextField();
        emailTextField.setForeground(Color.BLACK);
        emailTextField.setBounds(10,230,180,20);
        frame.add(emailTextField);
        frame.add(emailLabel);

        phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberLabel.setForeground(Color.black);
        phoneNumberLabel.setFont(new Font("Ink Free",Font.BOLD,12));
        phoneNumberLabel.setBounds(210,210,200,15);
        phoneNumberTextField = new JTextField();
        phoneNumberTextField.setForeground(Color.BLACK);
        phoneNumberTextField.setBounds(210,230,180,20);
        frame.add(phoneNumberTextField);
        frame.add(phoneNumberLabel);

        dateBirthLabel = new JLabel("Date of birth (mm/dd/yyyy):");
        dateBirthLabel.setForeground(Color.black);
        dateBirthLabel.setFont(new Font("Ink Free",Font.BOLD,11));
        dateBirthLabel.setBounds(210,110,200,15);
        dateBirthTextField = new JTextField();
        dateBirthTextField.setForeground(Color.BLACK);
        dateBirthTextField.setBounds(210,130,180,20);
        frame.add(dateBirthTextField);
        frame.add(dateBirthLabel);

        jobPositionLabel = new JLabel("Job Position:");
        jobPositionLabel.setForeground(Color.black);
        jobPositionLabel.setFont(new Font("Ink Free",Font.BOLD,12));
        jobPositionLabel.setBounds(10,260,200,15);
        listJobPositions = new JComboBox<>(list);
        listJobPositions.setBackground(Color.white);
        listJobPositions.setForeground(Color.black);
        listJobPositions.setBounds(10,280,380,20);
        listJobPositions.addItemListener(this);
        frame.add(jobPositionLabel);
        frame.add(listJobPositions);

        passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.black);
        passwordLabel.setFont(new Font("Ink Free",Font.BOLD,12));
        passwordLabel.setBounds(10,320,200,15);
        passwordField = new JPasswordField();
        passwordField.setForeground(Color.BLACK);
        passwordField.setBounds(10,340,180,20);
        frame.add(passwordField);
        frame.add(passwordLabel);

        confPasswordLabel = new JLabel("Confirm Password:");
        confPasswordLabel.setForeground(Color.black);
        confPasswordLabel.setFont(new Font("Ink Free",Font.BOLD,12));
        confPasswordLabel.setBounds(210,320,200,15);
        confPasswordField = new JPasswordField();
        confPasswordField.setForeground(Color.BLACK);
        confPasswordField.setBounds(210,340,180,20);
        frame.add(confPasswordField);
        frame.add(confPasswordLabel);

        regBtn = new JButton("Register");
        regBtn.setBounds(100,380,200,30);
        regBtn.setBackground(new Color(132,173,41));
        regBtn.setForeground(Color.white);
        regBtn.addActionListener(this);
        frame.add(regBtn);

        messageLabel = new JLabel();
        messageLabel.setForeground (new Color (182,37,37));
        messageLabel.setFont(new Font("Ink Free",Font.BOLD,15));
        messageLabel.setBounds(50, 40,400,15);
        frame.add(messageLabel);

        frame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == exitBtn) {
            frame.dispose();
        }
        if(e.getSource() == backBtn) {
            Login login = new Login();
            frame.dispose();
        }
        //on click regBtn verifies if password and password confirmation match
        //if passwords don't match, prints a message and sets red borders
        if(e.getSource() == regBtn && !new String(passwordField.getPassword()).equals(new String(confPasswordField.getPassword()))) {
            messageLabel.setText("Try again! Passwords do not match!");
            passwordField.setBorder(BorderFactory.createLineBorder(new Color(182,37,37)));
            confPasswordField.setBorder(BorderFactory.createLineBorder(new Color(182,37,37)));
        // verifies if there isn't an empty field
        } else if(e.getSource() == regBtn && (firstnameTextField.getText().isEmpty() ||
                lastnameTextField.getText().isEmpty()|| ssnTextField.getText().isEmpty() ||
                dateBirthTextField.getText().isEmpty()|| addressTextField.getText().isEmpty() ||
                emailTextField.getText().isEmpty()|| phoneNumberTextField.getText().isEmpty() ||
                passwordField.getPassword().length == 0 || confPasswordField.getPassword().length == 0)) {
                messageLabel.setText("Try again! Please complete all fields!");
        } else if (e.getSource() == regBtn){
            //check if in database already exists nin, email and phone number
            setId();
            if (!verifyExistingValues("nin",ssnTextField.getText())) {
                messageLabel.setText("This NIN already exists!");
                ssnTextField.setBorder(BorderFactory.createLineBorder(new Color(182,37,37)));
            } else if(!verifyExistingValues("email",emailTextField.getText())) {
                messageLabel.setText("This email address already exists!");
                ssnTextField.setBorder(BorderFactory.createLineBorder(new Color(182,37,37)));
            } else if(!verifyExistingValues("phonenumber",phoneNumberTextField.getText())) {
                messageLabel.setText("This phone number already exists!");
                ssnTextField.setBorder(BorderFactory.createLineBorder(new Color(182,37,37)));
            } else {
                userRegistration();
            }

        }


    }

    //sets a random six digits number as id
    public void setId() {
        boolean isTrue = true;
        String id_string = "";

        do {
            int id = random.nextInt(999999);
            id_string = String.format("%06d", id);
            if(verifyExistingValues("autogeneratedid",id_string)) {
                isTrue = false;
            }

        } while (isTrue);
        id = id_string;
    }

    //connects to database
    //verifies if passed value already exists in database
    //if it is found at least once, then it returns false
    public boolean verifyExistingValues(String column, String value) {

        DataBaseConnection connection = new DataBaseConnection();
        Connection connectDB = connection.getConnection();

        String str = "SELECT count(1) FROM registration WHERE " +
                column + "= '" + value + "'";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryR1 = statement.executeQuery(str);

            while(queryR1.next()) {
                if(queryR1.getInt(1) == 1) {
                    return false;
                }
            }
            connectDB.close();
        }catch(Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        return true;
    }
    // saves the user in database
    public void userRegistration() {
        DataBaseConnection connection = new DataBaseConnection();
        Connection connectDB = connection.getConnection();

        String phoneNumber = phoneNumberTextField.getText();
        String firstName = firstnameTextField.getText();
        String lastName = lastnameTextField.getText();
        String dateBirth = dateBirthTextField.getText();
        String address = addressTextField.getText();
        String ssn = ssnTextField.getText();
        String email = emailTextField.getText();
        String passWord = PasswordSecurity.encrypt(new String(passwordField.getPassword()));
        String job = String.valueOf(listJobPositions.getSelectedItem());


        String fields = "INSERT INTO registration (firstname, " +
                "lastname, nin, dateofbirth, address, email, phonenumber, jobPosition, autogeneratedid,password) VALUES ('";
        String values =  firstName + "', '" + lastName +"', '" + ssn + "', '" +
                dateBirth +"', '" + address +"', '" + email + "', '" + phoneNumber + "', '" + job + "', '"
                + id + "', '" + passWord +"')";

        String valuesToRegister = fields + values;

        try {
            Statement stDB = connectDB.createStatement();
            stDB.executeUpdate(valuesToRegister);
            connectDB.close();
            finishRegistration();

        } catch(Exception e){
            e.printStackTrace();
            e.getCause();

        }
    }

    //calls the finish frame with user's id
    public void finishRegistration() {
        frame.dispose();
        FinishFrame frf = new FinishFrame(id);
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {

    }

}
