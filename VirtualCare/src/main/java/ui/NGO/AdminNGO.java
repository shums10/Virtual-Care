/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.NGO;

import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.Db4oIOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.table.DefaultTableModel;
import model.AdminDetails;
import model.NGORequests;
import ui.User.UserDashboard;
import ui.User.UserSystem;

/**
 *
 * @author shubhampatil
 */
public class AdminNGO extends javax.swing.JPanel {

    /**
     * Creates new form AdminNGO
     */
    public AdminNGO(JSplitPane SplitPane, AdminDetails a) {
        initComponents();
        this.a = a;
        DefaultTableModel NGOMod = (DefaultTableModel) NGORequestsTable.getModel();
        this.NGOMod = NGOMod;
        PullNGORequeststoList();
        populateNGOtable();
        this.SplitPane = SplitPane;
    }
    
    JSplitPane SplitPane;
    ArrayList<NGORequests> NGOReqs;
    DefaultTableModel NGOMod;
    AdminDetails a;
    NGORequests N;
    
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
                if(a.getOrganization().equalsIgnoreCase(N.getToNGOOrg()))
                    NGOs.add(N);
            }
        }
        catch(DatabaseClosedException | Db4oIOException E){
            JOptionPane.showMessageDialog(this, "Database Error.");
        }
        this.NGOReqs = NGOs;
    }
    
    void populateNGOtable(){
        NGOMod.setRowCount(0);
        try{
            Iterator itr = NGOReqs.iterator();
            while(itr.hasNext()){
                NGORequests N = (NGORequests)itr.next();
                if(N.getStatus().equalsIgnoreCase("Pending")){
                    String data[] = {N.getPatientID(), String.valueOf(N.getAnnualIncome()), String.valueOf(N.getAmount()), N.getStatus()};
                    NGOMod.addRow(data);
                }
            }
        }
        catch(NullPointerException E){
            JOptionPane.showMessageDialog(this, "No Active NGO Requests available");
        }
    }
    
    void ShowExplaination(NGORequests N){
        lblExplainationbox.setText(N.getExplaination());
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        NGORequestsTable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblExplainationbox = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();

        jLabel1.setText("NGO ADMIN");

        NGORequestsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "PatientID", "Annual Income", "Amount", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(NGORequestsTable);

        jButton1.setText("View ");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Explanation:");

        jButton2.setText("Forward to Committie");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Decline");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblExplainationbox, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(129, 129, 129)
                        .addComponent(jButton2)
                        .addGap(56, 56, 56)
                        .addComponent(jButton3)))
                .addContainerGap(135, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(lblExplainationbox, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3)
                    .addComponent(jButton2))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(368, 368, 368)
                .addComponent(jLabel1))
            .addGroup(layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 606, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(btnLogout)
                .addGap(64, 64, 64)
                .addComponent(jButton1))
            .addGroup(layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addGap(38, 38, 38)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLogout)
                    .addComponent(jButton1))
                .addGap(29, 29, 29)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if(NGORequestsTable.getSelectedRow() < 0)
            return;
        int Row = NGORequestsTable.getSelectedRow();
        String PatientID = NGORequestsTable.getValueAt(Row, 0).toString();
        int Amount = Integer.parseInt(NGORequestsTable.getValueAt(Row, 2).toString());
        int AnnualIncome = Integer.parseInt(NGORequestsTable.getValueAt(Row, 1).toString());
        
        NGORequests N;
        Iterator itr = NGOReqs.iterator();
        while(itr.hasNext()){
            N = (NGORequests)itr.next();
            if(N.getPatientID().equalsIgnoreCase(PatientID) && N.getAmount() == Amount && N.getAnnualIncome() == AnnualIncome){
                ShowExplaination(N);
                this.N = N;
            }
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if(N == null){
            JOptionPane.showMessageDialog(this, "View a Patient first.");
        }
        else{
            N.setStatus("Committee Review");
            UserDashboard.AddNGOtoDB(N);
            JOptionPane.showMessageDialog(this, "Request forwarded to committee.");
            populateNGOtable();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if(N == null){
            JOptionPane.showMessageDialog(this, "View a Patient first.");
        }
        else{
            N.setStatus("Declined");
            UserDashboard.AddNGOtoDB(N);
            JOptionPane.showMessageDialog(this, "Request Declined.");
            populateNGOtable();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
        UserSystem LoginPanel = new UserSystem();
        SplitPane.removeAll();
        SplitPane.add(LoginPanel.SplitPane);
        SplitPane.repaint();
    }//GEN-LAST:event_btnLogoutActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable NGORequestsTable;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblExplainationbox;
    // End of variables declaration//GEN-END:variables
}
