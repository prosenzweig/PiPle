package com.piple.res;

import android.media.Image;

/**
 * Created by jeremie on 15/02/2017.
 */

public class Contact {

    private String pseudo;
    private String id;
    private Image profilpict;

    public Contact(String pseudo, String id) {
        this.pseudo = pseudo;
        this.id = id;
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
