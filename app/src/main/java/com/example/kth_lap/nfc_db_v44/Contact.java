package com.example.kth_lap.nfc_db_v44;

/**
 * Created by KTH_LAP on 2017-01-21.
 */

public class Contact {

    int id;
    String content;
    String imgPath;

    public Contact() {

    }

    public Contact(int id, String content,String imgPath) {
        this.id = id;
        this.content = content;
        this.imgPath = imgPath;
    }

    public Contact(String content,String imgPath) {
        this.content = content;
        this.imgPath = imgPath;
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

    public String getImgPath() {
        return this.imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}