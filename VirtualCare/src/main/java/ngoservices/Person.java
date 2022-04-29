/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ngoservices;

/**
 *
 * @author Akshay Barne
 */
public class Person {
    String name;
    String age;
    House house;

    public Person() {
    }

    public Person(String name, String age, House house) {
        this.name = name;
        this.age = age;
        this.house = house;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }
    
    
}
