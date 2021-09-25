package com.vetproject;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

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
import java.util.ArrayList;
import java.util.Properties;

public class MedicinePanel extends JPanel implements ActionListener {
    JLabel nameLabel, priceLabel, quantityLabel, sideEffectsLabel, messageLabel,
            fabDateLabel,expDateLabel, companyLabel, tableNameLabel, panelNameLabel, descriptionLabel;
    JButton addBtn, updateBtn, deleteBtn;
    JTextField nameTextField, priceTextField, quantityTextField, sideEffectsTextField, descriptionTextField;
    JTable medicineTable;
    JPanel panel;

    UtilDateModel model, model1;
    Properties p, p2;
    JDatePanelImpl fabDatePanel, expDatePanel;
    JComboBox<String> listCompanies;

    MedicinePanel() {
        this.setBackground(Color.white);
        this.setLayout(null);

        panelNameLabel = new JLabel("Manage Medication");
        panelNameLabel.setBounds(170, 20, 300, 30);
        panelNameLabel.setFont(new Font("Amaranth", Font.BOLD, 25));
        panelNameLabel.setForeground(Color.BLACK);

        nameLabel = new JLabel("Name: ");
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setFont(new Font("Amaranth", Font.BOLD, 15));
        nameLabel.setBounds(20, 60, 100, 30);

        nameTextField = new JTextField();
        nameTextField.setForeground(Color.BLACK);
        nameTextField.setFont(new Font("Amaranth", Font.BOLD, 15));
        nameTextField.setBounds(120, 65, 175, 20);

        priceLabel = new JLabel("Price: ");
        priceLabel.setForeground(Color.BLACK);
        priceLabel.setFont(new Font("Amaranth", Font.BOLD, 15));
        priceLabel.setBounds(20, 100, 100, 30);

        priceTextField = new JTextField();
        priceTextField.setForeground(Color.BLACK);
        priceTextField.setFont(new Font("Amaranth", Font.BOLD, 15));
        priceTextField.setBounds(120, 105, 175, 20);

        quantityLabel = new JLabel("Quantity: ");
        quantityLabel.setForeground(Color.BLACK);
        quantityLabel.setFont(new Font("Amaranth", Font.BOLD, 15));
        quantityLabel.setBounds(20, 140, 100, 30);

        quantityTextField = new JTextField();
        quantityTextField.setForeground(Color.BLACK);
        quantityTextField.setFont(new Font("Amaranth", Font.BOLD, 15));
        quantityTextField.setBounds(120, 145, 175, 20);

        descriptionLabel = new JLabel("Description: ");
        descriptionLabel.setForeground(Color.BLACK);
        descriptionLabel.setFont(new Font("Amaranth", Font.BOLD, 15));
        descriptionLabel.setBounds(20, 180, 120, 30);

        descriptionTextField = new JTextField();
        descriptionTextField.setForeground(Color.BLACK);
        descriptionTextField.setFont(new Font("Amaranth", Font.BOLD, 15));
        descriptionTextField.setBounds(120, 185, 175, 20);

        fabDateLabel = new JLabel("FabDate: ");
        fabDateLabel.setForeground(Color.BLACK);
        fabDateLabel.setFont(new Font("Amaranth", Font.BOLD, 15));
        fabDateLabel.setBounds(330, 60, 100, 30);

        model = new UtilDateModel();
        model.setSelected(true);
        p = new Properties();
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        fabDatePanel = new JDatePanelImpl(model, p);
        //datePanel.setBackground(new Color(132,173,41));
        for (Component comp : fabDatePanel.getComponents()) {
            for (Component c : ((Container) comp).getComponents()) {
                c.setBackground(Color.lightGray);
            }
        }
        fabDatePanel.setFont(new Font("Amaranth", Font.BOLD, 15));
        fabDatePanel.setBounds(435, 65, 175, 25);

        expDateLabel = new JLabel("ExpDate: ");
        expDateLabel.setForeground(Color.BLACK);
        expDateLabel.setFont(new Font("Amaranth", Font.BOLD, 15));
        expDateLabel.setBounds(330, 100, 100, 30);

        model1 = new UtilDateModel();
        model1.setSelected(true);
        p2 = new Properties();
        p2.put("text.month", "Month");
        p2.put("text.year", "Year");

        expDatePanel = new JDatePanelImpl(model1,p2);
        for (Component comp : expDatePanel.getComponents()) {
            for (Component c : ((Container) comp).getComponents()) {
                c.setBackground(Color.lightGray);
            }
        }
        expDatePanel.setFont(new Font("Amaranth", Font.BOLD, 15));
        expDatePanel.setBounds(435, 105, 175, 25);

        companyLabel = new JLabel("Company: ");
        companyLabel.setForeground(Color.BLACK);
        companyLabel.setFont(new Font("Amaranth", Font.BOLD, 15));
        companyLabel.setBounds(330, 140, 100, 30);

        String[] array = getCompanies().toArray(new String[0]);
        listCompanies = new JComboBox<>(array);
        listCompanies.setForeground(Color.BLACK);
        listCompanies.setFont(new Font("Amaranth", Font.BOLD, 15));
        listCompanies.setBounds(435, 145, 175, 20);

        sideEffectsLabel = new JLabel("Side Effects: ");
        sideEffectsLabel.setForeground(Color.BLACK);
        sideEffectsLabel.setFont(new Font("Amaranth", Font.BOLD, 15));
        sideEffectsLabel.setBounds(330, 180, 120, 30);

        sideEffectsTextField = new JTextField();
        sideEffectsTextField.setForeground(Color.BLACK);
        sideEffectsTextField.setFont(new Font("Amaranth", Font.BOLD, 15));
        sideEffectsTextField.setBounds(435, 185, 175, 20);

        addBtn = new JButton("ADD");
        addBtn.setForeground(Color.white);
        addBtn.setBackground(new Color(132, 173, 41));
        addBtn.setBounds(20, 240, 150, 30);
        addBtn.addActionListener(this);

        updateBtn = new JButton("UPDATE");
        updateBtn.setForeground(Color.white);
        updateBtn.setBackground(new Color(132, 173, 41));
        updateBtn.setBounds(230, 240, 150, 30);
        updateBtn.addActionListener(this);

        deleteBtn = new JButton("DELETE");
        deleteBtn.setForeground(Color.white);
        deleteBtn.setBackground(new Color(132, 173, 41));
        deleteBtn.setBounds(440, 240, 150, 30);
        deleteBtn.addActionListener(this);

        tableNameLabel = new JLabel("Medicine List");
        tableNameLabel.setForeground(Color.BLACK);
        tableNameLabel.setFont(new Font("Amaranth", Font.BOLD, 17));
        tableNameLabel.setBounds(220, 300, 150, 20);

        panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setBounds(5, 340, 620, 240);
        panel.setLayout(new BorderLayout());

        medicineTable = new JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        getDataFromDataBase(medicineTable);
        JScrollPane sp = new JScrollPane(medicineTable);
        medicineTable.setBounds(10, 360, 600, 100);
        medicineTable.setSize(630, 100);
        medicineTable.setForeground(Color.BLACK);
        medicineTable.setBackground(Color.white);
        medicineTable.setShowHorizontalLines(true);
        medicineTable.setShowGrid(true);
        medicineTable.setGridColor(Color.black);
        medicineTable.setSelectionBackground(Color.DARK_GRAY);
        medicineTable.setSelectionForeground(Color.white);
        medicineTable.setRowHeight(30);
        medicineTable.setRowSelectionAllowed(true);
        medicineTable.setAutoCreateRowSorter(false);

        //puts all data from the selected row in the text fields
        medicineTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                int row = medicineTable.getSelectedRow();
                if(row != -1) {
                    nameTextField.setText((String) medicineTable.getValueAt(medicineTable.getSelectedRow(), 0));
                    priceTextField.setText((String) medicineTable.getValueAt(medicineTable.getSelectedRow(), 1));
                    quantityTextField.setText((String) medicineTable.getValueAt(medicineTable.getSelectedRow(), 2));
                    descriptionTextField.setText((String) medicineTable.getValueAt(medicineTable.getSelectedRow(), 3));
                    sideEffectsTextField.setText((String) medicineTable.getValueAt(medicineTable.getSelectedRow(), 4));
                    listCompanies.setSelectedItem((String) medicineTable.getValueAt(medicineTable.getSelectedRow(), 5));
                } else {
                    nameTextField.setText("");
                    priceTextField.setText("");
                    quantityTextField.setText("");
                    descriptionTextField.setText("");
                    sideEffectsTextField.setText("");
                    listCompanies.setSelectedItem(0);
                }
            }
        });

        messageLabel = new JLabel();
        messageLabel.setForeground(Color.red);
        messageLabel.setFont(new Font("Ink Free",Font.BOLD,15));
        messageLabel.setBounds(160,280,400,15);
        this.add(messageLabel);

        medicineTable.setFillsViewportHeight(true);
        panel.add(sp);
        this.add(panel);
        this.add(deleteBtn);
        this.add(updateBtn);
        this.add(tableNameLabel);
        this.add(descriptionTextField);
        this.add(sideEffectsTextField);
        this.add(sideEffectsLabel);
        this.add(descriptionLabel);
        this.add(addBtn);
        this.add(priceLabel);
        this.add(priceTextField);
        this.add(quantityLabel);
        this.add(quantityTextField);
        this.add(fabDateLabel);
        this.add(fabDatePanel);
        this.add(nameLabel);
        this.add(nameTextField);
        this.add(expDateLabel);
        this.add(companyLabel);
        this.add(expDatePanel);
        this.add(listCompanies);
        this.add(panelNameLabel);
        this.setVisible(true);


    }


    @Override
    public void actionPerformed (ActionEvent e){
        if(e.getSource() == addBtn) {
            if(verifyExistingValues()) {
                addToDataBase();
                System.out.println(String.valueOf(fabDatePanel.getModel().getValue()));
                addRowToTable();
            } else {
                messageLabel.setText("This medicine already exists.");
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

    // adds a new row to database
    public void addToDataBase() {
        DataBaseConnection connection = new DataBaseConnection();
        Connection connectDB = connection.getConnection();

        String medName = nameTextField.getText();
        String price = priceTextField.getText();
        String qty = quantityTextField.getText();
        String des = descriptionTextField.getText();
        String se = sideEffectsLabel.getText();
        String company = String.valueOf(listCompanies.getSelectedItem());
        String fabMonth = String.valueOf(fabDatePanel.getModel().getMonth());
        String fabYear = String.valueOf(fabDatePanel.getModel().getYear());
        String fabDate = fabMonth + "/" + fabYear;
        String expMonth = String.valueOf(expDatePanel.getModel().getMonth());
        String expYear = String.valueOf(expDatePanel.getModel().getYear());
        String expDate = expMonth + "/" + expYear;


        String fields = "INSERT INTO MedicineTable (MedName, " +
                "Price, QTY, Description,SideEffects,Company,FabDate,ExpDate) VALUES ('";
        String values =  medName + "', '" + price +"', '" + qty + "', '" +
                des +"', '" + se +"', '" + company + "', '" + fabDate + "', '" + expDate +"')";

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

    //verifies if the medicine already exists in database
    public boolean verifyExistingValues() {

        DataBaseConnection connection = new DataBaseConnection();
        Connection connectDB = connection.getConnection();

        String str = "SELECT count(1) FROM MedicineTable WHERE MedName = '" + nameTextField.getText() + "'";

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

    public void getDataFromDataBase(JTable table) {
        DataBaseConnection connection = new DataBaseConnection();
        Connection connectDB = connection.getConnection();
        String[] columns = {"Name", "Price", "QTY", "Description", "Side Effects",
                "Company", "FabDate", "ExpDate"};

        DefaultTableModel model = new DefaultTableModel(columns, 0);
        String stm = "SELECT * FROM MedicineTable";
        try{
            Statement statement = connectDB.createStatement();
            ResultSet  queryResult = statement.executeQuery(stm);

            while(queryResult.next()) {
                String m1 = queryResult.getString("medName");
                String m2 = queryResult.getString("Price");
                String m3 = queryResult.getString("QTY");
                String m4 = queryResult.getString("Description");
                String m5 = queryResult.getString("SideEffects");
                String m6 = queryResult.getString("Company");
                String m7 = queryResult.getString("FabDate");
                String m8 = queryResult.getString("ExpDate");
                model.addRow(new Object[]{m1,m2,m3,m4,m5,m6,m7,m8});
                table.setModel(model);
            }
            connectDB.close();
        }catch(Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    //gets all companies in an arrayList
    public ArrayList<String> getCompanies() {
        DataBaseConnection connection = new DataBaseConnection();
        Connection connectDB = connection.getConnection();
        ArrayList<String> companies = new ArrayList<>();

        String stm = "SELECT ComName FROM CompaniesTable;";
        try{
            Statement statement = connectDB.createStatement();
            ResultSet  queryResult = statement.executeQuery(stm);

            while(queryResult.next()) {
                String m1 = queryResult.getString("ComName");
                companies.add(m1);
            }
            connectDB.close();
        }catch(Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return companies;

    }

    //adds new row to the table
    public void addRowToTable() {
        String medName = nameTextField.getText();
        String price = priceTextField.getText();
        String qty = quantityTextField.getText();
        String des = descriptionTextField.getText();
        String se = sideEffectsTextField.getText();
        String company = String.valueOf(listCompanies.getSelectedItem());
        String fabMonth = String.valueOf(fabDatePanel.getModel().getMonth());
        String fabYear = String.valueOf(fabDatePanel.getModel().getYear());
        String fabDate = fabMonth + "/" + fabYear;
        String expMonth = String.valueOf(expDatePanel.getModel().getMonth());
        String expYear = String.valueOf(expDatePanel.getModel().getYear());
        String expDate = expMonth + "/" + expYear;

        Object[] row = { medName, price, qty, des,se,company,fabDate,expDate};
        DefaultTableModel model = (DefaultTableModel) medicineTable.getModel();
        model.addRow(row);
    }

    //updates the selected row from the database's table
    public void updateDataFromDatabase() {
        DataBaseConnection connection = new DataBaseConnection();
        Connection connectDB = connection.getConnection();

        String medName = nameTextField.getText();
        String price = priceTextField.getText();
        String qty = quantityTextField.getText();
        String des = descriptionTextField.getText();
        String se = sideEffectsTextField.getText();
        String company = String.valueOf(listCompanies.getSelectedItem());
        String fabMonth = String.valueOf(fabDatePanel.getModel().getMonth());
        String fabYear = String.valueOf(fabDatePanel.getModel().getYear());
        String fabDate = fabMonth + "/" + fabYear;
        String expMonth = String.valueOf(expDatePanel.getModel().getMonth());
        String expYear = String.valueOf(expDatePanel.getModel().getYear());
        String expDate = expMonth + "/" + expYear;


        String fields = "UPDATE MedicineTable SET MedName = '" + medName + "', Price = '" + price + "', QTY = '" +
           qty + "', Description = '"  +  des + "', SideEffects = '" + se + "', Company = '" + company +
                "', FabDate = '"  + fabDate + "', ExpDate = '" + expDate + "' where ID = " + getID() + ";";


        try {
            Statement stDB = connectDB.createStatement();
            stDB.executeUpdate(fields);
            connectDB.close();

        } catch(Exception e){
            e.printStackTrace();
            e.getCause();

        }

    }

    //gets the selected medicine's id
    public int getID() {
        DataBaseConnection connection = new DataBaseConnection();
        Connection connectDB = connection.getConnection();
        String mName = (String) medicineTable.getValueAt(medicineTable.getSelectedRow(),0);
        String stm = "SELECT ID FROM MedicineTable WHERE MedName ='" + mName + "';";
        int  m1 = 0;
        try{
            Statement statement = connectDB.createStatement();
            ResultSet  queryResult = statement.executeQuery(stm);

            while(queryResult.next()) {
                m1 = queryResult.getInt("ID");
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
        String medName = nameTextField.getText();
        String price = priceTextField.getText();
        String qty = quantityTextField.getText();
        String des = descriptionTextField.getText();
        String se = sideEffectsTextField.getText();
        String company = String.valueOf(listCompanies.getSelectedItem());
        String fabMonth = String.valueOf(fabDatePanel.getModel().getMonth());
        String fabYear = String.valueOf(fabDatePanel.getModel().getYear());
        String fabDate = fabMonth + "/" + fabYear;
        String expMonth = String.valueOf(expDatePanel.getModel().getMonth());
        String expYear = String.valueOf(expDatePanel.getModel().getYear());
        String expDate = expMonth + "/" + expYear;
        medicineTable.getModel().setValueAt(medName, medicineTable.getSelectedRow(),0);
        medicineTable.getModel().setValueAt(price, medicineTable.getSelectedRow(),1);
        medicineTable.getModel().setValueAt(qty, medicineTable.getSelectedRow(),2);
        medicineTable.getModel().setValueAt(des, medicineTable.getSelectedRow(),3);
        medicineTable.getModel().setValueAt(se, medicineTable.getSelectedRow(),4);
        medicineTable.getModel().setValueAt(company, medicineTable.getSelectedRow(),5);
        medicineTable.getModel().setValueAt(fabDate, medicineTable.getSelectedRow(),6);
        medicineTable.getModel().setValueAt(expDate, medicineTable.getSelectedRow(),7);

    }

    //deletes the selected row from the table
    public void deleteRowFromTable() {
        int rowNum = medicineTable.getSelectedRow();
        System.out.println(rowNum);
        if(medicineTable.getSelectedRowCount() == 1) {
            ((DefaultTableModel) medicineTable.getModel()).removeRow(rowNum);
        } else if(medicineTable.getSelectedRowCount() == 0) {
            messageLabel.setText("Please select a row to delete!");
        }
    }

    //deletes the selected row from the database's table
    public void deleteRowFromDatabase() {
        DataBaseConnection connection = new DataBaseConnection();
        Connection connectDB = connection.getConnection();

        String str = "DELETE FROM MedicineTable WHERE ID = " + getID() + ";";

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


