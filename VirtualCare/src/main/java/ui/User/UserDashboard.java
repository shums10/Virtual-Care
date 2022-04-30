/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.User;

import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.Db4oIOException;
import java.awt.CardLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.TreeMap;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.table.DefaultTableModel;
import model.AdminDetails;
import model.DoctorDetails;
import model.NGORequests;
import model.UserDetails;

/**
 *
 * @author shubhampatil
 */
public class UserDashboard extends javax.swing.JPanel {

    /**
     * Creates new form UserDashboard
     */
    public UserDashboard(JSplitPane SplitPane, UserDetails u) {
        initComponents();
        this.u = u;
        fillaccountfields();
        Card = (CardLayout) cardLayout.getLayout();
        DefaultTableModel UserMod = (DefaultTableModel) tableHospitals.getModel();
        DefaultTableModel AptMod = (DefaultTableModel) tableDoctors.getModel();
        DefaultTableModel ViewAptMod = (DefaultTableModel) tableViewAppointment.getModel();
        DefaultTableModel NGOMod = (DefaultTableModel) tblNGORequests.getModel();    
        cardLayout.setVisible(false);
        PullDoctorstoList();
        this.UserMod = UserMod;
        this.AptMod = AptMod;
        this.ViewAptMod = AptMod;
        this.NGOMod = NGOMod;
        this.SplitPane = SplitPane;
    }

    JSplitPane SplitPane;
    CardLayout Card;
    UserDetails u;
    
    DefaultTableModel NGOMod;
    DefaultTableModel UserMod;
    DefaultTableModel AptMod;
    DefaultTableModel ViewAptMod;
    ArrayList<DoctorDetails> Doctors;
    ArrayList<AdminDetails> Admins;
    ArrayList<NGORequests> NGOReqs;
    LinkedHashSet<String> Cities;
    LinkedHashSet<String> Departments;
    LinkedHashSet<String> Hospitals;
    LinkedHashSet<String> NGOOrgs;
    
    void populateHashSets(){
        LinkedHashSet<String> Cities = new LinkedHashSet<>();
        LinkedHashSet<String> Departments = new LinkedHashSet<>();
        LinkedHashSet<String> Hospitals = new LinkedHashSet<>();
        try{
            Iterator Cityitr = Doctors.iterator();
            Iterator Deptitr = Doctors.iterator();
            Iterator Htr = Doctors.iterator();

            while(Cityitr.hasNext()){
                DoctorDetails d = (DoctorDetails)Cityitr.next();
                Cities.add(d.getLocation());
            }
            while(Deptitr.hasNext()){
                DoctorDetails d = (DoctorDetails)Deptitr.next();
                Departments.add(d.getDepartment());
            }
            while(Htr.hasNext()){
                DoctorDetails d = (DoctorDetails)Htr.next();
                Departments.add(d.getOrganisation());
            }
            this.Cities = Cities;
            this.Departments = Departments;
            this.Hospitals = Hospitals;
        }
        catch(NullPointerException E){
            JOptionPane.showMessageDialog(this, "No Doctors Added.");
        }
    }
    
    void populateNGOOrgsHashSet(){
        LinkedHashSet<String> NGOOrgs = new LinkedHashSet<>();
        try{
            Iterator itr = Admins.iterator();
            while(itr.hasNext()){
                AdminDetails a = (AdminDetails)itr.next();
                if(a.getEnterprise().equalsIgnoreCase("NGO"))
                NGOOrgs.add(a.getOrganization());
            }
            this.NGOOrgs = NGOOrgs;
        }
        catch(NullPointerException E){
            JOptionPane.showMessageDialog(this, "No Admins Added.");
        }
    }
    
    void populateNGOdropdown(){
        cmbNGOOrgs.removeAllItems();
        Iterator itr = NGOOrgs.iterator();
        while(itr.hasNext()){
            cmbNGOOrgs.addItem(itr.next().toString());
        }
    }
    
    void PullAdminstoList(){
        ArrayList<AdminDetails> Admins = new ArrayList<>();

        AdminDetails a;
        try {
            List<AdminDetails> adminresult = UserSystem.Admindb.query(AdminDetails.class);
            if(adminresult.isEmpty())
                return;
            Iterator adminitr = adminresult.iterator();
            while(adminitr.hasNext()){
                a = (AdminDetails)adminitr.next();
                Admins.add(a);
            }
        }
        catch(DatabaseClosedException | Db4oIOException E){
            JOptionPane.showMessageDialog(this, "Database Error.");
        }
        this.Admins = Admins;
    }
    
    void PullNGORequeststoList(){
        ArrayList<NGORequests> NGOs = new ArrayList<>();

        NGORequests N;
        try {
            List<NGORequests> NGOresult = UserSystem.NGOdb.query(NGORequests.class);
            if(NGOresult.isEmpty())
                return;
            Iterator NGOitr = NGOresult.iterator();
            while(NGOitr.hasNext()){
                N = (NGORequests)NGOitr.next();
                if(u.getEmail().equalsIgnoreCase(N.getPatientID()))
                    NGOs.add(N);
            }
        }
        catch(DatabaseClosedException | Db4oIOException E){
            JOptionPane.showMessageDialog(this, "Database Error.");
        }
        this.NGOReqs = NGOs;
    }
    
