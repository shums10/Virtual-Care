/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pharmaservices;

import ngoservices.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Akshay Barne
 */
public class Community {
    
    String name;
    City city;
    List<House> houses;

    public List<House> getHouses() {
        return houses;
    }

    public void setHouses(List<House> houses) {
        this.houses = houses;
    }
    
    public Community(String name) {
        this.name = name;
        houses = new ArrayList<>();
    }
   
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City cityAssigned) {
        city = cityAssigned;
    }
    
    public void addHouses(House ...houseObj){
        houses.addAll(Arrays.asList(houseObj));
    }
    
}