/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package commonutils;

import pharmaservices.*;
import ngoservices.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Akshay Barne
 */
public class City {
    
    String name;
    List<Community> communities;

    public City() {
        communities = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Community> getCommunities() {
        return communities;
    }
    public void addCommunity(Community ...c){
        communities.addAll(Arrays.asList(c));
    }

    public void setCommunities(List<Community> communities) {
        this.communities = communities;
    }
    
    public House getHouse(String houseName){
        House house = null;
        for(Community comm: communities){
            for(House h: comm.getHouses()){
                if(houseName.equalsIgnoreCase(h.getHouseNumber()))
                    house = h;
            }
        }
        return house;
    }
     
    public Community getCommunity(String community){
        Community c= null;
        for(Community comm: communities){
                if(!community.equalsIgnoreCase(comm.getName())) {
            } else {
                    c = comm;
            }
            }
           return c;
        }    

    public boolean doesHouseBelongToCommunity(Community com, House house){
        boolean flag = false;
        for(House h: com.getHouses()){
            if(house == h)
                flag = true;
        }
        return flag;
    }
}
