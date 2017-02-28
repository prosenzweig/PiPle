package com.piple.res;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created by jeremie on 26/02/2017.
 *
 * the class that is going to check and update everything concerning this view
 *
 */

public class RoadView {

    private boolean used;

    public RoadView(boolean used){
        this.used= used;
    }




    public boolean set(Message father){
        CreateOvalandsetRay(father);
        //TODO iteration pour checker la distance normale que devrait avoir chaques enfant par rapport à son père si on ne l'a pas déjà
       /* ListIterator iterator = children.listIterator();
        while(iterator.hasNext()){

            iterator.getChildren().get

        }*/

        //TODO fonction pour calculer le poid si on ne l'a pas déjà

       //TODO Iteration pour checker si il y a superposition,
                //TODO qui change cela en repoussant les l
        

    return true;
    }
    public void CreateOvalandsetRay(Message message)
    {
        //TODO: iteration pour calculer le rayon de toutes les bubbles et les instancier en les donnant à leur message et le faire que pour les 4 plus grosses
    }


    //TODO create IHM
}
