package com.example.paul.ronds;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ScrollableImage extends View {
    private Bitmap bmLargeImage; // bitmap large enough to be scrolled
    private Rect displayRect = null; // rect we display to
    private Rect scrollRect = null; // rect we scroll over our bitmap with
    private int scrollRectX = 0; // current left location of scroll rect
    private int scrollRectY = 0; // current top location of scroll rect
    private float scrollByX = 0; // x amount to scroll by
    private float scrollByY = 0; // y amount to scroll by

    Oval C1, C2, C3, C4, C5;

    private int width, height;

    private Paint background;

    public ScrollableImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollableImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setSize(int width, int height) {
        background = new Paint();
        background.setColor(Color.WHITE);
        this.width = width;
        this.height = height;

        // Destination rect for our main canvas draw. It never changes.
        displayRect = new Rect(0, 0, width, height);
        // Scroll rect: this will be used to 'scroll around' over the
        // bitmap in memory. Initialize as above.
        scrollRect = new Rect(0, 0, width, height);
        // scrollRect = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
    }

    public void setImage(Bitmap bmp) {
        if (bmLargeImage != null)
            bmLargeImage.recycle();

        bmLargeImage = bmp;
        scrollRect = new Rect(0, 0, width, height);
        scrollRectX = 0;
        scrollRectY = 0;
        scrollByX = 0;
        scrollByY = 0;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true; // done with this event so consume it
    }

    public void notifyScroll(float distX, float distY) {
        scrollByX = distX; // move update x increment
        scrollByY = distY; // move update y increment
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (bmLargeImage == null)
            return;

        if (scrollByX != 0 || scrollByY != 0) {
            // Our move updates are calculated in ACTION_MOVE in the opposite     direction
            // from how we want to move the scroll rect. Think of this as
            // dragging to
            // the left being the same as sliding the scroll rect to the right.
            int newScrollRectX = scrollRectX - (int) scrollByX;
            int newScrollRectY = scrollRectY - (int) scrollByY;
            scrollByX = 0;
            scrollByY = 0;

            // Don't scroll off the left or right edges of the bitmap.
            if (newScrollRectX < 0)
                newScrollRectX = 0;
            else if (newScrollRectX > (bmLargeImage.getWidth() - width))
                newScrollRectX = (bmLargeImage.getWidth() - width);

            // Don't scroll off the top or bottom edges of the bitmap.
            if (newScrollRectY < 0)
                newScrollRectY = 0;
            else if (newScrollRectY > (bmLargeImage.getHeight() - height))
                newScrollRectY = (bmLargeImage.getHeight() - height);
            scrollRect.set(newScrollRectX, newScrollRectY, newScrollRectX
                    + width, newScrollRectY + height);

            scrollRectX = newScrollRectX;
            scrollRectY = newScrollRectY;
        }

        canvas.drawRect(displayRect, background);
        // We have our updated scroll rect coordinates, set them and draw.
        canvas.drawBitmap(bmLargeImage, scrollRect, displayRect, background);

    }
}