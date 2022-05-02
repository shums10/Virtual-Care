/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pharmaservices;

import ngoservices.*;

/**
 *
 * @author Akshay Barne
 */
public class Patient extends Person{
    
    String patientId;
    EncounterHistory history;
    
    public Patient() {
        this.history = new EncounterHistory();
    }
    
    public Patient(String patientId, String name, String age, House house) {
        super(name, age, house);
        this.patientId = patientId;
        this. history = new EncounterHistory();
    }
    
    public Patient(String patientId) {
        this.patientId = patientId;
    }
    
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public EncounterHistory getHistory() {
        return history;
    }

    public void setHistory(EncounterHistory history) {
        this.history = history;
    }
}
