/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 *
 * @author swaroop
 */
public class PharmacyOrders {
    String ToOrg, FromHospital, Medicine, TimeStamp;
    int Quantity;

    public String getToOrg() {
        return ToOrg;
    }

    public void setToOrg(String ToOrg) {
        this.ToOrg = ToOrg;
    }

    public String getFromHospital() {
        return FromHospital;
    }

    public void setFromHospital(String FromHospital) {
        this.FromHospital = FromHospital;
    }
    public String getMedicine() {
        return Medicine;
    }
    
    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String Time = sdf1.format(timestamp);
        this.TimeStamp = Time;
    }
    
    public void setMedicine(String Medicine) {
        this.Medicine = Medicine;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }
}
