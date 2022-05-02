/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adminservices;

import pharmaservices.*;
import ngoservices.*;
import java.time.LocalDateTime;

/**
 *
 * @author Akshay Barne
 */
public class SystemModel {
    City city;
    PatientDirectory patientDirectory = new PatientDirectory();
    PersonDirectory personList = new PersonDirectory();
    
    public SystemModel(){
        //loading dummy values
        load();
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public PatientDirectory getPatientDirectory() {
        return patientDirectory;
    }

    public void setPatientDirectory(PatientDirectory patientDirectory) {
        this.patientDirectory = patientDirectory;
    }

    public PersonDirectory getPersonList() {
        return personList;
    }

    public void setPersonList(PersonDirectory personList) {
        this.personList = personList;
    }

    private void load() {
        House home1 = new House("1");
        House home2 = new House("2");
        House home3 = new House("3");
        House home4 = new House("4");
         
        Community community1 = new Community("CityView");
        Community community2= new Community("MissionPark");
        
        community1.addHouses(home1, home2);
        home1.setCommunity(community1);
        home2.setCommunity(community1);
        
        community2.addHouses(home3, home4);
        home3.setCommunity(community2);
        home4.setCommunity(community2);
        
        city = new City();
        city.setName("Newark");
        
        city.addCommunity(community1, community2);
        
        Patient p1 = new Patient("100","Sanjay", "25", home4);
        Patient p2 = new Patient("102","Ranvijay", "45", home1);
        Patient p3 = new Patient("103","Gandhi", "70", home2);
        Patient p4 = new Patient("104","Rugvedh", "50", home3);
        Patient p5 = new Patient("105","Abhay", "25", home4);
        Patient p6 = new Patient("106","Bryan", "60", home1);
        Patient p7 = new Patient("107","Senorita", "12", home4);
        Patient p8 = new Patient("108","Darius", "39", home2);
        Patient p9 = new Patient("109","Parvati", "12", home3);

        patientDirectory.addPatient(p1, p2, p3, p4, p5, p6, p7, p8, p9); 
 
        VitalSign v1 = new VitalSign(72, 130, 180);
        VitalSign v2 = new VitalSign(80, 120, 180);
        VitalSign v3 = new VitalSign(78, 150, 180);
        VitalSign v4 = new VitalSign(78, 109, 180);
        VitalSign v5 = new VitalSign(68, 130, 180);
        
        Encounter e1 = new Encounter(v1, LocalDateTime.now());
        Encounter e2 = new Encounter(v2, LocalDateTime.now());
        Encounter e3 = new Encounter( v3, LocalDateTime.now());
        Encounter e4 = new Encounter( v4, LocalDateTime.now());
        Encounter e5 = new Encounter( v5, LocalDateTime.now());
        Encounter e6 = new Encounter( v2, LocalDateTime.now());
        Encounter e7 = new Encounter(v5, LocalDateTime.now());
        
        p2.getHistory().addEncounter(e7);
        p3.getHistory().addEncounter(e3);
        p1.getHistory().addEncounter(e2);
        p4.getHistory().addEncounter(e3);
        p5.getHistory().addEncounter(e4);
        p6.getHistory().addEncounter(e6);
        p7.getHistory().addEncounter(e5);
        p8.getHistory().addEncounter(e1);
        p9.getHistory().addEncounter(e7);
    }
    
}
