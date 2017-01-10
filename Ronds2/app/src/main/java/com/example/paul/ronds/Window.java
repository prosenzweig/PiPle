package com.example.paul.ronds;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ScrollView;



/**
 * Created by Paul on 01/12/2016.
 */

public class Window extends View implements View.OnTouchListener{
    Oval C1, C2, C3, C4, C5;
    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;

    public Window(Context context) {
        super(context);
        C1 = new Oval(300, 500, 800);
        C2 = new Oval(150, -50, -50);
        C3 = new Oval(60, 850, 1400);
        C4 = new Oval(150, 500, 400);
        C5 = new Oval(150, 590, 400);
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor);
        C1.getmDrawable().draw(canvas);
        C2.getmDrawable().draw(canvas);
        C3.getmDrawable().draw(canvas);
        C4.getmDrawable().draw(canvas);
        C5.getmDrawable().draw(canvas);
        canvas.restore();
    }


    /*@Override
    public boolean onTouchEvent(MotionEvent e){
            int action = e.getAction();


        return true;
    }*/


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Let the ScaleGestureDetector inspect all events.
        mScaleDetector.onTouchEvent(ev);
        return true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }


    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();

            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));

            invalidate();
            return true;
        }
    }

}