/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package userservices;

import pharmaservices.*;
import ngoservices.*;
import java.time.LocalDate;

/**
 *
 * @author Akshay Barne
 */
public class VitalSign {
    private String date;
    private double temperature;
    private double bloodPressure;
    private int pulse;

    public VitalSign() {
    }

    public VitalSign(double temperature, double bloodPressure, int pulse) {
        this.temperature = temperature;
        this.bloodPressure = bloodPressure;
        this.pulse = pulse;
        this.date= LocalDate.now().toString();
    }
    
    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(double bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public int getPulse() {
        return pulse;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    @Override
    public String toString(){
        return this.date;
    }
}
