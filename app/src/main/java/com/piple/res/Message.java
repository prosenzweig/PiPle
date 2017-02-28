package com.piple.res;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Paul on 15/02/2017.
 */

public class Message {


    private String mmessage;
    private ArrayList<Message> children = new ArrayList();
    private Message father;

    private int type, childnumb, likenumb, poids;
    private String iduser, idmessage;
    private boolean hasbubble, important, viewed, silent;
    private Date createdate;
    private Oval Roval,Goval;



    public Message(String mmessage, ArrayList<Message> ovallist, int type, String iduser, String idmessage, boolean important, boolean viewed, boolean silent, Date createdate, Message father) {
        this.mmessage = mmessage;
        children = ovallist;
        this.type = type;
        this.iduser = iduser;
        this.idmessage = idmessage;
        this.important = important;
        this.viewed = viewed;
        this.silent = silent;
        this.createdate = createdate;
        this.father=father;
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
