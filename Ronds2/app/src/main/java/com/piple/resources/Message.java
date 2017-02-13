package com.piple.resources;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

/**
 * Class Message
 * Creates a filled circle shape from parameters :
 *      - rad (radius of the circle)
 *      - centerX, centerY (center coordinates of the circle)
 * Contains methods :
 *      - Oval -> Constructor
 *      - getMyDrawable -> getter of the filled circle shape
 *      - setColor -> set color of the circle (can be a shader)
 *      - setPosition -> set position of the center of the shape
 */
public class Message
{
    /// RESOURCES ///

    private int radius; //Radius of our circle
    private int centerX, centerY; //Coordinates x and y of the center of the circle
    private int color; //Color of the circle
    private ShapeDrawable myDrawable; //Variable to hold the actual shape
    private String message;

    /// CONSTRUCTORS ///

    /**
     * Default constructor.
     * Calls the main constructor and initializes the Circle with default values.
     */
    public Message()
    {
        this(50, 50, 50, 0xff000000, "Standard message.");
    }

    /**
     * Main contructor.
     *
     * @param radius radius of the circle
     * @param centerX x coordinate of the circle's center
     * @param centerY y coordinate of the circle's center
     * @param color color of the circle
     * @param message message displayed in the circle
     */
    public Message(int radius, int centerX, int centerY, int color, String message)
    {
        this.radius = radius;
        this.centerX = centerX;
        this.centerY = centerY;
        this.color = color;

        myDrawable = new ShapeDrawable(new OvalShape());
        myDrawable.setBounds(centerX - radius, centerY - radius, centerX + radius, centerY + radius);

        this.message = message;
    }

    /// METHODS ///



    /// GETTERS & SETTERS ///

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenter(int centerX, int centerY) {
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public ShapeDrawable getMyDrawable() {
        return myDrawable;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
