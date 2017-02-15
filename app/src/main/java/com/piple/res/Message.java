package com.piple.res;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Paul on 15/02/2017.
 */

public class Message {


    private String mmessage;
    private ArrayList<Message> Ovallist = new ArrayList();

    private int type;
    private String iduser, idmessage;
    private boolean important, viewed, silent;
    private Date createdate;

    public Message(String mmessage, ArrayList<Message> ovallist, int type, String iduser, String idmessage, boolean important, boolean viewed, boolean silent, Date createdate) {
        this.mmessage = mmessage;
        Ovallist = ovallist;
        this.type = type;
        this.iduser = iduser;
        this.idmessage = idmessage;
        this.important = important;
        this.viewed = viewed;
        this.silent = silent;
        this.createdate = createdate;
    }


    public String getMmessage() {
        return mmessage;
    }

    public void setMmessage(String mmessage) {
        this.mmessage = mmessage;
    }

    public ArrayList getOvallist() {
        return Ovallist;
    }

    public void setOvallist(ArrayList ovallist) {
        Ovallist = ovallist;
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
