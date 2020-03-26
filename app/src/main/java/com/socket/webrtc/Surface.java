package com.socket.webrtc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;

import org.webrtc.SurfaceViewRenderer;

public class Surface extends SurfaceViewRenderer {
    Paint paint = new Paint();

    public Surface(Context context) {
        super(context);
        //setWillNotDraw(false);
    }

    public Surface(Context context, AttributeSet attrs) {
        super(context, attrs);
        //setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("ABHI", "ondraw called");
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, 50, 50, paint);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        super.surfaceCreated(holder);
        setWillNotDraw(false);
    }
}
