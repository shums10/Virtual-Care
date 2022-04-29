/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utility;

import java.util.regex.Pattern;

/**
 *
 * @author Akshay Barne
 */
public class Validator {
    
    
   public boolean isNumeric(String value){
       String regex = "^[0-9]+$";
       Pattern p = Pattern.compile(regex);
       return p.matcher(value).matches();         
    }
   
    public boolean isAlphanumeric(String value){
       String regex = "^[a-zA-Z0-9]+$";
       Pattern p = Pattern.compile(regex);
       return p.matcher(value).matches();         
    }
        
      public boolean isAlphabetic (String value){
       String regex = "^[a-zA-Z ]+$";
       Pattern p = Pattern.compile(regex);
       return p.matcher(value).matches();       
    }
    
  
    public boolean isNotNullAndEmpty(String value){
        return (value!=null && !value.isEmpty());
    }
     
}
