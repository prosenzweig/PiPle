package com.piple.res;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Date;


public class Window extends PanZoomView{

    //private Message

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

        //TODO put everything back where it needs to be
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Messages");

        Message root = new Message("root", new ArrayList<Message>(), 0,"0GiPT5h7h1RJ1TrFWcRApMhrmPH3","lkjhlkh",true, true, true, new Date(), null);
        Message child1 = new Message("root child1", new ArrayList<Message>(), 0,"FbTRfsFwywTtvGPA8ZbnM5Bq5pC3","kjhkj",true, true, true, new Date(),root);
        Message child2 = new Message("root child2", new ArrayList<Message>(), 0,"0GiPT5h7h1RJ1TrFWcRApMhrmPH3","uidd",true, true, true, new Date(), root);
        Message child3 = new Message("root child3", new ArrayList<Message>(), 0,"FbTRfsFwywTtvGPA8ZbnM5Bq5pC3","poair",true, true, true, new Date(), root);
        Message child4 = new Message("root child4", new ArrayList<Message>(), 0,"FbTRfsFwywTtvGPA8ZbnM5Bq5pC3","poair",true, true, true, new Date(), root);
        Message child5 = new Message("root chil5", new ArrayList<Message>(), 0,"FbTRfsFwywTtvGPA8ZbnM5Bq5pC3","poair",true, true, true, new Date(), root);

        root.getChildren().add(child1);
        root.getChildren().add(child2);
        root.getChildren().add(child3);
        root.getChildren().add(child4);

        child3.getChildren().add(new Message("child1 child1", new ArrayList<Message>(), 0,"0GiPT5h7h1RJ1TrFWcRApMhrmPH3","lkjhlkh",true, true, true, new Date(), child1));
        child3.getChildren().add(new Message("child1 child2", new ArrayList<Message>(), 0,"0GiPT5h7h1RJ1TrFWcRApMhrmPH3","lkjhlkh",true, true, true, new Date(), child1));
        child3.getChildren().add(new Message("child1 child3", new ArrayList<Message>(), 0,"FbTRfsFwywTtvGPA8ZbnM5Bq5pC3","kjhkj",true, true, true, new Date(),child1));
        child3.getChildren().add(new Message("child1 child4", new ArrayList<Message>(), 0,"0GiPT5h7h1RJ1TrFWcRApMhrmPH3","uidd",true, true, true, new Date(), child1));

        child4.getChildren().add(new Message("child4 child1", new ArrayList<Message>(), 0,"0GiPT5h7h1RJ1TrFWcRApMhrmPH3","lkjhlkh",true, true, true, new Date(), child1));
        child4.getChildren().add(new Message("child4 child2", new ArrayList<Message>(), 0,"0GiPT5h7h1RJ1TrFWcRApMhrmPH3","lkjhlkh",true, true, true, new Date(), child1));
        child4.getChildren().add(new Message("child4 child3", new ArrayList<Message>(), 0,"FbTRfsFwywTtvGPA8ZbnM5Bq5pC3","kjhkj",true, true, true, new Date(),child1));
        child4.getChildren().add(new Message("child4 child4", new ArrayList<Message>(), 0,"0GiPT5h7h1RJ1TrFWcRApMhrmPH3","uidd",true, true, true, new Date(), child1));


        ref.child(root.getIdmessage()).setValue(root );
        ref.child(child1.getIdmessage()).setValue( child1);
        ref.child(child2.getIdmessage()).setValue(child2);
        ref.child(child3.getIdmessage()).setValue(child3 );



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

        drawMessages(canvas,new Oval((int)Math.abs(50*(root.getChildren().size()*0.25+1)), new Point(200,200),0xffffff00,root),0);


    }

    public void drawMessages(Canvas canvas,Oval root, double rootangle){
        int nbchildren = root.getMsg().getChildren().size();
        double angle = Math.PI/nbchildren;
        root.getmDrawable().draw(canvas);
        drawtext(canvas, root.getMsg().getMmessage(),root);

        for(int i=0; i<root.getMsg().getChildren().size(); i++ ){
            Message msg = root.getMsg().getChildren().get(i);
            int mray = (int)Math.abs(50*(msg.getChildren().size()*0.25+1));
            //int mray =(int)Math.abs(root.getray()*(0.5+0.04*msg.getChildren().size()));
            Oval child = new Oval(mray, beChildof(root,mray,angle*i-Math.PI/2+rootangle+angle/2,Math.abs(15+msg.getChildren().size()*mray*0.25)),0xffffff00, msg);
            drawMessages(canvas,child, angle*i-Math.PI/2+rootangle+angle/2);
        }
    }


    public Point beChildof(Oval father, int mRay, double angle, double margin ){
        Point mpoint = new Point();
        mpoint.x=(int)(father.getfpt().x + Math.sin(angle)*(margin+father.getfray()+mRay));
        mpoint.y=(int)(father.getfpt().y + Math.cos(angle)*(margin+father.getfray()+mRay));
        return mpoint;
    }
    public void drawtext(Canvas canvas, String text, Oval oval){

        //TODO create subfunction
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
            size--;
            paint.setTextSize(size);
            depasse=false;
            for(i=0;i<nblignes;i++){
                paint.getTextBounds(textlist.get(i), 0, textlist.get(i).length(), bounds);
                if(bounds.width()>oval.getfray()*1.6){
                    depasse=true;
                }
            }
        }while(depasse);

        for(i=0;i<nblignes;i++){
            paint.getTextBounds(textlist.get(i), 0, textlist.get(i).length(), bounds);
            canvas.drawText(textlist.get(i), oval.getfpt().x - (bounds.width())/2,oval.getfpt().y-(nblignes/2-i)*size , paint);
        }
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


    //TODO create IHM
}




