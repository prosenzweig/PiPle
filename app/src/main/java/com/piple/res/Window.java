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


public class Window extends PanZoomView implements View.OnClickListener {


    private Universe theuniverse;
    private Message currenttyped;
    private String currentmessage;
    private Canvas mycanvas;
    private Context windowcontext;
   private boolean creatinganoval=false;

    public Window(Context context) {
        super(context);
        windowcontext=context;

        Message message = new Message();
        message.setGoval(new Oval(0,0 ,(float) 80, Color.BLACK, windowcontext));
        currenttyped = message;



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
        currenttyped.getGoval().OnDraw(canvas);


        canvas.save();
        canvas.restore();
    }


    public void drawOnCanvas (Canvas canvas) {

        //TODO put everything back where it needs to be
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Messages");





        /*ref.addValueEventListener(new ValueEventListener() {
                                      @Override
                                      public void onDataChange(DataSnapshot dataSnapshot) {
                                          Message post = dataSnapshot.getValue(Message.class);
                                          System.out.println("coucoucoucoucouc"+post.getMmessage());
                                      }

                                      @Override
                                      public void onCancelled(DatabaseError databaseError) {
                                          System.out.println("failllllleeedddd");
                                      }
                                  });*/


    }

    public void drawMessages(Canvas canvas,Oval root, double rootangle){
       /* int nbchildren = root.getMsg().getChildren().size();
        double angle = Math.PI/nbchildren;
        root.getmDrawable().draw(canvas);
        drawtext(canvas, root.getMsg().getMmessage(),root);

        for(int i=0; i<root.getMsg().getChildren().size(); i++ ){
            Message msg = root.getMsg().getChildren().get(i);
            int mray = (int)Math.abs(50*(msg.getChildren().size()*0.25+1));
            //int mray =(int)Math.abs(root.getray()*(0.5+0.04*msg.getChildren().size()));
            Oval child = new Oval(mray, beChildof(root,mray,angle*i-Math.PI/2+rootangle+angle/2,Math.abs(15+msg.getChildren().size()*mray*0.25)),0xffffff00, msg, getContext());
            drawMessages(canvas,child, angle*i-Math.PI/2+rootangle+angle/2);
        }*/
    }


    public Point beChildof(Oval father, int mRay, double angle, double margin ){
        Point mpoint = new Point();

        return mpoint;
    }



    /*mAutoCenterAnimator = ObjectAnimator.ofInt(PieChart.this, "PieRotation", 0);
mAutoCenterAnimator.setIntValues(targetAngle);
mAutoCenterAnimator.setDuration(AUTOCENTER_ANIM_DURATION);
mAutoCenterAnimator.start();*/



    @Override public boolean onTouchEvent(MotionEvent ev)
    {
        boolean ret = super.onTouchEvent(ev);

            return ret;

    }

    public boolean onLongClick(){

        System.out.print("\n looong click");
        ((InputMethodManager) windowcontext.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        creatinganoval=true;
        return true;
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

    @Override
    public void onClick(View v) {
        //((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);

        System.out.println("BONJOUUUUR");
    }


    //TODO create IHM
}









