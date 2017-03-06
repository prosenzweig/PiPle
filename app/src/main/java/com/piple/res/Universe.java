package com.piple.res;



import android.media.Image;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
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

    public Universe(String id, String name, ArrayList universeUserList, ArrayList MOIList) {
        this.id = id;
        this.name = name;
        this.universeUserList = universeUserList;
        this.MOIList = MOIList;
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
    // on map l'univers
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Id", id);
        result.put("Name", name);
        ListIterator iterator = universeUserList.listIterator();
        // on appel la fonction de mapping des contacts
        while(iterator.hasNext()){

            Contact contact= (Contact)iterator.next();
            HashMap<String,Object> contacthashed= contact.toMap();
            iterator.previous();
            iterator.set(contacthashed);
            iterator.next();
        }
        result.put("UniverseUserList", universeUserList);
        // celle de mapping des MOI
        iterator = MOIList.listIterator();
        while(iterator.hasNext()){

            MOI mymoi= (MOI)iterator.next();
            HashMap<String,Object> contacthashed= mymoi.toMap();
            iterator.previous();
            iterator.set(contacthashed);
            iterator.next();
        }
        result.put("MOIList", MOIList);

        return result;
    }
    @Exclude
    public Universe toUniverse(Map<String, Object> univmap){

        ArrayList contactList = (ArrayList) univmap.get("UniverseUserList");
        ListIterator iterator = contactList.listIterator();

        //ICI ON MAP DIRECTEMENT LE MOI ET LE CONTACT DANS LA FONCTION ( parce que )

        while(iterator.hasNext()){
            HashMap<String,Object> hashedcontact = (HashMap<String,Object>) iterator.next();
            Contact contact=new Contact(hashedcontact.get("Pseudo").toString(), hashedcontact.get("Id").toString());
            iterator.previous();
            iterator.set(contact);
            iterator.next();
        }
        iterator = MOIList.listIterator();

        while(iterator.hasNext()){

            //on s'occupe directement des MOI de l'univers

            HashMap<String,Object> hashedMOI = (HashMap<String,Object>) iterator.next();
            Message father = new Message();
            father = father.toMessage((HashMap<String,Object>)hashedMOI.get("Father"));
            MOI mmoi=new MOI(hashedMOI.get("Name").toString(), father ,(boolean)hashedMOI.get("Delete"), (boolean)hashedMOI.get("Silent") );
            iterator.previous();
            iterator.set(mmoi);
            iterator.next();
        }
       return  new Universe(univmap.get("Id").toString(), univmap.get("Name").toString(), contactList, MOIList );
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
