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

        this.mray=mray;
        int[] colors = {0xffffffff, color};
        float[] stops = {0.8f, 1f};
        mDrawable.setBounds(mpoint.x-mray, mpoint.y-mray, mpoint.x+mray, mpoint.y+mray);
        mDrawable.getPaint().setColor(color);
        mDrawable.getPaint().setShader(new RadialGradient(mray,mray,mray, colors, stops, Shader.TileMode.MIRROR ));


    }

}

