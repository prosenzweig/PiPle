package com.piple.res;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by jeremie on 07/03/2017.




public class Roadview {

    private boolean roadview;
    private int[][]  relativpositiontable;
    public static Map<String,Integer> COLORTABLE;

    public Roadview(boolean roadview) {
        this.roadview = roadview;
    }




    public Oval CreateOval(Message message, float fatherposx, float fatherposy){

        computeray(message);
        //TODO iteration pour checker la distance normale que devrait avoir chaques enfant par rapport à son père si on ne l'a pas déjà
       /* ListIterator iterator = children.listIterator();
        while(iterator.hasNext()){

            iterator.getChildren().get

        }

        //TODO fonction pour calculer le poid si on ne l'a pas déjà

        //TODO Iteration pour checker si il y a superposition,
        //TODO qui change cela en repoussant les l


        return
    }


    public float computeray(ArrayList children){
        return (float)(200*Math.log(1+children.size()));
    }
    public int setColor(String id){
        return COLORTABLE.get(id);
    }
    public ArrayList amongthebiggest(ArrayList children){

        return
    }
    public Oval checkforcollision(Oval oval, Universe universe){


        return
    }







    public boolean isRoadview() {
        return roadview;
    }

    public void setRoadview(boolean roadview) {
        this.roadview = roadview;
    }

    public int[][] getRelativpositiontable() {
        return relativpositiontable;
    }

    public void setRelativpositiontable(int[][] relativpositiontable) {
        this.relativpositiontable = relativpositiontable;
    }
}
*/