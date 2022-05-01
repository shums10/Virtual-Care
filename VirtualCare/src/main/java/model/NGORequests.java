/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author swaroop
 */
public class NGORequests {
    String PatientID, Explaination, toNGOOrg, Status;
    int AnnualIncome, Amount;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getPatientID() {
        return PatientID;
    }

    public void setPatientID(String PatientID) {
        this.PatientID = PatientID;
    }

    public String getToNGOOrg() {
        return toNGOOrg;
    }

    public void setToNGOOrg(String toOrg) {
        this.toNGOOrg = toOrg;
    }

    public String getExplaination() {
        return Explaination;
    }

    public void setExplaination(String Explaination) {
        this.Explaination = Explaination;
    }

    public int getAnnualIncome() {
        return AnnualIncome;
    }

    public void setAnnualIncome(int AnnualIncome) {
        this.AnnualIncome = AnnualIncome;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int Amount) {
        this.Amount = Amount;
    }
}
