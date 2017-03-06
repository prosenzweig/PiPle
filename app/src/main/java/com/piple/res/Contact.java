package com.piple.res;



import android.media.Image;

import java.util.HashMap;
import java.util.Map;


/**
 * Class Contact
 *
 * Stores the information of a contact.
 */
public class Contact
{



    /// RESOURCES ///

    private String pseudo;
    private String id;
    private Image profilpict;



    /// CONSTRUCTORS ///

    public Contact(String pseudo, String id) {
        this.pseudo = pseudo;
        this.id = id;
    }



    /// GETTERS & SETTERS ///

    // on a juste la fonction de mapping car l'autre est dasn le map de l'univers
    public HashMap<String,Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("Id", id);
        result.put("Pseudo", pseudo);
        return result;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public Image getProfilpict() {
        return profilpict;
    }

    public void setProfilpict(Image profilpict) {
        this.profilpict = profilpict;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
