/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.Insurance;

import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.Db4oIOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.table.DefaultTableModel;
import model.InsuranceRequests;
import ui.Hospital.AdminHospital;
import ui.User.UserSystem;


/**
 *
 * @author shubhampatil
 */
public class InsuranceCommittie extends javax.swing.JPanel {

    /**
     * Creates new form InsuranceCommittie
     */
    public InsuranceCommittie(JSplitPane SplitPane) {
        initComponents();
        DefaultTableModel InsMod = (DefaultTableModel) tblViewInsuranceRequest.getModel();
        this.InsMod = InsMod;
        PullInsuranceRequeststoList();
        populateInsurancetable();
        this.SplitPane = SplitPane;
        DisplayImage();
    }
    
    JSplitPane SplitPane;
    ArrayList<InsuranceRequests> InsReq;
    DefaultTableModel InsMod;
    
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
                    InsReq.add(I);
            }
        }
        catch(DatabaseClosedException | Db4oIOException E){
            JOptionPane.showMessageDialog(this, "Database Error.");
        }
        this.InsReq = InsReq;
    }
    
    void populateInsurancetable(){
        InsMod.setRowCount(0);
        try{
            Iterator itr = InsReq.iterator();
            while(itr.hasNext()){
                InsuranceRequests I = (InsuranceRequests)itr.next();

                if(I.getStatus().equalsIgnoreCase("Committee Review")){
                    String data[] = {I.getFromHospital(), I.getPatientEmail(), String.valueOf(I.getAmount()), I.getToOrg(), I.getStatus()};
                    InsMod.addRow(data);
                }
            }
        }
        catch(NullPointerException E){
            JOptionPane.showMessageDialog(this, "No Requests Exist.");
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblViewInsuranceRequest = new javax.swing.JTable();
        btnApproveIC = new javax.swing.JButton();
        btnDeclineIC = new javax.swing.JButton();
        btnLogoutIC = new javax.swing.JButton();

        tblViewInsuranceRequest.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Hospital", "Patient Id", "Amount", "Organisation", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblViewInsuranceRequest);

        btnApproveIC.setText("Approve");
        btnApproveIC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApproveICActionPerformed(evt);
            }
        });

        btnDeclineIC.setText("Decline");
        btnDeclineIC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeclineICActionPerformed(evt);
            }
        });

        btnLogoutIC.setText("Logout");
        btnLogoutIC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutICActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(109, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 664, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(258, 258, 258)
                        .addComponent(btnApproveIC)
                        .addGap(139, 139, 139)
                        .addComponent(btnDeclineIC))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnLogoutIC)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnApproveIC)
                    .addComponent(btnDeclineIC))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 206, Short.MAX_VALUE)
                .addComponent(btnLogoutIC)
                .addGap(32, 32, 32))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnApproveICActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApproveICActionPerformed
        // TODO add your handling code here:
        if(tblViewInsuranceRequest.getSelectedRow() < 0)
            return;
        int Row = tblViewInsuranceRequest.getSelectedRow();
        String PatientEmail = tblViewInsuranceRequest.getValueAt(Row, 1).toString();
        
        InsuranceRequests I;
        
        Iterator itr = InsReq.iterator();
        while(itr.hasNext()){
            I = (InsuranceRequests)itr.next();
            if(I.getPatientEmail().equalsIgnoreCase(PatientEmail)){
                I.setStatus("Approved");
                AdminHospital.AddInsRequeststoDB(I);
                break;
            }
        }
        JOptionPane.showMessageDialog(this, "Request Approved.");
        populateInsurancetable();   
    }//GEN-LAST:event_btnApproveICActionPerformed

    private void btnLogoutICActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutICActionPerformed
        // TODO add your handling code here:
        UserSystem LoginPanel = new UserSystem();
        SplitPane.removeAll();
        SplitPane.add(LoginPanel.SplitPane);
        SplitPane.repaint();
    }//GEN-LAST:event_btnLogoutICActionPerformed

    private void btnDeclineICActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeclineICActionPerformed
        // TODO add your handling code here:
        if(tblViewInsuranceRequest.getSelectedRow() < 0)
            return;
        int Row = tblViewInsuranceRequest.getSelectedRow();
        String PatientEmail = tblViewInsuranceRequest.getValueAt(Row, 1).toString();
        
        InsuranceRequests I;
        
        Iterator itr = InsReq.iterator();
        while(itr.hasNext()){
            I = (InsuranceRequests)itr.next();
            if(I.getPatientEmail().equalsIgnoreCase(PatientEmail)){
                I.setStatus("Declined");
                AdminHospital.AddInsRequeststoDB(I);
                break;
            }
        }
        JOptionPane.showMessageDialog(this, "Request Declined.");
        populateInsurancetable();
    }//GEN-LAST:event_btnDeclineICActionPerformed

 private void DisplayImage() {
     Path currentRelativePath = Paths.get("");
     String s = currentRelativePath.toAbsolutePath().toString();
     
     // Logout Button
     String FilePath = s+"/images/LogoutIcon.png";
     //URL imgLogin = getClass().getResource(FilePath1 );
     ImageIcon login = new ImageIcon(FilePath);
     btnLogoutIC.setIcon(login);
 }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApproveIC;
    private javax.swing.JButton btnDeclineIC;
    private javax.swing.JButton btnLogoutIC;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblViewInsuranceRequest;
    // End of variables declaration//GEN-END:variables
}