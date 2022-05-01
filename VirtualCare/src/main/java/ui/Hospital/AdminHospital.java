/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.Hospital;

import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.Db4oIOException;
import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.table.DefaultTableModel;
import model.AdminDetails;
import model.DoctorDetails;
import model.InsuranceRequests;
import model.PharmacyOrders;
import model.UserDetails;
import ui.User.UserSystem;

/**
 *
 * @author shubhampatil
 */
public class AdminHospital extends javax.swing.JPanel {

    /**
     * Creates new form AdminHospital
     */
    public AdminHospital(JSplitPane SplitPane, AdminDetails a) {
        initComponents();
        this.a = a;
        lblHeaderHospital.setText("Welcome To " + a.getOrganization());
        DefaultTableModel DocMod = (DefaultTableModel) tableViewDoctorsAH.getModel();
        DefaultTableModel PhMod = (DefaultTableModel) tblPharmacy.getModel();
        DefaultTableModel InsMod = (DefaultTableModel) tblInsuranceReqs.getModel();
        Card = (CardLayout) cardPanel.getLayout();
        cardPanel.setVisible(false);
        this.DocMod = DocMod;
        this.PhMod = PhMod;
        this.InsMod = InsMod;
        this.SplitPane = SplitPane;
    }

    JSplitPane SplitPane;
    AdminDetails a;
    CardLayout Card;
    HashMap<String, DoctorDetails> DoctorMap;
    DefaultTableModel DocMod;
    DefaultTableModel PhMod;
    DefaultTableModel InsMod;
    LinkedHashSet<String> InsuranceOrgs;
    LinkedHashSet<String> PharmacyOrgs;
    HashMap<String, UserDetails> UserMap;
    ArrayList<PharmacyOrders> PhOrders;
    ArrayList<InsuranceRequests> InsReq;
        
    void PullPhOrderstoList(){
        ArrayList<PharmacyOrders> PhOrders = new ArrayList<>();

        PharmacyOrders P;
        try {
            List<PharmacyOrders> Pharmacyresult = UserSystem.Phardb.query(PharmacyOrders.class);
            if(Pharmacyresult.isEmpty())
                return;
            Iterator Phitr = Pharmacyresult.iterator();
            while(Phitr.hasNext()){
                P = (PharmacyOrders)Phitr.next();
                if(a.getOrganization().equalsIgnoreCase(P.getFromHospital()))
                    PhOrders.add(P);
            }
        }
        catch(DatabaseClosedException | Db4oIOException E){
            JOptionPane.showMessageDialog(this, "Database Error.");
        }
        this.PhOrders = PhOrders;
    }
    
    void PullUserstoHashMap(){
        HashMap<String, UserDetails> UserMap = new HashMap<>();
        
        UserDetails u;
        try {
            List<UserDetails> userresult = UserSystem.Userdb.query(UserDetails.class);
            if(userresult.isEmpty())
                return;
            Iterator useritr = userresult.iterator();
            while(useritr.hasNext()){
                u = (UserDetails)useritr.next();
                UserMap.put(u.getEmail(), u);
            }
        }
        catch(DatabaseClosedException | Db4oIOException E){
            JOptionPane.showMessageDialog(this, "Database Error.");
        }
        this.UserMap = UserMap;
    }
    
    void PullOrgstoSet(){
        LinkedHashSet<String> InsuranceOrgs = new LinkedHashSet<>();
        LinkedHashSet<String> PharmacyOrgs = new LinkedHashSet<>();
        AdminDetails a;
        try {
            List<AdminDetails> adminresult = UserSystem.Admindb.query(AdminDetails.class);
            if(adminresult.isEmpty())
                return;
            Iterator adminitr = adminresult.iterator();
            Iterator adminitr2 = adminresult.iterator();
            while(adminitr.hasNext()){
                a = (AdminDetails)adminitr.next();
                if(a.getEnterprise().equalsIgnoreCase("Insurance"))
                    InsuranceOrgs.add(a.getOrganization());
            }
            while(adminitr2.hasNext()){
                a = (AdminDetails)adminitr2.next();
                if(a.getEnterprise().equalsIgnoreCase("Pharmacy"))
                    PharmacyOrgs.add(a.getOrganization());
            }
        }
        catch(DatabaseClosedException | Db4oIOException E){
            JOptionPane.showMessageDialog(this, "Database Error.");
        }
        this.InsuranceOrgs = InsuranceOrgs;
        this.PharmacyOrgs = PharmacyOrgs;
    }
    
