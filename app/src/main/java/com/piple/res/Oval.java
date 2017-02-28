package com.piple.res;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

public class Oval {

    private ShapeDrawable mDrawable;
    private boolean message, msgholder, faceholder; // la bbubble peut être plusieurs choses en effet;
    private int importance; // remplace size ici
    //FOR THE GearofReply VIEW (paul's)

    private int fray;
    private Point fpt;
    private Contact contact;
    private Message msg;

    //FOR THE LinkVIEW (jerem's )
    
    private int rray;
    private int rdistance;


    public Oval(int fray, Point fpt, int color, Message msg ) {

        this.fpt=fpt;
        this.fray=fray;
        this.msg=msg;
        int[] colors = {0xffffffff, color};
        float[] stops = {0.8f, 1f};
        mDrawable = new ShapeDrawable(new OvalShape());
        mDrawable.setBounds(fpt.x-fray, fpt.y-fray, fpt.x+fray, fpt.y+fray);
        mDrawable.getPaint().setColor(color);
        mDrawable.getPaint().setShader(new RadialGradient(fray,fray,fray, colors, stops, Shader.TileMode.MIRROR ));
    }
    public Oval(){}





    public void setMsg(Message msg){
        this.msg=msg;
    }

    public void setcolor(int color){
        this.mDrawable.getPaint().setColor(color);
    }

    public ShapeDrawable getmDrawable(){
        return mDrawable;
    }

    public void setpos(float x, float y){
        mDrawable.setBounds((int)x-fray, (int)y-fray, (int)x+fray, (int)y+fray);
    }


    public Message getMsg() {
        return msg;
    }

    public void setDrawable(ShapeDrawable mDrawable) {
        this.mDrawable = mDrawable;
    }

    public int getfray() {
        return fray;
    }

    public void setfray(int mfray) {
        this.fray = mfray;
    }
    public Point getfpt() {
        return fpt;
    }

    public void setfpt(Point mfpt) {
        this.fpt = mfpt;
    }

    public boolean isMessage() {
        return message;
    }

    public void setMessage(boolean message) {
        this.message = message;
    }

    public boolean isMsgholder() {
        return msgholder;
    }

    public void setMsgholder(boolean msgholder) {
        this.msgholder = msgholder;
    }

    public boolean isFaceholder() {
        return faceholder;
    }

    public void setFaceholder(boolean faceholder) {
        this.faceholder = faceholder;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public int getFray() {
        return fray;
    }

    public void setFray(int fray) {
        this.fray = fray;
    }

    public Point getFpt() {
        return fpt;
    }

    public void setFpt(Point fpt) {
        this.fpt = fpt;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public int getRray() {
        return rray;
    }

    public void setRray(int rray) {
        this.rray = rray;
    }

    public int getRdistance() {
        return rdistance;
    }

    public void setRdistance(int rdistance) {
        this.rdistance = rdistance;
    }
}