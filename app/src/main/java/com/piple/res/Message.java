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

public class Message {


    private String mmessage;
    private ArrayList children = new ArrayList();
    private long type, childnumb, likenumb, poids; ///type always at 0 for now
    private String iduser, idmessage;
    private boolean important, viewed, silent;
    private Date createdate;
    private Oval Roval,Goval;



    public Message(String mmessage, ArrayList<Message> children, int type, String iduser, String idmessage, boolean important, boolean viewed, boolean silent, Date createdate) {
        this.mmessage = mmessage;
        this.children = children;
        this.type = type;
        this.iduser = iduser;
        this.idmessage = idmessage;
        this.important = important;
        this.viewed = viewed;
        this.silent = silent;
        this.createdate = createdate;
    }
    public Message(){}

    public Message(String mmessage, ArrayList<Message> children, int type, int childnumb, int poids, int likenumb, String iduser, String idmessage, boolean important, boolean viewed, boolean silent, Date createdate) {
        this.mmessage = mmessage;
        this.children = children;
        this.type = type;
        this.childnumb = childnumb;
        this.poids = poids;
        this.likenumb = likenumb;
        this.iduser = iduser;
        this.idmessage = idmessage;
        this.important = important;
        this.viewed = viewed;
        this.silent = silent;
        this.createdate = createdate;
    }
    // Two recursive functions for mapping the message and all its childs.

    //TODO: create functions just to map one message.

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("mMessage", mmessage);
        result.put("Type", type);
        result.put("ChildNumb", childnumb);
        result.put("LikeNumb",likenumb);
        result.put("Poids", poids);
        result.put("IdUser", iduser);
        result.put("IdMessage", idmessage);
        result.put("Important", important);
        result.put("Viewed", viewed);
        result.put("Silent",silent);
        result.put("CreateDate", createdate);
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
        this.type =(long)messagemap.get("Type");
        this.childnumb = (long) messagemap.get("ChildNumb");
        this.likenumb = (long) messagemap.get("LikeNumb");
        this.poids = (long) messagemap.get("Poids");
        this.iduser = messagemap.get("IdUser").toString();
        this.idmessage= null;
        this.important=(boolean)messagemap.get("Important");
        this.viewed=(boolean)messagemap.get("Viewed");
        this.silent=(boolean)messagemap.get("Silent");
        //HashMap<String, Object>=(HashMap<String,Object>)messagemap.get("CreateDate");

    }


    public String getMmessage() {
        return mmessage;
    }

    public void setMmessage(String mmessage) {
        this.mmessage = mmessage;
        Goval.setText(mmessage);
    }

    public ArrayList<Message> getChildren() {
        return children;
    }

    public void setChildren(ArrayList children) {
        this.children = children;
    }

    public long getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getIdmessage() {
        return idmessage;
    }

    public void setIdmessage(String idmessage) {
        this.idmessage = idmessage;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public boolean isSilent() {
        return silent;
    }

    public void setSilent(boolean silent) {
        this.silent = silent;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Oval getRoval() {
        return Roval;
    }

    public void setRoval(Oval roval) {
        Roval = roval;
    }

    public Oval getGoval() {
        return Goval;
    }

    public void setGoval(Oval goval) {
        Goval = goval;
        Goval.setText(mmessage);
    }

    public long getChildnumb() {
        return childnumb;
    }

    public void setChildnumb(int childnumb) {
        this.childnumb = childnumb;
    }

    public long getLikenumb() {
        return likenumb;
    }

    public void setLikenumb(int likenumb) {
        this.likenumb = likenumb;
    }

    public long getPoids() {
        return poids;
    }

    public void setPoids(int poids) {
        this.poids = poids;
    }
}
