package com.piple.res;



import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;


import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * @author jeremie
 * Class User
 *
 * Contains a user and his informations.
 */
@IgnoreExtraProperties
public class User
{



    /// RESOURCES ///

    private String id;
    private String pseudo;
    /**
     * an arraylist of contacts that the user has  ( not used in this version )
     */
    private ArrayList ContactList;
    private Image profilpicture;



    /// CONSTRUCTORS ///


    public User(String id, String pseudo) {
        this.id = id;
        this.pseudo = pseudo;
    }

    /**
     * transform this Object into a map
     *
     * @return the user map
     */

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Id", id);
        result.put("Pseudo", pseudo);
        result.put("ContactList", ContactList);
        result.put("ProfilPicture", profilpicture);


        return result;
    }



    /// GETTERS & SETTERS ///

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
