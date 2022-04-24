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
public class InsuranceRequests {
    String ToOrg, FromHospital, PatientEmail, TimeStamp, Status;
    int Amount;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

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

    public String getPatientEmail() {
        return PatientEmail;
    }

    public void setPatientEmail(String PatientEmail) {
        this.PatientEmail = PatientEmail;
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

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int Amount) {
        this.Amount = Amount;
    }
}
