package com.piple.res;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

import java.util.ArrayList;
import java.util.Date;

public class Oval {

    private ShapeDrawable mDrawable;
    private int mray;
    private Point mpt;



    private String mmessage;
    private ArrayList Ovallist = new ArrayList();




    private int type, iduser, idmessage;
    private boolean important, viewed, silent;
    private Date createdate;



    public Oval(ShapeDrawable mDrawable, String mmessage, int iduser, int type, int idmessage, boolean important, boolean viewed, boolean silent, Date createdate) {
        this.mDrawable = mDrawable;
        this.mmessage = mmessage;
        this.iduser = iduser;
        this.type = type;
        this.idmessage = idmessage;
        this.important = important;
        this.viewed = viewed;
        this.silent = silent;
        this.createdate = createdate;



        mDrawable = new ShapeDrawable(new OvalShape());

    }

    public void setcolor(int color){
        this.mDrawable.getPaint().setColor(color);
    }

    public ShapeDrawable getmDrawable(){
        return mDrawable;
    }

    public void setpos(float x, float y){
        mDrawable.setBounds((int)x-mray, (int)y-mray, (int)x+mray, (int)y+mray);
    }
    public void setOval(int mray, Point mpoint, int color){
        this.mpt=mpoint;
        this.mray=mray;
        int[] colors = {0xffffffff, color};
        float[] stops = {0.8f, 1f};
        mDrawable.setBounds(mpoint.x-mray, mpoint.y-mray, mpoint.x+mray, mpoint.y+mray);
        mDrawable.getPaint().setColor(color);
        mDrawable.getPaint().setShader(new RadialGradient(mray,mray,mray, colors, stops, Shader.TileMode.MIRROR ));


    }

    public void setmDrawable(ShapeDrawable mDrawable) {
        this.mDrawable = mDrawable;
    }

    public int getMray() {
        return mray;
    }

    public void setMray(int mray) {
        this.mray = mray;
    }
    public Point getMpt() {
        return mpt;
    }

    public void setMpt(Point mpt) {
        this.mpt = mpt;
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

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public int getIdmessage() {
        return idmessage;
    }

    public void setIdmessage(int idmessage) {
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