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
import model.InsuranceAgentDetails;
import model.InsuranceRequests;
import ui.Hospital.AdminHospital;
import ui.User.UserSystem;

/**
 *
 * @author shubhampatil
 */
public class InsuranceAgent extends javax.swing.JPanel {

    /**
     * Creates new form InsuranceAgent
     */
    public InsuranceAgent(JSplitPane SplitPane, InsuranceAgentDetails IA) {
        initComponents();
        this.IA = IA;
        DefaultTableModel InsMod = (DefaultTableModel) tblViewInsuranceRequest.getModel();
        this.InsMod = InsMod;
        PullInsuranceRequeststoList();
        populateInsurancetable();
        this.SplitPane = SplitPane;
        DisplayImage();
    }
    
    JSplitPane SplitPane;
    InsuranceAgentDetails IA;
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
                if(IA.getOrganization().equalsIgnoreCase(I.getToOrg()))
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

                if(I.getStatus().equalsIgnoreCase("Agent Review")){
                    String data[] = {I.getFromHospital(), I.getPatientEmail(), String.valueOf(I.getAmount()), I.getStatus()};
                    InsMod.addRow(data);
                }
            }
        }
        catch(NullPointerException E){
            JOptionPane.showMessageDialog(this, "No Requests Exist.");
        }
    }
    
    private void DisplayImage() {
     Path currentRelativePath = Paths.get("");
     String s = currentRelativePath.toAbsolutePath().toString();
     
     // Logout Button
     String FilePath = s+"/Images/LoginIcon.png";
     // URL imgLogin = getClass().getResource(FilePath1 );
     ImageIcon login = new ImageIcon(FilePath);
     btnLogOutIA.setIcon(login);
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
        btnForward = new javax.swing.JButton();
        btnDeclineIA = new javax.swing.JButton();
        btnLogOutIA = new javax.swing.JButton();

        setBackground(new java.awt.Color(51, 153, 0));

        tblViewInsuranceRequest.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Hospital", "Patient Id", "Amount", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblViewInsuranceRequest);

        btnForward.setBackground(new java.awt.Color(153, 153, 153));
        btnForward.setText("Forward to Committie");
        btnForward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnForwardActionPerformed(evt);
            }
        });

        btnDeclineIA.setBackground(new java.awt.Color(153, 153, 153));
        btnDeclineIA.setText("Decline");
        btnDeclineIA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeclineIAActionPerformed(evt);
            }
        });

        btnLogOutIA.setText("LogOut");
        btnLogOutIA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogOutIAActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(87, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 664, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(259, 259, 259)
                        .addComponent(btnForward)
                        .addGap(117, 117, 117)
                        .addComponent(btnDeclineIA))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(btnLogOutIA)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnForward)
                    .addComponent(btnDeclineIA))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 159, Short.MAX_VALUE)
                .addComponent(btnLogOutIA)
                .addGap(40, 40, 40))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnForwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnForwardActionPerformed
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
                I.setStatus("Committee Review");
                AdminHospital.AddInsRequeststoDB(I);
                break;
            }
        }
        JOptionPane.showMessageDialog(this, "Request forwarded to Committee");
        populateInsurancetable();
    }//GEN-LAST:event_btnForwardActionPerformed

    private void btnDeclineIAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeclineIAActionPerformed
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
    }//GEN-LAST:event_btnDeclineIAActionPerformed

    private void btnLogOutIAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogOutIAActionPerformed
        // TODO add your handling code here:
        UserSystem LoginPanel = new UserSystem();
        SplitPane.removeAll();
        SplitPane.add(LoginPanel.SplitPane);
        SplitPane.repaint();
    }//GEN-LAST:event_btnLogOutIAActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeclineIA;
    private javax.swing.JButton btnForward;
    private javax.swing.JButton btnLogOutIA;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblViewInsuranceRequest;
    // End of variables declaration//GEN-END:variables
}
