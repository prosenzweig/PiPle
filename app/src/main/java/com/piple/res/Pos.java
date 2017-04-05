package com.piple.res;

/**
 * Created by jeremie on 08/03/2017.
 * Class Pos : stores the coordinates of one point (in float and not double like Point)
 */

public class Pos {

    /**
     * The Point's coordinate
     */
    float X,Y;

    public Pos(float x, float y) {
        X = x;
        Y = y;
    }

    public float getX() {
        return X;
    }

    public void setX(float x) {
        X = x;
    }

    public float getY() {
        return Y;
    }

    public void setY(float y) {
        Y = y;
    }
}
