/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.Pharmacy;

import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.Db4oIOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.table.DefaultTableModel;
import model.AdminDetails;
import model.PharmacyOrders;
import ui.Hospital.AdminHospital;
import ui.User.UserSystem;

/**
 *
 * @author shubhampatil
 */
public class AdminPharmacy extends javax.swing.JPanel {

    /**
     * Creates new form AdminPharmacy
     */
    public AdminPharmacy(JSplitPane SplitPane, AdminDetails a) {
        initComponents();
        this.a = a;
        DefaultTableModel PhMod = (DefaultTableModel) tblMedicineRequests.getModel();
        this.PhMod = PhMod;
        jLabel1.setText("Pharmacy " + a.getOrganization());
        this.SplitPane = SplitPane;
    }

    JSplitPane SplitPane;
    AdminDetails a;
    ArrayList<PharmacyOrders> PhReq;
    DefaultTableModel PhMod;
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    void PullPhOrderstoList(){
        ArrayList<PharmacyOrders> PhReq = new ArrayList<>();

        PharmacyOrders P;
        try {
            List<PharmacyOrders> Pharmacyresult = UserSystem.Phardb.query(PharmacyOrders.class);
            if(Pharmacyresult.isEmpty())
                return;
            Iterator Phitr = Pharmacyresult.iterator();
            while(Phitr.hasNext()){
                P = (PharmacyOrders)Phitr.next();
                if(a.getOrganization().equalsIgnoreCase(P.getToOrg()))
                    PhReq.add(P);
            }
        }
        catch(DatabaseClosedException | Db4oIOException E){
            JOptionPane.showMessageDialog(this, "Database Error.");
        }
        this.PhReq = PhReq;
    }
    
    void populateOrderstable(){
        PhMod.setRowCount(0);
        Iterator itr = PhReq.iterator();
        while(itr.hasNext()){
            PharmacyOrders P = (PharmacyOrders)itr.next();

            if(P.getStatus().equalsIgnoreCase("Pending")){
                String data[] = {P.getFromHospital(), P.getMedicine(), String.valueOf(P.getQuantity())};
                PhMod.addRow(data);
            }
        }
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMedicineRequests = new javax.swing.JTable();
        btnDelivered = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();

        jLabel1.setText("Pharmacy");

        tblMedicineRequests.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Hospital", "Medicine Name", "Quatinty"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblMedicineRequests);

        btnDelivered.setText("Delivered");
        btnDelivered.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeliveredActionPerformed(evt);
            }
        });

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(305, 305, 305)
                        .addComponent(btnDelivered))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(248, 248, 248)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnLogout)))
                .addContainerGap(80, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(btnDelivered)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 168, Short.MAX_VALUE)
                .addComponent(btnLogout)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnDeliveredActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeliveredActionPerformed
        // TODO add your handling code here:
        if(tblMedicineRequests.getSelectedRow() < 0)
            return;
        int Row = tblMedicineRequests.getSelectedRow();
        String Medicine = tblMedicineRequests.getValueAt(Row, 1).toString();
        String Hospital = tblMedicineRequests.getValueAt(Row, 0).toString();
        
        PharmacyOrders P;
        
        Iterator itr = PhReq.iterator();
        while(itr.hasNext()){
            P = (PharmacyOrders)itr.next();
            if(P.getFromHospital().equalsIgnoreCase(Hospital) && P.getMedicine().equalsIgnoreCase(Medicine)){
                
                P.setStatus("Delivered");
                AdminHospital.AddPhOrderstoDB(P);
                break;
            }
        }
        JOptionPane.showMessageDialog(this, "Request Approved.");
        populateOrderstable();
    }//GEN-LAST:event_btnDeliveredActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
        UserSystem LoginPanel = new UserSystem();
        SplitPane.removeAll();
        SplitPane.add(LoginPanel.SplitPane);
        SplitPane.repaint();
    }//GEN-LAST:event_btnLogoutActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelivered;
    private javax.swing.JButton btnLogout;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblMedicineRequests;
    // End of variables declaration//GEN-END:variables
}