    void PullInsuranceRequeststoList(){
        ArrayList<InsuranceRequests> InsReq = new ArrayList<>();

        InsuranceRequests I;
        try {
            List<InsuranceRequests> Insuranceresult = UserSystem.Insudb.query(InsuranceRequests.class);
            if(Insuranceresult.isEmpty())
                return;
            Iterator Insuitr = Insuranceresult.iterator();
            while(Insuitr.hasNext()){
                I = (InsuranceRequests)Insuitr.next();
                if(a.getOrganization().equalsIgnoreCase(I.getFromHospital()))
                    InsReq.add(I);
            }
        }
        catch(DatabaseClosedException | Db4oIOException E){
            JOptionPane.showMessageDialog(this, "Database Error.");
        }
        this.InsReq = InsReq;
    }
    
    void populateInsuranceDropdown(){
        cmbBoxOrgansation.removeAllItems();
        try{
            Iterator itr = InsuranceOrgs.iterator();
            while(itr.hasNext()){
                cmbBoxOrgansation.addItem(itr.next().toString());
            }
        }
        catch(NullPointerException E){
            return;
        }
    }
    
    void populatePharmacyDropdown(){
        cmbPharmacyorg.removeAllItems();
        try{
            Iterator itr = PharmacyOrgs.iterator();
            while(itr.hasNext()){
                cmbPharmacyorg.addItem(itr.next().toString());
            }
        }
        catch(NullPointerException E){
            return;
        }
    }
    
    void populatePhOrderstable(){
        PhMod.setRowCount(0);
        try{
            Iterator itr = PhOrders.iterator();
            while(itr.hasNext()){
                PharmacyOrders P = (PharmacyOrders)itr.next();

                if(P.getFromHospital().equalsIgnoreCase(a.getOrganization())){
                    String data[] = {P.getToOrg(), P.getMedicine(), String.valueOf(P.getQuantity()), P.getStatus()};
                    PhMod.addRow(data);
                }
            }
        }
        catch(NullPointerException E){
            JOptionPane.showMessageDialog(this, "Orders List is Empty");
        }
    }
    
    void populateInsurancetable(){
        InsMod.setRowCount(0);
        try{
            Iterator itr = InsReq.iterator();
            while(itr.hasNext()){
                InsuranceRequests I = (InsuranceRequests)itr.next();

                String data[] = {I.getToOrg(), I.getPatientEmail(), String.valueOf(I.getAmount()), I.getStatus()};
                InsMod.addRow(data);
            }
        }
        catch(NullPointerException E){
            JOptionPane.showMessageDialog(this, "No Requests Exist.");
        }
    }
    
    void clearallfields(){
        txtFirstNameAH.setText("");
        txtLastNameAH.setText("");
        txtAvailableDays.setText("");
        txtTime.setText("");
        txtFees.setText("");
        txtRatings.setText("");
        txtEmailIdAH.setText("");
        txtPasswordAH.setText("");
    }
    
    DoctorDetails MakeDoctor(){
        DoctorDetails d = new DoctorDetails();
        d.setDepartment(cmbBoxDepartment.getSelectedItem().toString());
        d.setFirstName(txtFirstNameAH.getText().trim());
        d.setLastName(txtLastNameAH.getText().trim());
        String s = txtAvailableDays.getText();
        String WorkingDays[] = s.split(",");
        d.setWorkingDays(WorkingDays);
        d.setTime(txtTime.getText().trim());
        d.setFees(Integer.parseInt(txtFees.getText().trim()));
        d.setRatings(Integer.parseInt(txtRatings.getText().trim()));
        d.setEmail(txtEmailIdAH.getText().trim());
        d.setPassword(Arrays.toString(txtPasswordAH.getPassword()));
        d.setLocation(a.getLocation());
        d.setOrganisation(a.getOrganization());
        return d;
    }
    
