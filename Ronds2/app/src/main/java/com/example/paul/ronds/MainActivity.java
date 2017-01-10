package com.example.paul.ronds;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import static android.view.KeyEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;


/**
 * Class MainActivity
 *      extends Activity
 *      implements View.OnTouchListener
 *
 * Creates the main activity.
 */
public class MainActivity extends Activity implements View.OnTouchListener
{
    private Window myWin;
    private float x_drag_start, y_drag_start;
    private float x_win_start, y_win_start;
    private boolean first = true;

    /**
     * Method onCreate
     * Implements the behavior of the activity when it is created.
     *
     * @param savedInstanceState saved state from the program
     */
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        myWin = new Window(this);
        setContentView(myWin);

        ViewGroup.LayoutParams myParams = myWin.getLayoutParams();
        myParams.width =8000;
        myParams.height =18000;
        myWin.setLayoutParams(myParams);

        myWin.setOnTouchListener(this);
    }

    /**
     * Method onTouch
     * Implements the behavior of the activity on an user touch event.
     *
     * @param v ?
     * @param e touch event
     * @return true
     */
    @Override
    public boolean onTouch(View v, MotionEvent e){

        int action = e.getAction();
        switch (action){

            case ACTION_DOWN :
                if(first==true){
                    x_drag_start=e.getX();
                    y_drag_start=e.getY();
                    x_win_start = myWin.getX();
                    y_win_start = myWin.getY();
                }
                else{
                    ((ViewGroup) myWin.getParent()).removeView(myWin);
                    myWin.setX(x_win_start+e.getX()-x_drag_start);
                    myWin.setY(y_win_start+e.getY()-y_drag_start);
                    setContentView(myWin);
                }

                break;
            case ACTION_UP :
                first=false;
                break;
        }

        return true;
    }
}


