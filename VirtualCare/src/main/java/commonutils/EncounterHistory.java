/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package commonutils;

import pharmaservices.*;
import ngoservices.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Akshay Barne
 */
public class EncounterHistory {
   
    List<Encounter> encounterList;

    public EncounterHistory() {
        encounterList = new ArrayList<>();
    }
    
    public  List<Encounter> getEncounterList() {
        return encounterList;
    }

    public void setEncounterList(List<Encounter> encounterList) {
        this.encounterList = encounterList;
    }

    public Encounter getLastEncounter(){
        if(!encounterList.isEmpty())
            return encounterList.get(encounterList.size() - 1);
        else
            return null;
    }
           
    public void addEncounter(Encounter e){
        encounterList.add(e);
    }
    
}
