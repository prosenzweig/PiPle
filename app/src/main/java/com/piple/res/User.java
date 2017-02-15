package com.piple.res;

import android.media.Image;

import java.util.ArrayList;

/**
 * Created by jeremie on 15/02/2017.
 */

public class User {

    private String id;
    private String pseudo;
    private ArrayList ContactList;
    private Image profilpicture;
    private ArrayList

    public User(String id, String pseudo) {
        this.id = id;
        this.pseudo = pseudo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public ArrayList getContactList() {
        return ContactList;
    }

    public void setContactList(ArrayList contactList) {
        ContactList = contactList;
    }

    public Image getProfilpicture() {
        return profilpicture;
    }

    public void setProfilpicture(Image profilpicture) {
        this.profilpicture = profilpicture;
    }
}
