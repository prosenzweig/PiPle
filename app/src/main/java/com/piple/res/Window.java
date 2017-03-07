
import java.util.HashMap;
    private boolean creatinganoval=false;
        currenttyped.setGoval(new Oval(500,100 ,(float) 80, Color.BLACK, context));

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawMessages(canvas,currenttyped,0);
    public Message clickedOn(Point pt, Message root){
        Message retour = null;
        Message answer = null;
        for(int i = 0; i<root.getChildren().size();i++){
            answer = clickedOn(pt,root.getChildren().get(i));
            if(answer!=null){
                return  answer;
            }
        }
        if(Math.pow(Math.pow(pt.x-root.getGoval().getX(),2)+Math.pow(pt.y-root.getGoval().getY(),2),0.5)<root.getGoval().getRay()){
            return root;
        }
        else{
            return null;
        }
    }
    root.getGoval().draw(canvas);
    for(int i=0; i<nbchildren; i++ ) {
        Message msg = root.getChildren().get(i);
        //int mray = (int) Math.abs(50 * (msg.getChildren().size() * 0.25 + 1));
        int mray =(int)Math.abs(root.getGoval().getRay()*(0.5+0.04*msg.getChildren().size()));
        double mangle =  angle * i - Math.PI / 2 + rootangle + angle / 2;
        double margin = 15 + msg.getChildren().size() * mray * 0.25;
        Point mpt = beChildof(root.getGoval(), mray,mangle, margin );
        msg.setGoval(new Oval(mray, mpt.x, mpt.y, 0xffffff00, getContext()));
        drawMessages(canvas, msg, mangle);
    }
    @Override public boolean onTouchEvent(MotionEvent ev)
    {
        boolean ret = super.onTouchEvent(ev);
      //  gestureDetector.onTouchEvent(ev);
        if(clickedOn(new Point((int)ev.getX(),(int)ev.getY()),currenttyped)!=null){
            clickedOn(new Point((int)ev.getX(),(int)ev.getY()),currenttyped).setMmessage("OUUUAIIII");
            invalidate();
        }
            return ret;