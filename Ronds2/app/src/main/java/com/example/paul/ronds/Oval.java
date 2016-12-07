package com.example.paul.ronds;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.MotionEvent;
import android.view.View;

import static android.graphics.Color.parseColor;
import static android.graphics.Paint.Style.STROKE;
import static android.view.MotionEvent.ACTION_UP;

public class Oval {
    private ShapeDrawable mDrawable;
    private int mray;

    public Oval(int ray, int centerx, int centery) {

        int[] colors = {0xffffffff,0xbb9cda5e };
        float[] stops = {0.9f, 1f};
        mray=ray;
        mDrawable = new ShapeDrawable(new OvalShape());
        mDrawable.setBounds(centerx-ray, centery-ray, centerx+ray, centery+ray);
        mDrawable.getPaint().setShader(new RadialGradient((float)centerx-2*ray/3, (float)centery-5*ray/3,ray, colors, stops,Shader.TileMode.MIRROR ));
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

