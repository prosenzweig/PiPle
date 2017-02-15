package com.piple.res;



import android.graphics.Point;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import java.util.Date;



/**
 * Class Oval
 *
 * Contains a circle shape used as container of a message.
 */
public class Oval
{



    /// RESOURCES ///

    private ShapeDrawable mDrawable;
    private int mRay;
    private Point mPt;
    private Message mMessage;
    private int type, idUser, idMessage;
    private boolean important, viewed, silent;
    private Date createDate;



    /// CONSTRUCTORS ///

    public Oval(ShapeDrawable mDrawable, Message mMessage, int idUser, int type, int idMessage, boolean important, boolean viewed, boolean silent, Date createDate)
    {
        this.mDrawable = mDrawable;
        this.mMessage = mMessage;
        this.idUser = idUser;
        this.type = type;
        this.idMessage = idMessage;
        this.important = important;
        this.viewed = viewed;
        this.silent = silent;
        this.createDate = createDate;

        mDrawable = new ShapeDrawable(new OvalShape());
    }



    public Oval(int ray, Point pt, int color, Message msg )
    {
        this.mPt=pt;
        this.mRay=ray;
        this.mMessage=msg;
        int[] colors = {0xffffffff, color};
        float[] stops = {0.8f, 1f};
        mDrawable = new ShapeDrawable(new OvalShape());
        mDrawable.setBounds(pt.x-ray, pt.y-ray, pt.x+ray, pt.y+ray);
        mDrawable.getPaint().setColor(color);
        mDrawable.getPaint().setShader(new RadialGradient(ray,ray,ray, colors, stops, Shader.TileMode.MIRROR ));
    }



    /// GETTERS & SETTERS ///

    public void setcolor(int color){
        this.mDrawable.getPaint().setColor(color);
    }

    public ShapeDrawable getmDrawable(){
        return mDrawable;
    }

    public void setpos(float x, float y){
        mDrawable.setBounds((int)x- mRay, (int)y- mRay, (int)x+ mRay, (int)y+ mRay);
    }

    public void setOval(int mray, Point mpoint, int color){
        this.mPt =mpoint;
        this.mRay =mray;
        int[] colors = {0xffffffff, color};
        float[] stops = {0.8f, 1f};
        mDrawable.setBounds(mpoint.x-mray, mpoint.y-mray, mpoint.x+mray, mpoint.y+mray);
        mDrawable.getPaint().setColor(color);
        mDrawable.getPaint().setShader(new RadialGradient(mray,mray,mray, colors, stops, Shader.TileMode.MIRROR ));
    }

    public void setDrawable(ShapeDrawable mDrawable) {
        this.mDrawable = mDrawable;
    }

    public int getmRay() {
        return mRay;
    }

    public void setmRay(int mRay) {
        this.mRay = mRay;
    }

    public Point getmPt() {
        return mPt;
    }

    public void setmPt(Point mPt) {
        this.mPt = mPt;
    }

    public Message getmMessage() {
        return mMessage;
    }

    public void setmMessage(Message mMessage) {
        this.mMessage = mMessage;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public boolean isSilent() {
        return silent;
    }

    public void setSilent(boolean silent) {
        this.silent = silent;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}