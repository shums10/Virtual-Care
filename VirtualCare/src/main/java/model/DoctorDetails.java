/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author swaroop
 */
public class DoctorDetails {
    String FirstName, LastName, WorkingDays[], Time, Email, Password, Department;
    int Ratings, Fees;

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String[] getWorkingDays() {
        return WorkingDays;
    }

    public void setWorkingDays(String[] WorkingDays) {
        this.WorkingDays = WorkingDays;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String Department) {
        this.Department = Department;
    }

    public int getRatings() {
        return Ratings;
    }

    public void setRatings(int Ratings) {
        this.Ratings = Ratings;
    }

    public int getFees() {
        return Fees;
    }

    public void setFees(int Fees) {
        this.Fees = Fees;
    }
}
