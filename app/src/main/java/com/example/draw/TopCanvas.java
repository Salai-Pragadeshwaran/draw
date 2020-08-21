package com.example.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TopCanvas extends View {

    private TopCanvasListener listener;

    public interface TopCanvasListener{
       Void onInputTop(TouchPath input, Boolean start);
    }

    public void undoTop(){
        if(touchPathsTop.size()>0){
            touchPathsTop.remove(touchPathsTop.size() - 1);
        }
    }

    public void updateTopColor(int n){
        color = n;
    }

    public void changeTopColor(int n){
        switch(n){
            case 1: {
                blackPaint.setColor(getResources().getColor(R.color.whitePaint));
                break;
            }
            case 2: {
                blackPaint.setColor(getResources().getColor(R.color.blackPaint));
                break;
            }
            case 3: {
                blackPaint.setColor(getResources().getColor(R.color.redPaint));
                break;
            }
            case 4: {
                blackPaint.setColor(getResources().getColor(R.color.yellowPaint));
                break;
            }
            case 5: {
                blackPaint.setColor(getResources().getColor(R.color.greenPaint));
                break;
            }
            case 6: {
                blackPaint.setColor(getResources().getColor(R.color.bluePaint));
                break;
            }
            default:{
                blackPaint.setColor(getResources().getColor(R.color.whitePaint));
                break;
            }
        }
    }

    public void updateTouchPaths(TouchPath tp, Boolean start){
        if (!start) {
            TouchPath a = touchPathsTop.get(touchPathsTop.size() - 1);
            touchPathsTop.remove(touchPathsTop.size() - 1);
            touchPathsTop.add(new TouchPath(tp.getPath(), a.getColor()));
        }else {
            touchPathsTop.add(new TouchPath(tp.getPath(), color));
        }
    }

    static ArrayList<TouchPath> touchPathsTop = new ArrayList<>();
    Path mPath;
    float currentX;
    float currentY;
    float TOUCH_TOLERANCE = 4;
    static Paint blackPaint = new Paint();
    static int color = 1;


    public TopCanvas(Context context) {
        super(context);
        init(null);
    }

    public TopCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TopCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public TopCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {
        blackPaint.setColor(getResources().getColor(R.color.whitePaint));
        blackPaint.setStyle(Paint.Style.STROKE);
        blackPaint.setStrokeWidth(10);
        blackPaint.isAntiAlias();

        listener = (TopCanvasListener) getContext();

    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawColor(getResources().getColor(R.color.canvasBg));
        for(TouchPath tp: touchPathsTop){
            changeTopColor(tp.getColor());
            canvas.drawPath(tp.getPath(), blackPaint);
        }
        postInvalidate();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                startDrawing(x, y);
            }
            case MotionEvent.ACTION_UP:{
                stopDrawing();
            }
            case MotionEvent.ACTION_MOVE:{
                continueDrawing(x, y);
            }
        }
        return true;
    }

    private void startDrawing(float x, float y) {
        mPath = new Path();
        TouchPath path = new TouchPath(mPath, color);
        touchPathsTop.add(path);

        mPath.reset();
        mPath.moveTo(x, y);
        currentX = x;
        currentY = y;
        listener.onInputTop(new TouchPath(mPath, color), true);

    }
    private void stopDrawing() {
        mPath.lineTo(currentX, currentY);
    }
    private void continueDrawing(float x, float y) {
        float dx = Math.abs(x - currentX);
        float dy = Math.abs(y - currentY);

        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(currentX, currentY, x, y);
            currentX = x;
            currentY = y;
        }
        listener.onInputTop(new TouchPath(mPath, color), false);
    }
}
