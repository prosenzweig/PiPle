package com.piple.res;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;

import java.lang.Math;
import java.util.ArrayList;

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
        drawtext(canvas, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus venenatis leo eu mi ultricies maximus. In porttitor pharetra ultricies. Donec vulputate risus vel leo convallis, eu ultricies justo lobortis. Suspendisse rutrum ligula libero, sit amet vulputate mauris consequat vel. Sed id posuere est. In lobortis, ligula sed commodo rutrum, nisi est interdum velit, vel porta quam lorem id felis. Aliquam hendrerit rhoncus magna, non sodales velit feugiat at. Nunc aliquet laoreet arcu, eu varius purus pretium ut. Donec purus massa, feugiat eu leo et, lobortis maximus ex. Integer eros ante, dignissim ut consectetur eu, feugiat vel diam. Nunc eu velit eros. Nam ultrices eget risus ac ultricies. Interdum et malesuada fames ac ante ipsum primis in faucibus.", Papa);
        enfant1.getmDrawable().draw(canvas);
        enfant2.getmDrawable().draw(canvas);
        drawtext(canvas, "texte cours",enfant2);
        enfant3.getmDrawable().draw(canvas);


    }



    public Point beChildof(Point father,int fatherRay, int mRay, double angle ){
        Point mpoint = new Point();
        mpoint.x=(int)(father.x + Math.sin(angle)*(margin+fatherRay+mRay));
        mpoint.y=(int)(father.y + Math.cos(angle)*(margin+fatherRay+mRay));
        return mpoint;
    }
    public void drawtext(Canvas canvas, String text, Oval oval){
        int i;
        Paint paint = new Paint();
        int size=50;
        ArrayList<String> textlist = new ArrayList();
        paint.setColor(Color.BLACK);
        paint.setTextSize(size);
        Rect bounds = new Rect();
        boolean depasse;


        if(text.length()>144){
            text = text.substring(0,144);
            text=text+"...";
        }

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
        String accu;
        for(i=0;i<nblignes;i++){
            accu="";
            if(strcount<strs.length) {
                do {
                    accu = accu + " " + strs[strcount];
                    strcount++;
                } while ((accu.length() < lenligne) && (strcount < strs.length));
            }
            textlist.add(accu);
        }

        do{
            size=size--;
            paint.setTextSize(size);
            depasse=false;
            for(i=0;i<nblignes;i++){
                paint.getTextBounds(textlist.get(i), 0, textlist.get(i).length(), bounds);
                if(bounds.width()>oval.ray*1.8){
                    depasse=true;
                }
            }
        }while(depasse);

        for(i=0;i<nblignes;i++){
            paint.getTextBounds(textlist.get(i), 0, textlist.get(i).length(), bounds);
            canvas.drawText(textlist.get(i), oval.pt.x - (bounds.width())/2,oval.pt.y-(nblignes/2-i)*size , paint);
        }
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

