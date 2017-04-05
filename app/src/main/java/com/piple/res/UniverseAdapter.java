package com.piple.res;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.piple.app.R;

import java.util.List;

/**
 * @author internet and jeremie
        * Class UniverseAdapter
        *      extends ArrayAdapter
        *
        *
        * Is used to instanciate different universe button inside the HomeActivity's listview
        */

public class UniverseAdapter extends ArrayAdapter<Universe> {

    /**
     *
     * @param context the context of the current viex
     * @param Universes the list of univers object to draw
     */
    public UniverseAdapter(Context context, List<Universe> Universes) {
        super(context, 0, Universes);
    }

    /**
     *
     *function that override the arrayadapter's one to create a view holder of the universe button and image instead
     *
     * @param position
     *      the position of this view
     * @param convertView
     *      the initial view to be converted
     * @param parent
     *      the parent view
     * @return View that had been converted and containing the universe button and image ( if found)
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.universebutton,parent, false);
            // if it's not recycled, initialize some attributes

        }

        UniverseViewHolder viewHolder = (UniverseViewHolder) convertView.getTag();
        if(viewHolder == null){
            // on créer ce qui va contenir cette partie de vue ( cela peut être  bien plus complexe ofcourse)
            //Lié au layout  universe button
            viewHolder = new UniverseViewHolder();
            viewHolder.Universename = (TextView) convertView.findViewById(R.id.name);
            viewHolder.Unimage = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Universe> Universes
        Universe Universe = getItem(position);
        viewHolder.Universename.setText(Universe.getName());
        //TODO : set real image
        viewHolder.Unimage.setBackgroundColor(1234);

        return convertView;
    }

    /**
     * Class UniverseViewHolder
     *      Contains the information of the holder of one universe being instanciated by universe adpater
     *      @see UniverseAdapter
     */

    private class UniverseViewHolder{
        public TextView Universename;
        public ImageView Unimage;


        public UniverseViewHolder(){}

    }
}