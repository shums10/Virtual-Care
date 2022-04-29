/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adminservices;

import pharmaservices.*;
import ngoservices.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Akshay Barne
 */
public class PersonDirectory {
     List<Person> list = new ArrayList<>();
    
    public void addPerson(Person ...p){
        list.addAll(Arrays.asList(p));
    }

    public List<Person> getList() {
        return list;
    }

    public void setList(List<Person> list) {
        this.list = list;
    }    
}
