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
 * Class User
 *
 * Contains a user and his information.
 */
@IgnoreExtraProperties
public class User
{



    /// RESOURCES ///

    private String id;
    private String pseudo;
    private ArrayList ContactList;
    private Image profilpicture;
    private ArrayList universelist;



    /// CONSTRUCTORS ///
    public User(){  //toujours avoir un constructeur vide // Default constructor required for calls to DataSnapshot.getValue(Post.class)

    }

    public User(String id, String pseudo) {
        universelist = new ArrayList();
        this.id = id;
        this.pseudo = pseudo;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Id", id);
        result.put("Pseudo", pseudo);
        result.put("ContactList", ContactList);
        result.put("ProfilPicture", profilpicture);
        result.put("UniverseList", universelist);


        return result;
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
