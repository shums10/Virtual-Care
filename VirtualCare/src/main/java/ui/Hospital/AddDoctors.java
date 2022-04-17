/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.Hospital;

/**
 *
 * @author shubhampatil
 */
public class AddDoctors extends javax.swing.JPanel {

    /**
     * Creates new form AddDoctors
     */
    public AddDoctors() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblAddDoctors = new javax.swing.JLabel();
        lblFirstName = new javax.swing.JLabel();
        lblAvailableDays = new javax.swing.JLabel();
        lblLastName = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        lblFees = new javax.swing.JLabel();
        lblRatings = new javax.swing.JLabel();
        txtFirstNameAH = new javax.swing.JTextField();
        txtLastNameAH = new javax.swing.JTextField();
        txtAvailableDays = new javax.swing.JTextField();
        txtTime = new javax.swing.JTextField();
        txtFees = new javax.swing.JTextField();
        txtRatings = new javax.swing.JTextField();
        cmbBoxDepartment = new javax.swing.JComboBox<>();
        lblDepartment = new javax.swing.JLabel();
        btnRegisterDoctor = new javax.swing.JButton();
        txtEmailIdAH = new javax.swing.JTextField();
        txtPasswordAH = new javax.swing.JTextField();
        lblRatings1 = new javax.swing.JLabel();
        lblRatings2 = new javax.swing.JLabel();

        lblAddDoctors.setText("Add Doctors");

        lblFirstName.setText("First Name:");

        lblAvailableDays.setText("Workings Days:");

        lblLastName.setText("Last Name:");

        lblTime.setText("Time:");

        lblFees.setText("Fees:");

        lblRatings.setText("Ratings:");

        txtFirstNameAH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFirstNameAHActionPerformed(evt);
            }
        });

        txtLastNameAH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLastNameAHActionPerformed(evt);
            }
        });

        txtAvailableDays.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAvailableDaysActionPerformed(evt);
            }
        });

        txtTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimeActionPerformed(evt);
            }
        });

        txtFees.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFeesActionPerformed(evt);
            }
        });

        txtRatings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRatingsActionPerformed(evt);
            }
        });

        cmbBoxDepartment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblDepartment.setText("Department");

        btnRegisterDoctor.setText("Register Doctor");
        btnRegisterDoctor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterDoctorActionPerformed(evt);
            }
        });

        txtEmailIdAH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailIdAHActionPerformed(evt);
            }
        });

        txtPasswordAH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordAHActionPerformed(evt);
            }
        });

        lblRatings1.setText("Email Id:");

        lblRatings2.setText("Password:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblAddDoctors)
                .addGap(303, 303, 303))
            .addGroup(layout.createSequentialGroup()
                .addGap(174, 174, 174)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTime)
                    .addComponent(lblFirstName)
                    .addComponent(lblAvailableDays)
                    .addComponent(lblLastName)
                    .addComponent(lblFees)
                    .addComponent(lblRatings)
                    .addComponent(lblRatings1)
                    .addComponent(lblRatings2))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPasswordAH, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmailIdAH, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRegisterDoctor)
                    .addComponent(txtFees, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtFirstNameAH, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblDepartment)
                        .addGap(18, 18, 18)
                        .addComponent(cmbBoxDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRatings, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAvailableDays, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLastNameAH, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblAddDoctors)
                .addGap(64, 64, 64)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFirstName)
                    .addComponent(txtFirstNameAH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDepartment)
                    .addComponent(cmbBoxDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLastName)
                    .addComponent(txtLastNameAH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAvailableDays)
                    .addComponent(txtAvailableDays, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTime)
                    .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFees)
                    .addComponent(txtFees, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRatings)
                    .addComponent(txtRatings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmailIdAH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRatings1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPasswordAH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRatings2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(btnRegisterDoctor)
                .addGap(78, 78, 78))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtFirstNameAHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFirstNameAHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFirstNameAHActionPerformed

    private void txtLastNameAHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLastNameAHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLastNameAHActionPerformed

    private void txtAvailableDaysActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAvailableDaysActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAvailableDaysActionPerformed

    private void txtTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimeActionPerformed

    private void txtFeesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFeesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFeesActionPerformed

    private void txtRatingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRatingsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRatingsActionPerformed

    private void btnRegisterDoctorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterDoctorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRegisterDoctorActionPerformed

    private void txtEmailIdAHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailIdAHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailIdAHActionPerformed

    private void txtPasswordAHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordAHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordAHActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRegisterDoctor;
    private javax.swing.JComboBox<String> cmbBoxDepartment;
    private javax.swing.JLabel lblAddDoctors;
    private javax.swing.JLabel lblAvailableDays;
    private javax.swing.JLabel lblDepartment;
    private javax.swing.JLabel lblFees;
    private javax.swing.JLabel lblFirstName;
    private javax.swing.JLabel lblLastName;
    private javax.swing.JLabel lblRatings;
    private javax.swing.JLabel lblRatings1;
    private javax.swing.JLabel lblRatings2;
    private javax.swing.JLabel lblTime;
    private javax.swing.JTextField txtAvailableDays;
    private javax.swing.JTextField txtEmailIdAH;
    private javax.swing.JTextField txtFees;
    private javax.swing.JTextField txtFirstNameAH;
    private javax.swing.JTextField txtLastNameAH;
    private javax.swing.JTextField txtPasswordAH;
    private javax.swing.JTextField txtRatings;
    private javax.swing.JTextField txtTime;
    // End of variables declaration//GEN-END:variables
}