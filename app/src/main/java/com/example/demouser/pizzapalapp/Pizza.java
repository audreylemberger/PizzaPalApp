package com.example.demouser.pizzapalapp;

/**
 * Created by demouser on 1/17/17.
 */


import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;


/**
 * Class to handle each individual pizza
 * @author kataiello
 * @version 12/13/16
 */
public class Pizza
{

    private boolean isVegan;
    private boolean isVeg;
    private boolean isKosher;
    private boolean isGF;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    private String vendor;

    private String toppings;
    private String building;
    private String room;

    public boolean isVegan() {
        return isVegan;
    }

    public void setVegan(boolean vegan) {
        isVegan = vegan;
    }

    public boolean isVeg() {
        return isVeg;
    }

    public void setVeg(boolean veg) {
        isVeg = veg;
    }

    public boolean isKosher() {
        return isKosher;
    }

    public void setKosher(boolean kosher) {
        isKosher = kosher;
    }

    public boolean isGF() {
        return isGF;
    }

    public void setGF(boolean GF) {
        isGF = GF;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getToppings() {
        return toppings;
    }

    public void setToppings(String toppings) {
        this.toppings = toppings;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }



    public Pizza(){

    }



    public Pizza(String room, String building, String toppings, String vend, boolean isVegan, boolean
            isVeg, boolean isKosher, boolean isGF)
    {
        //indices are vegetarian, vegan, kosher/halal, gluten free
        this.building = building;
        this.room = room;

        this.toppings = toppings;


        this.vendor = vend;
        this.isVegan = isVegan;
        this.isVeg = isVeg;
        this.isKosher = isKosher;
        this.isGF = isGF;
        //this.note = note;
//        delivered = System.currentTimeMillis();
//        delivered.set(Calendar.DAY_OF_YEAR, Calendar.DATE, Calendar.HOUR,Calendar.MINUTE, Calendar.SECOND);
//        delivered.add(Calendar.HOUR, 3);
//        expires = delivered.getTime();

//        long expiremilis = 60000l * 120; // 1 minute
//        Calendar expireDate= Calendar.getInstance();
////      Expires on one minute from now
//        expireDate.setTimeInMillis(System.currentTimeMillis() + expiremilis);
    }





}