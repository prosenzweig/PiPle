package com.piple.res;



import android.media.Image;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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
    private ArrayList MOIList;
    private Image  icon;



    /// CONSTRUCTORS ///
    public Universe()
    {
// Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }


    public Universe(Contact user, String name, String id) {
        universeUserList = new ArrayList();
        universeUserList.add(user);
        this.name = name;
        this.id = id;
    }
    public Universe(ArrayList Contacts, String name, String id) {
        universeUserList = Contacts;
        this.name = name;
        this.id = id;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Id", id);
        result.put("Name", name);
        result.put("UniverseUserList", universeUserList);
        return result;
    }
    @Exclude
    public Universe toUniverse(Map<String, Object> univmap){

        ArrayList contactList = (ArrayList) univmap.get("UniverseUserList");
       return  new Universe(contactList, univmap.get("Name").toString(), univmap.get("Id").toString());
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

}
