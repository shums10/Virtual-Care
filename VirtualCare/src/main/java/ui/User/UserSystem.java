/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ui.User;

import model.UserDetails;
import com.db4o.*;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.Db4oIOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import model.AdminDetails;
import model.DeliveryHeadDetails;
import model.DoctorDetails;
import model.InsuranceAgentDetails;
import ui.Admin.AdminSystem;
import ui.Delivery.DeliveryHead;
import ui.Hospital.AdminHospital;
import ui.Hospital.DoctorsDashboard;
import ui.Insurance.AdminInsurance;
import ui.Insurance.InsuranceAgent;
import ui.Insurance.InsuranceCommittie;
import ui.NGO.AdminNGO;
import ui.NGO.NgoCommittie;
import ui.Pharmacy.AdminPharmacy;

/**
 *
 * @author shubhampatil
 */
public class UserSystem extends javax.swing.JFrame {

    /**
     * Creates new form UserSystem
     */
    public UserSystem() {
        initComponents();
        createconnection();
        PopulateHashMap();
        setAdminElementsVisibility(false);
        populateorgs();
        DisplayImage();
    }
    HashMap<String, UserDetails> UserMap = new HashMap<>();
    HashMap<String, AdminDetails> AdminMap = new HashMap<>();
    HashMap<String, DoctorDetails> DoctorMap = new HashMap<>();
    HashMap<String, InsuranceAgentDetails> InsAgentMap = new HashMap<>();
    HashMap<String, DeliveryHeadDetails> DelHeadMap = new HashMap<>();
    
    public static ObjectContainer Userdb;
    public static ObjectContainer Admindb;
    public static ObjectContainer Doctordb;
    public static ObjectContainer Phardb;
    public static ObjectContainer Insudb;
    public static ObjectContainer NGOdb;
    public static ObjectContainer InsAgentdb;
    public static ObjectContainer DelHeaddb;
    
    void populateorgs(){
        cmbBoxOrg.removeAllItems();
        Collection<AdminDetails> values = AdminMap.values();
        ArrayList<AdminDetails> Admins = new ArrayList<>(values);
        Iterator itr = Admins.iterator();
        while(itr.hasNext()){
            AdminDetails a = (AdminDetails)itr.next();
            if(a.getEnterprise().equalsIgnoreCase(cmbBoxEnterprise.getSelectedItem().toString()))
                cmbBoxOrg.addItem(a.getOrganization());
        }
    }
    
    void setAdminElementsVisibility(boolean visibility){
        lblEnterprise.setVisible(visibility);
        cmbBoxEnterprise.setVisible(visibility);
        lblOrg.setVisible(visibility);
        cmbBoxOrg.setVisible(visibility);
    }
    
    void clearallfields(){
        txtUserEmail.setText("");
        txtPassword.setText("");
        buttonGroup1.clearSelection();
        setAdminElementsVisibility(false);
    }
    
