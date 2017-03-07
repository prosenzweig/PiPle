package com.piple.res;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Date;


public class Window extends PanZoomView {


    private Universe theuniverse;
    private Message currenttyped;
    private String currentmessage;
    private Context windowcontext;
    private ScaleGestureDetector mScaleDetect;
   private boolean creatinganoval=false;
    /*final GestureDetector gestureDetector = new GestureDetector(getContext()) {
        public void onLongPress(MotionEvent e) {
            System.out.print("\n looong click");
            ((InputMethodManager) windowcontext.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

            creatinganoval=true;
        }
    });*/


    public Window(Context context) {
        super(context);
        windowcontext=context;


        currenttyped = new Message();
        currenttyped.setGoval(new Oval(0,0 ,(float) 80, Color.BLACK, windowcontext));
        currenttyped.setMmessage(" ");
        mScaleDetect = new ScaleGestureDetector(context, new longPressListener());



    }

    public Universe getTheuniverse() {
        return theuniverse;
    }

    public void setTheuniverse(Universe theuniverse) {
        this.theuniverse = theuniverse;
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
        currenttyped.getGoval().OnDraw(canvas);



        canvas.restore();

    }

    public void drawOnCanvas (Canvas canvas)
    {

    }



    public void drawMessages(Canvas canvas,Message root, double rootangle){
    int nbchildren = root.getChildren().size();
    double angle = Math.PI/nbchildren;

    root.getGoval().OnDraw(canvas);


    for(int i=0; i<root.getChildren().size(); i++ ) {
        Message msg = root.getChildren().get(i);
        int mray = (int) Math.abs(50 * (msg.getChildren().size() * 0.25 + 1));
        //int mray =(int)Math.abs(root.getray()*(0.5+0.04*msg.getChildren().size()));
        Point mpt = beChildof(root.getGoval(), mray, angle * i - Math.PI / 2 + rootangle + angle / 2, Math.abs(15 + msg.getChildren().size() * mray * 0.25));
        msg.setGoval(new Oval(mray, mpt.x, mpt.y, 0xffffff00, getContext()));
        drawMessages(canvas, msg, angle * i - Math.PI / 2 + rootangle + angle / 2);
    }

    }


    public Point beChildof(Oval father, int mRay, double angle, double margin ){
        Point mpoint = new Point();
        mpoint.x=(int)(father.getX()+ Math.sin(angle)*(margin+father.getRay()+mRay));
        mpoint.y=(int)(father.getY() + Math.cos(angle)*(margin+father.getRay()+mRay));

        return mpoint;

    }



    /*mAutoCenterAnimator = ObjectAnimator.ofInt(PieChart.this, "PieRotation", 0);
mAutoCenterAnimator.setIntValues(targetAngle);
mAutoCenterAnimator.setDuration(AUTOCENTER_ANIM_DURATION);
mAutoCenterAnimator.start();*/



   /* @Override public boolean onTouchEvent(MotionEvent ev)
    {
        boolean ret = super.onTouchEvent(ev);
      //  gestureDetector.onTouchEvent(ev);

        currenttyped.setGoval(new Oval(ev.getX(),ev.getY(),100,Color.BLUE,getContext()));
        invalidate();
            return ret;

    }*/









    /* ONButton pressed I don't care which one :

    ((InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(this.getWindowToken(), 0);

                to delete keybord
     */










    //TODO : avoir la fonction de passage entre les deux views
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


    public void OnZoomAction(){
        //TODO : si on zoom suffisament alors on arrive en gearofreply view sinon si on dézoom on repasse en roadview et si on continue MOIview
           /* if(mScaleFactor>0.5f && mScaleFactor<8.9f){
                myRoadview.set(fathermessage);
            }*/
    }
    /**
     * Return true if pinch zooming is supported.
     *
     * @return boolean
     */
    public boolean supportsZoom () {
        return true;
    }



    /*


    ROADVIEW

     */
    public boolean set(Message father){
        CreateOvalandsetRay(father);
        //TODO iteration pour checker la distance normale que devrait avoir chaques enfant par rapport à son père si on ne l'a pas déjà
       /* ListIterator iterator = children.listIterator();
        while(iterator.hasNext()){

            iterator.getChildren().get

        }*/

        //TODO fonction pour calculer le poid si on ne l'a pas déjà

        //TODO Iteration pour checker si il y a superposition,
        //TODO qui change cela en repoussant les l


        return true;
    }
    public void CreateOvalandsetRay(Message message)
    {
        //TODO: iteration pour calculer le rayon de toutes les bubbles et les instancier en les donnant à leur message et le faire que pour les 4 plus grosses
    }


        //((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);




    //TODO create IHM



    protected class longPressListener extends ScaleGestureDetector.SimpleOnScaleGestureListener
    {

        public void onLongPress(MotionEvent event) {

        Log.d("press","it happened");

        }




    }
}









