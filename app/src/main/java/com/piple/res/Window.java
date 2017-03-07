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
import java.util.HashMap;
import java.util.ListIterator;


public class Window extends PanZoomView {



    /*
    RESSOURCES
     */
    private Universe theuniverse = null;
    private Message currenttyped;
    private String currentmessage;

    //on commence en roadview
    private Roadview  roadview = new Roadview(true);

    private Context windowcontext;
    private ScaleGestureDetector mPressDetect;



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
        currenttyped.setGoval(new Oval(0,0 ,(float) 80, Color.BLACK));
        currenttyped.setMmessage(" ");


        mPressDetect = new ScaleGestureDetector(context, new longPressListener());



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






    /**
     *
     * DRAWINGS
     *
     *
     * Do whatever drawing is appropriate for this view.
     * The canvas object is already set up to be drawn on. That means that all translations and scaling
     * operations have already been done.
     *
     * @param canvas Canvas
     * @return void
     */


    @Override public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        drawOnCanvas (canvas);
        canvas.restore();

    }


    public void drawOnCanvas (Canvas canvas)
    {
        if(roadview.isRoadview()){
            if(theuniverse!=null) {
                //TODO: afficher le nom de l'univers en gras en haut a gauche avec si un appuis sur la zone alors on lance HomeActivity
                if (theuniverse.getMOIList() != null) {
                    ListIterator iterator = theuniverse.getMOIList().listIterator();
                    while (iterator.hasNext()) {
                        MOI moi = (MOI)iterator.next();
                        drawtheMOI(moi.getFather(), canvas);
                    }
                }

            }
        }

    }




    
   public void drawtheMOI(Message message, Canvas canvas){

       message.getRoval().draw(canvas);
       ListIterator iterator= message.getChildren().listIterator();
       while(iterator.hasNext()){
           drawtheMOI((Message)iterator.next(),canvas);
       }

   }


    public void todrawMoi(int num, MOI moi){


        //TODO do differents if its in GORview or RoadView
        Message message = moi.getFather();
        //TODO code this function
        message.setRoval(roadview.CreateOval(message,0,num*2000));
        //TODO code the instanciation of message and name and other Oval component
        moi.setFather(message);

        ArrayList children= moi.getFather().getChildren();

        todrawMessage(children, 0, num*2000);

    }

    public void todrawMessage(ArrayList<Message> children, float fatherposx, float fatherposy){

        if(children!=null) {
            //TODO code this fonction that returns only the 5 biggest messages and if there is more return a 6th special message that will make the draw message create a special bubble containing special info
            ArrayList childrentodraw = roadview.amongthebiggest(children);
            for (int i = 0; i < children.size(); i++) {
                Message message = children.get(i);
                message.setRoval(roadview.CreateOval(message, fatherposx, fatherposy));
                message.setRoval(roadview.checkforcollision(message.getRoval(), theuniverse));
                todrawMessage(message.getChildren(),message.getRoval().getX(),message.getRoval().getY());
            }
            }
        }










        public void OnZoomAction(){
        //TODO : si on zoom suffisament alors on arrive en gearofreply view sinon si on dézoom on repasse en roadview et si on continue MOIview
           /* if(mScaleFactor>0.5f && mScaleFactor<8.9f){
                myRoadview.set(fathermessage);
            }*/
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





/*public void drawMessages(Canvas canvas,Message root, double rootangle){
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

    }*/



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




