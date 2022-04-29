/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package userservices;

import pharmaservices.*;
import ngoservices.*;
import java.time.LocalDateTime;

/**
 *
 * @author Akshay Barne
 */
public class Encounter {
   
    VitalSign vitalSign;
    LocalDateTime dateEncounter;

    public Encounter(VitalSign vitalSign, LocalDateTime dateEncounter) {
        this.vitalSign = vitalSign;
        this.dateEncounter = dateEncounter;
    }
    
    public VitalSign getVitalSign() {
        return vitalSign;
    }

    public void setVitalSign(VitalSign vitalSign) {
        this.vitalSign = vitalSign;
    }

    public LocalDateTime getDateEncounter() {
        return dateEncounter;
    }

    public void setDateEncounter(LocalDateTime dateEncounter) {
        this.dateEncounter = dateEncounter;
    }    
}
