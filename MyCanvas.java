package net.codebot.ragdoll;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MyCanvas extends View {
    private Paint paint = new Paint();
    private Activity activity;
    private MyDoll doll;
    private Paint linePaint = new Paint();

    public MyCanvas(Context context, AttributeSet attr) {
        super(context, attr);
        activity = (context instanceof Activity) ? (Activity) context : null;
        linePaint.setColor(Color.GRAY);
        linePaint.setStrokeWidth(7);
        linePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int ptrCount = event.getPointerCount();
        float x = event.getX(0);
        float y = event.getY(0);
        float x2;
        float y2;

        if (ptrCount == 2) {
            x2 = event.getX(1);
            y2 = event.getY(1);
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_MOVE:
                    if (doll.selectedLeg == null) {
                        break;
                    }
                    doll.scale(x, y, x2, y2);
                    invalidate();
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    doll.findLeg(x, y, x2, y2);
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    doll.finalizeLeg();
                    break;
            }
        }
        if (ptrCount == 1) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    doll.findPart(x, y);
                    break;
                case MotionEvent.ACTION_UP:
                    doll.finalizePart();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (doll.selectedPart == null) {
                        break;
                    }
                    doll.man(x, y);
                    invalidate();
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        doll.draw(canvas, paint);
        //canvas.drawLine(0, 350, 2300, 350, linePaint);
        //canvas.drawLine(1000, 0, 1000, 1530, linePaint);
    }

    public void setDoll(MyDoll _doll) {
        this.doll = _doll;
        invalidate();
    }

    public MyDoll getDoll() {
        return doll;
    }
}
