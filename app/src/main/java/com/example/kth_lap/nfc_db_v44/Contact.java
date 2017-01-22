package com.example.kth_lap.nfc_db_v44;

/**
 * Created by KTH_LAP on 2017-01-21.
 */

public class Contact {

    int id;
    String name;
    String phone_number;

    public Contact(){

    }
    public Contact(int id, String name, String phone_number){
        this.id = id;
        this.name = name;
        this.phone_number = phone_number;
    }

    public Contact(String name, String phone_number){
        this.name = name;
        this.phone_number = phone_number;
    }

    public int getID(){
        return this.id;
    }

    public void setID(int id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getPhoneNumber(){
        return this.phone_number;
    }

    public void setPhoneNumber(String phone_number){
        this.phone_number = phone_number;
    }
}