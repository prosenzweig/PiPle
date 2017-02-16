package com.piple.res;



import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;



/**
 * Class User
 *
 * Contains a user and his information.
 */
public class User
{



    /// RESOURCES ///

    private String id;
    private String pseudo;
    private ArrayList ContactList;
    private Image profilpicture;
    private ArrayList universelist;



    /// CONSTRUCTORS ///

    public User(String id, String pseudo) {
        universelist = new ArrayList();
        this.id = id;
        this.pseudo = pseudo;
    }



    /// GETTERS & SETTERS ///

    public ArrayList getUniverselist() {
        return universelist;
    }

    public void setUniverselist(Universe universe) {
        universelist.add(universe);
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
