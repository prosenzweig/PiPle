package com.piple.res;

import android.graphics.drawable.shapes.Shape;

import java.util.HashMap;
import java.util.ListIterator;

/**
 * Created by jeremie on 27/02/2017.
 */

public class MOI {

    private String name;
    private Message father;
    private Shape shape;
    private boolean delete=false;
    private boolean silent=false;


    public MOI(){

    }

    public MOI(String name, Message father, boolean delete, boolean silent) {
        this.name = name;
        this.father = father;
        this.delete = delete;
        this.silent = silent;
    }

    public MOI(String name, Message father) {
        this.name = name;
        this.father = father;
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Name", name);
        HashMap<String,Object> hashfather = father.toMap();
        result.put("Father", hashfather);
        result.put("Delete", delete);
        result.put("Silent",silent);
        return result;
    }
    public MOI toMOI(HashMap<String,Object> hashedMOI){

            Message father = new Message();
            father = father.toMessage((HashMap<String,Object>)hashedMOI.get("Father"));
            MOI mmoi=new MOI(hashedMOI.get("Name").toString(), father ,(boolean)hashedMOI.get("Delete"), (boolean)hashedMOI.get("Silent") );
            return mmoi;
        }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Message getFather() {
        return father;
    }

    public void setFather(Message father) {
        father = father;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public boolean isSilenced() {
        return silent;
    }

    public void setSilenced(boolean silenced) {
        this.silent = silenced;
    }


}
