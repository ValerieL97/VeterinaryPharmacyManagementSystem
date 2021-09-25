package com.vetproject;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SellingPanel extends JPanel implements ActionListener {

    JLabel panelNameLabel, medicineLabel, quantityLabel, messageLabel;
    JTextField medicineTextField, quantityTextField;
    JTable table;
    JButton addBtn, deleteBtn, printBtn, generateBillBtn, deleteBillBtn;
    JTextArea recTextArea;

    JPanel panel, panelBill;

    SellingPanel() {
        this.setBackground(Color.white);
        this.setLayout(null);

        panelNameLabel = new JLabel("Billing");
        panelNameLabel.setBounds(250, 20, 300, 30);
        panelNameLabel.setFont(new Font("Amaranth", Font.BOLD, 25));
        panelNameLabel.setForeground(Color.BLACK);
        this.add(panelNameLabel);

        medicineLabel = new JLabel("Medicine:");
        medicineLabel.setForeground(Color.black);
        medicineLabel.setBounds(10, 105, 100, 20);
        medicineLabel.setFont(new Font("Amaranth", Font.BOLD, 17));
        this.add(medicineLabel);

        medicineTextField = new JTextField();
        medicineTextField.setForeground(Color.BLACK);
        medicineTextField.setFont(new Font("Amaranth", Font.BOLD, 15));
        medicineTextField.setBounds(120, 105, 175, 20);
        this.add(medicineTextField);

        quantityLabel = new JLabel("Quantity:");
        quantityLabel.setForeground(Color.black);
        quantityLabel.setBounds(10, 145, 100, 20);
        quantityLabel.setFont(new Font("Amaranth", Font.BOLD, 17));
        this.add(quantityLabel);

        quantityTextField = new JTextField();
        quantityTextField.setForeground(Color.BLACK);
        quantityTextField.setFont(new Font("Amaranth", Font.BOLD, 15));
        quantityTextField.setBounds(120, 145, 175, 20);
        this.add(quantityTextField);

        addBtn = new JButton("Add");
        addBtn.setForeground(Color.white);
        addBtn.setBackground(new Color(132, 173, 41));
        addBtn.setBounds(10, 200, 120, 30);
        addBtn.addActionListener(this);
        this.add(addBtn);

        deleteBtn = new JButton("Delete");
        deleteBtn.setForeground(Color.white);
        deleteBtn.setBackground(new Color(132, 173, 41));
        deleteBtn.setBounds(170, 200, 120, 30);
        deleteBtn.addActionListener(this);
        this.add(deleteBtn);

        messageLabel = new JLabel();
        messageLabel.setForeground(Color.red);
        messageLabel.setFont(new Font("Ink Free", Font.BOLD, 15));
        messageLabel.setBounds(50, 180, 400, 15);
        this.add(messageLabel);

        panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setBounds(300, 100, 320, 150);
        panel.setLayout(new BorderLayout());
        this.add(panel);

        String[] columns = {"Medicine", "Company", "QTY", "Price", "ExpDate"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        table = new JTable() {
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        table.setModel(model);
        JScrollPane sp = new JScrollPane(table);
        table.setBounds(20, 320, 580, 250);
        table.setForeground(Color.BLACK);
        table.setBackground(Color.white);
        table.setShowHorizontalLines(true);
        table.setShowGrid(true);
        table.setGridColor(Color.black);
        table.setSelectionBackground(Color.DARK_GRAY);
        table.setSelectionForeground(Color.white);
        table.setRowSelectionAllowed(true);
        table.setRowHeight(25);
        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);

        panel.add(new JScrollPane(table));
        this.add(panel);

        recTextArea = new JTextArea();
        recTextArea.setBounds(10, 10, 400, 250);
        recTextArea.setEditable(false);
        panelBill = new JPanel();
        panelBill.setBounds(200, 300, 420, 270);
        panelBill.setBorder(BorderFactory.createLineBorder(new Color(132, 173, 41)));
        panelBill.setBackground(Color.white);
        panelBill.add(recTextArea);
        this.add(panelBill);

        generateBillBtn = new JButton("Generate Bill");
        generateBillBtn.setForeground(Color.white);
        generateBillBtn.setBackground(new Color(132, 173, 41));
        generateBillBtn.setBounds(20, 320, 150, 30);
        generateBillBtn.addActionListener(this);
        this.add(generateBillBtn);

        deleteBillBtn = new JButton("Delete Bill");
        deleteBillBtn.setForeground(Color.white);
        deleteBillBtn.setBackground(new Color(132, 173, 41));
        deleteBillBtn.setBounds(20, 380, 150, 30);
        deleteBillBtn.addActionListener(this);
        this.add(deleteBillBtn);

        printBtn = new JButton("Print Bill");
        printBtn.setForeground(Color.white);
        printBtn.setBackground(new Color(132, 173, 41));
        printBtn.setBounds(20, 440, 150, 30);
        printBtn.addActionListener(this);
        this.add(printBtn);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addBtn) {
            if(medicineTextField.getText().isEmpty() || quantityTextField.getText().isEmpty()) {
                messageLabel.setText("Complete all fields!");
            } else if (verifyExistingValues()) {
                messageLabel.setText("Not Found!");
            } else {
                addDataToTable();
                messageLabel.setText("");
            }
        }

        if (e.getSource() == deleteBtn) {
            deleteRowFromTable();
            messageLabel.setText("");
        }

        if (e.getSource() == generateBillBtn) {
            recTextArea.setText(generateReceipt());
        }

        if (e.getSource() == deleteBillBtn) {
            recTextArea.setText("");
        }
        if (e.getSource() == printBtn) {
            generateBillDoc();
        }


    }
    //gets all data about medicine from database
    public String[] getMedDetails(String mName) {
        DataBaseConnection connection = new DataBaseConnection();
        Connection connectDB = connection.getConnection();
        String stm = "SELECT Company, Price, ExpDate FROM MedicineTable WHERE MedName ='" + mName + "';";
        String m1 = "";
        String m2 = "";
        String m3 = "";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(stm);

            while (queryResult.next()) {
                m1 = queryResult.getString("Company");
                m2 = queryResult.getString("Price");
                m3 = queryResult.getString("ExpDate");
            }
            connectDB.close();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return new String[]{m1, m2, m3};
    }

    //adds data from database to the table
    public void addDataToTable() {
        String medName = medicineTextField.getText();
        String[] str = getMedDetails(medName);
        String qty = quantityTextField.getText();
        String company = str[0];
        String price = str[1];
        String expD = str[2];

        Object[] row = {medName, company, qty, price, expD};
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(row);
    }

    //verifies if in database exists the medicine
    public boolean verifyExistingValues() {

        DataBaseConnection connection = new DataBaseConnection();
        Connection connectDB = connection.getConnection();

        String str = "SELECT count(1) FROM MedicineTable WHERE MedName = '" + medicineTextField.getText() + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryR1 = statement.executeQuery(str);

            while (queryR1.next()) {
                if (queryR1.getInt(1) == 1) {
                    return false;
                }
            }
            connectDB.close();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        return true;
    }

    public void deleteRowFromTable() {
        if (table.getSelectedRowCount() == 1) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.removeRow(table.getSelectedRow());
        } else if (table.getSelectedRowCount() == 0) {
            messageLabel.setText("Please select a row!");
        }
    }
    //generates the bill's content
    public String generateReceipt() {
        String med = "";
        String comp = "";
        String pr = "";
        String qty = "";
        String exd = "";
        for (int i = 0; i < table.getRowCount(); i++) {
            med = med + " | " + (String) table.getValueAt(i, 0);
            comp = comp + " | " + (String) table.getValueAt(i, 1);
            qty = qty + " | " + (String) table.getValueAt(i, 2);
            pr = pr + " | " + (String) table.getValueAt(i, 3);
            exd = exd + " | " + (String) table.getValueAt(i, 4);
        }
        String str = "\t -- RECEIPT --\n\n" +
                "\tWelcome to FELIX \n\n" +
                "Medication" + med + "\n\n" +
                "Quantity " + qty + "\n\n" +
                "ExpDate  " + exd + "\n\n" +
                "Price   " + pr + "\n\n" +
                "Total:" + totalPrice() + "\n\n" +
                "\t-- THANK YOU! --";

        return str;
    }

    //calculates the total price for medicines
    public String totalPrice() {
        String price = "";
        float pr = 0;
        for (int i = 0; i < table.getRowCount(); i++) {
            String med = (String) table.getValueAt(i, 3);
            String qt = (String) table.getValueAt(i, 2);
            pr = pr + (Float.parseFloat(med) * Float.parseFloat(qt));
        }
        price = String.valueOf(pr);
        return price;
    }

    //generates a pdf file with the bill for printing
    public void generateBillDoc() {
        String inv = "b.pdf";
        Document doc = new Document();
        PdfWriter writer;

        try {
            writer = PdfWriter.getInstance(doc, new FileOutputStream(inv));
            doc.open();
            String rec = recTextArea.getText();
            Paragraph p1= new Paragraph(rec);
            doc.add(p1);
            doc.close();
            openPdf(inv);
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    //opens the pdf file
    public static void openPdf(String file) {
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(new File(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