    PharmacyOrders MakePhOrders(){
        PharmacyOrders Ph = new PharmacyOrders();
        Ph.setToOrg(cmbPharmacyorg.getSelectedItem().toString());
        Ph.setMedicine(txtMedicineName.getText().trim());
        Ph.setQuantity(Integer.parseInt(txtQuantity.getText().trim()));
        Ph.setFromHospital(a.getOrganization());
        Ph.setTimeStamp();
        Ph.setStatus("Pending");
        return Ph;
    }
    
    InsuranceRequests MakeInsRequests(){
        InsuranceRequests Ir = new InsuranceRequests();
        Ir.setToOrg(cmbBoxOrgansation.getSelectedItem().toString());
        Ir.setPatientEmail(txtPatientId.getText().trim());
        Ir.setAmount(Integer.parseInt(txtAmount.getText()));
        Ir.setFromHospital(a.getOrganization());
        Ir.setTimeStamp();
        Ir.setStatus("Pending");
        return Ir;
    }
    
    void displaydoctorsintable(){
        DocMod.setRowCount(0);
        try{
        Collection<DoctorDetails> values = DoctorMap.values();
        ArrayList<DoctorDetails> Doctors = new ArrayList<>(values);
        Iterator itr = Doctors.iterator();
        while(itr.hasNext()){
            DoctorDetails d = (DoctorDetails) itr.next();
            String WorkingDays = Arrays.toString(d.getWorkingDays());
            String data[] = {d.getFirstName() + d.getLastName(), d.getDepartment(), WorkingDays , String.valueOf(d.getRatings()), d.getEmail()};
            DocMod.addRow(data);
            }
        }
        catch(NullPointerException E){
            return;
        }
    }
    
    public static void AddPhOrderstoDB(PharmacyOrders Ph){     
        try {
            UserSystem.Phardb.store(Ph);
            System.out.println("Stored " + Ph.getToOrg());
        }
        catch(DatabaseClosedException | Db4oIOException E){
            System.out.println("Database Error");
        }
    }
    
    public static void AddInsRequeststoDB(InsuranceRequests Ir){     
        try {
            UserSystem.Insudb.store(Ir);
            System.out.println("Stored " + Ir.getToOrg());
        }
        catch(DatabaseClosedException | Db4oIOException E){
            System.out.println("Database Error");
        }
    }
    
    void PullDoctorstoHashMap(){
        HashMap<String, DoctorDetails> DoctorMap = new HashMap<>();

        DoctorDetails d;
        try {
            List<DoctorDetails> doctorresult = UserSystem.Doctordb.query(DoctorDetails.class);
            if(doctorresult.isEmpty())
                return;
            Iterator doctoritr = doctorresult.iterator();
            while(doctoritr.hasNext()){
                d = (DoctorDetails)doctoritr.next();
                if(d.getOrganisation().equalsIgnoreCase(a.getOrganization())){
                    DoctorMap.put(d.getEmail(), d);
                }
            }
        }
        catch(DatabaseClosedException | Db4oIOException E){
            JOptionPane.showMessageDialog(this, "Database Error.");
        }
        this.DoctorMap = DoctorMap;
    }
    
    
    public static void AddDoctortoDB(DoctorDetails d){
        try {
            UserSystem.Doctordb.store(d);
            System.out.println("Stored: " + d.getEmail());
        }
        catch(DatabaseClosedException | Db4oIOException E){
            System.out.println("Error in storing: " + d.getEmail());
        }
    }
    
    void RemoveDoctorfromDB(DoctorDetails d){
        try {
            UserSystem.Doctordb.delete(d);
            System.out.println("Deleted: " + d.getEmail());
        }
        catch(DatabaseClosedException | Db4oIOException E){
            JOptionPane.showMessageDialog(this, "Database Error.");
        }
    }
    
