package com.piple.res;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;

import static android.view.GestureDetector.*;


public class Window extends PanZoomView implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener{


    private FirebaseDatabase database;
    private Universe theuniverse;
    private Message currenttyped;
    private String currentmessage;
    private GestureDetectorCompat mDetector;
    private DatabaseReference mRef;
    private final int Centerx;
    private final int Centery;
    private Message target;


    public Window(Context context) {
        super(context);
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);

        //used for checking the total size needed for all the bubble to be reachable but not being able to go for miles
        //without any stops
        totalscreensize.put("up",0);
        totalscreensize.put("down",0);
        totalscreensize.put("right",0);
        totalscreensize.put("left",0);
        currentmessage="";

        database = FirebaseDatabase.getInstance();

        mDetector=new GestureDetectorCompat(getContext(),this);


        WindowManager wm =(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display dis = wm.getDefaultDisplay();
        Point pt = new Point();
        dis.getSize(pt);
        Centerx=pt.x/2;
        Centery=pt.y/3;


    }

    public Universe getTheuniverse() {
        return theuniverse;
    }

    public void setTheuniverse(Universe theuniverse) {
        this.theuniverse = theuniverse;
    }

    public Window (Context context, AttributeSet attrs) {
        super (context, attrs);
        WindowManager wm =(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display dis = wm.getDefaultDisplay();
        Point pt = new Point();
        dis.getSize(pt);
        Centerx=pt.x/2;
        Centery=pt.y/3;
    }

    public Window (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        WindowManager wm =(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display dis = wm.getDefaultDisplay();
        Point pt = new Point();
        dis.getSize(pt);
        Centerx=pt.x/2;
        Centery=pt.y/3;    }


    public void setmRef(DatabaseReference mRef) {
        this.mRef = mRef;
    }

    //C'EST ICI QU'ON DESSINE
    @Override
    public void drawOnCanvas(Canvas canvas) {
        super.drawOnCanvas(canvas);
        for(int i=0; i<theuniverse.getMOIList().size();i++){
            if(theuniverse.getMOIList().get(i).getClass()==MOI.class){
            MOI mmoi = theuniverse.getMOIList().get(i);
            mmoi.getFather().setGoval(new Oval(Centerx+i*750,100,100,theuniverse.getColormap().get(mmoi.getFather().getIduser()), getContext()));
            drawMessages(canvas,mmoi.getFather(),0);
        }}
        if(target!=null){
            moveto(target);
        }
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.restore();
    }

/*
    Function to check if the point is inside the message's oval.

     */

    //TODO: change with oval because two kinds of oval function

    public Message clickedOn(Point pt, Message root){
        Message answer;
        for(int i = 0; i<root.getChildren().size();i++){
            answer = clickedOn(pt,root.getChildren().get(i));
            if(answer!=null){
                return  answer;
            }
        }
        if(Math.pow(Math.pow(pt.x/mScaleFactor-(root.getGoval().getX()+mPosX/mScaleFactor),2)+Math.pow(pt.y/mScaleFactor-(root.getGoval().getY()+mPosY/mScaleFactor),2),0.5)<root.getGoval().getRay()){
            return root;
        }
        else{
            return null;
        }
    }


    //FONCTION RECURSIVE D'AFFICHAGE DE L'ARBRE A PARTIR DE LA RACINE (commencer avec un angle de 0)
    public void drawMessages(Canvas canvas,Message root, double rootangle){

        int nbchildren = root.getChildren().size();

        //angle de séparation entre les message
        double angle = Math.PI/nbchildren;

        //Dessin de l'oval ( root )
        root.getGoval().draw(canvas ,root);


        //Dessin du pseudo
        String pseudo="";
        for(int i=0; i<theuniverse.getuniverseUserList().size();i++){
            if(theuniverse.getuniverseUserList().get(i).getId().equals(root.getIduser())){
                pseudo=theuniverse.getuniverseUserList().get(i).getPseudo();
            }
        }
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        float size =root.getGoval().getRay()/5;
        paint.setTextSize(size);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(pseudo,root.getGoval().getX(),root.getGoval().getY()-root.getGoval().getRay()-size/2,paint);




        //On parcours tous les enfants de root
        for(int i=0; i<nbchildren; i++ ) {
            Message msg = root.getChildren().get(i);
            double mangle =  angle * i - Math.PI / 2 + rootangle + angle / 2;

            float mray;
            double margin;

            //Si on se trouve dans la vue raprochée
            if(mScaleFactor>2) {

                // DECISION DU RAYON
                mray = (float)(root.getGoval().getRay() * (0.7 + 0.06 * (2*msg.getChildren().size()-nbchildren)));

                //DECISION DE LA MARGE
                margin =  mray*(0.2+msg.getChildren().size() *0.1);

            }//Si on se trouve dans la vue éloignée
            else{

                mray = (float)(50 * (msg.getChildren().size() * 0.25 + 1));
                margin = 10 + msg.getChildren().size() * mray * 0.15 + nbchildren*root.getGoval().getRay()*0.2;
            }


            //dessin du trait
            if(margin>50) {
                Point beginline = beChildof(root.getGoval(), 0, mangle, 20);
                Point endline = beChildof(root.getGoval(), mray, mangle, margin - mray - 40);
                canvas.drawLine(beginline.x, beginline.y, endline.x, endline.y, new Paint());
            }



            //Création de l'oval enfant
            Point mpt = beChildof(root.getGoval(), mray,mangle, margin );
            msg.setGoval(new Oval(mpt.x,mpt.y, mray, theuniverse.getColormap().get(msg.getIduser()), getContext()));

            //Appel récurcif avec l'enfant créé
            drawMessages(canvas, msg, mangle);
        }

    }



        //Renvoie le point du centre de la bulle pour que celle-ci soit placé correctement (en fonction du père, du rayon, de l'angle et de la marge
        public Point beChildof(Oval father, float mRay, double angle, double margin ){
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

        Message clicked;
        Boolean found = false;
        ListIterator list = theuniverse.getMOIList().listIterator();
        MOI moi = new MOI();
        while((list.hasNext())&&(!found)) {
           moi = (MOI)list.next();

            clicked=clickedOn(new Point((int) e.getX(), (int) e.getY()), moi.getFather());

            //si le click est sur une bulle
            if(clicked!=null){
                found = true;
                currenttyped = new Message();
                clicked.getChildren().add(currenttyped);
                InputMethodManager im = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                im.showSoftInput(this, InputMethodManager.SHOW_FORCED);
            }}

        //sinon : new MOI
        if (!found) {
            InputMethodManager im = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            im.showSoftInput(this, InputMethodManager.SHOW_FORCED);
             moi = new MOI();
            currenttyped = new Message();
            moi.setFather(currenttyped);
            theuniverse.getMOIList().add(moi);
        }

        currenttyped.setCreatedate(new Date());
        currenttyped.setIduser(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
        currenttyped.setType(0);
        currenttyped.setPoids(0);
        currenttyped.setLikenumb(0);
    }


    public void moveto(Message target){
        if(mScaleFactor<=2 && 200/target.getGoval().getRay()>2){
            this.target=target;
        }else
        {
            this.target=null;
        }
        mScaleFactor = 200/target.getGoval().getRay();

        mPosX=mScaleFactor*(Centerx/mScaleFactor-target.getGoval().getX());

        mPosY=mScaleFactor*(Centery/mScaleFactor-target.getGoval().getY());


        postInvalidate();
    }



    @Override
    public boolean onKeyUp(int keycode, KeyEvent keyEvent) {

        switch (keyEvent.getUnicodeChar()){
            case 0 :
                if(currentmessage.length()>0){
                    currentmessage=currentmessage.subSequence(0,currentmessage.length()-1).toString();
                }
                currenttyped.setMmessage(currentmessage);

                break;
            case 10:
                //TODO change this case and have a true view as on the mockups
                if(currentmessage!=""){
                    save();
                }

                currenttyped=null;
                ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.getWindowToken(), 0);
                currentmessage="";
                break;

            default:
                currentmessage+=(char)keyEvent.getUnicodeChar();
                currenttyped.setMmessage(currentmessage);

        }

        postInvalidate();

        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Message clicked;
        ListIterator list = theuniverse.getMOIList().listIterator();
        MOI moi = new MOI();
        Boolean found = false;
        while(list.hasNext() ){
            moi = (MOI)list.next();

            clicked=clickedOn(new Point((int) e.getX(), (int) e.getY()), moi.getFather());

            //si le click est sur une bulle
            if(clicked!=null){
                found = true;
                moveto(clicked);
            }}
        if(!found){
        }
        return false;
    }

    /*

    Fonction qui sauve le Message ou le MOI if set to true
    père du Message nouveau et MOI de ce message ou que l'on souhaite save
     */

    public void save(){

        Map<String,Object> hash = new HashMap<>();
        theuniverse.MoiListtoMap();
        hash.put("MOIList",theuniverse.getMOIList());
        mRef.updateChildren(hash);
        theuniverse.toMOIList();

    }




   /*



   ************************ /!\ SUITE PAS IMPORTANT /!\***********************************************************
    *
     *
     *
     * */



    /*
    to check if the POINT is on the oval
     */
    public Message isOn(Point pt, Message root){
        Message answer;
        for(int i = 0; i<root.getChildren().size();i++){
            answer = clickedOn(pt,root.getChildren().get(i));
            if(answer!=null){
                return  answer;
            }
        }
        if(Math.pow(Math.pow(pt.x-(root.getGoval().getX()),2)+Math.pow(pt.y-(root.getGoval().getY()),2),0.5)<root.getGoval().getRay()){
            return root;
        }
        else{
            return null;
        }
    }



    public FirebaseDatabase getDatabase() {
        return database;
    }

    public void setDatabase(FirebaseDatabase database) {
        this.database = database;
    }

    public DatabaseReference getmRef() {
        return mRef;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }

   public void  placeMessage(Message fathertoplace){
        String id =fathertoplace.getIdmessage();
        ArrayList listMOI = theuniverse.getMOIList();
        ListIterator iterator = listMOI.listIterator();
        //On parcours tout les MOI
        while(iterator.hasNext()){
            MOI moi = (MOI) iterator.next();

            //si le message n'a pas le meme id que le father :
            if(!moi.getFather().getIdmessage().equals(id)){
                //father devient le nouveau message a placer qui contient fathertoplace si il est retourné
                moi.setFather(findMessageAndPlace(moi.getFather(),fathertoplace));
               // if(father!=null) {
                    // si il a été retourné c'est qu'il a été modifié donc on le set dans le moi
                   // moi.setFather(father);
                }
           // }else {
                // alors c'est le FATHER le bon direct
                moi.setFather(fathertoplace);
            }
            // on set le MOI modifié
            iterator.previous();
          //  iterator.set(moi);
            iterator.next();
        }
        // on set le listMOI modifié
       // theuniverse.setMOIList(listMOI);
    //}

    public Message findMessageAndPlace(Message init, Message fathertoplace){
        // si il a des enfants
        if(init.getChildren()!=null) {
            Message m;
            // on les parcours
            for(int i=0; i<init.getChildren().size(); i++){
                // si un des enfant correspond au father a mettre a jour on le set et on retourne la valeure
                if(init.getChildren().get(i).getIdmessage().equals(fathertoplace.getIdmessage())) {
                    ArrayList list = init.getChildren();
                    list.add(fathertoplace);
                    init.setChildren(list);
                    return init;
                }else {
                    //si ce n'est pas le cas on check la meme chose mais avec ses enfants de l'enfant

                    // m est retourné si il a été trouvé le bon father
                    m = findMessageAndPlace(init.getChildren().get(i), fathertoplace);
                    if(m!=null) {
                        // si il a été retourné alors on met a jour l'enfant qui a été modifié et retourné et on retourne le père de cette liste
                        ArrayList list = init.getChildren();
                        list.set(i,m);
                        init.setChildren(list);
                        return init;
                    }
                }
                }

            }
            // dans tout les autres cas on retourne null
        return null;
    }



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

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return false;
    }



    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }


    //TODO create IHM
}









