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
 * Class Window
 *      extends PanZoomView
 *
 * Window in which a universe will be contained.
 */

public class Window
        extends
            PanZoomView
{



    /// RESOURCES ///

    private static int MARGIN =15;



    /// CONSTRUCTORS ///

    public Window(Context context)
    {
        super(context);
    }

    public Window (Context context, AttributeSet attrs)
    {
        super (context, attrs);
    }

    public Window (Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }



    /// METHODS ///

    /**
     * Method onDraw
     *      overrides method from View
     *
     * Draws the window on the screen.
     *
     * @param canvas screen of the phone
     */
    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.save();
        canvas.restore();
    }


    /**
     * Method drawOnCanvas
     *
     * Draws elements on the window.
     *
     * @param canvas screen of the phone
     */
    public void drawOnCanvas(Canvas canvas)
    {
        /*Point ptPapa = new Point(100,100);
        Oval Papa = new Oval(150, ptPapa, 0xffff0000);
        Oval enfant1 = new Oval(50, beChildof(ptPapa, 150, 50, Math.PI/4), 0xff00ff00);
        Oval enfant2 = new Oval(60, beChildof(ptPapa, 150, 60, 0), 0xff0000ff);
        Oval enfant3 = new Oval(100, beChildof(ptPapa, 150, 100, Math.PI/2), 0x99ff00ff);
        Papa.getmDrawable().draw(canvas);
        drawText(canvas, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus venenatis leo eu mi ultricies maximus. In porttitor pharetra ultricies. Donec vulputate risus vel leo convallis, eu ultricies justo lobortis. Suspendisse rutrum ligula libero, sit amet vulputate mauris consequat vel. Sed id posuere est. In lobortis, ligula sed commodo rutrum, nisi est interdum velit, vel porta quam lorem id felis. Aliquam hendrerit rhoncus magna, non sodales velit feugiat at. Nunc aliquet laoreet arcu, eu varius purus pretium ut. Donec purus massa, feugiat eu leo et, lobortis maximus ex. Integer eros ante, dignissim ut consectetur eu, feugiat vel diam. Nunc eu velit eros. Nam ultrices eget risus ac ultricies. Interdum et malesuada fames ac ante ipsum primis in faucibus.", ptPapa);
        enfant1.getmDrawable().draw(canvas);
        enfant2.getmDrawable().draw(canvas);
        drawText(canvas, "texte cours",beChildof(ptPapa, 150, 60, 0) , 60);
        enfant3.getmDrawable().draw(canvas);*/
    }



    /**
     * Method beChildOf
     *
     * TODO: What does it do ?
     *
     * @param father father of the child
     * @param fatherRay radius of the father
     * @param mRay radius of the child
     * @param angle angle from the father at which it will be displayed
     * @return center of the child's coordinates
     */
    public Point beChildof(Point father, int fatherRay, int mRay, double angle)
    {
        Point mpoint = new Point();
        mpoint.x=(int)(father.x + Math.sin(angle)*(MARGIN +fatherRay+mRay));
        mpoint.y=(int)(father.y + Math.cos(angle)*(MARGIN +fatherRay+mRay));
        return mpoint;
    }



    /**
     * Method drawText
     *
     * Displays a bubble's message inside the bubble, adapting to the shape of the bubble.
     *
     * @param canvas screen of the phone
     * @param text message to be displayed
     * @param oval bubble that will contain the message
     */
    public void drawText(Canvas canvas, String text, Oval oval)
    {
        Paint paint = new Paint();
        int size = 50;
        int lenLigne = 20;
        int nbLines;
        ArrayList<String> textList = new ArrayList<>();
        Rect bounds = new Rect();
        boolean overflows;

        paint.setColor(Color.BLACK);
        paint.setTextSize(size);

        if (text.length() > 144) {
            text = text.substring(0, 144);
            text += "...";
        }

        nbLines = text.length() / lenLigne;

        if (nbLines == 0)
            nbLines = 1;

        String[] strs = text.split(" ");
        int[] len = new int[strs.length];

        for (int i=0 ; i<strs.length ; i++) {
            len[i] = strs[i].length();
        }

        int strcount = 0;
        String accu;

        for (int i=0 ; i<nbLines ; i++) {
            accu="";

            if (strcount < strs.length) {
                do {
                    accu = accu + " " + strs[strcount];
                    strcount++;
                } while ((accu.length() < lenLigne) && (strcount < strs.length));
            }

            textList.add(accu);
        }

        do {
            size--;
            paint.setTextSize(size);
            overflows=false;

            for(int i=0;i<nbLines;i++) {
                paint.getTextBounds(textList.get(i), 0, textList.get(i).length(), bounds);
                if(bounds.width()>oval.getmRay()*1.8){
                    overflows=true;
                }
            }

        }while (overflows);

        for (int i=0 ; i<nbLines ; i++) {
            paint.getTextBounds(textList.get(i), 0, textList.get(i).length(), bounds);
            canvas.drawText(textList.get(i), oval.getmPt().x - (bounds.width())/2, oval.getmPt().y-(nbLines/2-i)*size, paint);
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

