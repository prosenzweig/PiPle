package com.piple.res;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

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

import static android.view.GestureDetector.*;


public class Window extends PanZoomView implements GestureDetector.OnGestureListener{



    private Universe theuniverse=null;
    private Message currenttyped;
    private Message root;
    private String currentmessage;
    private Context windowcontext;
    private GestureDetectorCompat mDetector;
    //on commence en roadview
    private Roadview  roadview = new Roadview(true);


    public Window(Context context) {
        super(context);

        root = new Message();
        root.setGoval(new Oval(500,100 ,(float) 80, Color.BLACK, getContext()));
        root.setMmessage(" ");
        Message child1 = new Message();
        root.getChildren().add(child1);


        //used for checking the total size needed for all the bubble to be reachable but not being able to go for miles
        //without any stops
        totalscreensize.put("up",0);
        totalscreensize.put("down",0);
        totalscreensize.put("right",0);
        totalscreensize.put("left",0);


        mDetector=new GestureDetectorCompat(getContext(),this);
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

    @Override
    public void drawOnCanvas(Canvas canvas) {
        super.drawOnCanvas(canvas);
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

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.restore();
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





/*
    Function to check if the point is inside the message's oval.

     */

    //TODO: change with oval because two kinds of oval function

    public Message clickedOn(Point pt, Message root){

        Message retour = null;
        Message answer = null;
        for(int i = 0; i<root.getChildren().size();i++){
            answer = clickedOn(pt,root.getChildren().get(i));
            if(answer!=null){
                return  answer;
            }
        }
        if(Math.pow(Math.pow(pt.x-root.getGoval().getX(),2)+Math.pow(pt.y-root.getGoval().getY(),2),0.5)<root.getGoval().getRay()){
            return root;
        }
        else{
            return null;
        }
    }


    //FONCTION RECURSIVE D'AFFICHAGE DE L'ARBRE A PARTIR DE LA RACINE (commencer avec un angle de 0)
    public void drawMessages(Canvas canvas,Message root, double rootangle){

        int nbchildren = root.getChildren().size();
        double angle = Math.PI/nbchildren;

        root.getGoval().draw(canvas);


        for(int i=0; i<nbchildren; i++ ) {
            Message msg = root.getChildren().get(i);
            double mangle =  angle * i - Math.PI / 2 + rootangle + angle / 2;

            // DECISION DU RAYON
            //int mray = (int) Math.abs(50 * (msg.getChildren().size() * 0.25 + 1));
            int mray =(int)Math.abs(root.getGoval().getRay()*(0.5+0.04*msg.getChildren().size()));

            //DECISION DE LA MARGE
            double margin = 15 + msg.getChildren().size() * mray * 0.25;


            Point mpt = beChildof(root.getGoval(), mray,mangle, margin );
            msg.setGoval(new Oval(mray, mpt.x, mpt.y, 0xffffff00, getContext()));
            drawMessages(canvas, msg, mangle);
        }

        }


        //Renvoie le point du centre de la bulle pour que celle-ci soit placé correctement (en fonction du père, du rayon, de l'angle et de la marge
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
    @Override
    public void onLongPress(MotionEvent e) {

        if(root!=null) {
            Message clicked = clickedOn(new Point((int) e.getX(), (int) e.getY()), root);
            if(clicked!=null){
                clicked.setMmessage(clicked.getMmessage()+"u");
            }
        }
        else{
            //clavier -> création du premier message
        }
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }






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

    /**
     * Notified when a tap occurs with the down {@link MotionEvent}
     * that triggered it. This will be triggered immediately for
     * every down event. All other events should be preceded by this.
     *
     * @param e The down motion event.
     */
    @Override
    public boolean onDown(MotionEvent e) {

        Log.d("blabla","COUCOUCOUC");
        return false;
    }

    /**
     * The user has performed a down {@link MotionEvent} and not performed
     * a move or up yet. This event is commonly used to provide visual
     * feedback to the user to let them know that their action has been
     * recognized i.e. highlight an element.
     *
     * @param e The down motion event
     */
    @Override
    public void onShowPress(MotionEvent e) {

    }

    /**
     * Notified when a tap occurs with the up {@link MotionEvent}
     * that triggered it.
     *
     * @param e The up motion event that completed the first tap
     * @return true if the event is consumed, else false
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    /**
     * Notified when a scroll occurs with the initial on down {@link MotionEvent} and the
     * current move {@link MotionEvent}. The distance in x and y is also supplied for
     * convenience.
     *
     * @param e1        The first down motion event that started the scrolling.
     * @param e2        The move motion event that triggered the current onScroll.
     * @param distanceX The distance along the X axis that has been scrolled since the last
     *                  call to onScroll. This is NOT the distance between {@code e1}
     *                  and {@code e2}.
     * @param distanceY The distance along the Y axis that has been scrolled since the last
     *                  call to onScroll. This is NOT the distance between {@code e1}
     *                  and {@code e2}.
     * @return true if the event is consumed, else false
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }



    /**
     * Notified of a fling event when it occurs with the initial on down {@link MotionEvent}
     * and the matching up {@link MotionEvent}. The calculated velocity is supplied along
     * the x and y axis in pixels per second.
     *
     * @param e1        The first down motion event that started the fling.
     * @param e2        The move motion event that triggered the current onFling.
     * @param velocityX The velocity of this fling measured in pixels per second
     *                  along the x axis.
     * @param velocityY The velocity of this fling measured in pixels per second
     *                  along the y axis.
     * @return true if the event is consumed, else false
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }


    //TODO create IHM
}









