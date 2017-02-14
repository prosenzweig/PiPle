package com.piple.res;

import android.graphics.Point;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

public class Oval {
    private ShapeDrawable mDrawable;
    private int mray;

    public Oval(int ray, Point mpoint, int color) {

        int[] colors = {0xffffffff,color};
        float[] stops = {0.8f, 1f};
        mray=ray;
        mDrawable = new ShapeDrawable(new OvalShape());
        mDrawable.setBounds(mpoint.x-ray, mpoint.y-ray, mpoint.x+ray, mpoint.y+ray);
        mDrawable.getPaint().setColor(color);
        mDrawable.getPaint().setShader(new RadialGradient(ray,ray,ray, colors, stops, Shader.TileMode.MIRROR ));
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

}

