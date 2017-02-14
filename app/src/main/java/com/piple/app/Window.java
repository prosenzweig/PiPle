package com.piple.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;

import java.lang.Math;
import java.util.ArrayList;

import static java.util.Arrays.copyOfRange;

/**
 * Created by Paul on 01/12/2016.
 */

public class Window extends PanZoomView {

    private static int margin =15;
    public Window(Context context) {
        super(context);

    }

    public Window (Context context, AttributeSet attrs) {
        super (context, attrs);
    }

    public Window (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.restore();
    }


    public void drawOnCanvas (Canvas canvas) {

        Point ptPapa = new Point(200,200);
        Oval Papa = new Oval(150, ptPapa, 0xffff0000);
        Oval enfant1 = new Oval(50, beChildof(ptPapa, 150, 50, Math.PI/4), 0xff00ff00);
        Oval enfant2 = new Oval(60, beChildof(ptPapa, 150, 60, 0), 0xff0000ff);
        Oval enfant3 = new Oval(100, beChildof(ptPapa, 150, 100, Math.PI/2), 0x99ff00ff);
        Papa.getmDrawable().draw(canvas);
        drawtext(canvas, "salut paul c'est ton cousin qui t'ecrit e dublin.Je passe de tres tres bonne vacances ici.Le soleil brile la mer est radieuse et ton cul pue !", ptPapa, 150);
        enfant1.getmDrawable().draw(canvas);
        enfant2.getmDrawable().draw(canvas);
        drawtext(canvas, "texte cours",beChildof(ptPapa, 150, 60, 0) , 60);
        enfant3.getmDrawable().draw(canvas);


    }



    public Point beChildof(Point father,int fatherRay, int mRay, double angle ){
        Point mpoint = new Point();
        mpoint.x=(int)(father.x + Math.sin(angle)*(margin+fatherRay+mRay));
        mpoint.y=(int)(father.y + Math.cos(angle)*(margin+fatherRay+mRay));
        return mpoint;
    }
    public void drawtext(Canvas canvas, String text, Point pt, int ray){
        int i;
        Paint paint = new Paint();
        int size=50;
        ArrayList<String> textlist = new ArrayList();
        paint.setColor(Color.BLACK);
        paint.setTextSize(size);
        Rect bounds = new Rect();
        Rect boundsmax = new Rect();


        int nblignes=text.length()/20;
        if(nblignes==0){
            nblignes=1;
        }
        String[] strs = text.split(" ");
        int[] len = new int[strs.length];


        for(i=0;i<strs.length;i++){
            len[i]=strs[i].length();
        }
        int lenligne = text.length()/nblignes;
        int strcount=0;
        String accu, accu1;
        for(i=0;i<nblignes;i++){
            accu="";
            if(strcount<strs.length) {
                do {
                    accu = accu + " " + strs[strcount];
                    strcount++;
                    //accu1=accu + " " + strs[strcount];
                } while ((accu.length() < lenligne)/*&&(accu1.length()<lenligne)*/ && (strcount < strs.length));
            }
            textlist.add(accu);
        }
        do{
            size=size-5;
            paint.setTextSize(size);
            boundsmax=new Rect();
            for(i=0;i<nblignes;i++){
                paint.getTextBounds(textlist.get(i), 0, textlist.get(i).length(), bounds);
                if((boundsmax.right-boundsmax.left)<(bounds.right-bounds.left)){
                    boundsmax=bounds;
                    System.out.println("couououou");
                }
            }
        }while((boundsmax.right-boundsmax.left)>ray);

        for(i=0;i<nblignes;i++){
            paint.getTextBounds(textlist.get(i), 0, textlist.get(i).length(), bounds);
            canvas.drawText(textlist.get(i), pt.x - (bounds.right -bounds.left)/2,pt.y-(nblignes/2-i)*size , paint);
        }

        /*
        if(text.length()<15){
            paint.getTextBounds(text, 0, text.length(), bounds);
            while(bounds.right-bounds.left<1.6*ray){
                size++;
                paint.setTextSize(size);
                paint.getTextBounds(text, 0, text.length(), bounds);
            }
            canvas.drawText(text, pt.x - (bounds.right -bounds.left)/2,pt.y+10/2 , paint);
        }
        else if(text.length()<25){
            String[] strs = text.split(" ");
            int[] len ;
            for(i=0;i<strs.length;i++){
                len[i]=strs[i].length();
            }
            String accu= strs[0];
            for(i=1; i<strs.length/2;i++){
                accu = accu.concat(" ");
                accu = accu.concat(strs[i]);
            }
            textlist.add(accu);
            accu = strs[strs.length/2];
            for(i=strs.length/2+1; i<strs.length;i++){
                accu = accu.concat(" ");
                accu = accu.concat(strs[i]);
            }
            textlist.add(accu);
            paint.getTextBounds(accu, 0, textlist.get(0).length(), bounds);
            while(bounds.right-bounds.left<1.6*ray){
                size++;
                paint.setTextSize(size);
                paint.getTextBounds(textlist.get(0), 0, textlist.get(0).length(), bounds);
            }
            canvas.drawText(textlist.get(0), pt.x - (bounds.right -bounds.left)/2,pt.y-10 , paint);

            canvas.drawText(textlist.get(1), pt.x - (bounds.right -bounds.left)/2,pt.y+10 , paint);
        }*/


    }

    /**
     * Return the resource id of the sample image. Note that this class always returns 0, indicating
     * that there is no sample drawable.
     *
     * @return int
     */

    public int sampleDrawableId () {
        return 0;
    }

    /**
     * Return true if panning is supported.
     *
     * @return boolean
     */

    public boolean supportsPan () {
        return true;
    }

    /**
     * Return true if scaling is done around the focus point of the pinch.
     *
     * @return boolean
     */

    public boolean supportsScaleAtFocusPoint () {
        return true;
    }

    /**
     * Return true if pinch zooming is supported.
     *
     * @return boolean
     */

    public boolean supportsZoom () {
        return true;
    }


}

