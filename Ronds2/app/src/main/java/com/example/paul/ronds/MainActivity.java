package com.example.paul.ronds;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.view.ViewPager;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import static android.view.KeyEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_UP;


/**
 * Created by Paul on 28/11/2016.
 */

public class MainActivity extends Activity implements View.OnTouchListener{
    private Window mwin;
    private float x_drag_start;
    private float y_drag_start;
    private float x_win_start;
    private float y_win_start;
    private boolean first = true;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mwin = new Window(this);
        setContentView(mwin);
        ViewGroup.LayoutParams myparam = mwin.getLayoutParams();
        myparam.width =8000;
        myparam.height =18000;
        mwin.setLayoutParams(myparam);

        mwin.setOnTouchListener(this);


    }

    @Override
    public boolean onTouch(View v, MotionEvent e){

        int action = e.getAction();
        switch (action){

            case ACTION_DOWN :
                if(first==true){
                    x_drag_start=e.getX();
                    y_drag_start=e.getY();
                    x_win_start = mwin.getX();
                    y_win_start = mwin.getY();
                }
                else{
                    ((ViewGroup) mwin.getParent()).removeView(mwin);
                    mwin.setX(x_win_start+e.getX()-x_drag_start);
                    mwin.setY(y_win_start+e.getY()-y_drag_start);
                    setContentView(mwin);
                }

                break;
            case ACTION_UP :
                first=false;
                break;
        }

        return true;
    }
}


