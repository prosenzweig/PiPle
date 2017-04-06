package com.piple.res;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import java.util.ArrayList;

/**
 * Class Oval : represents the shape of the circle for one Bubble (oval is contained in Message)
 *      extends View : to be able to draw on a canvas
 */
public class Oval extends View{

    /**
     * the circle shape
     */
    private ShapeDrawable mDrawable;

    /**
     * the circle's coordinates and ray
     */
    private float x,y,ray;

    public Oval(float x,float y, float ray, int color, Context cont) {
        super(cont);
        this.x=x;
        this.y=y;
        this.ray=ray;

        int[] colors = {0xffffffff,color&0xddffffff,color};
        float[] stops = {0.9f, 0.95f,0.945f};
        mDrawable = new ShapeDrawable(new OvalShape());
        mDrawable.setBounds((int)(x-ray),(int)(y-ray),(int)(x+ray),(int)(y+ray));
        mDrawable.getPaint().setColor(color);
        mDrawable.getPaint().setShader(new RadialGradient(ray,ray,ray, colors, stops, Shader.TileMode.MIRROR ));

    }
    public Oval(Context cont) {
    super(cont);
    }

    /**
     * Draws the message ( it's mdrawable circle and then it's text)
     * to display the text, this method seprates it in several lines, and modulates the size to make it fit in the circle
     * @param canvas the canvas to draw on
     * @param msg the message to draw
     */
    public void draw(Canvas canvas, Message msg){


        super.draw(canvas);
        mDrawable.draw(canvas);


        if(msg.getMmessage()!=null) {
            int i;
            Paint paint = new Paint();
            int size = 5;
            ArrayList<String> textlist = new ArrayList();
            paint.setColor(Color.BLACK);
            paint.setTextSize(size);
            Rect bounds = new Rect();
            boolean depasse;


            String text=msg.getMmessage();
            if (text.length() > 144) {
                text = text.substring(0, 144);
                text = text + "...";
            }
            String[] strs = text.split(" ");
            int[] len = new int[strs.length];
            int nblignes = strs.length/2;
            if(nblignes==0){
                nblignes=1;
            }
            if(nblignes>7){
                nblignes=7;
            }


            for (i = 0; i < strs.length; i++) {
                len[i] = strs[i].length();
            }
            int lenligne = text.length() / nblignes;
            int strcount = 0;
            String accu;
            for (i = 0; i < nblignes; i++) {
                accu = "";
                if (strcount < strs.length) {
                    do {
                        accu = accu + " " + strs[strcount];
                        strcount++;
                    } while ((accu.length() < lenligne) && (strcount < strs.length));
                }
                textlist.add(accu);
            }



            do {
                size++;
                paint.setTextSize(size);
                depasse = false;
                for (i = 0; i < nblignes; i++) {
                    paint.getTextBounds(textlist.get(i), 0, textlist.get(i).length(), bounds);
                    if (bounds.width()/1.7 > (ray*Math.cos(Math.asin((nblignes / 2 - i - 0.5) * size/ray)))) {
                        depasse = true;
                    }
                }
            } while (!depasse);

            size-=5;

            for (i = 0; i < nblignes; i++) {
                paint.getTextBounds(textlist.get(i), 0, textlist.get(i).length(), bounds);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(textlist.get(i), x, (float)(y - (nblignes / 2 - i - 0.5) * size), paint);
            }
        }


    }

    public void setDrawable(ShapeDrawable mDrawable) {
        this.mDrawable = mDrawable;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    public Float getRay() {
        return ray;
    }

    public void setRay(Float ray) {
        this.ray = ray;
    }


}