    boolean checkDuplicateInsurance(){
        PullInsuranceRequeststoList();
        if(InsReq == null)
            return true;
        Iterator itr = InsReq.iterator();
        while(itr.hasNext()){
            InsuranceRequests Ir = (InsuranceRequests)itr.next();
            
            if(Ir.getAmount() == Integer.parseInt(txtAmount.getText()) && Ir.getFromHospital().equalsIgnoreCase(a.getOrganization()) && Ir.getPatientEmail().equalsIgnoreCase(txtPatientId.getText()) && Ir.getToOrg().equalsIgnoreCase(cmbBoxOrgansation.getSelectedItem().toString()))
                return false;
            }
        return true;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelLeft = new javax.swing.JPanel();
        btnAddDoctors = new javax.swing.JButton();
        btnViewDoctors = new javax.swing.JButton();
        btnOrderMedicines = new javax.swing.JButton();
        btnInsuranceFunds = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        cardPanel = new javax.swing.JPanel();
        ViewDoctors = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableViewDoctorsAH = new javax.swing.JTable();
        btnDeleteAH = new javax.swing.JButton();
        OrderMedicines = new javax.swing.JPanel();
        txtMedicineName = new javax.swing.JTextField();
        lblQuantity = new javax.swing.JLabel();
        lblMedicineName = new javax.swing.JLabel();
        btnOrder = new javax.swing.JButton();
        txtQuantity = new javax.swing.JTextField();
        cmbPharmacyorg = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPharmacy = new javax.swing.JTable();
        btnPhDeleteOrder = new javax.swing.JButton();
        AddDoctors = new javax.swing.JPanel();
        txtFees = new javax.swing.JTextField();
        lblRatings2 = new javax.swing.JLabel();
        txtEmailIdAH = new javax.swing.JTextField();
        lblLastName = new javax.swing.JLabel();
        cmbBoxDepartment = new javax.swing.JComboBox<>();
        txtFirstNameAH = new javax.swing.JTextField();
        lblFees = new javax.swing.JLabel();
        txtLastNameAH = new javax.swing.JTextField();
        lblRatings1 = new javax.swing.JLabel();
        lblAddDoctors = new javax.swing.JLabel();
        txtRatings = new javax.swing.JTextField();
        lblFirstName = new javax.swing.JLabel();
        btnRegisterDoctor = new javax.swing.JButton();
        lblDepartment = new javax.swing.JLabel();
        txtTime = new javax.swing.JTextField();
        lblTime = new javax.swing.JLabel();
        lblRatings = new javax.swing.JLabel();
        lblAvailableDays = new javax.swing.JLabel();
        txtAvailableDays = new javax.swing.JTextField();
        txtPasswordAH = new javax.swing.JPasswordField();
        ReuestInsuranceFunds = new javax.swing.JPanel();
        btnSend = new javax.swing.JButton();
        lblAmount = new javax.swing.JLabel();
        txtAmount = new javax.swing.JTextField();
        lblPatientId = new javax.swing.JLabel();
        txtPatientId = new javax.swing.JTextField();
        lblOrganisation = new javax.swing.JLabel();
        cmbBoxOrgansation = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblInsuranceReqs = new javax.swing.JTable();
        lblHeaderHospital = new javax.swing.JLabel();

        jPanelLeft.setPreferredSize(new java.awt.Dimension(1000, 1000));

        btnAddDoctors.setText("ADD Doctors");
        btnAddDoctors.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDoctorsActionPerformed(evt);
            }
        });

        btnViewDoctors.setText("VIEW Doctors");
        btnViewDoctors.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewDoctorsActionPerformed(evt);
            }
        });

        btnOrderMedicines.setText("ORDER Medicines");
        btnOrderMedicines.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrderMedicinesActionPerformed(evt);
            }
        });

        btnInsuranceFunds.setText("Request Insurance Funds");
        btnInsuranceFunds.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsuranceFundsActionPerformed(evt);
            }
        });

        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelLeftLayout = new javax.swing.GroupLayout(jPanelLeft);
        jPanelLeft.setLayout(jPanelLeftLayout);
        jPanelLeftLayout.setHorizontalGroup(
            jPanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLeftLayout.createSequentialGroup()
                .addGroup(jPanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelLeftLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(btnInsuranceFunds))
                    .addGroup(jPanelLeftLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(btnOrderMedicines)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLeftLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLeftLayout.createSequentialGroup()
                        .addGroup(jPanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAddDoctors)
                            .addComponent(btnViewDoctors))
                        .addGap(39, 39, 39))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLeftLayout.createSequentialGroup()
                        .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))))
        );
        jPanelLeftLayout.setVerticalGroup(
            jPanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLeftLayout.createSequentialGroup()
                .addGap(137, 137, 137)
                .addComponent(btnAddDoctors)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnViewDoctors)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnOrderMedicines)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnInsuranceFunds)
                .addGap(71, 71, 71)
                .addComponent(btnLogout)
                .addGap(100, 100, 100))
        );

        cardPanel.setPreferredSize(new java.awt.Dimension(1000, 1000));
        cardPanel.setLayout(new java.awt.CardLayout());

        tableViewDoctorsAH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Department", "Availablility", "Ratings", "Email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableViewDoctorsAH);

        btnDeleteAH.setText("Delete");
        btnDeleteAH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteAHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(321, 321, 321)
                        .addComponent(btnDeleteAH))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 702, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDeleteAH))
        );

        javax.swing.GroupLayout ViewDoctorsLayout = new javax.swing.GroupLayout(ViewDoctors);
        ViewDoctors.setLayout(ViewDoctorsLayout);
        ViewDoctorsLayout.setHorizontalGroup(
            ViewDoctorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewDoctorsLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100))
        );
        ViewDoctorsLayout.setVerticalGroup(
            ViewDoctorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewDoctorsLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100))
        );

        cardPanel.add(ViewDoctors, "card3");

        txtMedicineName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMedicineNameActionPerformed(evt);
            }
        });

        lblQuantity.setText("Quantity");

        lblMedicineName.setText("Medicine Name");

        btnOrder.setText("Order");
        btnOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrderActionPerformed(evt);
            }
        });

        cmbPharmacyorg.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("Organization");

        tblPharmacy.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Organisation", "Medicine", "Quantity", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblPharmacy);

        btnPhDeleteOrder.setText("Delete");
        btnPhDeleteOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPhDeleteOrderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout OrderMedicinesLayout = new javax.swing.GroupLayout(OrderMedicines);
        OrderMedicines.setLayout(OrderMedicinesLayout);
        OrderMedicinesLayout.setHorizontalGroup(
            OrderMedicinesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OrderMedicinesLayout.createSequentialGroup()
                .addGroup(OrderMedicinesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(OrderMedicinesLayout.createSequentialGroup()
                        .addGap(286, 286, 286)
                        .addComponent(btnOrder))
                    .addGroup(OrderMedicinesLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(OrderMedicinesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(OrderMedicinesLayout.createSequentialGroup()
                                .addGroup(OrderMedicinesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbPharmacyorg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addGap(37, 37, 37)
                                .addGroup(OrderMedicinesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMedicineName, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblMedicineName))
                                .addGap(48, 48, 48)
                                .addGroup(OrderMedicinesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblQuantity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtQuantity)))))
                    .addGroup(OrderMedicinesLayout.createSequentialGroup()
                        .addGap(289, 289, 289)
                        .addComponent(btnPhDeleteOrder)))
                .addContainerGap(131, Short.MAX_VALUE))
        );
        OrderMedicinesLayout.setVerticalGroup(
            OrderMedicinesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OrderMedicinesLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(OrderMedicinesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMedicineName)
                    .addComponent(lblQuantity)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(OrderMedicinesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMedicineName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbPharmacyorg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(btnOrder)
                .addGap(57, 57, 57)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(btnPhDeleteOrder)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cardPanel.add(OrderMedicines, "card4");

        AddDoctors.setPreferredSize(new java.awt.Dimension(1000, 1000));

        txtFees.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFeesActionPerformed(evt);
            }
        });

        lblRatings2.setText("Password:");

        txtEmailIdAH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailIdAHActionPerformed(evt);
            }
        });

        lblLastName.setText("Last Name:");

        cmbBoxDepartment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dentist", "Orthopedic", "Oncologist", "Coardiologist", "Neurologist", "General Physician", "" }));

        txtFirstNameAH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFirstNameAHActionPerformed(evt);
            }
        });

        lblFees.setText("Fees:");

        txtLastNameAH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLastNameAHActionPerformed(evt);
            }
        });

        lblRatings1.setText("Email Id:");

        lblAddDoctors.setText("Add Doctors");

        txtRatings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRatingsActionPerformed(evt);
            }
        });

        lblFirstName.setText("First Name:");

        btnRegisterDoctor.setText("Register Doctor");
        btnRegisterDoctor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterDoctorActionPerformed(evt);
            }
        });

        lblDepartment.setText("Department");

        txtTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimeActionPerformed(evt);
            }
        });

        lblTime.setText("Time:");

        lblRatings.setText("Ratings:");

        lblAvailableDays.setText("Workings Days:");

        txtAvailableDays.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAvailableDaysActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AddDoctorsLayout = new javax.swing.GroupLayout(AddDoctors);
        AddDoctors.setLayout(AddDoctorsLayout);
        AddDoctorsLayout.setHorizontalGroup(
            AddDoctorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddDoctorsLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(lblAddDoctors)
                .addGap(303, 303, 303))
            .addGroup(AddDoctorsLayout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addGroup(AddDoctorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTime)
                    .addComponent(lblFirstName)
                    .addComponent(lblAvailableDays)
                    .addComponent(lblLastName)
                    .addComponent(lblFees)
                    .addComponent(lblRatings)
                    .addComponent(lblRatings1)
                    .addComponent(lblRatings2))
                .addGap(33, 33, 33)
                .addGroup(AddDoctorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFees, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(AddDoctorsLayout.createSequentialGroup()
                        .addComponent(txtFirstNameAH, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblDepartment)
                        .addGap(18, 18, 18)
                        .addComponent(cmbBoxDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRatings, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAvailableDays, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLastNameAH, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(AddDoctorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtPasswordAH, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtEmailIdAH, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
                    .addGroup(AddDoctorsLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(btnRegisterDoctor)))
                .addGap(70, 70, 70))
        );
        AddDoctorsLayout.setVerticalGroup(
            AddDoctorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddDoctorsLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblAddDoctors)
                .addGap(64, 64, 64)
                .addGroup(AddDoctorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFirstName)
                    .addComponent(txtFirstNameAH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDepartment)
                    .addComponent(cmbBoxDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AddDoctorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLastName)
                    .addComponent(txtLastNameAH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(AddDoctorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAvailableDays)
                    .addComponent(txtAvailableDays, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AddDoctorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTime)
                    .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AddDoctorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFees)
                    .addComponent(txtFees, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(AddDoctorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRatings)
                    .addComponent(txtRatings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AddDoctorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmailIdAH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRatings1))
                .addGap(18, 18, 18)
                .addGroup(AddDoctorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRatings2)
                    .addComponent(txtPasswordAH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnRegisterDoctor)
                .addGap(100, 100, 100))
        );

        cardPanel.add(AddDoctors, "card2");

        ReuestInsuranceFunds.setPreferredSize(new java.awt.Dimension(1000, 1000));

        btnSend.setText("Send");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        lblAmount.setText("Amount:");

        txtAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAmountActionPerformed(evt);
            }
        });

        lblPatientId.setText("Patient id:");

        txtPatientId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPatientIdActionPerformed(evt);
            }
        });

        lblOrganisation.setText("Organisation:");

        cmbBoxOrgansation.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        tblInsuranceReqs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Organization", "PatientID", "Amount", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblInsuranceReqs);

        javax.swing.GroupLayout ReuestInsuranceFundsLayout = new javax.swing.GroupLayout(ReuestInsuranceFunds);
        ReuestInsuranceFunds.setLayout(ReuestInsuranceFundsLayout);
        ReuestInsuranceFundsLayout.setHorizontalGroup(
            ReuestInsuranceFundsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReuestInsuranceFundsLayout.createSequentialGroup()
                .addGroup(ReuestInsuranceFundsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ReuestInsuranceFundsLayout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ReuestInsuranceFundsLayout.createSequentialGroup()
                        .addGap(136, 136, 136)
                        .addGroup(ReuestInsuranceFundsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblPatientId)
                            .addComponent(lblOrganisation)
                            .addComponent(lblAmount))
                        .addGap(18, 18, 18)
                        .addGroup(ReuestInsuranceFundsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbBoxOrgansation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPatientId, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(ReuestInsuranceFundsLayout.createSequentialGroup()
                        .addGap(206, 206, 206)
                        .addComponent(btnSend)))
                .addGap(100, 100, 100))
        );
        ReuestInsuranceFundsLayout.setVerticalGroup(
            ReuestInsuranceFundsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ReuestInsuranceFundsLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(ReuestInsuranceFundsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOrganisation)
                    .addComponent(cmbBoxOrgansation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ReuestInsuranceFundsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPatientId)
                    .addComponent(txtPatientId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ReuestInsuranceFundsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAmount)
                    .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(btnSend)
                .addGap(100, 100, 100))
        );

        cardPanel.add(ReuestInsuranceFunds, "card5");

        lblHeaderHospital.setText("Welcome to ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(cardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 742, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblHeaderHospital, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblHeaderHospital, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(cardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1058, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanelLeft, javax.swing.GroupLayout.DEFAULT_SIZE, 1123, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtFeesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFeesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFeesActionPerformed

    private void txtEmailIdAHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailIdAHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailIdAHActionPerformed

    private void txtFirstNameAHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFirstNameAHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFirstNameAHActionPerformed

    private void txtLastNameAHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLastNameAHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLastNameAHActionPerformed

    private void txtRatingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRatingsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRatingsActionPerformed

    private void btnRegisterDoctorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterDoctorActionPerformed
        // TODO add your handling code here:
        try{
        DoctorDetails d = MakeDoctor();
        AddDoctortoDB(d);
        JOptionPane.showMessageDialog(this, "Doctor Added.");
        }
        catch(NumberFormatException E){
            JOptionPane.showMessageDialog(this, "Fees and Ratings should be a number");
            return;
        }
        clearallfields();
    }//GEN-LAST:event_btnRegisterDoctorActionPerformed

    private void txtTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimeActionPerformed

    private void txtAvailableDaysActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAvailableDaysActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAvailableDaysActionPerformed

    private void txtMedicineNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMedicineNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMedicineNameActionPerformed

    private void txtAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAmountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAmountActionPerformed

    private void txtPatientIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPatientIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPatientIdActionPerformed

    private void btnAddDoctorsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDoctorsActionPerformed
        // TODO add your handling code here:
        cardPanel.setVisible(true);
        Card.show(cardPanel, "card2");
    }//GEN-LAST:event_btnAddDoctorsActionPerformed

    private void btnViewDoctorsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewDoctorsActionPerformed
        // TODO add your handling code here:
        PullDoctorstoHashMap();
        displaydoctorsintable();
        cardPanel.setVisible(true);
        Card.show(cardPanel, "card3");
    }//GEN-LAST:event_btnViewDoctorsActionPerformed

    private void btnOrderMedicinesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrderMedicinesActionPerformed
        // TODO add your handling code here:
        PullOrgstoSet();
        populatePharmacyDropdown();
        PullPhOrderstoList();
        populatePhOrderstable();
        cardPanel.setVisible(true);
        Card.show(cardPanel, "card4");
    }//GEN-LAST:event_btnOrderMedicinesActionPerformed

    private void btnInsuranceFundsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsuranceFundsActionPerformed
        // TODO add your handling code here:
        PullOrgstoSet();
        populateInsuranceDropdown();
        PullInsuranceRequeststoList();
        populateInsurancetable();
        Card.show(cardPanel, "card5");
        cardPanel.setVisible(true);
    }//GEN-LAST:event_btnInsuranceFundsActionPerformed

    private void btnDeleteAHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteAHActionPerformed
        // TODO add your handling code here:
        if(tableViewDoctorsAH.getSelectedRow() < 0)
            return;
        int Row = tableViewDoctorsAH.getSelectedRow();
        DoctorDetails d = DoctorMap.get(tableViewDoctorsAH.getValueAt(Row, 4).toString());
        RemoveDoctorfromDB(d);
        DoctorMap.remove(d.getEmail());
        JOptionPane.showMessageDialog(this, "Doctor Deleted.");
        PullDoctorstoHashMap();
        displaydoctorsintable();
    }//GEN-LAST:event_btnDeleteAHActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
        UserSystem LoginPanel = new UserSystem();
        SplitPane.removeAll();
        SplitPane.add(LoginPanel.SplitPane);
        SplitPane.repaint();
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        // TODO add your handling code here:
        PullUserstoHashMap();
        if(UserMap.get(txtPatientId.getText().toString()) == null){
            JOptionPane.showMessageDialog(this, "Patient Doesn't Exist.");
        }
        else if(checkDuplicateInsurance()){
            InsuranceRequests Ir = MakeInsRequests();
            AddInsRequeststoDB(Ir);
            txtPatientId.setText("");
            txtAmount.setText("");
            JOptionPane.showMessageDialog(this, "Request Sent.");
            PullInsuranceRequeststoList();
            populateInsurancetable();
        }
        else
            JOptionPane.showMessageDialog(this, "Duplicate entries are not allowed.");
    }//GEN-LAST:event_btnSendActionPerformed

    private void btnOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrderActionPerformed
        // TODO add your handling code here:
        PharmacyOrders Ph = MakePhOrders();
        AddPhOrderstoDB(Ph);
        txtMedicineName.setText("");
        txtQuantity.setText("");
        PullPhOrderstoList();
        populatePhOrderstable();
        JOptionPane.showMessageDialog(this, "Order Placed");
    }//GEN-LAST:event_btnOrderActionPerformed

    private void btnPhDeleteOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPhDeleteOrderActionPerformed
        // TODO add your handling code here:
        if(tblPharmacy.getSelectedRow() < 0)
            return;
        int row = tblPharmacy.getSelectedRow();
        String Pharmacy = tblPharmacy.getValueAt(row, 0).toString();
        String Medicine = tblPharmacy.getValueAt(row, 1).toString();
        String Quantity = tblPharmacy.getValueAt(row, 2).toString();
        
        Iterator itr = PhOrders.iterator();
        while(itr.hasNext()){
            PharmacyOrders Ph = (PharmacyOrders)itr.next();
            
            if(Ph.getToOrg().equalsIgnoreCase(Pharmacy) && Ph.getMedicine().equals(Medicine) && Ph.getStatus().equalsIgnoreCase("Pending") && Ph.getQuantity() == Integer.parseInt(Quantity)){
                Ph.setStatus("Deleted");
                AddPhOrderstoDB(Ph);
                PullPhOrderstoList();
                populatePhOrderstable();
            }
        }
    }//GEN-LAST:event_btnPhDeleteOrderActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddDoctors;
    private javax.swing.JPanel OrderMedicines;
    private javax.swing.JPanel ReuestInsuranceFunds;
    private javax.swing.JPanel ViewDoctors;
    private javax.swing.JButton btnAddDoctors;
    private javax.swing.JButton btnDeleteAH;
    private javax.swing.JButton btnInsuranceFunds;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnOrder;
    private javax.swing.JButton btnOrderMedicines;
    private javax.swing.JButton btnPhDeleteOrder;
    private javax.swing.JButton btnRegisterDoctor;
    private javax.swing.JButton btnSend;
    private javax.swing.JButton btnViewDoctors;
    private javax.swing.JPanel cardPanel;
    private javax.swing.JComboBox<String> cmbBoxDepartment;
    private javax.swing.JComboBox<String> cmbBoxOrgansation;
    private javax.swing.JComboBox<String> cmbPharmacyorg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelLeft;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblAddDoctors;
    private javax.swing.JLabel lblAmount;
    private javax.swing.JLabel lblAvailableDays;
    private javax.swing.JLabel lblDepartment;
    private javax.swing.JLabel lblFees;
    private javax.swing.JLabel lblFirstName;
    private javax.swing.JLabel lblHeaderHospital;
    private javax.swing.JLabel lblLastName;
    private javax.swing.JLabel lblMedicineName;
    private javax.swing.JLabel lblOrganisation;
    private javax.swing.JLabel lblPatientId;
    private javax.swing.JLabel lblQuantity;
    private javax.swing.JLabel lblRatings;
    private javax.swing.JLabel lblRatings1;
    private javax.swing.JLabel lblRatings2;
    private javax.swing.JLabel lblTime;
    private javax.swing.JTable tableViewDoctorsAH;
    private javax.swing.JTable tblInsuranceReqs;
    private javax.swing.JTable tblPharmacy;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextField txtAvailableDays;
    private javax.swing.JTextField txtEmailIdAH;
    private javax.swing.JTextField txtFees;
    private javax.swing.JTextField txtFirstNameAH;
    private javax.swing.JTextField txtLastNameAH;
    private javax.swing.JTextField txtMedicineName;
    private javax.swing.JPasswordField txtPasswordAH;
    private javax.swing.JTextField txtPatientId;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtRatings;
    private javax.swing.JTextField txtTime;
    // End of variables declaration//GEN-END:variables
}
