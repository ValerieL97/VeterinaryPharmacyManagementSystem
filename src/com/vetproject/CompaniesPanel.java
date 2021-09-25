package com.vetproject;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CompaniesPanel extends JPanel implements ActionListener {
    JLabel nameLabel, addressLabel, phoneLabel,companyLabel, agentLabel,messageLabel,
            nameAgLabel,phoneAgLabel, tableNameLabel, panelNameLabel, emailLabel;
    JButton addBtn, updateBtn, deleteBtn;
    JTextField nameTextField, addressTextField, phoneTextField, emailTextField,
            nameAgTextField, phoneAgField;
    JTable companiesTable;
    JPanel panel;

    CompaniesPanel() {
        this.setBackground(Color.white);
        this.setLayout(null);

        panelNameLabel = new JLabel("Manage Companies");
        panelNameLabel.setBounds(170,20,300,30);
        panelNameLabel.setFont(new Font("Amaranth",Font.BOLD,25));
        panelNameLabel.setForeground(Color.BLACK);

        companyLabel = new JLabel("Company");
        companyLabel.setForeground(Color.BLACK);
        companyLabel.setFont(new Font("Amaranth",Font.BOLD,20));
        companyLabel.setBounds(100, 60,140,30);

        nameLabel = new JLabel("Name: ");
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setFont(new Font("Amaranth",Font.BOLD,15));
        nameLabel.setBounds(20, 100,100,30);

        nameTextField = new JTextField();
        nameTextField.setForeground(Color.BLACK);
        nameTextField.setFont(new Font("Amaranth",Font.BOLD,15));
        nameTextField.setBounds(120, 105,175,20);

        addressLabel = new JLabel("Address: ");
        addressLabel.setForeground(Color.BLACK);
        addressLabel.setFont(new Font("Amaranth",Font.BOLD,15));
        addressLabel.setBounds(20, 180,100,30);

        addressTextField = new JTextField();
        addressTextField.setForeground(Color.BLACK);
        addressTextField.setFont(new Font("Amaranth",Font.BOLD,15));
        addressTextField.setBounds(120, 185,175,20);

        phoneLabel = new JLabel("Phone n.: ");
        phoneLabel.setForeground(Color.BLACK);
        phoneLabel.setFont(new Font("Amaranth",Font.BOLD,15));
        phoneLabel.setBounds(20, 140,100,30);

        phoneTextField = new JTextField();
        phoneTextField.setForeground(Color.BLACK);
        phoneTextField.setFont(new Font("Amaranth",Font.BOLD,15));
        phoneTextField.setBounds(120, 145,175,20);

        agentLabel = new JLabel("Agent");
        agentLabel.setForeground(Color.BLACK);
        agentLabel.setFont(new Font("Amaranth",Font.BOLD,20));
        agentLabel.setBounds(450, 60,100,30);


        nameAgLabel = new JLabel("Name: ");
        nameAgLabel.setForeground(Color.BLACK);
        nameAgLabel.setFont(new Font("Amaranth",Font.BOLD,15));
        nameAgLabel.setBounds(330, 100,100,30);

        nameAgTextField = new JTextField();
        nameAgTextField.setForeground(Color.BLACK);
        nameAgTextField.setFont(new Font("Amaranth",Font.BOLD,15));
        nameAgTextField.setBounds(435, 105,175,20);

        phoneAgLabel = new JLabel("Phone n.: ");
        phoneAgLabel.setForeground(Color.BLACK);
        phoneAgLabel.setFont(new Font("Amaranth",Font.BOLD,15));
        phoneAgLabel.setBounds(330, 140,100,30);

        phoneAgField = new JTextField();
        phoneAgField.setForeground(Color.BLACK);
        phoneAgField.setFont(new Font("Amaranth",Font.BOLD,15));
        phoneAgField.setBounds(435, 145,175,20);

        emailLabel = new JLabel("Email: ");
        emailLabel.setForeground(Color.BLACK);
        emailLabel.setFont(new Font("Amaranth",Font.BOLD,15));
        emailLabel.setBounds(330, 180,100,30);

        emailTextField = new JTextField();
        emailTextField.setForeground(Color.BLACK);
        emailTextField.setFont(new Font("Amaranth",Font.BOLD,15));
        emailTextField.setBounds(435, 185,175,20);

        addBtn = new JButton("ADD");
        addBtn.setForeground(Color.white);
        addBtn.setBackground(new Color(132,173,41));
        addBtn.setBounds(20,240,150,30);
        addBtn.addActionListener(this);

        updateBtn = new JButton("UPDATE");
        updateBtn.setForeground(Color.white);
        updateBtn.setBackground(new Color(132,173,41));
        updateBtn.setBounds(230,240,150,30);
        updateBtn.addActionListener(this);

        deleteBtn = new JButton("DELETE");
        deleteBtn.setForeground(Color.white);
        deleteBtn.setBackground(new Color(132,173,41));
        deleteBtn.setBounds(440,240,150,30);
        deleteBtn.addActionListener(this);

        tableNameLabel = new JLabel("Companies List");
        tableNameLabel.setForeground(Color.BLACK);
        tableNameLabel.setFont(new Font("Amaranth",Font.BOLD,17));
        tableNameLabel.setBounds(220,300,150,20);

        panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setBounds(5,340,620,240);
        panel.setLayout(new BorderLayout());
        //initializes a table, which can't be direct edited
        companiesTable = new JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        //puts data from the database's table in the JTable
        getDataFromDataBase(companiesTable);
        companiesTable.setForeground(Color.BLACK);
        companiesTable.setBackground(Color.white);
        companiesTable.setShowHorizontalLines(true);
        companiesTable.setShowGrid(true);
        companiesTable.setGridColor(Color.black);
        companiesTable.setSelectionBackground(Color.DARK_GRAY);
        companiesTable.setSelectionForeground(Color.white);
        companiesTable.setRowHeight(30);
        companiesTable.setRowSelectionAllowed(true);
        companiesTable.setFillsViewportHeight(true);
        companiesTable.setAutoCreateRowSorter(false);

        //puts all data from the selected row in the text fields
        companiesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                int row = companiesTable.getSelectedRow();
                if(row != -1) {
                    nameTextField.setText((String) companiesTable.getValueAt(companiesTable.getSelectedRow(), 0));
                    phoneTextField.setText((String) companiesTable.getValueAt(companiesTable.getSelectedRow(), 1));
                    addressTextField.setText((String) companiesTable.getValueAt(companiesTable.getSelectedRow(), 2));
                    nameAgTextField.setText((String) companiesTable.getValueAt(companiesTable.getSelectedRow(), 3));
                    phoneAgField.setText((String) companiesTable.getValueAt(companiesTable.getSelectedRow(), 4));
                    emailTextField.setText((String) companiesTable.getValueAt(companiesTable.getSelectedRow(), 5));
                } else {
                    nameTextField.setText("");
                    phoneTextField.setText("");
                    addressTextField.setText("");
                    nameAgTextField.setText("");
                    phoneAgField.setText("");
                    emailTextField.setText("");
                }
            }
        });

        messageLabel = new JLabel();
        messageLabel.setForeground(Color.red);
        messageLabel.setFont(new Font("Ink Free",Font.BOLD,15));
        messageLabel.setBounds(160,280,400,15);
        this.add(messageLabel);

        panel.add(new JScrollPane(companiesTable));
        this.add(panel);
        this.add(deleteBtn);
        this.add(updateBtn);
        this.add(addBtn);
        this.add(panelNameLabel);
        this.add(tableNameLabel);
        this.add(agentLabel);
        this.add(phoneAgField);
        this.add(phoneAgLabel);
        this.add(nameAgLabel);
        this.add(nameAgTextField);
        this.add(emailLabel);
        this.add(emailTextField);
        this.add(companyLabel);
        this.add(addressLabel);
        this.add(addressTextField);
        this.add(phoneLabel);
        this.add(phoneTextField);
        this.add(nameLabel);
        this.add(nameTextField);


        this.setVisible(true);



    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == addBtn){
            if(verifyExistingValues()) {
                addToDataBase();
                addRowToTable();
            } else {
                messageLabel.setText("This company already exists.");
            }
        }

        if(e.getSource() == updateBtn) {
            updateDataFromDatabase();
            updateTable();
        }

        if(e.getSource() == deleteBtn) {
            deleteRowFromDatabase();
            deleteRowFromTable();
        }


    }

    public void getDataFromDataBase(JTable table) {
        DataBaseConnection connection = new DataBaseConnection();
        Connection connectDB = connection.getConnection();
        String[] columns = {"Name","PhoneNum.","Address","Agent","AgentEmail",
                "AgentPhoneNum"};

        DefaultTableModel model = new DefaultTableModel(columns, 0);
        String stm = "SELECT * FROM CompaniesTable";
        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(stm);

            while(queryResult.next()) {
                String m1 = queryResult.getString("ComName");
                String m2 = queryResult.getString("ComPhone");
                String m3 = queryResult.getString("ComAddress");
                String m4 = queryResult.getString("AgName");
                String m5 = queryResult.getString("AgPhone");
                String m6 = queryResult.getString("AgEmail");
                model.addRow(new Object[]{m1,m2,m3,m4,m5,m6});
                table.setModel(model);
            }
            connectDB.close();
        }catch(Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    //verifies if the company already exists  in database
    public boolean verifyExistingValues() {

        DataBaseConnection connection = new DataBaseConnection();
        Connection connectDB = connection.getConnection();

        String str = "SELECT count(1) FROM CompaniesTable WHERE ComName = '" + nameTextField.getText() + "'";

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

    //adds a row in the database's table
    public void addToDataBase() {
        DataBaseConnection connection = new DataBaseConnection();
        Connection connectDB = connection.getConnection();

        String comName = nameTextField.getText();
        String comPhone = phoneTextField.getText();
        String comAddress = addressTextField.getText();
        String agent = nameAgTextField.getText();
        String agentPhone = phoneAgField.getText();
        String agentEmail = emailTextField.getText();

        String fields = "INSERT INTO CompaniesTable (ComName, " +
                "ComPhone, ComAddress, AgName, AgPhone, AgEmail) VALUES ('";
        String values =  comName + "', '" + comPhone +"', '" + comAddress + "', '" +
                agent +"', '" + agentPhone +"', '" + agentEmail +"')";

        String valuesToRegister = fields + values;

        try {
            Statement stDB = connectDB.createStatement();
            stDB.executeUpdate(valuesToRegister);
            connectDB.close();

        } catch(Exception e){
            e.printStackTrace();
            e.getCause();

        }

    }

    // adds a new row to companiesTable
    public void addRowToTable() {
        String comName = nameTextField.getText();
        String comPhone = phoneTextField.getText();
        String comAddress = addressTextField.getText();
        String agent = nameAgTextField.getText();
        String agentPhone = phoneAgField.getText();
        String agentEmail = emailTextField.getText();

        Object[] row = { comName, comPhone, comAddress, agent,agentPhone,agentEmail};
        DefaultTableModel model = (DefaultTableModel) companiesTable.getModel();
        model.addRow(row);
    }

    //updates the selected row from the database's table
    public void updateDataFromDatabase() {
        DataBaseConnection connection = new DataBaseConnection();
        Connection connectDB = connection.getConnection();

        String comName = nameTextField.getText();
        String comPhone = phoneTextField.getText();
        String comAddress = addressTextField.getText();
        String agent = nameAgTextField.getText();
        String agentPhone = phoneAgField.getText();
        String agentEmail = emailTextField.getText();


        String fields = "UPDATE CompaniesTable SET ComName = '" + comName + "', ComPhone = '" + comPhone +
                "', ComAddress = '" + comAddress + "', AgName = '"  + agent + "', AgPhone = '" + agentPhone +
                "', AgEmail = '" + agentEmail + "' where id =" + getID() + ";";


        try {
            Statement stDB = connectDB.createStatement();
            stDB.executeUpdate(fields);
            connectDB.close();

        } catch(Exception e){
            e.printStackTrace();
            e.getCause();

        }

    }

    //gets the selected company's id
    public int getID() {
        DataBaseConnection connection = new DataBaseConnection();
        Connection connectDB = connection.getConnection();
        String mName = (String) companiesTable.getValueAt(companiesTable.getSelectedRow(),0);
        String stm = "SELECT id FROM CompaniesTable WHERE ComName ='" + mName + "';";
        int  m1 = 0;
        try {
            Statement statement = connectDB.createStatement();
            ResultSet  queryResult = statement.executeQuery(stm);

            while(queryResult.next()) {
                m1 = queryResult.getInt("id");
            }
            connectDB.close();
        }catch(Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        return m1;
    }

    //updates the selected row from the table
    public void updateTable() {

        String comName = nameTextField.getText();
        String comPhone = phoneTextField.getText();
        String comAddress = addressTextField.getText();
        String agent = nameAgTextField.getText();
        String agentPhone = phoneAgField.getText();
        String agentEmail = emailTextField.getText();

        companiesTable.getModel().setValueAt(comName, companiesTable.getSelectedRow(),0);
        companiesTable.getModel().setValueAt(comPhone, companiesTable.getSelectedRow(),1);
        companiesTable.getModel().setValueAt(comAddress, companiesTable.getSelectedRow(),2);
        companiesTable.getModel().setValueAt(agent, companiesTable.getSelectedRow(),3);
        companiesTable.getModel().setValueAt(agentPhone, companiesTable.getSelectedRow(),4);
        companiesTable.getModel().setValueAt(agentEmail, companiesTable.getSelectedRow(),5);

    }

    //deletes the selected row from the table
    public void deleteRowFromTable() {
        if (companiesTable.getSelectedRowCount() == 1) {
            DefaultTableModel model = (DefaultTableModel) companiesTable.getModel();
            model.removeRow(companiesTable.getSelectedRow());
        } else if(companiesTable.getSelectedRowCount() == 0) {
            messageLabel.setText("Please select a row for delete!");
        }
    }

    //deletes the selected row from the database's table
    public void deleteRowFromDatabase() {

        DataBaseConnection connection = new DataBaseConnection();
        Connection connectDB = connection.getConnection();

        String str = "DELETE FROM CompaniesTable WHERE id =" + getID() + ";";

        try{
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(str);
            connectDB.close();
        }catch(Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }

}
