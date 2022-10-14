package net.codebot.ragdoll;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;

public class MyDoll {
    public Part selectedPart;
    public Part selectedLeg;

    public MyDoll() {
    }

    public void init() {
    }

    public void reset() {
    }

    public void draw(Canvas canvas, Paint paint) {
    }

    public boolean findPart(float x, float y) {
        return false;
    }

    public boolean findLeg(float x1, float y1, float x2, float y2) {
        return false;
    }

    public void man(float x, float y) {
    }

    public void scale(float x1, float y1, float x2, float y2) {
    }

    // vector BA to vector BC
    public float getDegree(float x1, float y1, float x2, float y2, float x3, float y3) {
        double BAx = x1 - x2;
        double BAy = y1 - y2;
        double BCx = x3 - x2;
        double BCy = y3 - y2;
        double angle = Math.atan2(BCy, BCx) - Math.atan2(BAy, BAx);
        if (angle > Math.PI) {
            angle -= 2 * Math.PI;
        } else if (angle <= -Math.PI) {
            angle += 2 * Math.PI;
        }
        return (float)(angle * 180 / Math.PI);
    }

    public float dist(float x1, float y1, float x2, float y2) {
        return (float)Math.sqrt(((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)));
    }

    public void finalizePart() {
        if (selectedPart == null) {
            return;
        }
        selectedPart.finalize();
        selectedPart = null;
    }

    public void finalizeLeg() {
        if (selectedLeg == null) {
            return;
        }
        selectedLeg.finalize();
        selectedLeg = null;
    }
}
