package com.piple.res;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import java.util.ArrayList;

public class Oval extends View{

    private ShapeDrawable mDrawable;
    private boolean message, msgholder, faceholder; // la bbubble peut être plusieurs choses en effet;
    private int importance; // remplace size ici
    //FOR THE GearofReply VIEW (paul's)
    private String text;


    private float x,y,ray;
    private Contact contact;
    //FOR THE LinkVIEW (jerem's )
    
    private int rray;
    private int rdistance;
    private Button mbut;

    public Oval(float x,float y, float ray, int color, Context cont) {
        super(cont);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        this.x=x;
        this.y=y;
        this.ray=ray;

        int[] colors = {0xffffffff, color};
        float[] stops = {0.8f, 1f};
        mDrawable = new ShapeDrawable(new OvalShape());
        mDrawable.setBounds((int)(x-ray),(int)(y-ray),(int)(x+ray),(int)(y+ray));
        mDrawable.getPaint().setColor(color);
        mDrawable.getPaint().setShader(new RadialGradient(ray,ray,ray, colors, stops, Shader.TileMode.MIRROR ));
    }
    public Oval(Context cont) {
    super(cont);
    }

    /*
    EXEMPLE AUTRE








     */

    public void OnDraw(Canvas canvas){


        mDrawable.draw(canvas);
        Log.d("dddo","ddd");


        if(text!=null) {
            int i;
            Paint paint = new Paint();
            int size = 50;
            ArrayList<String> textlist = new ArrayList();
            paint.setColor(Color.BLACK);
            paint.setTextSize(size);
            Rect bounds = new Rect();
            boolean depasse;


            if (text.length() > 144) {
                text = text.substring(0, 144);
                text = text + "...";
            }

            int nblignes = text.length() / 20;
            if (nblignes == 0) {
                nblignes = 1;
            }
            String[] strs = text.split(" ");
            int[] len = new int[strs.length];


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
                size--;
                paint.setTextSize(size);
                depasse = false;
                for (i = 0; i < nblignes; i++) {
                    paint.getTextBounds(textlist.get(i), 0, textlist.get(i).length(), bounds);
                    if (bounds.width() > ray * 1.6) {
                        depasse = true;
                    }
                }
            } while (depasse);

            for (i = 0; i < nblignes; i++) {
                paint.getTextBounds(textlist.get(i), 0, textlist.get(i).length(), bounds);
                canvas.drawText(textlist.get(i), x - (bounds.width()) / 2, y - (nblignes / 2 - i) * size, paint);
            }
        }

    }




    public void setcolor(int color){
        this.mDrawable.getPaint().setColor(color);
    }

    public ShapeDrawable getmDrawable(){
        return mDrawable;
    }



    public void setDrawable(ShapeDrawable mDrawable) {
        this.mDrawable = mDrawable;
    }



    public boolean isMessage() {
        return message;
    }

    public void setMessage(boolean message) {
        this.message = message;
    }

    public boolean isMsgholder() {
        return msgholder;
    }

    public void setMsgholder(boolean msgholder) {
        this.msgholder = msgholder;
    }

    public boolean isFaceholder() {
        return faceholder;
    }

    public void setFaceholder(boolean faceholder) {
        this.faceholder = faceholder;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }



    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public int getRray() {
        return rray;
    }

    public void setRray(int rray) {
        this.rray = rray;
    }

    public int getRdistance() {
        return rdistance;
    }

    public void setRdistance(int rdistance) {
        this.rdistance = rdistance;
    }

    public void setmDrawable(ShapeDrawable mDrawable) {
        this.mDrawable = mDrawable;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    @Override
    public float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }

    public Float getRay() {
        return ray;
    }

    public void setRay(Float ray) {
        this.ray = ray;
    }

    public Button getMbut() {
        return mbut;
    }

    public void setMbut(Button mbut) {
        this.mbut = mbut;
    }

}