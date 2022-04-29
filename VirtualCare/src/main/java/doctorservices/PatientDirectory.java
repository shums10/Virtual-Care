/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package doctorservices;

import ngoservices.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Akshay Barne
 */
public class PatientDirectory {
    List<Patient> list = new ArrayList<>();
    
    public void addPatient(Patient ...p){
        list.addAll(Arrays.asList(p));
    }

    public List<Patient> getList() {
        return list;
    }

    public void setList(List<Patient> list) {
        this.list = list;
    }
    
    public Patient getPatient(String patientId){
     Patient patient= null;
     for(Patient patientObj: list){
             if(patientId.equalsIgnoreCase(patientObj.getPatientId()))
                 patient = patientObj;
         }
        return patient;
     }
    
      public void deletePatient(Patient patient){
        list.remove(patient);
     } 
    
    
}
