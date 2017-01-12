package com.example.paul.ronds;

import android.content.Context;
import android.graphics.Canvas;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by Paul on 10/01/2017.
 */

public class scrollview extends ScrollView {

    private Window mwin;

    public scrollview(Context context){
        super(context);
        mwin = new Window(context);
        this.addView(mwin);

    }

    /*protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();

        C1.getmDrawable().draw(canvas);
        canvas.restore();

        computeScroll();
    }*/

}