    void createconnection(){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        if(this.Userdb == null){
            String UserFILEPath = s + "/Databases/Users.db";
            ObjectContainer Userdb = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), UserFILEPath);
            this.Userdb = Userdb;
        }
        if(this.Admindb == null){
            String AdminFILEPath = s + "/Databases/Admins.db";
            ObjectContainer Admindb = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), AdminFILEPath);
            this.Admindb = Admindb;
        }
        if(this.Doctordb == null){
            String DoctorFILEPath = s + "/Databases/Doctors.db";
            ObjectContainer Doctordb = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), DoctorFILEPath);
            this.Doctordb = Doctordb;
        }
        if(this.Phardb == null){
            String PharFILEPath = s + "/Databases/PharmacyOrders.db";
            ObjectContainer Phardb = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), PharFILEPath);
            this.Phardb = Phardb;
        }
        if(this.Insudb == null){
            String InsuFILEPath = s + "/Databases/InsuranceRequests.db";
            ObjectContainer Insudb = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), InsuFILEPath);
            this.Insudb = Insudb;
        }
        if(this.NGOdb == null){
            String NGOFILEPath = s + "/Databases/NGORequests.db";
            ObjectContainer NGOdb = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), NGOFILEPath);
            this.NGOdb = NGOdb;
        }
        if(this.InsAgentdb == null){
            String NGOFILEPath = s + "/Databases/InsuranceAgent.db";
            ObjectContainer InsAgentdb = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), NGOFILEPath);
            this.InsAgentdb = InsAgentdb;
        }
        if(this.DelHeaddb == null){
            String NGOFILEPath = s + "/Databases/DeliveryHead.db";
            ObjectContainer DelHeaddb = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), NGOFILEPath);
            this.DelHeaddb = DelHeaddb;
        }
        else
            return;
    }
    
    void PopulateHashMap(){
        HashMap<String, UserDetails> UserMap = new HashMap<>();
        HashMap<String, AdminDetails> AdminMap = new HashMap<>();
        HashMap<String, DoctorDetails> DoctorMap = new HashMap<>();
        HashMap<String, InsuranceAgentDetails> InsAgentMap = new HashMap<>();
        HashMap<String, DeliveryHeadDetails> DelHeadMap = new HashMap<>();
        
        UserDetails u;
        AdminDetails a;
        DoctorDetails d;
        InsuranceAgentDetails IA;
        DeliveryHeadDetails DH;
        try {
            List<UserDetails> userresult = Userdb.query(UserDetails.class);
            List<AdminDetails> adminresult = Admindb.query(AdminDetails.class);
            List<DoctorDetails> doctorresult = Doctordb.query(DoctorDetails.class);
            List<InsuranceAgentDetails> InsAgentresult = InsAgentdb.query(InsuranceAgentDetails.class);
            List<DeliveryHeadDetails> DelHeadresult = DelHeaddb.query(DeliveryHeadDetails.class);
            if(userresult.isEmpty() && adminresult.isEmpty() && doctorresult.isEmpty() && InsAgentresult.isEmpty() && DelHeadresult.isEmpty())
                return;

            Iterator useritr = userresult.iterator();
            while(useritr.hasNext()){
                u = (UserDetails)useritr.next();
                UserMap.put(u.getEmail(), u);
            }
            
            Iterator adminitr = adminresult.iterator();
            while(adminitr.hasNext()){
                a = (AdminDetails)adminitr.next();
                AdminMap.put(a.getEmail(), a);
            }
            
            Iterator doctoritr = doctorresult.iterator();
            while(doctoritr.hasNext()){
                d = (DoctorDetails)doctoritr.next();
                DoctorMap.put(d.getEmail(), d);
            }
            
            Iterator InsAgentitr = InsAgentresult.iterator();
            while(InsAgentitr.hasNext()){
                IA = (InsuranceAgentDetails)InsAgentitr.next();
                InsAgentMap.put(IA.getEmail(), IA);
            }
            
            Iterator DelHeaditr = DelHeadresult.iterator();
            while(DelHeaditr.hasNext()){
                DH = (DeliveryHeadDetails)DelHeaditr.next();
                DelHeadMap.put(DH.getEmail(), DH);
            }
        }
        catch(DatabaseClosedException | Db4oIOException E){
            JOptionPane.showMessageDialog(this, "Database Error.");
        }
        this.UserMap = UserMap;
        this.AdminMap = AdminMap;
        this.DoctorMap = DoctorMap;
        this.InsAgentMap = InsAgentMap;
        this.DelHeadMap = DelHeadMap;
    }
    
    private void DisplayImage() {
     Path currentRelativePath = Paths.get("");
     String s = currentRelativePath.toAbsolutePath().toString();
     
     // Logout Button
     String FilePath = s+"/images/LoginIcon.png";
     // URL imgLogin = getClass().getResource(FilePath1 );
     ImageIcon login = new ImageIcon(FilePath);
     btnLogIn.setIcon(login);
     
     // Background Icon
     String FilePath1 = s+"/images/adduser.png";
     ImageIcon userbackground = new ImageIcon(FilePath1);
     btnSignUp.setIcon(userbackground);
     
     // Add Position Button
     String FilePath2 = s+"/images/LoginIcon.png";
     ImageIcon addposition = new ImageIcon(FilePath2);
     btnSignIn.setIcon(addposition);
     
     String FilePath3 = s+"/images/HC8.gif";
     ImageIcon addhomeimage = new ImageIcon(FilePath3);
     lblDB.setIcon(addhomeimage);
          
     
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        SplitPane = new javax.swing.JSplitPane();
        LogInPanel = new javax.swing.JPanel();
        lblVirtualCare = new javax.swing.JLabel();
        lblSignIn = new javax.swing.JLabel();
        lblUserEmail = new javax.swing.JLabel();
        txtUserEmail = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnSignIn = new javax.swing.JButton();
        cmbBoxEnterprise = new javax.swing.JComboBox<>();
        rdBtnUser = new javax.swing.JRadioButton();
        rdBtnAdmin = new javax.swing.JRadioButton();
        lblEnterprise = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        FillAdmin = new javax.swing.JButton();
        FillUser = new javax.swing.JButton();
        rdBtnDoctor = new javax.swing.JRadioButton();
        rdBtnOther = new javax.swing.JRadioButton();
        cmbBoxOrg = new javax.swing.JComboBox<>();
        lblOrg = new javax.swing.JLabel();
        lblDB = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnSignUp = new javax.swing.JButton();
        btnLogIn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1115, 925));
        setPreferredSize(new java.awt.Dimension(1115, 925));

        SplitPane.setMaximumSize(new java.awt.Dimension(100000, 100000));
        SplitPane.setMinimumSize(new java.awt.Dimension(1000, 1000));
        SplitPane.setPreferredSize(new java.awt.Dimension(1400, 1400));

        LogInPanel.setBackground(new java.awt.Color(0, 204, 255));
        LogInPanel.setMaximumSize(new java.awt.Dimension(100000, 100000));
        LogInPanel.setPreferredSize(new java.awt.Dimension(1000, 1000));

        lblVirtualCare.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lblVirtualCare.setText("VIRTUAL CARE");

        lblSignIn.setText("Sign In");

        lblUserEmail.setText("User Email :");

        jLabel1.setText("Password:");

        btnSignIn.setText("Sign In");
        btnSignIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignInActionPerformed(evt);
            }
        });

        cmbBoxEnterprise.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hospital", "NGO", "Pharmacy", "Insurance" }));
        cmbBoxEnterprise.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBoxEnterpriseActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdBtnUser);
        rdBtnUser.setText("User");
        rdBtnUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdBtnUserActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdBtnAdmin);
        rdBtnAdmin.setText("Admin");
        rdBtnAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdBtnAdminActionPerformed(evt);
            }
        });

        lblEnterprise.setText("Enterprise:");

        FillAdmin.setText("FillAdmin");
        FillAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FillAdminActionPerformed(evt);
            }
        });

        FillUser.setText("FillUser");
        FillUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FillUserActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdBtnDoctor);
        rdBtnDoctor.setText("Doctor");
        rdBtnDoctor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdBtnDoctorActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdBtnOther);
        rdBtnOther.setText("Other");
        rdBtnOther.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdBtnOtherActionPerformed(evt);
            }
        });

        cmbBoxOrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBoxOrgActionPerformed(evt);
            }
        });

        lblOrg.setText("Organisation");

        lblDB.setSize(new java.awt.Dimension(1000, 16));

        javax.swing.GroupLayout LogInPanelLayout = new javax.swing.GroupLayout(LogInPanel);
        LogInPanel.setLayout(LogInPanelLayout);
        LogInPanelLayout.setHorizontalGroup(
            LogInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LogInPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(cmbBoxOrg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(588, 588, 588))
            .addGroup(LogInPanelLayout.createSequentialGroup()
                .addGroup(LogInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LogInPanelLayout.createSequentialGroup()
                        .addGap(550, 550, 550)
                        .addComponent(lblOrg))
                    .addGroup(LogInPanelLayout.createSequentialGroup()
                        .addGap(480, 480, 480)
                        .addComponent(lblSignIn)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LogInPanelLayout.createSequentialGroup()
                .addGroup(LogInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, LogInPanelLayout.createSequentialGroup()
                        .addGap(242, 242, 242)
                        .addGroup(LogInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(LogInPanelLayout.createSequentialGroup()
                                .addGroup(LogInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1)
                                    .addComponent(lblUserEmail))
                                .addGroup(LogInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(LogInPanelLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(txtUserEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LogInPanelLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(56, 56, 56)
                                .addGroup(LogInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblEnterprise, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbBoxEnterprise, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(LogInPanelLayout.createSequentialGroup()
                                .addGroup(LogInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(LogInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(LogInPanelLayout.createSequentialGroup()
                                            .addGap(158, 158, 158)
                                            .addComponent(btnSignIn, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, LogInPanelLayout.createSequentialGroup()
                                            .addGap(67, 67, 67)
                                            .addComponent(rdBtnUser)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(rdBtnAdmin)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(rdBtnDoctor)))
                                    .addGroup(LogInPanelLayout.createSequentialGroup()
                                        .addGap(39, 39, 39)
                                        .addComponent(FillAdmin)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(LogInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(FillUser)
                                    .addComponent(rdBtnOther)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, LogInPanelLayout.createSequentialGroup()
                        .addGap(455, 455, 455)
                        .addComponent(lblVirtualCare, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblDB, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1049, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(234, Short.MAX_VALUE))
        );
        LogInPanelLayout.setVerticalGroup(
            LogInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LogInPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblVirtualCare, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblSignIn)
                .addGap(28, 28, 28)
                .addComponent(lblEnterprise, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(LogInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUserEmail)
                    .addComponent(txtUserEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbBoxEnterprise, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(lblOrg)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(LogInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbBoxOrg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(LogInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdBtnUser)
                    .addComponent(rdBtnAdmin)
                    .addComponent(rdBtnDoctor)
                    .addComponent(rdBtnOther))
                .addGap(46, 46, 46)
                .addComponent(btnSignIn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(LogInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FillAdmin)
                    .addComponent(FillUser))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDB, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(444, Short.MAX_VALUE))
        );

        SplitPane.setRightComponent(LogInPanel);

        jPanel2.setBackground(new java.awt.Color(3, 200, 227));

        btnSignUp.setText("Sign Up");
        btnSignUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignUpActionPerformed(evt);
            }
        });

        btnLogIn.setText("Log In");
        btnLogIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogInActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnLogIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSignUp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(225, 225, 225)
                .addComponent(btnSignUp)
                .addGap(34, 34, 34)
                .addComponent(btnLogIn)
                .addContainerGap(1079, Short.MAX_VALUE))
        );

        SplitPane.setLeftComponent(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(SplitPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(SplitPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbBoxEnterpriseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBoxEnterpriseActionPerformed
        // TODO add your handling code here:
        populateorgs();
    }//GEN-LAST:event_cmbBoxEnterpriseActionPerformed

    private void btnSignUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignUpActionPerformed
        // TODO add your handling code here:
        SignUp SignUppanel = new SignUp();
        SplitPane.setRightComponent(SignUppanel);
    }//GEN-LAST:event_btnSignUpActionPerformed

    private void rdBtnUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdBtnUserActionPerformed
        // TODO add your handling code here:
        setAdminElementsVisibility(false);
    }//GEN-LAST:event_rdBtnUserActionPerformed

    private void btnSignInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignInActionPerformed
        // TODO add your handling code here:
        UserDetails u = UserMap.get(txtUserEmail.getText().trim());
        AdminDetails a = AdminMap.get(txtUserEmail.getText().trim());
        DoctorDetails d = DoctorMap.get(txtUserEmail.getText().trim());
        InsuranceAgentDetails IA = InsAgentMap.get(txtUserEmail.getText().trim());
        DeliveryHeadDetails DH = DelHeadMap.get(txtUserEmail.getText().trim());
        
        if(txtUserEmail.getText().trim().equals("SysAdmin@virtualcare.com") && String.valueOf(txtPassword.getPassword()).equals("SysAdmin") && rdBtnAdmin.isSelected()){
           AdminSystem Dashboard = new AdminSystem(SplitPane, a);
           SplitPane.setDividerSize(0);
           SplitPane.remove(jPanel2);
           SplitPane.setRightComponent(Dashboard);
        }
        else if(txtUserEmail.getText().trim().equals("shums1004@oye.com") && String.valueOf(txtPassword.getPassword()).equals("admin@123") && rdBtnOther.isSelected()){
           NgoCommittie Dashboard = new NgoCommittie(SplitPane);
           SplitPane.setDividerSize(0);
           SplitPane.remove(jPanel2);
           SplitPane.setRightComponent(Dashboard);
        }
        else if(txtUserEmail.getText().trim().equals("shums1004@bluehealth.com") && String.valueOf(txtPassword.getPassword()).equals("admin@123") && rdBtnOther.isSelected()){
           InsuranceCommittie Dashboard = new InsuranceCommittie(SplitPane);
           SplitPane.setDividerSize(0);
           SplitPane.remove(jPanel2);
           SplitPane.setRightComponent(Dashboard);
        }
        else if((rdBtnUser.isSelected() && u == null) || (rdBtnAdmin.isSelected() && a == null) || (rdBtnDoctor.isSelected() && d == null) || (rdBtnOther.isSelected() && IA == null && DH == null)){
            JOptionPane.showMessageDialog(this, "Email Doesn't Exist. Please Signup or notify an admin to add.");
        }
        else if(rdBtnAdmin.isSelected()){
                if(!Arrays.toString(txtPassword.getPassword()).equals(a.getPassword()) || !a.getEnterprise().equalsIgnoreCase(cmbBoxEnterprise.getSelectedItem().toString()))
                    JOptionPane.showMessageDialog(this, "Incorrect Password. Try Again.");
                else if(!a.getOrganization().equalsIgnoreCase(cmbBoxOrg.getSelectedItem().toString())){
                    JOptionPane.showMessageDialog(this, "Choose Correct Organization. Try Again.");
                }
                else{
                    if(cmbBoxEnterprise.getSelectedItem().equals("Hospital")){
                        AdminHospital Dashboard = new AdminHospital(SplitPane, a);
                        SplitPane.setDividerSize(0);
                        SplitPane.remove(jPanel2);
                        SplitPane.setRightComponent(Dashboard);
                    }
                    else if(cmbBoxEnterprise.getSelectedItem().equals("NGO")){
                        AdminNGO Dashboard = new AdminNGO(SplitPane, a);
                        SplitPane.setDividerSize(0);
                        SplitPane.remove(jPanel2);
                        SplitPane.setRightComponent(Dashboard);
                    }
                    else if(cmbBoxEnterprise.getSelectedItem().equals("Pharmacy")){
                        AdminPharmacy Dashboard = new AdminPharmacy(SplitPane, a);
                        SplitPane.setDividerSize(0);
                        SplitPane.remove(jPanel2);
                        SplitPane.setRightComponent(Dashboard);
                    }
                    else{
                        AdminInsurance Dashboard = new AdminInsurance(SplitPane, a);
                        SplitPane.setDividerSize(0);
                        SplitPane.remove(jPanel2);
                        SplitPane.setRightComponent(Dashboard);
                    }
                }
        }
        else if (rdBtnUser.isSelected()){
            if(!Arrays.toString(txtPassword.getPassword()).equals(u.getPassword()))
                JOptionPane.showMessageDialog(this, "Incorrect Password. Try Again.");
            else{
                UserDashboard Dashboard = new UserDashboard(SplitPane, u);
                SplitPane.setDividerSize(0);
                SplitPane.remove(jPanel2);
                SplitPane.setRightComponent(Dashboard);
            }
        }
        else if (rdBtnDoctor.isSelected()){
            if(!Arrays.toString(txtPassword.getPassword()).equals(d.getPassword()))
                JOptionPane.showMessageDialog(this, "Incorrect Password. Try Again.");
            else{
                DoctorsDashboard Dashboard = new DoctorsDashboard(SplitPane, d);
                SplitPane.setDividerSize(0);
                SplitPane.remove(jPanel2);
                SplitPane.setRightComponent(Dashboard);
            }
        }
        else if (rdBtnOther.isSelected() && DH == null){
            if(!Arrays.toString(txtPassword.getPassword()).equals(IA.getPassword()))
                JOptionPane.showMessageDialog(this, "Incorrect Password. Try Again.");
            else{
                InsuranceAgent Dashboard = new InsuranceAgent(SplitPane, IA);
                SplitPane.setDividerSize(0);
                SplitPane.remove(jPanel2);
                SplitPane.setRightComponent(Dashboard);
            }
        }
        else if (rdBtnOther.isSelected() && IA == null){
            if(!Arrays.toString(txtPassword.getPassword()).equals(DH.getPassword()))
                JOptionPane.showMessageDialog(this, "Incorrect Password. Try Again.");
            else{
                DeliveryHead Dashboard = new DeliveryHead(SplitPane, DH);
                SplitPane.setDividerSize(0);
                SplitPane.remove(jPanel2);
                SplitPane.setRightComponent(Dashboard);
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "Select type of user.");
        }
        clearallfields();
    }//GEN-LAST:event_btnSignInActionPerformed

    private void btnLogInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogInActionPerformed
        // TODO add your handling code here:
        SplitPane.setRightComponent(LogInPanel);
        PopulateHashMap();
    }//GEN-LAST:event_btnLogInActionPerformed

    private void rdBtnAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdBtnAdminActionPerformed
        // TODO add your handling code here:
        setAdminElementsVisibility(true);
    }//GEN-LAST:event_rdBtnAdminActionPerformed

    private void FillAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FillAdminActionPerformed
        // TODO add your handling code here:
        txtUserEmail.setText("SysAdmin@virtualcare.com");
        txtPassword.setText("SysAdmin");
        rdBtnAdmin.setSelected(true);
    }//GEN-LAST:event_FillAdminActionPerformed

    private void FillUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FillUserActionPerformed
        // TODO add your handling code here:
        txtUserEmail.setText("hariyapratik@gmail.com");
        txtPassword.setText("xyz");
        rdBtnUser.setSelected(true);
    }//GEN-LAST:event_FillUserActionPerformed

    private void cmbBoxOrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBoxOrgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbBoxOrgActionPerformed

    private void rdBtnDoctorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdBtnDoctorActionPerformed
        // TODO add your handling code here:
        setAdminElementsVisibility(false);
    }//GEN-LAST:event_rdBtnDoctorActionPerformed

    private void rdBtnOtherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdBtnOtherActionPerformed
        // TODO add your handling code here:
        setAdminElementsVisibility(false);
    }//GEN-LAST:event_rdBtnOtherActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserSystem().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton FillAdmin;
    private javax.swing.JButton FillUser;
    private javax.swing.JPanel LogInPanel;
    public javax.swing.JSplitPane SplitPane;
    private javax.swing.JButton btnLogIn;
    private javax.swing.JButton btnSignIn;
    private javax.swing.JButton btnSignUp;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cmbBoxEnterprise;
    private javax.swing.JComboBox<String> cmbBoxOrg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblDB;
    private javax.swing.JLabel lblEnterprise;
    private javax.swing.JLabel lblOrg;
    private javax.swing.JLabel lblSignIn;
    private javax.swing.JLabel lblUserEmail;
    private javax.swing.JLabel lblVirtualCare;
    private javax.swing.JRadioButton rdBtnAdmin;
    private javax.swing.JRadioButton rdBtnDoctor;
    private javax.swing.JRadioButton rdBtnOther;
    private javax.swing.JRadioButton rdBtnUser;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUserEmail;
    // End of variables declaration//GEN-END:variables
}
