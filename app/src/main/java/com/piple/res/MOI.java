package com.piple.res;

import android.graphics.drawable.shapes.Shape;

import java.util.HashMap;
import java.util.ListIterator;

/**
 * Created by jeremie on 27/02/2017.
 *
 * Class MOI that descreibe the conersation container and its information it contains the initial message as well
 */

public class MOI {

    private String name;
    private Message father;
    /**
     *
     * value that will define its shape (notused)
     */
    private Shape shape;
    private boolean delete=false;
    private boolean silent=false;


    public MOI(){

    }



    /**
     * transform it into a map
     * @return a map
     */
    public HashMap<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Name", name);
       HashMap<String,Object> hashfather = father.toMap();
        result.put("Father", hashfather);
        result.put("Delete", delete);
        result.put("Silent",silent);
        return result;
    }

    /**
     * transform the map into itself
     * @param hashedMOI
     */
    public void toMOI(HashMap<String,Object> hashedMOI){

            Message father = new Message();
            father.toMessage((HashMap<String,Object>)hashedMOI.get("Father"));
            if(hashedMOI.get("Name")!=null) {
                this.name = hashedMOI.get("Name").toString();
            }
            this.father =father;
            this.delete = (boolean)hashedMOI.get("Delete");
            this.silent = (boolean)hashedMOI.get("Silent") ;


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
        this.father = father;
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