    void populateHospitaltable(){
        try{
            Iterator itr = Doctors.iterator();
            TreeMap<Integer, DoctorDetails> filter = new TreeMap<>();
            while(itr.hasNext()){
                DoctorDetails d = (DoctorDetails)itr.next();
                if(cmbBoxTreatment.getSelectedItem().equals(d.getDepartment()) && cmbBoxCity.getSelectedItem().equals(d.getLocation())){
                    filter.put(d.getFees(), d);
                }
            }
            Collection C = filter.keySet();
            if(cmbBoxPrices.getSelectedIndex() == 0){
                Iterator Ctr = C.iterator();
                while(Ctr.hasNext()){
                    DoctorDetails d = filter.get((Integer)Ctr.next());
                    String data[] = {d.getOrganisation(), d.getLocation(), "$" + String.valueOf(d.getFees()), String.valueOf(d.getRatings())};
                    UserMod.addRow(data);
                }
            }
            else{
                ArrayList<Integer> Keys = new ArrayList<>(C);
                Collections.reverse(Keys);
                Iterator Ctr = Keys.iterator();
                while(Ctr.hasNext()){
                    DoctorDetails d = filter.get((Integer)Ctr.next());
                    String data[] = {d.getOrganisation(), d.getLocation(), "$" + String.valueOf(d.getFees()), String.valueOf(d.getRatings())};
                    UserMod.addRow(data);
                }
            }
        }
        catch(NullPointerException E){
            JOptionPane.showMessageDialog(this, "No Doctors Added.");
        }
    }
    
    void populateNGOtable(){
        NGOMod.setRowCount(0);
        try{
            Iterator itr = NGOReqs.iterator();
            while(itr.hasNext()){
                NGORequests N = (NGORequests)itr.next();

                String data[] = {N.getToNGOOrg(), String.valueOf(N.getAmount()), N.getStatus()};
                NGOMod.addRow(data);
            }
        }
        catch(NullPointerException E){
            JOptionPane.showMessageDialog(this, "No Active NGO Requests available.");
        }
    }
    
    void populateAppointmentstable(){
        AptMod.setRowCount(0);
        int Row = tableHospitals.getSelectedRow();
        String Hospital = tableHospitals.getValueAt(Row, 0).toString();
        String City = tableHospitals.getValueAt(Row, 1).toString();
        try{
            Iterator itr = Doctors.iterator();
            while(itr.hasNext()){
                DoctorDetails d = (DoctorDetails)itr.next();
                if(d.getLocation().equalsIgnoreCase(City) && d.getOrganisation().equalsIgnoreCase(Hospital)){
                    String data[] = {d.getFirstName() + d.getLastName(), d.getTime(), String.valueOf(d.getFees()), String.valueOf(d.getRatings())};
                    AptMod.addRow(data);
                }
            }
        }
        catch(NullPointerException E){
            JOptionPane.showMessageDialog(this, "No Doctors Available.");
        }
    }
    
