/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.User;

import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.Db4oIOException;
import java.awt.CardLayout;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.table.DefaultTableModel;
import model.AdminDetails;
import model.DoctorDetails;
import model.NGORequests;
import model.UserDetails;
import ui.Hospital.AdminHospital;

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
        PullAdminstoList();
        populateCityHashSet();
        this.UserMod = UserMod;
        this.AptMod = AptMod;
        this.ViewAptMod = ViewAptMod;
        this.NGOMod = NGOMod;
        this.SplitPane = SplitPane;
        DisplayImage();
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
    LinkedHashSet<String> NGOOrgs;
    
    void populateCityHashSet(){
        LinkedHashSet<String> Cities = new LinkedHashSet<>();
        try{
            Iterator itr = Admins.iterator();
            
            while(itr.hasNext()){
                AdminDetails a = (AdminDetails)itr.next();
                if(a.getEnterprise().equalsIgnoreCase("Hospital")){
                   Cities.add(a.getLocation()); 
                }
            }
            this.Cities = Cities;
        }
        catch(NullPointerException E){
            JOptionPane.showMessageDialog(this, "No Hospitals Added.");
        }
        
    }
    
    void populateDepartmentsHashSet(String Hospital){
        LinkedHashSet<String> Departments = new LinkedHashSet<>();
        try{
            Iterator Deptitr = Doctors.iterator();

            while(Deptitr.hasNext()){
                DoctorDetails d = (DoctorDetails)Deptitr.next();
                if(d.getOrganisation().equalsIgnoreCase(Hospital))
                    Departments.add(d.getDepartment());
            }
            this.Departments = Departments;
        }
        catch(NullPointerException E){
            JOptionPane.showMessageDialog(this, "No Doctors Added.");
        }
    }
    
    void populatedepartmentsdropdown(){
        cmbDepartment.removeAllItems();
        Iterator itr = Departments.iterator();
        while(itr.hasNext()){
            cmbDepartment.addItem(itr.next().toString());
        }

    }
    
     private void DisplayImage() {
     Path currentRelativePath = Paths.get("");
     String s = currentRelativePath.toAbsolutePath().toString();
     
     // Logout Button
     String FilePath1 = s+"/images/bookappointment.png.";
     // URL imgLogin = getClass().getResource(FilePath1 );
     ImageIcon login1 = new ImageIcon(FilePath1);
     btnBookAppointment.setIcon(login1);
     
     String FilePath2 = s+"/images/HC11.gif";
     // URL imgLogin = getClass().getResource(FilePath1 );
     ImageIcon login2 = new ImageIcon(FilePath2);
     lblViewAppointments.setIcon(login2);
     
     String FilePath3 = s+"/images/HC2.gif";
     // URL imgLogin = getClass().getResource(FilePath1 );
     ImageIcon login3 = new ImageIcon(FilePath3);
     lblUserDB.setIcon(login3);
     
     String FilePath4 = s+"/images/HC7.gif";
     // URL imgLogin = getClass().getResource(FilePath1 );
     ImageIcon login4 = new ImageIcon(FilePath4);
     lblBookAppointment.setIcon(login4);
     
     
     String FilePath5 = s+"/images/fund.gif";
     // URL imgLogin = getClass().getResource(FilePath1 );
     ImageIcon login5 = new ImageIcon(FilePath5);
     lblFundRequest.setIcon(login5);
     
     String FilePath6 = s+"/images/HC12.gif";
     // URL imgLogin = getClass().getResource(FilePath1 );
     ImageIcon login6 = new ImageIcon(FilePath6);
     lblAccountDetails.setIcon(login6);
     
     
     String FilePath7 = s+"/images/HC15.jpg";
     // URL imgLogin = getClass().getResource(FilePath1 );
     ImageIcon login7 = new ImageIcon(FilePath7);
     lblcardbg.setIcon(login7);
     
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
        UserMod.setRowCount(0);
        PullAdminstoList();
        try{
            Iterator itr = Admins.iterator();
            while(itr.hasNext()){
                AdminDetails a = (AdminDetails)itr.next();

                if(a.getEnterprise().equalsIgnoreCase("Hospital") && cmbBoxCity.getSelectedItem().toString().equalsIgnoreCase(a.getLocation())){
                    PullDoctorstoList();
                    Iterator Dtr = Doctors.iterator();
                    int Avgprice = 0;
                    int totalnumber = 1;
                    while(Dtr.hasNext()){
                        DoctorDetails d = (DoctorDetails)Dtr.next();
                        if(a.getOrganization().equalsIgnoreCase(d.getOrganisation())){
                            Avgprice = Avgprice + d.getFees();
                            totalnumber++;
                        }
                    }
                    if(totalnumber == 1)
                        Avgprice = Avgprice/totalnumber;
                    else
                        Avgprice = Avgprice/--totalnumber;
                    String data[] = {a.getOrganization(), a.getLocation(), String.valueOf(Avgprice), String.valueOf(a.getRatings())};
                    UserMod.addRow(data);
                }
            }
        }
        catch(NullPointerException E){
               return;
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
    
    void populateDoctorstable(String Hospital){
        AptMod.setRowCount(0);
        try{
            Iterator itr = Doctors.iterator();
            while(itr.hasNext()){
                DoctorDetails d = (DoctorDetails)itr.next();
                if(d.getOrganisation().equalsIgnoreCase(Hospital) && d.getDepartment().equalsIgnoreCase(cmbDepartment.getSelectedItem().toString())){
                    String data[] = {d.getFirstName() + d.getLastName(), d.getDepartment(), d.getTime(), String.valueOf(d.getFees()), String.valueOf(d.getRatings())};
                    AptMod.addRow(data);
                }
            }
        }
        catch(NullPointerException E){
            populateCityHashSet();
            populatedepartmentsdropdown();
            populateDoctorstable(Hospital);
        }
    }
    
    boolean CheckBlankFields(){
        if(txtCityUAD.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this, "Fields can't be blank");
            return false;
        }
        else if(txtDobUAD.getDate().toString().equals("")){
            JOptionPane.showMessageDialog(this, "Fields can't be blank");
            return false;
        }
        else if(txtEmailIdUAD.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this, "Fields can't be blank");
            return false;
        }
        else if(txtFirstNameUAD.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this, "Fields can't be blank");
            return false;
        }
        else if(txtPhoneNumberAD.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this, "Fields can't be blank");
            return false;
        }
        else if(txtLastNameUAD.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this, "Fields can't be blank");
            return false;
        }
        else if(txtPinCodeUAD.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this, "Fields can't be blank");
            return false;
        }
        else if(txtStreetUAD.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this, "Fields can't be blank");
            return false;
        }
        else if(txtNewPasswordUAD.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this, "Fields can't be blank");
            return false;
        }
        else{
            return true;
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
    
    void populatecitydropdown(){
        cmbBoxCity.removeAllItems();
            Iterator Ctr = Cities.iterator();
            while(Ctr.hasNext()){
                String x = Ctr.next().toString();
                cmbBoxCity.addItem(x);
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
            Iterator itr = Doctors.iterator();
            while(itr.hasNext()){
                DoctorDetails d = (DoctorDetails)itr.next();
                if(FullName.equalsIgnoreCase(d.getFirstName()+d.getLastName())){
                    u.AddAppointments(d);
                    d.AddAppointments(u);
                    AdminHospital.AddDoctortoDB(d);
                    SignUp.AddUsertoDB(u);
                    break;
                }
            }
    }
    
    
    
    boolean CheckBlankFieldsNGO(){
        if(txtExplanation.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this, "Fields can't be blank");
            return false;
        }
        else if(txtAnnualIncome.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this, "Fields can't be blank");
            return false;
        }
        else if(txtRequestAmount.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this, "Fields can't be blank");
            return false;
        }
        else{
            return true;
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

                if((d.getFirstName()+d.getLastName()).equalsIgnoreCase(Doctor) && d.getOrganisation().equalsIgnoreCase(Hospital)){
                    if(d.GetPrescription(u).equalsIgnoreCase("")){
                        JOptionPane.showMessageDialog(this, "No Prescription Available.");
                    }
                    else{
                        JOptionPane.showMessageDialog(this, d.GetPrescription(u));
                    }
                }
            }
        }
        catch(NullPointerException E){
            JOptionPane.showMessageDialog(this, "No Prescription Available.");
        }
    }
    
    boolean CheckDuplicateNGORequest(NGORequests N){
        PullNGORequeststoList();
        if(NGOReqs == null){
            return false;
        }
        else if(NGOReqs.isEmpty()){
            return false;
        }
        else{
            Iterator itr = NGOReqs.iterator();
            
            while(itr.hasNext()){
                NGORequests NG = (NGORequests)itr.next();
                if(NG.getPatientID().equalsIgnoreCase(N.getPatientID()) && NG.getAmount() == N.getAmount() && NG.getToNGOOrg().equalsIgnoreCase(N.getToNGOOrg()) && NG.getAnnualIncome() == N.getAnnualIncome()){
                    return true;
                }
            }
            return false;
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
        lblcardbg = new javax.swing.JLabel();
        ViewAppointments = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableViewAppointment = new javax.swing.JTable();
        btnJoinVirtually = new javax.swing.JButton();
        ViewPrescription = new javax.swing.JButton();
        lblPrescription = new javax.swing.JLabel();
        lblViewAppointments = new javax.swing.JLabel();
        BookAppointment = new javax.swing.JPanel();
        lblHeaderHospital = new javax.swing.JLabel();
        lblHospitalLogo = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableDoctors = new javax.swing.JTable();
        btnBookAppointment = new javax.swing.JButton();
        lblBookAppointment = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cmbDepartment = new javax.swing.JComboBox<>();
        FundRequest = new javax.swing.JPanel();
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
        lblFundRequest = new javax.swing.JLabel();
        UserDB = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableHospitals = new javax.swing.JTable();
        lblPrices = new javax.swing.JLabel();
        cmbBoxCity = new javax.swing.JComboBox<>();
        lblCity = new javax.swing.JLabel();
        cmbBoxPrices = new javax.swing.JComboBox<>();
        btnView = new javax.swing.JButton();
        lblUserDB = new javax.swing.JLabel();
        lblVirtualCare = new javax.swing.JLabel();
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
        lblAccountDetails = new javax.swing.JLabel();

        setBackground(new java.awt.Color(102, 102, 255));
        setMinimumSize(new java.awt.Dimension(1115, 925));
        setPreferredSize(new java.awt.Dimension(1200, 1200));

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
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnViewAppointments, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAccount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnRequestFund, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
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
                .addGap(45, 45, 45)
                .addComponent(btnLogout)
                .addContainerGap(851, Short.MAX_VALUE))
        );

        cardLayout.setBackground(new java.awt.Color(102, 102, 255));
        cardLayout.setPreferredSize(new java.awt.Dimension(1100, 1100));
        cardLayout.setLayout(new java.awt.CardLayout());

        lblcardbg.setMaximumSize(new java.awt.Dimension(1100, 1100));
        lblcardbg.setPreferredSize(new java.awt.Dimension(1100, 1100));
        lblcardbg.setRequestFocusEnabled(false);
        cardLayout.add(lblcardbg, "card2");

        ViewAppointments.setBackground(new java.awt.Color(102, 102, 255));
        ViewAppointments.setMinimumSize(new java.awt.Dimension(823, 697));
        ViewAppointments.setPreferredSize(new java.awt.Dimension(1000, 1000));

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
                        .addGroup(ViewAppointmentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ViewAppointmentsLayout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(btnJoinVirtually)
                                .addGap(38, 38, 38)
                                .addComponent(ViewPrescription))
                            .addGroup(ViewAppointmentsLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblViewAppointments, javax.swing.GroupLayout.PREFERRED_SIZE, 894, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblPrescription, javax.swing.GroupLayout.PREFERRED_SIZE, 602, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        ViewAppointmentsLayout.setVerticalGroup(
            ViewAppointmentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewAppointmentsLayout.createSequentialGroup()
                .addGroup(ViewAppointmentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ViewAppointmentsLayout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ViewAppointmentsLayout.createSequentialGroup()
                        .addGap(196, 196, 196)
                        .addGroup(ViewAppointmentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnJoinVirtually)
                            .addComponent(ViewPrescription))))
                .addGap(38, 38, 38)
                .addGroup(ViewAppointmentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPrescription, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblViewAppointments, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(468, Short.MAX_VALUE))
        );

        cardLayout.add(ViewAppointments, "card4");

        BookAppointment.setBackground(new java.awt.Color(102, 102, 255));
        BookAppointment.setMinimumSize(new java.awt.Dimension(823, 697));
        BookAppointment.setPreferredSize(new java.awt.Dimension(1000, 1000));

        lblHeaderHospital.setText("Welcome to ");

        lblHospitalLogo.setBackground(new java.awt.Color(204, 153, 0));

        tableDoctors.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Doctor", "Department", "Time", "Price", "Ratings"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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

        lblBookAppointment.setBackground(new java.awt.Color(102, 102, 255));

        jLabel1.setText("Department:");

        cmbDepartment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDepartmentActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout BookAppointmentLayout = new javax.swing.GroupLayout(BookAppointment);
        BookAppointment.setLayout(BookAppointmentLayout);
        BookAppointmentLayout.setHorizontalGroup(
            BookAppointmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BookAppointmentLayout.createSequentialGroup()
                .addGroup(BookAppointmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BookAppointmentLayout.createSequentialGroup()
                        .addGroup(BookAppointmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(BookAppointmentLayout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(lblHospitalLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(67, 67, 67)
                                .addComponent(lblHeaderHospital, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(BookAppointmentLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 766, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(BookAppointmentLayout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addGroup(BookAppointmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(cmbDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(199, 199, 199)
                                .addComponent(btnBookAppointment)))
                        .addGap(0, 163, Short.MAX_VALUE))
                    .addComponent(lblBookAppointment, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        BookAppointmentLayout.setVerticalGroup(
            BookAppointmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BookAppointmentLayout.createSequentialGroup()
                .addGroup(BookAppointmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BookAppointmentLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(lblHospitalLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(BookAppointmentLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(lblHeaderHospital, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(BookAppointmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBookAppointment)
                    .addGroup(BookAppointmentLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(cmbDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(41, 41, 41)
                .addComponent(lblBookAppointment, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(169, Short.MAX_VALUE))
        );

        cardLayout.add(BookAppointment, "card5");

        FundRequest.setBackground(new java.awt.Color(102, 102, 255));
        FundRequest.setMinimumSize(new java.awt.Dimension(823, 697));
        FundRequest.setPreferredSize(new java.awt.Dimension(1000, 1000));
        FundRequest.setRequestFocusEnabled(false);

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

        lblFundRequest.setBackground(new java.awt.Color(102, 102, 255));

        javax.swing.GroupLayout FundRequestLayout = new javax.swing.GroupLayout(FundRequest);
        FundRequest.setLayout(FundRequestLayout);
        FundRequestLayout.setHorizontalGroup(
            FundRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FundRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addGroup(FundRequestLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(FundRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lblRequestAmount)
                        .addComponent(lblExplanation)
                        .addComponent(lblAnnualIncome))
                    .addGap(88, 88, 88)
                    .addGroup(FundRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtExplanation, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtAnnualIncome, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtRequestAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbNGOOrgs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(64, 64, 64))
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, FundRequestLayout.createSequentialGroup()
                    .addGap(80, 80, 80)
                    .addGroup(FundRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(FundRequestLayout.createSequentialGroup()
                .addGroup(FundRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FundRequestLayout.createSequentialGroup()
                        .addGap(192, 192, 192)
                        .addComponent(btnRequest))
                    .addGroup(FundRequestLayout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(lblFundRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 633, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        FundRequestLayout.setVerticalGroup(
            FundRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FundRequestLayout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(FundRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblExplanation)
                    .addComponent(txtExplanation, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(FundRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAnnualIncome)
                    .addComponent(txtAnnualIncome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(FundRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRequestAmount)
                    .addComponent(txtRequestAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(FundRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbNGOOrgs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(btnRequest)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(lblFundRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cardLayout.add(FundRequest, "card6");

        UserDB.setBackground(new java.awt.Color(102, 102, 255));
        UserDB.setMinimumSize(new java.awt.Dimension(823, 697));
        UserDB.setPreferredSize(new java.awt.Dimension(1000, 1000));
        UserDB.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        UserDB.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(84, 96, 663, 324));

        lblPrices.setText("Prices");
        UserDB.add(lblPrices, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 430, -1, -1));

        cmbBoxCity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBoxCityActionPerformed(evt);
            }
        });
        UserDB.add(cmbBoxCity, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 470, -1, -1));

        lblCity.setText("City");
        UserDB.add(lblCity, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 440, -1, -1));

        cmbBoxPrices.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lowest First", "Highest First" }));
        cmbBoxPrices.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBoxPricesActionPerformed(evt);
            }
        });
        UserDB.add(cmbBoxPrices, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 470, -1, -1));

        btnView.setText("View");
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });
        UserDB.add(btnView, new org.netbeans.lib.awtextra.AbsoluteConstraints(634, 468, -1, -1));

        lblUserDB.setMaximumSize(new java.awt.Dimension(1000, 1000));
        lblUserDB.setMinimumSize(new java.awt.Dimension(750, 330));
        lblUserDB.setPreferredSize(new java.awt.Dimension(750, 390));
        UserDB.add(lblUserDB, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 560, 810, 370));

        lblVirtualCare.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lblVirtualCare.setText("VIRTUAL CARE");
        UserDB.add(lblVirtualCare, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 0, -1, -1));

        cardLayout.add(UserDB, "card2");

        AccountDetails.setBackground(new java.awt.Color(102, 102, 255));
        AccountDetails.setMinimumSize(new java.awt.Dimension(823, 697));
        AccountDetails.setPreferredSize(new java.awt.Dimension(1000, 1000));

        lblReenterPassword.setText("New Password:");

        lblFirstName.setText("First Name:");

        lblAge.setText("Date of Birth");

        lblEmailId.setText("Email Id:");

        lblStreet.setText("Street:");

        lblLastName.setText("Last Name:");

        lblCity1.setText("PinCode:");

        lblCity2.setText("City:");

        lblPassword.setText("Old Password:");

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
                        .addContainerGap()
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
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(AccountDetailsLayout.createSequentialGroup()
                .addGroup(AccountDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AccountDetailsLayout.createSequentialGroup()
                        .addGap(178, 178, 178)
                        .addComponent(btnUpdateUAD))
                    .addGroup(AccountDetailsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblAccountDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(247, Short.MAX_VALUE))
        );

        AccountDetailsLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblAge, lblCity1, lblCity2, lblEmailId, lblFirstName, lblLastName, lblPassword, lblPhoneNumber, lblReenterPassword, lblStreet});

        AccountDetailsLayout.setVerticalGroup(
            AccountDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AccountDetailsLayout.createSequentialGroup()
                .addContainerGap()
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
                .addGap(47, 47, 47)
                .addComponent(btnUpdateUAD)
                .addGap(18, 18, 18)
                .addComponent(lblAccountDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(131, Short.MAX_VALUE))
        );

        cardLayout.add(AccountDetails, "card7");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(253, Short.MAX_VALUE)
                    .addComponent(cardLayout, javax.swing.GroupLayout.PREFERRED_SIZE, 941, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(cardLayout, javax.swing.GroupLayout.PREFERRED_SIZE, 1067, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 133, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

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
        populateCityHashSet();
        populatecitydropdown();
        cardLayout.setVisible(true);
        Card.show(cardLayout, "card2");
        populateHospitaltable();
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
        if(CheckBlankFields()){
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
        populateDepartmentsHashSet(Hospital);
        populatedepartmentsdropdown();
        populateDoctorstable(Hospital);
        cardLayout.setVisible(true);
        Card.show(cardLayout, "card5");
    }//GEN-LAST:event_btnViewActionPerformed

    private void btnBookAppointmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBookAppointmentActionPerformed
        // TODO add your handling code here:
        if(tableDoctors.getSelectedRow() < 0)
            return;
        BookAppointment();
        JOptionPane.showMessageDialog(this, "Appointment Booked.");
    }//GEN-LAST:event_btnBookAppointmentActionPerformed

    private void btnRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRequestActionPerformed
        // TODO add your handling code here:
        if(CheckBlankFieldsNGO()){
            try{
                NGORequests N = MakeNGORequests();
                if(!CheckDuplicateNGORequest(N)){
                    AddNGOtoDB(N);
                    JOptionPane.showMessageDialog(this, "Request Sent");
                }
                else{
                    JOptionPane.showMessageDialog(this, "Duplicate Entry");
                }
            }
            catch(NumberFormatException E){
                JOptionPane.showMessageDialog(this, "Amount should be a number.");
            }
            PullNGORequeststoList();
            populateNGOtable();
        }
    }//GEN-LAST:event_btnRequestActionPerformed

    private void ViewPrescriptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ViewPrescriptionActionPerformed
        // TODO add your handling code here:
        viewprescription();
    }//GEN-LAST:event_ViewPrescriptionActionPerformed

    private void cmbDepartmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDepartmentActionPerformed
        // TODO add your handling code here:
        String Hospital = tableHospitals.getValueAt(tableHospitals.getSelectedRow(), 0).toString();
        populateDoctorstable(Hospital);
    }//GEN-LAST:event_cmbDepartmentActionPerformed


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
    private javax.swing.JComboBox<String> cmbDepartment;
    private javax.swing.JComboBox<String> cmbNGOOrgs;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblAccountDetails;
    private javax.swing.JLabel lblAge;
    private javax.swing.JLabel lblAnnualIncome;
    private javax.swing.JLabel lblBookAppointment;
    private javax.swing.JLabel lblCity;
    private javax.swing.JLabel lblCity1;
    private javax.swing.JLabel lblCity2;
    private javax.swing.JLabel lblEmailId;
    private javax.swing.JLabel lblExplanation;
    private javax.swing.JLabel lblFirstName;
    private javax.swing.JLabel lblFundRequest;
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
    private javax.swing.JLabel lblUserDB;
    private javax.swing.JLabel lblViewAppointments;
    private javax.swing.JLabel lblVirtualCare;
    private javax.swing.JLabel lblcardbg;
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
