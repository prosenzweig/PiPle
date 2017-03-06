package com.piple.res;

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
    private ArrayList<Message> children = new ArrayList();
    private int type, childnumb, likenumb, poids;
    private String iduser, idmessage;
    private boolean important, viewed, silent;
    private Date createdate;
    private Oval Roval,Goval;



    public Message(String mmessage, ArrayList<Message> ovallist, int type, String iduser, String idmessage, boolean important, boolean viewed, boolean silent, Date createdate) {
        this.mmessage = mmessage;
        children = ovallist;
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
        ListIterator iterator = children.listIterator();
        while(iterator.hasNext()){

            Message child= (Message) iterator.next();
            HashMap<String,Object> contacthashed= child.toMap();
            iterator.previous();
            iterator.set(contacthashed);
            iterator.next();
        }
        result.put("Children", children);
        return result;
    }
    public Message toMessage(Map<String, Object> messagemap){

        ArrayList itschildren = (ArrayList) messagemap.get("Children");
        ListIterator iterator = itschildren.listIterator();
        while(iterator.hasNext()){
            HashMap<String,Object> hashedchild = (HashMap<String,Object>) iterator.next();
            Message child = new Message();
            child = child.toMessage(hashedchild);
            iterator.set(child);
            iterator.next();
        }
        return  new Message(messagemap.get("mMessage").toString(),children, (int)messagemap.get("Type"),(int)messagemap.get("ChildNumb"),(int)messagemap.get("LikeNumb"),(int)messagemap.get("Poids"),messagemap.get("IdUser").toString(),messagemap.get("IdMessage").toString(),(boolean)messagemap.get("Important"),(boolean)messagemap.get("Viewed"),(boolean)messagemap.get("Silent"),(Date)messagemap.get("CreateDate"));
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

    public void setOvallist(ArrayList ovallist) {
        children = ovallist;
    }

    public int getType() {
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
}