    void CheckBlankFields(){
        if(txtCityUAD.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this, "Fields can't be blank");
        }
        else if(txtDobUAD.getDate().toString().equals("")){
            JOptionPane.showMessageDialog(this, "Fields can't be blank");
        }
        else if(txtEmailIdUAD.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this, "Fields can't be blank");
        }
        else if(txtFirstNameUAD.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this, "Fields can't be blank");
        }
        else if(txtPhoneNumberAD.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this, "Fields can't be blank");
        }
        else if(txtLastNameUAD.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this, "Fields can't be blank");
        }
        else if(txtPinCodeUAD.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this, "Fields can't be blank");
        }
        else if(txtStreetUAD.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this, "Fields can't be blank");
        }
        else if(txtNewPasswordUAD.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this, "Fields can't be blank");
        }
    }
    
    void populateviewappointments(){
        ViewAptMod.setRowCount(0);
        ArrayList<DoctorDetails> Docs = new ArrayList<>();
        try{
            Docs = u.getAppointments();

            Iterator itr = Docs.iterator();

            while(itr.hasNext()){
                DoctorDetails d = (DoctorDetails)itr.next();
                String data[] = {d.getOrganisation(), d.getFirstName() + d.getLastName(), d.getLocation(), d.getTime()};
                ViewAptMod.addRow(data);
            }
        }
        catch(NullPointerException N){
            JOptionPane.showMessageDialog(this, "No Appointments Available.");
        }
    }
    
    void populatedropdowns(){
        try{
            Iterator Ctr = Cities.iterator();
            Iterator Dtr = Departments.iterator();

            while(Ctr.hasNext()){
                cmbBoxCity.addItem(Ctr.next().toString());
            }
            while(Dtr.hasNext()){
                cmbBoxTreatment.addItem(Dtr.next().toString());
            }
        }
        catch(NullPointerException E){
            JOptionPane.showMessageDialog(this, "No Doctors Available.");
        }
    }
    
    void PullDoctorstoList(){
        ArrayList<DoctorDetails> Doctors = new ArrayList<>();
        DoctorDetails d;
        
        try {
            List<DoctorDetails> doctorresult = UserSystem.Doctordb.query(DoctorDetails.class);
            if(doctorresult.isEmpty())
                return;
            Iterator doctoritr = doctorresult.iterator();
            while(doctoritr.hasNext()){
                d = (DoctorDetails)doctoritr.next();
                Doctors.add(d);
            }
        }
        catch(DatabaseClosedException | Db4oIOException E){
            JOptionPane.showMessageDialog(this, "Database Error.");
        }
        this.Doctors = Doctors;
    }
    
    void clearAllfields(){
        txtOldPasswordUAD.setText("");
        txtNewPasswordUAD.setText("");
        txtFirstNameUAD.setText("");
        txtLastNameUAD.setText("");
        txtDobUAD.setDate(null);
        txtPhoneNumberAD.setText("");
        txtStreetUAD.setText("");
        txtCityUAD.setText("");
        txtPinCodeUAD.setText("");
        txtEmailIdUAD.setText("");
    }
    
    void fillaccountfields(){
        clearAllfields();
        txtFirstNameUAD.setText(u.getFirstName());
        txtLastNameUAD.setText(u.getLastName());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        try{
            txtDobUAD.setDate(formatter.parse(u.getDOB()));
        }
        catch(ParseException P){
            System.out.println("Unable to parse DoB");
        }
        if(String.valueOf(u.getPhonenumber()).equals(0))
            txtPhoneNumberAD.setText(String.valueOf(u.getPhonenumber()));
        else
            txtPhoneNumberAD.setText("");
        txtStreetUAD.setText(u.getStreet());
        txtCityUAD.setText(u.getCity());
        txtPinCodeUAD.setText(String.valueOf(u.getPinCode()));
        txtEmailIdUAD.setText(u.getEmail());

    }
    
    public static void AddNGOtoDB(NGORequests N){
        try {
            UserSystem.NGOdb.store(N);
            System.out.println("Stored: " + N.getToNGOOrg());
        }
        catch(DatabaseClosedException | Db4oIOException E){
            System.out.println("Error in storing:  " + N.getToNGOOrg());
        }
    }
    
    NGORequests MakeNGORequests(){
        NGORequests N = new NGORequests();
        N.setAmount(Integer.valueOf(txtRequestAmount.getText()));
        N.setAnnualIncome(Integer.valueOf(txtAnnualIncome.getText()));
        N.setExplaination(txtExplanation.getText());
        N.setPatientID(u.getEmail());
        N.setToNGOOrg(cmbNGOOrgs.getSelectedItem().toString());
        N.setStatus("Pending");
        return N;
    }
    
    void BookAppointment(){
        int Row = tableDoctors.getSelectedRow();
        String FullName = tableDoctors.getValueAt(Row, 0).toString();
        try{
            Iterator itr = Doctors.iterator();
            while(itr.hasNext()){
                DoctorDetails d = (DoctorDetails)itr.next();
                if(FullName.equalsIgnoreCase(d.getFirstName()+d.getLastName())){
                    u.AddAppointments(d);
                    d.AddAppointments(u);
                    UserSystem.Doctordb.store(d);
                    UserSystem.Userdb.store(u);
                }
            }
        }
        catch(NullPointerException E){
            JOptionPane.showMessageDialog(this, "No doctors Added.");
        }
    }
    
    void CheckBlankFieldsNGO(){
        if(txtExplanation.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this, "Fields can't be blank");
        }
        else if(txtAnnualIncome.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this, "Fields can't be blank");
        }
        else if(txtRequestAmount.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this, "Fields can't be blank");
        }
    }
    
    void viewprescription(){
        int row = tableViewAppointment.getSelectedRow();
        String Doctor = tableViewAppointment.getValueAt(row, 1).toString();
        String Hospital = tableViewAppointment.getValueAt(row, 0).toString();
        try{
            Iterator itr = Doctors.iterator();
            while(itr.hasNext()){
                DoctorDetails d = (DoctorDetails)itr.next();

                if((d.getFirstName()+d.getLastName()).equalsIgnoreCase(Doctor) && d.getOrganisation().equalsIgnoreCase(Hospital))
                    JOptionPane.showMessageDialog(this, d.GetPrescription(u));
            }
        }
        catch(NullPointerException E){
            JOptionPane.showMessageDialog(this, "No Prescription Available.");
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnViewAppointments = new javax.swing.JButton();
        btnAccount = new javax.swing.JButton();
        btnDashboard = new javax.swing.JButton();
        btnRequestFund = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        cardLayout = new javax.swing.JPanel();
        AccountDetails = new javax.swing.JPanel();
        lblReenterPassword = new javax.swing.JLabel();
        lblFirstName = new javax.swing.JLabel();
        lblAge = new javax.swing.JLabel();
        lblEmailId = new javax.swing.JLabel();
        lblStreet = new javax.swing.JLabel();
        lblLastName = new javax.swing.JLabel();
        lblCity1 = new javax.swing.JLabel();
        lblCity2 = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        lblVirtualCare1 = new javax.swing.JLabel();
        txtCityUAD = new javax.swing.JTextField();
        txtEmailIdUAD = new javax.swing.JTextField();
        txtStreetUAD = new javax.swing.JTextField();
        txtFirstNameUAD = new javax.swing.JTextField();
        txtPinCodeUAD = new javax.swing.JTextField();
        txtLastNameUAD = new javax.swing.JTextField();
        txtDobUAD = new com.toedter.calendar.JDateChooser();
        btnUpdateUAD = new javax.swing.JButton();
        lblPhoneNumber = new javax.swing.JLabel();
        txtPhoneNumberAD = new javax.swing.JTextField();
        txtNewPasswordUAD = new javax.swing.JPasswordField();
        txtOldPasswordUAD = new javax.swing.JPasswordField();
        ViewAppointments = new javax.swing.JPanel();
        lblVirtualCare2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableViewAppointment = new javax.swing.JTable();
        btnJoinVirtually = new javax.swing.JButton();
        ViewPrescription = new javax.swing.JButton();
        lblPrescription = new javax.swing.JLabel();
        UserDB = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblVirtualCare = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableHospitals = new javax.swing.JTable();
        lblPrices = new javax.swing.JLabel();
        cmbBoxCity = new javax.swing.JComboBox<>();
        cmbBoxTreatment = new javax.swing.JComboBox<>();
        lblCity = new javax.swing.JLabel();
        cmbBoxPrices = new javax.swing.JComboBox<>();
        btnView = new javax.swing.JButton();
        BookAppointment = new javax.swing.JPanel();
        lblHeaderHospital = new javax.swing.JLabel();
        lblHospitalLogo = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableDoctors = new javax.swing.JTable();
        btnBookAppointment = new javax.swing.JButton();
        lblVirtualCare3 = new javax.swing.JLabel();
        FundRequest = new javax.swing.JPanel();
        lblVirtualCare4 = new javax.swing.JLabel();
        lblAnnualIncome = new javax.swing.JLabel();
        txtAnnualIncome = new javax.swing.JTextField();
        lblExplanation = new javax.swing.JLabel();
        txtExplanation = new javax.swing.JTextField();
        btnRequest = new javax.swing.JButton();
        lblRequestAmount = new javax.swing.JLabel();
        txtRequestAmount = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cmbNGOOrgs = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblNGORequests = new javax.swing.JTable();

        setBackground(new java.awt.Color(102, 102, 255));
        setMinimumSize(new java.awt.Dimension(1115, 925));

        jPanel1.setBackground(new java.awt.Color(0, 102, 204));

        btnViewAppointments.setText("View Appointments");
        btnViewAppointments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewAppointmentsActionPerformed(evt);
            }
        });

        btnAccount.setText("Account");
        btnAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAccountActionPerformed(evt);
            }
        });

        btnDashboard.setText("Dashboard");
        btnDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDashboardActionPerformed(evt);
            }
        });

        btnRequestFund.setText("Request Fund Help");
        btnRequestFund.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRequestFundActionPerformed(evt);
            }
        });

        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnRequestFund)
                            .addComponent(btnDashboard)
                            .addComponent(btnAccount)
                            .addComponent(btnViewAppointments)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(btnLogout)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(btnAccount)
                .addGap(18, 18, 18)
                .addComponent(btnViewAppointments)
                .addGap(26, 26, 26)
                .addComponent(btnDashboard)
                .addGap(31, 31, 31)
                .addComponent(btnRequestFund)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 436, Short.MAX_VALUE)
                .addComponent(btnLogout)
                .addGap(121, 121, 121))
        );

        cardLayout.setLayout(new java.awt.CardLayout());

        AccountDetails.setMinimumSize(new java.awt.Dimension(823, 697));

        lblReenterPassword.setText("New Password:");

        lblFirstName.setText("First Name:");

        lblAge.setText("Date of Birth");

        lblEmailId.setText("Email Id:");

        lblStreet.setText("Street:");

        lblLastName.setText("Last Name:");

        lblCity1.setText("PinCode:");

        lblCity2.setText("City:");

        lblPassword.setText("Old Password:");

        lblVirtualCare1.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lblVirtualCare1.setText("VIRTUAL CARE");

        txtCityUAD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCityUADActionPerformed(evt);
            }
        });

        txtEmailIdUAD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailIdUADActionPerformed(evt);
            }
        });

        txtStreetUAD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStreetUADActionPerformed(evt);
            }
        });

        txtFirstNameUAD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFirstNameUADActionPerformed(evt);
            }
        });

        txtPinCodeUAD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPinCodeUADActionPerformed(evt);
            }
        });

        txtLastNameUAD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLastNameUADActionPerformed(evt);
            }
        });

        btnUpdateUAD.setText("UPDATE");
        btnUpdateUAD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateUADActionPerformed(evt);
            }
        });

        lblPhoneNumber.setText("Phone Number:");

        txtPhoneNumberAD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPhoneNumberADActionPerformed(evt);
            }
        });

        txtNewPasswordUAD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNewPasswordUADActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AccountDetailsLayout = new javax.swing.GroupLayout(AccountDetails);
        AccountDetails.setLayout(AccountDetailsLayout);
        AccountDetailsLayout.setHorizontalGroup(
            AccountDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AccountDetailsLayout.createSequentialGroup()
                .addGroup(AccountDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AccountDetailsLayout.createSequentialGroup()
                        .addGap(270, 270, 270)
                        .addGroup(AccountDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblFirstName)
                            .addComponent(lblLastName)
                            .addComponent(lblStreet)
                            .addComponent(lblCity2)
                            .addComponent(lblCity1)
                            .addComponent(lblEmailId)
                            .addComponent(lblPassword)
                            .addComponent(lblReenterPassword)
                            .addComponent(lblPhoneNumber)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AccountDetailsLayout.createSequentialGroup()
                        .addGap(187, 187, 187)
                        .addComponent(lblAge)))
                .addGap(47, 47, 47)
                .addGroup(AccountDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(AccountDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(AccountDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(AccountDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(AccountDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtEmailIdUAD, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(AccountDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtLastNameUAD, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                                        .addComponent(txtFirstNameUAD)))
                                .addComponent(txtDobUAD, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtPinCodeUAD, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtCityUAD, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtStreetUAD, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtPhoneNumberAD, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(txtNewPasswordUAD, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtOldPasswordUAD, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(269, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AccountDetailsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(AccountDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AccountDetailsLayout.createSequentialGroup()
                        .addComponent(lblVirtualCare1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(350, 350, 350))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AccountDetailsLayout.createSequentialGroup()
                        .addComponent(btnUpdateUAD)
                        .addGap(348, 348, 348))))
        );
        AccountDetailsLayout.setVerticalGroup(
            AccountDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AccountDetailsLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblVirtualCare1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74)
                .addGroup(AccountDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AccountDetailsLayout.createSequentialGroup()
                        .addComponent(txtFirstNameUAD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(AccountDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtLastNameUAD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblLastName)))
                    .addComponent(lblFirstName))
                .addGap(19, 19, 19)
                .addGroup(AccountDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDobUAD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAge))
                .addGap(14, 14, 14)
                .addGroup(AccountDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPhoneNumber)
                    .addComponent(txtPhoneNumberAD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(AccountDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStreet)
                    .addComponent(txtStreetUAD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AccountDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCity2)
                    .addComponent(txtCityUAD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AccountDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCity1)
                    .addComponent(txtPinCodeUAD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AccountDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmailId)
                    .addComponent(txtEmailIdUAD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AccountDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword)
                    .addComponent(txtOldPasswordUAD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(AccountDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblReenterPassword)
                    .addComponent(txtNewPasswordUAD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(btnUpdateUAD)
                .addGap(28, 28, 28))
        );

        cardLayout.add(AccountDetails, "card7");

        ViewAppointments.setMinimumSize(new java.awt.Dimension(823, 697));
        ViewAppointments.setPreferredSize(new java.awt.Dimension(823, 697));

        lblVirtualCare2.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lblVirtualCare2.setText("VIRTUAL CARE");

        tableViewAppointment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "HospitalName", "Doctor", "Location", "Time"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tableViewAppointment);

        btnJoinVirtually.setText("Join Virtually");

        ViewPrescription.setText("View Prescription");
        ViewPrescription.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ViewPrescriptionActionPerformed(evt);
            }
        });

        lblPrescription.setBackground(new java.awt.Color(204, 204, 255));

        javax.swing.GroupLayout ViewAppointmentsLayout = new javax.swing.GroupLayout(ViewAppointments);
        ViewAppointments.setLayout(ViewAppointmentsLayout);
        ViewAppointmentsLayout.setHorizontalGroup(
            ViewAppointmentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewAppointmentsLayout.createSequentialGroup()
                .addGroup(ViewAppointmentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(ViewAppointmentsLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(btnJoinVirtually)
                        .addGap(38, 38, 38)
                        .addComponent(ViewPrescription)
                        .addGap(0, 465, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ViewAppointmentsLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblVirtualCare2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(264, 264, 264))
            .addGroup(ViewAppointmentsLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(lblPrescription, javax.swing.GroupLayout.PREFERRED_SIZE, 602, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ViewAppointmentsLayout.setVerticalGroup(
            ViewAppointmentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewAppointmentsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblVirtualCare2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(ViewAppointmentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ViewAppointmentsLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ViewAppointmentsLayout.createSequentialGroup()
                        .addGap(162, 162, 162)
                        .addGroup(ViewAppointmentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnJoinVirtually)
                            .addComponent(ViewPrescription))))
                .addGap(28, 28, 28)
                .addComponent(lblPrescription, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(180, Short.MAX_VALUE))
        );

        cardLayout.add(ViewAppointments, "card4");

        UserDB.setMinimumSize(new java.awt.Dimension(823, 697));
        UserDB.setPreferredSize(new java.awt.Dimension(823, 697));

        jLabel1.setText("Department");

        lblVirtualCare.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lblVirtualCare.setText("VIRTUAL CARE");

        tableHospitals.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Hospital", "City", "Prices", "Ratings"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableHospitals);

        lblPrices.setText("Prices");

        cmbBoxCity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBoxCityActionPerformed(evt);
            }
        });

        cmbBoxTreatment.setToolTipText("");
        cmbBoxTreatment.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cmbBoxTreatment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBoxTreatmentActionPerformed(evt);
            }
        });

        lblCity.setText("City");

        cmbBoxPrices.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lowest First", "Highest First" }));
        cmbBoxPrices.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBoxPricesActionPerformed(evt);
            }
        });

        btnView.setText("View");
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout UserDBLayout = new javax.swing.GroupLayout(UserDB);
        UserDB.setLayout(UserDBLayout);
        UserDBLayout.setHorizontalGroup(
            UserDBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UserDBLayout.createSequentialGroup()
                .addContainerGap(718, Short.MAX_VALUE)
                .addComponent(btnView)
                .addGap(50, 50, 50))
            .addGroup(UserDBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UserDBLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 663, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(39, 39, 39))
                .addGroup(UserDBLayout.createSequentialGroup()
                    .addGroup(UserDBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(UserDBLayout.createSequentialGroup()
                            .addGap(295, 295, 295)
                            .addComponent(lblVirtualCare))
                        .addGroup(UserDBLayout.createSequentialGroup()
                            .addGap(62, 62, 62)
                            .addGroup(UserDBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cmbBoxTreatment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1)))
                        .addGroup(UserDBLayout.createSequentialGroup()
                            .addGap(93, 93, 93)
                            .addGroup(UserDBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cmbBoxCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblCity))
                            .addGap(18, 18, 18)
                            .addGroup(UserDBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cmbBoxPrices, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblPrices))))
                    .addContainerGap(416, Short.MAX_VALUE)))
        );
        UserDBLayout.setVerticalGroup(
            UserDBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UserDBLayout.createSequentialGroup()
                .addContainerGap(665, Short.MAX_VALUE)
                .addComponent(btnView)
                .addContainerGap())
            .addGroup(UserDBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(UserDBLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblVirtualCare)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbBoxTreatment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jLabel1)
                    .addGap(18, 18, 18)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addGroup(UserDBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblCity)
                        .addComponent(lblPrices))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(UserDBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbBoxCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbBoxPrices, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap()))
        );

        cardLayout.add(UserDB, "card2");

        BookAppointment.setMinimumSize(new java.awt.Dimension(823, 697));
        BookAppointment.setPreferredSize(new java.awt.Dimension(823, 697));

        lblHeaderHospital.setText("Welcome to ");

        lblHospitalLogo.setBackground(new java.awt.Color(204, 153, 0));

        tableDoctors.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Doctor", "Time", "Price", "Ratings"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tableDoctors);

        btnBookAppointment.setText("Book Appointment");
        btnBookAppointment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBookAppointmentActionPerformed(evt);
            }
        });

        lblVirtualCare3.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lblVirtualCare3.setText("VIRTUAL CARE");

        javax.swing.GroupLayout BookAppointmentLayout = new javax.swing.GroupLayout(BookAppointment);
        BookAppointment.setLayout(BookAppointmentLayout);
        BookAppointmentLayout.setHorizontalGroup(
            BookAppointmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BookAppointmentLayout.createSequentialGroup()
                .addGroup(BookAppointmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BookAppointmentLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(lblHospitalLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(BookAppointmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(BookAppointmentLayout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addComponent(lblHeaderHospital, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(BookAppointmentLayout.createSequentialGroup()
                                .addGap(148, 148, 148)
                                .addComponent(lblVirtualCare3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(BookAppointmentLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 766, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(BookAppointmentLayout.createSequentialGroup()
                        .addGap(294, 294, 294)
                        .addComponent(btnBookAppointment)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        BookAppointmentLayout.setVerticalGroup(
            BookAppointmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BookAppointmentLayout.createSequentialGroup()
                .addGroup(BookAppointmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BookAppointmentLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(lblHospitalLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(BookAppointmentLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblVirtualCare3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(lblHeaderHospital, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnBookAppointment)
                .addContainerGap(235, Short.MAX_VALUE))
        );

        cardLayout.add(BookAppointment, "card5");

        FundRequest.setMinimumSize(new java.awt.Dimension(823, 697));
        FundRequest.setPreferredSize(new java.awt.Dimension(823, 697));

        lblVirtualCare4.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lblVirtualCare4.setText("VIRTUAL CARE");

        lblAnnualIncome.setText("Annual Income:");

        lblExplanation.setText("Explanation for fund request:");

        txtExplanation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtExplanationActionPerformed(evt);
            }
        });

        btnRequest.setText("Request");
        btnRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRequestActionPerformed(evt);
            }
        });

        lblRequestAmount.setText("Request Amount:");

        jLabel2.setText("Organisation:");

        cmbNGOOrgs.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        tblNGORequests.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Organization", "Request Amount", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tblNGORequests);

        javax.swing.GroupLayout FundRequestLayout = new javax.swing.GroupLayout(FundRequest);
        FundRequest.setLayout(FundRequestLayout);
        FundRequestLayout.setHorizontalGroup(
            FundRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FundRequestLayout.createSequentialGroup()
                .addGap(185, 185, 185)
                .addGroup(FundRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblRequestAmount)
                    .addComponent(lblExplanation)
                    .addComponent(lblAnnualIncome))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 41, Short.MAX_VALUE)
                .addGroup(FundRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtAnnualIncome, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtExplanation, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRequestAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
            .addGroup(FundRequestLayout.createSequentialGroup()
                .addGroup(FundRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FundRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnRequest)
                        .addGroup(FundRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(FundRequestLayout.createSequentialGroup()
                                .addGap(315, 315, 315)
                                .addComponent(lblVirtualCare4))
                            .addGroup(FundRequestLayout.createSequentialGroup()
                                .addGap(275, 275, 275)
                                .addComponent(jLabel2)
                                .addGap(47, 47, 47)
                                .addComponent(cmbNGOOrgs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(FundRequestLayout.createSequentialGroup()
                        .addGap(185, 185, 185)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        FundRequestLayout.setVerticalGroup(
            FundRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FundRequestLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblVirtualCare4)
                .addGap(39, 39, 39)
                .addGroup(FundRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblExplanation)
                    .addComponent(txtExplanation, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(FundRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAnnualIncome)
                    .addComponent(txtAnnualIncome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(FundRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRequestAmount)
                    .addComponent(txtRequestAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(FundRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbNGOOrgs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnRequest)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        cardLayout.add(FundRequest, "card6");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(883, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(280, Short.MAX_VALUE)
                    .addComponent(cardLayout, javax.swing.GroupLayout.PREFERRED_SIZE, 823, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(106, Short.MAX_VALUE)
                    .addComponent(cardLayout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(106, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmbBoxTreatmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBoxTreatmentActionPerformed
        // TODO add your handling code here:
        populateHospitaltable();
    }//GEN-LAST:event_cmbBoxTreatmentActionPerformed

    private void btnViewAppointmentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewAppointmentsActionPerformed
        // TODO add your handling code here:
        populateviewappointments();
        cardLayout.setVisible(true);
        Card.show(cardLayout, "card4");
    }//GEN-LAST:event_btnViewAppointmentsActionPerformed

    private void btnAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccountActionPerformed
        // TODO add your handling code here:
        fillaccountfields();
        cardLayout.setVisible(true);
        Card.show(cardLayout, "card7");
    }//GEN-LAST:event_btnAccountActionPerformed

    private void txtCityUADActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCityUADActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCityUADActionPerformed

    private void txtEmailIdUADActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailIdUADActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailIdUADActionPerformed

    private void txtStreetUADActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStreetUADActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStreetUADActionPerformed

    private void txtFirstNameUADActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFirstNameUADActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFirstNameUADActionPerformed

    private void txtPinCodeUADActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPinCodeUADActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPinCodeUADActionPerformed

    private void txtLastNameUADActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLastNameUADActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLastNameUADActionPerformed

    private void txtPhoneNumberADActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPhoneNumberADActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPhoneNumberADActionPerformed

    private void txtExplanationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtExplanationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtExplanationActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
        UserSystem LoginPanel = new UserSystem();
        SplitPane.removeAll();
        SplitPane.add(LoginPanel.SplitPane);
        SplitPane.repaint();
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDashboardActionPerformed
        // TODO add your handling code here:
        PullDoctorstoList();
        populateHashSets();
        populatedropdowns();
        cardLayout.setVisible(true);
        Card.show(cardLayout, "card2");
    }//GEN-LAST:event_btnDashboardActionPerformed

    private void btnRequestFundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRequestFundActionPerformed
        // TODO add your handling code here
        PullAdminstoList();
        populateNGOOrgsHashSet();
        populateNGOdropdown();
        PullNGORequeststoList();
        populateNGOtable();
        cardLayout.setVisible(true);
        Card.show(cardLayout, "card6");
    }//GEN-LAST:event_btnRequestFundActionPerformed

    private void btnUpdateUADActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateUADActionPerformed
        // TODO add your handling code here:
        CheckBlankFields();
            try{
            if(Arrays.toString(txtOldPasswordUAD.getPassword()).equals(u.getPassword())){
                if(!txtFirstNameUAD.getText().trim().equals(""))
                    u.setFirstName(txtFirstNameUAD.getText().trim());
                if(!txtLastNameUAD.getText().trim().equals(""))
                    u.setLastName(txtLastNameUAD.getText().trim()); 
                if(!txtDobUAD.getDateFormatString().trim().equals("")){
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
                    u.setDOB(formatter.format(txtDobUAD.getDate()));
                }
                if(!txtPhoneNumberAD.getText().trim().equals(""))
                    u.setPhonenumber(Long.parseLong(txtPhoneNumberAD.getText().trim()));
                if(!txtStreetUAD.getText().trim().equals(""))
                    u.setStreet(txtStreetUAD.getText().trim());
                if(!txtCityUAD.getText().trim().equals(""))
                    u.setCity(txtCityUAD.getText().trim());
                if(!txtPinCodeUAD.getText().trim().equals(""))
                    u.setPinCode(Integer.parseInt(txtPinCodeUAD.getText().trim()));
                if(!txtEmailIdUAD.getText().trim().equals(""))
                    u.setEmail(txtEmailIdUAD.getText().trim());
                if(!(new String(txtNewPasswordUAD.getPassword()).equals("")))
                    u.setPassword(Arrays.toString(txtNewPasswordUAD.getPassword()));
            
                SignUp.AddUsertoDB(u);
                JOptionPane.showMessageDialog(this, "Details Updated.");
            }
            else
                JOptionPane.showMessageDialog(this, "Incorrect Password.");
            }
            catch(NumberFormatException E){
                JOptionPane.showMessageDialog(this, "PinCode should be a number.");
            }
        

    }//GEN-LAST:event_btnUpdateUADActionPerformed

    private void txtNewPasswordUADActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNewPasswordUADActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNewPasswordUADActionPerformed

    private void cmbBoxPricesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBoxPricesActionPerformed
        // TODO add your handling code here:
        populateHospitaltable();
    }//GEN-LAST:event_cmbBoxPricesActionPerformed

    private void cmbBoxCityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBoxCityActionPerformed
        // TODO add your handling code here:
        populateHospitaltable();
    }//GEN-LAST:event_cmbBoxCityActionPerformed

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        // TODO add your handling code here:
        if(tableHospitals.getSelectedRow() < 0)
            return;
        String Hospital = tableHospitals.getValueAt(tableHospitals.getSelectedRow(), 0).toString();
        lblHeaderHospital.setText("Welcome to" + Hospital);
        PullDoctorstoList();
        populateAppointmentstable();
        cardLayout.setVisible(true);
        Card.show(cardLayout, "card5");
    }//GEN-LAST:event_btnViewActionPerformed

    private void btnBookAppointmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBookAppointmentActionPerformed
        // TODO add your handling code here:
        if(tableDoctors.getSelectedRow() < 0)
            return;
        try{
        BookAppointment();
        JOptionPane.showMessageDialog(this, "Appointment Booked.");
        }
        catch(NullPointerException E){
            JOptionPane.showMessageDialog(this, "No Doctors Added");
        }

    }//GEN-LAST:event_btnBookAppointmentActionPerformed

    private void btnRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRequestActionPerformed
        // TODO add your handling code here:
        CheckBlankFieldsNGO();
        try{
            NGORequests N = MakeNGORequests();
            AddNGOtoDB(N);
        }
        catch(NumberFormatException E){
            JOptionPane.showMessageDialog(this, "Amount should be a number.");
        }
        PullNGORequeststoList();
        populateNGOtable();
        JOptionPane.showMessageDialog(this, "Request Sent");
    }//GEN-LAST:event_btnRequestActionPerformed

    private void ViewPrescriptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ViewPrescriptionActionPerformed
        // TODO add your handling code here:
        viewprescription();
    }//GEN-LAST:event_ViewPrescriptionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AccountDetails;
    private javax.swing.JPanel BookAppointment;
    private javax.swing.JPanel FundRequest;
    private javax.swing.JPanel UserDB;
    private javax.swing.JPanel ViewAppointments;
    private javax.swing.JButton ViewPrescription;
    private javax.swing.JButton btnAccount;
    private javax.swing.JButton btnBookAppointment;
    private javax.swing.JButton btnDashboard;
    private javax.swing.JButton btnJoinVirtually;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnRequest;
    private javax.swing.JButton btnRequestFund;
    private javax.swing.JButton btnUpdateUAD;
    private javax.swing.JButton btnView;
    private javax.swing.JButton btnViewAppointments;
    private javax.swing.JPanel cardLayout;
    private javax.swing.JComboBox<String> cmbBoxCity;
    private javax.swing.JComboBox<String> cmbBoxPrices;
    private javax.swing.JComboBox<String> cmbBoxTreatment;
    private javax.swing.JComboBox<String> cmbNGOOrgs;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblAge;
    private javax.swing.JLabel lblAnnualIncome;
    private javax.swing.JLabel lblCity;
    private javax.swing.JLabel lblCity1;
    private javax.swing.JLabel lblCity2;
    private javax.swing.JLabel lblEmailId;
    private javax.swing.JLabel lblExplanation;
    private javax.swing.JLabel lblFirstName;
    private javax.swing.JLabel lblHeaderHospital;
    private javax.swing.JLabel lblHospitalLogo;
    private javax.swing.JLabel lblLastName;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblPhoneNumber;
    private javax.swing.JLabel lblPrescription;
    private javax.swing.JLabel lblPrices;
    private javax.swing.JLabel lblReenterPassword;
    private javax.swing.JLabel lblRequestAmount;
    private javax.swing.JLabel lblStreet;
    private javax.swing.JLabel lblVirtualCare;
    private javax.swing.JLabel lblVirtualCare1;
    private javax.swing.JLabel lblVirtualCare2;
    private javax.swing.JLabel lblVirtualCare3;
    private javax.swing.JLabel lblVirtualCare4;
    private javax.swing.JTable tableDoctors;
    private javax.swing.JTable tableHospitals;
    private javax.swing.JTable tableViewAppointment;
    private javax.swing.JTable tblNGORequests;
    private javax.swing.JTextField txtAnnualIncome;
    private javax.swing.JTextField txtCityUAD;
    private com.toedter.calendar.JDateChooser txtDobUAD;
    private javax.swing.JTextField txtEmailIdUAD;
    private javax.swing.JTextField txtExplanation;
    private javax.swing.JTextField txtFirstNameUAD;
    private javax.swing.JTextField txtLastNameUAD;
    private javax.swing.JPasswordField txtNewPasswordUAD;
    private javax.swing.JPasswordField txtOldPasswordUAD;
    private javax.swing.JTextField txtPhoneNumberAD;
    private javax.swing.JTextField txtPinCodeUAD;
    private javax.swing.JTextField txtRequestAmount;
    private javax.swing.JTextField txtStreetUAD;
    // End of variables declaration//GEN-END:variables
}
