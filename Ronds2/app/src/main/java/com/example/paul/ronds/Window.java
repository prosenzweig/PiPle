package com.example.paul.ronds;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;


/**
 *      extends View
 *
 * Creates a window to contain several shapes, that can then be displayed as a whole.
 */
public class Window extends View
{
    Oval C1, C2, C3, C4, C5;
    private ScaleGestureDetector myScaleDetector;
    private float myScaleFactor = 1.f;

    /**
     * Main constructor
     *
     * @param context gives the window the current context of the program
     */
    public Window(Context context)
    {
        super(context);

        C1 = new Oval(300, 500, 800);
        C2 = new Oval(150, -50, -50);
        C3 = new Oval(60, 850, 1400);
        C4 = new Oval(150, 500, 400);
        C5 = new Oval(150, 590, 400);

        myScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    /**
     * Draw method, draws all shapes wanted on the window.
     *
     * @param canvas ?
     */
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        canvas.save();
        canvas.scale(myScaleFactor, myScaleFactor);

        C1.getMyDrawable().draw(canvas);
        C2.getMyDrawable().draw(canvas);
        C3.getMyDrawable().draw(canvas);
        C4.getMyDrawable().draw(canvas);
        C5.getMyDrawable().draw(canvas);

        canvas.restore();
    }

    /**
     * Detects a touch event.
     *
     * @param ev touch event information
     * @return true
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        // Let the ScaleGestureDetector inspect all events.
        myScaleDetector.onTouchEvent(ev);
        return true;
    }



    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    /**
     * Class ScaleListener
     *      extends ScaleGestureDetector.SimpleOnScaleGestureListener
     *
     * Detects the gesture of the user and scales it.
     */
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener
    {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            myScaleFactor *= detector.getScaleFactor();

            // Don't let the object get too small or too large.
            myScaleFactor = Math.max(0.1f, Math.min(myScaleFactor, 5.0f));

            invalidate();
            return true;
        }
    }
}