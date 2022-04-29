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
import javax.swing.JOptionPane;
import model.AdminDetails;
import model.DoctorDetails;
import ui.Admin.AdminSystem;
import ui.Hospital.AdminHospital;
import ui.Hospital.DoctorsDashboard;
import ui.Insurance.AdminInsurance;
import ui.NGO.AdminNGO;
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
    }
    HashMap<String, UserDetails> UserMap = new HashMap<>();
    HashMap<String, AdminDetails> AdminMap = new HashMap<>();
    HashMap<String, DoctorDetails> DoctorMap = new HashMap<>();
    
    public static ObjectContainer Userdb;
    public static ObjectContainer Admindb;
    public static ObjectContainer Doctordb;
    public static ObjectContainer Phardb;
    public static ObjectContainer Insudb;
    public static ObjectContainer NGOdb;
    
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
        else
            return;
    }
    
    void PopulateHashMap(){
        HashMap<String, UserDetails> UserMap = new HashMap<>();
        HashMap<String, AdminDetails> AdminMap = new HashMap<>();
        HashMap<String, DoctorDetails> DoctorMap = new HashMap<>();
        
        UserDetails u;
        AdminDetails a;
        DoctorDetails d;
        try {
            List<UserDetails> userresult = Userdb.query(UserDetails.class);
            List<AdminDetails> adminresult = Admindb.query(AdminDetails.class);
            List<DoctorDetails> doctorresult = Doctordb.query(DoctorDetails.class);
            if(userresult.isEmpty() && adminresult.isEmpty() && doctorresult.isEmpty())
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
        }
        catch(DatabaseClosedException | Db4oIOException E){
            JOptionPane.showMessageDialog(this, "Database Error.");
        }
        this.UserMap = UserMap;
        this.AdminMap = AdminMap;
        this.DoctorMap = DoctorMap;
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
        hiddenPanel = new javax.swing.JPanel();
        cmbBoxOrg = new javax.swing.JComboBox<>();
        lblOrg = new javax.swing.JLabel();
        lblEnterprise = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        FillAdmin = new javax.swing.JButton();
        FillUser = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        rdBtnDoctor = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        btnSignUp = new javax.swing.JButton();
        btnLogIn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1115, 925));
        setPreferredSize(new java.awt.Dimension(1115, 925));

        SplitPane.setMinimumSize(new java.awt.Dimension(1115, 925));
        SplitPane.setPreferredSize(new java.awt.Dimension(1115, 925));

        LogInPanel.setBackground(new java.awt.Color(0, 204, 255));

        lblVirtualCare.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lblVirtualCare.setText("VIRTUAL CARE");

        lblSignIn.setText("Sign In");

        lblUserEmail.setText("User Email :");

        jLabel1.setText("Password:");

        btnSignIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/LoginIcon.png"))); // NOI18N
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

        cmbBoxOrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBoxOrgActionPerformed(evt);
            }
        });

        lblOrg.setText("Organisation");

        javax.swing.GroupLayout hiddenPanelLayout = new javax.swing.GroupLayout(hiddenPanel);
        hiddenPanel.setLayout(hiddenPanelLayout);
        hiddenPanelLayout.setHorizontalGroup(
            hiddenPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hiddenPanelLayout.createSequentialGroup()
                .addGroup(hiddenPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(hiddenPanelLayout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(cmbBoxOrg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(hiddenPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblOrg)))
                .addContainerGap(89, Short.MAX_VALUE))
        );
        hiddenPanelLayout.setVerticalGroup(
            hiddenPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, hiddenPanelLayout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(lblOrg)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbBoxOrg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

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

        jLabel2.setText("jLabel2");

        buttonGroup1.add(rdBtnDoctor);
        rdBtnDoctor.setText("Doctor");

        javax.swing.GroupLayout LogInPanelLayout = new javax.swing.GroupLayout(LogInPanel);
        LogInPanel.setLayout(LogInPanelLayout);
        LogInPanelLayout.setHorizontalGroup(
            LogInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LogInPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(LogInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LogInPanelLayout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addGroup(LogInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(LogInPanelLayout.createSequentialGroup()
                                .addComponent(FillAdmin)
                                .addGap(169, 169, 169)
                                .addComponent(FillUser))
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
                                .addGap(92, 92, 92)
                                .addComponent(rdBtnUser)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(LogInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnSignIn)
                                    .addGroup(LogInPanelLayout.createSequentialGroup()
                                        .addComponent(rdBtnAdmin)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rdBtnDoctor)
                                        .addGap(54, 54, 54)
                                        .addComponent(hiddenPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(LogInPanelLayout.createSequentialGroup()
                        .addGap(394, 394, 394)
                        .addComponent(lblSignIn))
                    .addGroup(LogInPanelLayout.createSequentialGroup()
                        .addGap(353, 353, 353)
                        .addComponent(lblVirtualCare, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(247, Short.MAX_VALUE))
        );
        LogInPanelLayout.setVerticalGroup(
            LogInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LogInPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(LogInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LogInPanelLayout.createSequentialGroup()
                        .addGroup(LogInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(LogInPanelLayout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(lblVirtualCare, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(lblSignIn)
                                .addGap(18, 18, 18)
                                .addComponent(lblEnterprise, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(LogInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblUserEmail)
                                    .addComponent(txtUserEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbBoxEnterprise, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(50, 50, 50)
                                .addGroup(LogInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(LogInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(rdBtnUser)
                                    .addComponent(rdBtnAdmin)
                                    .addComponent(rdBtnDoctor))
                                .addGap(46, 46, 46))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LogInPanelLayout.createSequentialGroup()
                                .addComponent(hiddenPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(17, 17, 17)))
                        .addComponent(btnSignIn)
                        .addGap(32, 32, 32)
                        .addGroup(LogInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(FillUser)
                            .addComponent(FillAdmin)))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 693, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(222, Short.MAX_VALUE))
        );

        SplitPane.setRightComponent(LogInPanel);

        jPanel2.setBackground(new java.awt.Color(3, 200, 227));

        btnSignUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adduser.png"))); // NOI18N
        btnSignUp.setText("Sign Up");
        btnSignUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignUpActionPerformed(evt);
            }
        });

        btnLogIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/LoginIcon.png"))); // NOI18N
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
                    .addComponent(btnSignUp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(225, 225, 225)
                .addComponent(btnSignUp)
                .addGap(34, 34, 34)
                .addComponent(btnLogIn)
                .addContainerGap(588, Short.MAX_VALUE))
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
        if(txtUserEmail.getText().trim().equals("SysAdmin@virtualcare.com") && String.valueOf(txtPassword.getPassword()).equals("SysAdmin") && rdBtnAdmin.isSelected()){
           AdminSystem Dashboard = new AdminSystem(SplitPane, a);
           SplitPane.setDividerSize(0);
           SplitPane.remove(jPanel2);
           SplitPane.setRightComponent(Dashboard);
        }
        else if((rdBtnUser.isSelected() && u == null) || (rdBtnAdmin.isSelected() && a == null) || (rdBtnDoctor.isSelected() && d == null)){
            JOptionPane.showMessageDialog(this, "Email Doesn't Exist. Please Signup.");
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
    private javax.swing.JPanel hiddenPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblEnterprise;
    private javax.swing.JLabel lblOrg;
    private javax.swing.JLabel lblSignIn;
    private javax.swing.JLabel lblUserEmail;
    private javax.swing.JLabel lblVirtualCare;
    private javax.swing.JRadioButton rdBtnAdmin;
    private javax.swing.JRadioButton rdBtnDoctor;
    private javax.swing.JRadioButton rdBtnUser;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUserEmail;
    // End of variables declaration//GEN-END:variables
}
