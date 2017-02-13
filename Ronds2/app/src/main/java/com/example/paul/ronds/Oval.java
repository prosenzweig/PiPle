package com.example.paul.ronds;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

/**
 * Class Oval
 * Creates a filled circle shape from parameters :
 *      - rad (radius of the circle)
 *      - centerX, centerY (center coordinates of the circle)
 * Contains methods :
 *      - Oval -> Constructor
 *      - getMyDrawable -> getter of the filled circle shape
 *      - setColor -> set color of the circle (can be a shader)
 *      - setPosition -> set position of the center of the shape
 */
public class Oval
{
    private ShapeDrawable myDrawable; //Variable to hold the actual shape
    private int radius; //Radius of our circle

    /**
     * Main contructor
     *
     * @param rad radius we want for the circle
     * @param centerX x coordinate assigned to the circle's center
     * @param centerY y coordinate assigned to the circle's center
     */
    public Oval(int rad, int centerX, int centerY)
    {
        int[] colors = {0xffffffff, 0xbb9cda5e};
        float[] stops = {0.9f, 1f};

        radius = rad;

        myDrawable = new ShapeDrawable(new OvalShape());
        myDrawable.setBounds(centerX-rad, centerY-rad, centerX+rad, centerY+rad);
    }

    /**
     * Getter of the circle shape
     *
     * @return the circle shape
     */
    public ShapeDrawable getMyDrawable()
    {
        return myDrawable;
    }

    /**
     * Setter of the color of the shape
     *
     * @param color color to be assigned to the circle
     */
    public void setColor(int color)
    {
        this.myDrawable.getPaint().setColor(color);
    }

    /**
     * Setter of the position of the circle's center
     *
     * @param x x coordinate assigned to the circle's center
     * @param y y coordinate assigned to the circle's center
     */
    public void setPosition(float x, float y){
        myDrawable.setBounds((int)x-radius, (int)y-radius, (int)x+radius, (int)y+radius);
    }
}

