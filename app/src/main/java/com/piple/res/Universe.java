package com.piple.res;



import java.util.ArrayList;



/**
 * Class Universe
 *
 * Contains a whole universe.
 */
public class Universe
{



    /// RESOURCES ///

    private String id;
    private String name;
    private ArrayList universeUserList;
    private boolean currentUniverse;



    /// CONSTRUCTORS ///

    public Universe(Contact user, String name, String id) {
        universeUserList = new ArrayList();
        universeUserList.add(user);
        this.name = name;
        this.id = id;
    }



    /// GETTERS & SETTERS ///

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList getUniverseUserList() {
        return universeUserList;
    }

    public void setUniverseUserList(ArrayList user) {
        this.universeUserList = user;
    }

    public boolean isCurrentUniverse() {
        return currentUniverse;
    }

    public void setCurrentUniverse(boolean currentUniverse) {
        this.currentUniverse = currentUniverse;
    }

}
