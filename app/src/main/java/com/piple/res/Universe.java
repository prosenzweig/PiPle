package com.piple.res;



import android.graphics.Color;
import android.media.Image;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;


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
    private ArrayList<Contact> universeUserList;
    private ArrayList MOIList;
    private Map<String ,Integer> colormap;
    //private Image  icon;



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
    public void checkid(String id, String pseudo){
        Contact cont = new Contact(pseudo, id);
        if(!universeUserList.contains(cont)){
            universeUserList.add(cont);
            addColormap();
        }

    }
    public void addColormap(){
        ArrayList<Integer> colorlist = new ArrayList<>();
        colorlist.add(0xffC8A2B5);
        colorlist.add(0xffffebcd);
        colorlist.add(0xffC1DFBB);
        colorlist.add(0xff5DBCD2);
        colorlist.add(0xffF2C584);

        colormap=new HashMap<>();
        for(int i =0; i<universeUserList.size();i++){

            colormap.put(universeUserList.get(i).getId(),colorlist.get(i%5));
        }
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
        if (MOIList!=null){
            MoiListtoMap();
       }
        else MOIList =  new ArrayList<MOI>();
        result.put("MOIList", MOIList);

        return result;
    }

    public void MoiListtoMap() {

        ListIterator iterator = this.MOIList.listIterator();
        while (iterator.hasNext()) {

            MOI mymoi = (MOI) iterator.next();
            HashMap<String, Object> moihashed = mymoi.toMap();
            iterator.previous();
            iterator.set(moihashed);
            iterator.next();
        }
    }
    @Exclude
    public void toUniverse(Map<String, Object> univmap){

        ArrayList contactList = (ArrayList) univmap.get("UniverseUserList");
        if(contactList!=null) {
        ListIterator iterator = contactList.listIterator();

        //ICI ON MAP DIRECTEMENT LE MOI ET LE CONTACT DANS LA FONCTION ( parce que )


            while (iterator.hasNext()) {
                HashMap<String, Object> hashedcontact = (HashMap<String, Object>) iterator.next();
                Contact contact = new Contact(hashedcontact.get("Pseudo").toString(), hashedcontact.get("Id").toString());
                iterator.previous();
                iterator.set(contact);
                iterator.next();
            }
        }else  contactList = new ArrayList();

        ArrayList MOIList = (ArrayList) univmap.get("MOIList");
       if(MOIList!=null){
           Log.d("UNIVERSE","pas nulle");
            ListIterator iterator = MOIList.listIterator();
            while(iterator.hasNext()){
                Log.d("UNIVERSE","iteré");
                MOI moi = new MOI();
                moi.toMOI((HashMap<String,Object>)iterator.next());
                iterator.previous();
                iterator.set(moi);
                iterator.next();
            }

        }
        else MOIList = new ArrayList<>();

        this.id = univmap.get("Id").toString();
        this.name = univmap.get("Name").toString();
        this.universeUserList = contactList;
        this.MOIList = MOIList;

        addColormap();
   }

   public void toMOIList(){
       ListIterator iterator = MOIList.listIterator();
       while(iterator.hasNext()){
           MOI moi = new MOI();
           moi.toMOI((HashMap<String,Object>)iterator.next());
           iterator.previous();
           iterator.set(moi);
           iterator.next();
       }

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

    public ArrayList<Contact> getuniverseUserList() {
        return universeUserList;
    }

    public void setuniverseUserList(ArrayList user) {
        this.universeUserList = user;
    }

    public ArrayList<MOI> getMOIList() {
        return MOIList;
    }

    public void setMOIList(ArrayList MOIList) {
        this.MOIList = MOIList;
    }

    public Map<String, Integer> getColormap() {
        return colormap;
    }


}
