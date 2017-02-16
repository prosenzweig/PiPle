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


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Messages");

        Message root = new Message("blabla", new ArrayList<Message>(), 0,"0GiPT5h7h1RJ1TrFWcRApMhrmPH3","lkjhlkh",true, true, true, new Date(), null);
        Message child1 = new Message("Comment allez-vous monsieur?", new ArrayList<Message>(), 0,"FbTRfsFwywTtvGPA8ZbnM5Bq5pC3","kjhkj",true, true, true, new Date(),root);
        Message child2 = new Message("très bien Merci !", new ArrayList<Message>(), 0,"0GiPT5h7h1RJ1TrFWcRApMhrmPH3","uidd",true, true, true, new Date(), child1);
        Message child3 = new Message("En vrai ça marche plutot", new ArrayList<Message>(), 0,"FbTRfsFwywTtvGPA8ZbnM5Bq5pC3","poair",true, true, true, new Date(), root);

        root.getChildren().add(child1);
        root.getChildren().add(child3);
        child1.getChildren().add(child2);

        child1.getChildren().add(new Message("blabla", new ArrayList<Message>(), 0,"0GiPT5h7h1RJ1TrFWcRApMhrmPH3","lkjhlkh",true, true, true, new Date(), null));
        child1.getChildren().add(new Message("Comment allez-vous monsieur?", new ArrayList<Message>(), 0,"FbTRfsFwywTtvGPA8ZbnM5Bq5pC3","kjhkj",true, true, true, new Date(),root));
        child1.getChildren().add(new Message("très bien Merci !", new ArrayList<Message>(), 0,"0GiPT5h7h1RJ1TrFWcRApMhrmPH3","uidd",true, true, true, new Date(), child1));

        /*ref.child(root.getIdmessage()).setValue(root );
        ref.child(child1.getIdmessage()).setValue( child1);
        ref.child(child2.getIdmessage()).setValue(child2);
        ref.child(child3.getIdmessage()).setValue(child3 );*/


        /*
        ref.addValueEventListener(new ValueEventListener() {
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

        System.out.println("blablablablablabl");
        drawMessages(canvas,new Oval(100, new Point(200,200),0xffffff00,root));


    }

    public void drawMessages(Canvas canvas,Oval root){
        int nbchildren = root.getMsg().getChildren().size();
        double angle = Math.PI/nbchildren;
        root.getmDrawable().draw(canvas);
        drawtext(canvas, root.getMsg().getMmessage(),root);
        for(int i=0; i<root.getMsg().getChildren().size(); i++ ){
            Message msg = root.getMsg().getChildren().get(i);
            drawMessages(canvas,new Oval(70, beChildof(root, 70, angle*i-Math.PI/2),0xffffff00, msg));
        }
    }


    public Point beChildof(Oval father, int mRay, double angle ){
        Point mpoint = new Point();
        mpoint.x=(int)(father.getpt().x + Math.sin(angle)*(margin+father.getray()+mRay));
        mpoint.y=(int)(father.getpt().y + Math.cos(angle)*(margin+father.getray()+mRay));
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
                if(bounds.width()>oval.getray()*1.8){
                    depasse=true;
                }
            }
        }while(depasse);

        for(i=0;i<nblignes;i++){
            paint.getTextBounds(textlist.get(i), 0, textlist.get(i).length(), bounds);
            canvas.drawText(textlist.get(i), oval.getpt().x - (bounds.width())/2,oval.getpt().y-(nblignes/2-i)*size , paint);
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

