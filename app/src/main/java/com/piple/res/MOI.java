package com.piple.res;

import android.graphics.drawable.shapes.Shape;

/**
 * Created by jeremie on 27/02/2017.
 */

public class MOI {

    private String name;
    private Message Father;
    private Shape shape;
    private boolean delete=false;
    private boolean silenced=false;


    public MOI(){

    }

    public MOI(String name, Message father) {
        this.name = name;
        Father = father;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Message getFather() {
        return Father;
    }

    public void setFather(Message father) {
        Father = father;
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
        return silenced;
    }

    public void setSilenced(boolean silenced) {
        this.silenced = silenced;
    }
}
