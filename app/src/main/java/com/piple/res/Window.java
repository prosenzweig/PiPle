package com.piple.res;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.Display;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.lang.Math;
import java.util.Date;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

/**
 * Class Window : displays the tree of messages, manages the interaction with the user.
 *      extends PanZoomView that enables panning (horizontally and vertically) and zooming with 2 fingers
 *      implements OnGestureListener to enable longPress
 *      implements OnDoubleTapListener
 */
public class Window extends PanZoomView implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener{


    /**
     * Represents the current universe
     */
    private Universe theuniverse;
    /**
     * the message that is being written by the user (!= null only when typing)
     */
    private Message currenttyped;
    /**
     * The string of the message being typed
     */
    private String currentmessage;
    private GestureDetectorCompat mDetector;
    private DatabaseReference mRef;
    /**
     * Center of the screen
     */
    private final Point Center;
    /**
     * Bubble to center the screen on (see moveto(Message target)
     */
    private Message target;


    public Window(Context context) {
        super(context);
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);

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
        currentmessage="";

        database = FirebaseDatabase.getInstance();

        mDetector=new GestureDetectorCompat(getContext(),this);


        WindowManager wm =(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display dis = wm.getDefaultDisplay();
        Point pt = new Point();
        dis.getSize(pt);
        Center = new Point(pt.x/2,pt.y/3);
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
        Center = new Point(pt.x/2,pt.y/3);
    }

    public Window (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        WindowManager wm =(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display dis = wm.getDefaultDisplay();
        Point pt = new Point();
        dis.getSize(pt);
        Center = new Point(pt.x/2,pt.y/3);
    }


    public void setmRef(DatabaseReference mRef) {
        this.mRef = mRef;
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
        for(int i=0; i<theuniverse.getMOIList().size();i++){
            if(theuniverse.getMOIList().get(i).getClass()==MOI.class){
            MOI mmoi = theuniverse.getMOIList().get(i);

            mmoi.getFather().setOval(new Oval(Center.x+i*2000,100,300,theuniverse.getColormap().get(mmoi.getFather().getIduser()), getContext()));
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

    /**
     * checks if pt is on root and calls itself recursivly to check root's children
     * @param pt the point we want to check the location of
     * @param root the message we want to check if the point is on
     * @return root if pt is on it, null otherwise
     * @author Paul Best
     */
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



    /**
     * Recursive fonction that draws the whole tree descending from root
     * draws root on the canvas, then decides of the ray and margin of root's children, instanciates the children, then calls itself recursivly for each children
      * @param canvas
     * @param root the root of the tree to be displayed
     * @param rootangle the direction of the tree to be displayed in rad (0 mins vertically from top to bottom
     * @author Paul Best
     */
    public void drawMessages(Canvas canvas,Message root, double rootangle){

        int nbchildren = root.getChildren().size();
        //angle de séparation entre les message
        double angle = Math.PI/nbchildren;
        //Dessin de l'oval ( root )
        root.getGoval().draw(canvas ,root);

        //Dessin du pseudo
        drawPseudo(root, canvas);

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
            msg.setOval(new Oval(mpt.x,mpt.y, mray, theuniverse.getColormap().get(msg.getIduser()), getContext()));

            //Appel récurcif avec l'enfant créé
            drawMessages(canvas, msg, mangle);
        }

    }

    /**
     * Display the pseudo of the user whose id corresponds to the message author's id
     * @param root the message whose pseudo must be displayed
     * @param canvas
     *
     */
        public void drawPseudo(Message root, Canvas canvas){
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

        }


    /**
     * Computes the center of a bubble knowing its father, ray, angle, and margin
      * @param father   the father bubble
     * @param mRay  the ray of the child bubble
     * @param angle the angle between the child and the father ( 0 means vertically from top to bottom )
     * @param margin the margin in pixels between the father's circle and the child's circle
     * @return the Point of the center for the child bubble
     * @author Paul Best
     */
    public Point beChildof(Oval father, float mRay, double angle, double margin ){
            Point mpoint = new Point();
            mpoint.x=(int)(father.getX()+ Math.sin(angle)*(margin+father.getRay()+mRay));
            mpoint.y=(int)(father.getY() + Math.cos(angle)*(margin+father.getRay()+mRay));

            return mpoint;

        }


    /**
     * Manages the longPress event.
     * Checks if the click is on a bubble
     * if so, create a new child to it and open the keyboard
     * if not, create a new MOI and its father bubble
     * @param e
     * @author Paul Best
     */
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
            }
        }

        //sinon : new MOI
        if (!found) {
            InputMethodManager im = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            im.showSoftInput(this, InputMethodManager.SHOW_FORCED);
             moi = new MOI();
            currenttyped = new Message();
            moi.setFather(currenttyped);
            theuniverse.getMOIList().add(moi);
        }

        currenttyped.setIduser(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
    }


    /**
     * Pans the canvas to center the bubble target and zooms on the target
     * @param target message to be centered
     * @author Paul Best
     */
    public void moveto(Message target){

            if(mScaleFactor<=2 && 200/target.getGoval().getRay()>2){
                this.target=target;
            }else
            {
                this.target=null;
            }
            mScaleFactor = 200/target.getGoval().getRay();

            mPosX=mScaleFactor*(Center.x/mScaleFactor-target.getGoval().getX());
            mPosY=mScaleFactor*(Center.y/mScaleFactor-target.getGoval().getY());

            postInvalidate();

        }


    /**
     * Manages the interaction with the keyboard.
     * adds the letter pressed to the message currentyped
     * takes one letter out if the key delete is pressed
     * saves the message if the key enter is pressed
     * @param keycode
     * @param keyEvent
     * @author Paul Best
     * @return
     */

        /**
         * Manages the interaction with the keyboard.
         * adds the letter pressed to the message currentyped
         * takes one letter out if the key delete is pressed
         * saves the message if the key enter is pressed
         * @param keycode
         * @param keyEvent
         * @author Paul Best
         * @return
         */
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

    /**
     * Manages the doubletap event by check if it is on a bubble (using clickedon), and if so center it (using moveto)
     * @param e
     * @return
     * @author Paul Best
     */
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Message clicked;
        ListIterator list = theuniverse.getMOIList().listIterator();

        Boolean found = false;
        while(list.hasNext() ){

             MOI moi = (MOI)list.next();
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

    /**
     *  Fonction qui va sauvée l'univers a la base de donnée quand elle est appelée
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

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
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



    /**
     * Return true if pinch zooming is supported.
     *
     * @return boolean
     */
    public boolean supportsZoom () {
        return true;
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

}









