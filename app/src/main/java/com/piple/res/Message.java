package com.piple.res;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

/**
 * Created by Paul on 15/02/2017.
 */

/**
 *
 * Class Message
 *      that contains the message and bubble info
 *        @see Oval
 *
 *
 *        @author jeremie
 *
 */
public class Message {

    /**
     *
     * the message string
     */
    private String mmessage;
    private ArrayList children = new ArrayList();
    /***
     * information form the message
     *
     *  poid is the size of the message and is compute in
     *  @see Window
     *
     */
    private String iduser, idmessage;
    private Oval oval;



    public Message(String mmessage, ArrayList<Message> children, int type, String iduser, String idmessage, boolean important, boolean viewed, boolean silent, Date createdate) {
        this.mmessage = mmessage;
        this.children = children;
        this.iduser = iduser;
        this.idmessage = idmessage;
    }
    public Message(){}

    public Message(String mmessage, ArrayList<Message> children, int type, int childnumb, int poids, int likenumb, String iduser, String idmessage, boolean important, boolean viewed, boolean silent, Date createdate) {
        this.mmessage = mmessage;
        this.children = children;
        this.iduser = iduser;
        this.idmessage = idmessage;
    }
    // Two recursive functions for mapping the message and all its childs.

    //TODO: create functions just to map one message.

    /***
     *
     * convert the object into a map and al its children
     *
     * @return the map
     */
    public HashMap<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("mMessage", mmessage);
        result.put("IdUser", iduser);
        result.put("IdMessage", idmessage);
        if(children!=null){
        ListIterator iterator = children.listIterator();
        while(iterator.hasNext()){

            Message child= (Message) iterator.next();
            HashMap<String,Object> contacthashed= child.toMap();
            iterator.previous();
            iterator.set(contacthashed);
            iterator.next();
        }}
        result.put("Children", children);
        return result;
    }


    /**
     * convert the map into its message object form and all its children
     * @param messagemap
     */

    public void toMessage(Map<String, Object> messagemap){

        children = (ArrayList) messagemap.get("Children");
        if(children!=null){
        ListIterator iterator = children.listIterator();
        while(iterator.hasNext()){
            HashMap<String,Object> hashedchild = (HashMap<String,Object>) iterator.next();
            Message child = new Message();
            child.toMessage(hashedchild);
            iterator.previous();
            iterator.set(child);
            iterator.next();
        }}else children = new ArrayList();

        this.mmessage = messagemap.get("mMessage").toString();
        this.iduser = messagemap.get("IdUser").toString();
        this.idmessage= null;
        //HashMap<String, Object>=(HashMap<String,Object>)messagemap.get("CreateDate");

    }


    public String getMmessage() {
        return mmessage;
    }

    public void setMmessage(String mmessage) {
        this.mmessage = mmessage;
    }

    public ArrayList<Message> getChildren() {
        return children;
    }


    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }



    public Oval getGoval() {
        return oval;
    }

    public void setOval(Oval oval) {
        this.oval = oval;
    }
}
