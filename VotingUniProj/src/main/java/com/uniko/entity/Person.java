/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uniko.entity;

import java.io.Serializable;

/**
 *
 * @author prant
 */
//@Entity
//@Table(name = "person")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    //@Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    //private Long id;
    
    //@Column(name = "firstname")
    private String firstName;
    
    //@Column(name = "lastname")
    private String lastName;
    
    //@Column(name = "email")
    private String email;
    
    public Person(){};

    public Person(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    
    

    /*public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }*/

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    

    @Override
    public String toString() {
        return "com.uniko.entity.Person[firstName=" + firstName + ",lastName=" + lastName + " ]";
    }
    
}
