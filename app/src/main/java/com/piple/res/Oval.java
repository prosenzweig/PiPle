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
    private int ray;
    private Point pt;
    Message msg;





    public Oval(int ray, Point pt, int color, Message msg ) {

        this.pt=pt;
        this.ray=ray;
        this.msg=msg;
        int[] colors = {0xffffffff, color};
        float[] stops = {0.8f, 1f};
        mDrawable = new ShapeDrawable(new OvalShape());
        mDrawable.setBounds(pt.x-ray, pt.y-ray, pt.x+ray, pt.y+ray);
        mDrawable.getPaint().setColor(color);
        mDrawable.getPaint().setShader(new RadialGradient(ray,ray,ray, colors, stops, Shader.TileMode.MIRROR ));
    }

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
        mDrawable.setBounds((int)x-ray, (int)y-ray, (int)x+ray, (int)y+ray);
    }

    public void setDrawable(ShapeDrawable mDrawable) {
        this.mDrawable = mDrawable;
    }

    public int getray() {
        return ray;
    }

    public void setray(int mray) {
        this.ray = mray;
    }
    public Point getpt() {
        return pt;
    }

    public void setpt(Point mpt) {
        this.pt = mpt;
    }

}