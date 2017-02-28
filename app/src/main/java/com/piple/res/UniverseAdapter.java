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

public class UniverseAdapter extends ArrayAdapter<Universe> {

    public UniverseAdapter(Context context, List<Universe> Universes) {
        super(context, 0, Universes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.universebutton,parent, false);
            // if it's not recycled, initialize some attributes

        }

        UniverseViewHolder viewHolder = (UniverseViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new UniverseViewHolder();
            viewHolder.Universename = (TextView) convertView.findViewById(R.id.name);
            System.out.println("viewcreate");
            viewHolder.Unimage = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Universe> Universes
        Universe Universe = getItem(position);
        viewHolder.Universename.setText(Universe.getName());
        //TODO : set real image
        viewHolder.Unimage.setBackgroundColor(1234);
        System.out.println("returned");
        return convertView;
    }

    private class UniverseViewHolder{
        public TextView Universename;
        public ImageView Unimage;


        public UniverseViewHolder(){}

    }
}