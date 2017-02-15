package com.piple.res;

import java.util.ArrayList;

/**
 * Created by jeremie on 15/02/2017.
 */

public class Universe {

    private int id;

    private String name;
    private ArrayList user;
    private boolean currentUniverse;

    public Universe(ArrayList user, String name, int id) {
        this.user = user;
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList getUser() {
        return user;
    }

    public void setUser(ArrayList user) {
        this.user = user;
    }

}
