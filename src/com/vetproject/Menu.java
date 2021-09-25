package com.vetproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu implements ActionListener {
    JFrame frame;
    JButton exitBtn, manageMedicineBtn, manageCompanyBtn,
            sellingBtn;
    JPanel panels, medicinePanel, companiesPanel, panel1, sellingPanel;
    JLabel icon;
    CardLayout cd;

    Menu() {
        frame = new JFrame();
        frame.getContentPane().setLayout(null);
        frame.setUndecorated(true);
        frame.setSize(850,600);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(132,173,41));
        frame.setLayout(null);

        panels = new JPanel();
        cd = new CardLayout();
        panels.setLayout(cd);
        panels.setBackground(Color.white);
        panels.setBounds(220,0,630,600);
        medicinePanel = new MedicinePanel();
        companiesPanel = new CompaniesPanel();
        sellingPanel = new SellingPanel();

        panel1 = new JPanel();
        icon = new JLabel(new ImageIcon("img/menu.jpg"));
        panel1.add(icon);

        panels.add(panel1,"1");
        panels.add(medicinePanel,"2");
        panels.add(companiesPanel,"3");
        panels.add(sellingPanel,"4");
        cd.show(panels,"1");

        frame.add(panels);

        manageMedicineBtn = new JButton("Manage Medication");
        manageMedicineBtn.setFont(new Font("Amaranth",Font.BOLD,14));
        manageMedicineBtn.setForeground(new Color(132,173,41));
        manageMedicineBtn.setBackground(Color.white);
        manageMedicineBtn.setBounds(10,140,190,30);
        manageMedicineBtn.addActionListener(this);
        frame.add(manageMedicineBtn);

        manageCompanyBtn = new JButton("Manage Companies");
        manageCompanyBtn.setFont(new Font("Amaranth",Font.BOLD,14));
        manageCompanyBtn.setForeground(new Color(132,173,41));
        manageCompanyBtn.setBackground(Color.white);
        manageCompanyBtn.setBounds(10,210,190,30);
        manageCompanyBtn.addActionListener(this);
        frame.add(manageCompanyBtn);

        sellingBtn = new JButton("Selling");
        sellingBtn.setFont(new Font("Amaranth",Font.BOLD,15));
        sellingBtn.setForeground(new Color(132,173,41));
        sellingBtn.setBackground(Color.white);
        sellingBtn.setBounds(10,280,190,30);
        sellingBtn.addActionListener(this);
        frame.add(sellingBtn);

        exitBtn = new JButton("Exit");
        exitBtn.setFont(new Font("Amaranth",Font.BOLD,15));
        exitBtn.setForeground(new Color(132,173,41));
        exitBtn.setBackground(Color.white);
        exitBtn.setBounds(10,350,190,30);
        exitBtn.addActionListener(this);
        frame.add(exitBtn);

        frame.setVisible(true);
    }


    //change panels on button click
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == exitBtn) {
            frame.dispose();
        }
        if(e.getSource() == manageMedicineBtn) {
            cd.show(panels,"2");

        }
        if(e.getSource() == manageCompanyBtn) {
            cd.show(panels,"3");
        }
        if(e.getSource() == sellingBtn) {
            cd.show(panels,"4");
        }
    }


}
