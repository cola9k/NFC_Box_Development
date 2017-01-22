package com.example.kth_lap.nfc_db_v44;

/**
 * Created by KTH_LAP on 2017-01-21.
 */

public class Contact {

    int id;
    String content;

    public Contact() {

    }

    public Contact(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public Contact(String content) {
        this.content = content;
    }

    public int getID() {
        return this.id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}