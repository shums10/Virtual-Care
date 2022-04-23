/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.User;

import model.UserDetails;
import com.db4o.*;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.Db4oIOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author shubhampatil
 */
public class SignUp extends javax.swing.JPanel {

    /**
     * Creates new form SignUp
     */
    public SignUp() {
        initComponents();
    }
    
    public static void AddUsertoDB(UserDetails u){     
        try {
            UserSystem.Userdb.store(u);
            System.out.println("Stored " + u.getFirstName());
        }
        catch(DatabaseClosedException | Db4oIOException E){
            System.out.println("Database Error");
        }
    }
    
    UserDetails MakeUser(){
        UserDetails u = new UserDetails();
        u.setCity(txtCity.getText().trim());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        u.setDOB(formatter.format(txtDoB.getDate()).toString());
        System.out.println(u.getDOB());
        u.setEmail(txtEmailId.getText().trim());
        u.setFirstName(txtFirstName.getText().trim());
        u.setLastName(txtLastName.getText().trim());
        u.setPinCode(Integer.parseInt(txtPinCode.getText().trim()));
        u.setStreet(txtStreet.getText().trim());
        u.setPassword(Arrays.toString(txtPassword.getPassword()).trim());
        return u;
    }
    
    void ClearAllFields(){
        txtCity.setText("");
        txtEmailId.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtDoB.setDate(null);
        txtPinCode.setText("");
        txtStreet.setText("");
        txtPassword.setText("");
        txtReenterPassword.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblVirtualCare = new javax.swing.JLabel();
        lblFirstName = new javax.swing.JLabel();
        lblAge = new javax.swing.JLabel();
        lblEmailId = new javax.swing.JLabel();
        lblStreet = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        lblLastName = new javax.swing.JLabel();
        txtLastName = new javax.swing.JTextField();
        lblCity = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        lblReenterPassword = new javax.swing.JLabel();
        txtCity = new javax.swing.JTextField();
        txtEmailId = new javax.swing.JTextField();
        txtStreet = new javax.swing.JTextField();
        btnRegister = new javax.swing.JButton();
        txtDoB = new com.toedter.calendar.JDateChooser();
        txtPinCode = new javax.swing.JTextField();
        lblCity1 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        txtReenterPassword = new javax.swing.JPasswordField();

        lblVirtualCare.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lblVirtualCare.setText("VIRTUAL CARE");

        lblFirstName.setText("First Name:");

        lblAge.setText("Date of Birth");

        lblEmailId.setText("Email Id:");

        lblStreet.setText("Street:");

        txtFirstName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFirstNameActionPerformed(evt);
            }
        });

        lblLastName.setText("Last Name:");

        txtLastName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLastNameActionPerformed(evt);
            }
        });

        lblCity.setText("City:");

        lblPassword.setText("Password:");

        lblReenterPassword.setText("Re-enter Password:");

        txtCity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCityActionPerformed(evt);
            }
        });

        txtEmailId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailIdActionPerformed(evt);
            }
        });

        txtStreet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStreetActionPerformed(evt);
            }
        });

        btnRegister.setText("Register");
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
            }
        });

        txtPinCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPinCodeActionPerformed(evt);
            }
        });

        lblCity1.setText("PinCode:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblVirtualCare, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(312, 312, 312))
            .addGroup(layout.createSequentialGroup()
                .addGap(262, 262, 262)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblEmailId)
                                .addComponent(lblPassword)
                                .addComponent(lblReenterPassword)
                                .addComponent(lblCity1))
                            .addGap(88, 88, 88)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtEmailId, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                                .addComponent(txtPassword)
                                .addComponent(txtReenterPassword)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRegister)
                            .addGap(134, 134, 134)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblAge)
                            .addComponent(lblLastName)
                            .addComponent(lblCity)
                            .addComponent(lblStreet)
                            .addComponent(lblFirstName))
                        .addGap(88, 88, 88)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtLastName, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(txtFirstName, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(txtStreet, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(txtDoB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtPinCode, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(txtCity)))))
                .addGap(101, 228, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblVirtualCare, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(lblFirstName))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLastName))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblAge)
                        .addGap(18, 18, 18)
                        .addComponent(lblStreet)
                        .addGap(23, 23, 23))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtDoB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtStreet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCity))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCity1)
                    .addComponent(txtPinCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmailId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmailId))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblReenterPassword)
                    .addComponent(txtReenterPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addComponent(btnRegister)
                .addContainerGap(183, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtFirstNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFirstNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFirstNameActionPerformed

    private void txtLastNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLastNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLastNameActionPerformed

    private void txtCityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCityActionPerformed

    private void txtEmailIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailIdActionPerformed

    private void txtStreetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStreetActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStreetActionPerformed

    private void txtPinCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPinCodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPinCodeActionPerformed

    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterActionPerformed
        // TODO add your handling code here:
        if(Arrays.toString(txtPassword.getPassword()).equals(Arrays.toString(txtReenterPassword.getPassword()))){
            UserDetails u = MakeUser();
            AddUsertoDB(u);
            ClearAllFields();
            JOptionPane.showMessageDialog(this, "User Added.");
        }
        else
            JOptionPane.showMessageDialog(this, "Re-enter Correct Password.");
    }//GEN-LAST:event_btnRegisterActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRegister;
    private javax.swing.JLabel lblAge;
    private javax.swing.JLabel lblCity;
    private javax.swing.JLabel lblCity1;
    private javax.swing.JLabel lblEmailId;
    private javax.swing.JLabel lblFirstName;
    private javax.swing.JLabel lblLastName;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblReenterPassword;
    private javax.swing.JLabel lblStreet;
    private javax.swing.JLabel lblVirtualCare;
    private javax.swing.JTextField txtCity;
    private com.toedter.calendar.JDateChooser txtDoB;
    private javax.swing.JTextField txtEmailId;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtPinCode;
    private javax.swing.JPasswordField txtReenterPassword;
    private javax.swing.JTextField txtStreet;
    // End of variables declaration//GEN-END:variables
}
