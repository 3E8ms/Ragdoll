package net.codebot.ragdoll;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;

public class Tree extends MyDoll {
    private Part torso;
    private ArrayList<Part> parts = new ArrayList<>();
    private ArrayList<Part> legs = new ArrayList<>();
    private float center = 1000;
    private float firstX;
    private float firstY;
    private float dist1;

    public Tree() {
        torso = new Part("Torso", 250, 400, null);

        parts.add(torso);

        init();
    }

    @Override
    public void init() {
        torso.translate(center - torso.width/2, 350);
        torso.finalize();
    }

    @Override
    public void reset() {
        torso.matrix.reset();
        torso.tempMatrix.reset();
        init();
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        for (Part p : parts) {
            p.draw(canvas, paint);
        }
    }

    @Override
    public boolean findPart(float x, float y) {
        for (Part p : parts) {
            if (p.inPart(x, y)) {
                Log.i(String.valueOf(R.string.DEBUG_MVC_ID), String.format("Part found: %s", p.name));
                selectedPart = p;
                firstX = x;
                firstY = y;
                return true;
            }
        }
        Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "Part not found");
        return false;
    }

    @Override
    public boolean findLeg(float x1, float y1, float x2, float y2) {
        for (Part p : legs) {
            if (p.inPart((x1 + x2)/2, (y1 + y2)/2)) {
                Log.i(String.valueOf(R.string.DEBUG_MVC_ID), String.format("Leg found: %s", p.name));
                selectedLeg = p;
                dist1 = dist(x1, y1, x2, y2);
                return true;
            }
        }
        Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "Leg not found");
        return false;
    }

    @Override
    public void man(float x, float y) {
    }

    @Override
    public void scale(float x1, float y1, float x2, float y2) {
    }
}
