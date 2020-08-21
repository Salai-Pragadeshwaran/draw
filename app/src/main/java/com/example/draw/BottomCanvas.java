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

public class BottomCanvas extends View {

    //idk why but only if I make this public static the lines are drawn properly
    static ArrayList<TouchPath> touchPathsBottom = new ArrayList<>();
    Path mPath;
    float currentX;
    float currentY;
    float TOUCH_TOLERANCE = 4;
    Paint blackPaint = new Paint();
    int color = 1;

    private BottomCanvas.BottomCanvasListener listener;

    public interface BottomCanvasListener{
        Void onInputBottom(TouchPath input, Boolean start);
    }

    public void changeBottomColor(int n) {
        color = n;
    }

    public void undoBottom(){
        if(touchPathsBottom.size()>0){
            touchPathsBottom.remove(touchPathsBottom.size() - 1);
        }
    }

    public void updateTouchPaths(TouchPath tp, Boolean start){
        if (!start) {
            touchPathsBottom.remove(touchPathsBottom.size() - 1);
        }
        touchPathsBottom.add(tp);
    }



    public BottomCanvas(Context context) {
        super(context);
        init(null);
    }

    public BottomCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BottomCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public BottomCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {
        blackPaint.setColor(getResources().getColor(R.color.colorAccent));
        blackPaint.setStyle(Paint.Style.STROKE);
        blackPaint.setStrokeWidth(10);
        blackPaint.isAntiAlias();

        listener = (BottomCanvas.BottomCanvasListener) getContext();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawColor(getResources().getColor(R.color.canvasBg));
        for(TouchPath tp: touchPathsBottom){
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
        touchPathsBottom.add(path);

        mPath.reset();
        mPath.moveTo(x, y);
        currentX = x;
        currentY = y;
        listener.onInputBottom(new TouchPath(mPath, color), true);
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
        listener.onInputBottom(new TouchPath(mPath, color), false);
    }
}

