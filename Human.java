package net.codebot.ragdoll;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;

public class Human extends MyDoll {
    private Part torso;
    private Part head;
    private Part rUpperArm;
    private Part rLowerArm;
    private Part rHand;
    private Part lUpperArm;
    private Part lLowerArm;
    private Part lHand;
    private Part rUpperLeg;
    private Part rLowerLeg;
    private Part rFoot;
    private Part lUpperLeg;
    private Part lLowerLeg;
    private Part lFoot;
    private ArrayList<Part> parts = new ArrayList<>();
    private ArrayList<Part> legs = new ArrayList<>();
    private float center = 1000;
    private float firstX;
    private float firstY;
    private float dist1;

    public Human() {
        torso = new Part("Torso", 250, 400, null);
        head = new Part("Head", 140, 200, torso);
        rUpperArm = new Part("RUpperArm", 80, 250, torso);
        rLowerArm = new Part("RLowerArm", 80, 200, rUpperArm);
        rHand = new Part("RHand", 100, 80, rLowerArm);
        lUpperArm = new Part("LUpperArm", 80, 250, torso);
        lLowerArm = new Part("LLowerArm", 80, 200, lUpperArm);
        lHand = new Part("LHand", 100, 80, lLowerArm);
        rUpperLeg = new Part("RUpperLeg", 80, 250, torso);
        rLowerLeg = new Part("RLowerLeg", 80, 250, rUpperLeg);
        rFoot = new Part("RFoot", 150, 80, rLowerLeg);
        lUpperLeg = new Part("LUpperLeg", 80, 250, torso);
        lLowerLeg = new Part("LLowerLeg", 80, 250, lUpperLeg);
        lFoot = new Part("LFoot", 150, 80, lLowerLeg);

        // add from top layer to bottom layer
        parts.add(lFoot);
        parts.add(lLowerLeg);
        parts.add(lUpperLeg);
        parts.add(rFoot);
        parts.add(rLowerLeg);
        parts.add(rUpperLeg);
        parts.add(lHand);
        parts.add(lLowerArm);
        parts.add(lUpperArm);
        parts.add(rHand);
        parts.add(rLowerArm);
        parts.add(rUpperArm);
        parts.add(head);
        parts.add(torso);

        legs.add(lLowerLeg);
        legs.add(lUpperLeg);
        legs.add(rLowerLeg);
        legs.add(rUpperLeg);
        init();
    }

    @Override
    public void init() {
        torso.translate(center - torso.width/2, 350);
        torso.finalize();

        head.tempMatrix.set(torso.matrix);
        head.finalize();
        head.translate((torso.width-head.width)/2, -head.height);
        head.finalize();

        rUpperArm.tempMatrix.set(torso.matrix);
        rUpperArm.finalize();
        rUpperArm.translate(torso.width-rUpperArm.width/2, 0);
        rUpperArm.finalize();

        rLowerArm.tempMatrix.set(rUpperArm.matrix);
        rLowerArm.finalize();
        rLowerArm.translate((rLowerArm.width-rUpperArm.width)/2, rUpperArm.height);
        rLowerArm.finalize();

        rHand.tempMatrix.set(rLowerArm.matrix);
        rHand.finalize();
        rHand.translate((rHand.width-rLowerArm.width)/2, rLowerArm.height);
        rHand.finalize();

        lUpperArm.tempMatrix.set(torso.matrix);
        lUpperArm.finalize();
        lUpperArm.translate(-lUpperArm.width/2, 0);
        lUpperArm.finalize();

        lLowerArm.tempMatrix.set(lUpperArm.matrix);
        lLowerArm.finalize();
        lLowerArm.translate((-lLowerArm.width+lUpperArm.width)/2, lUpperArm.height);
        lLowerArm.finalize();

        lHand.tempMatrix.set(lLowerArm.matrix);
        lHand.finalize();
        lHand.translate((-lHand.width+lLowerArm.width)/2, lLowerArm.height);
        lHand.finalize();

        rUpperLeg.tempMatrix.set(torso.matrix);
        rUpperLeg.finalize();
        rUpperLeg.translate(torso.width-rUpperLeg.width, torso.height);
        rUpperLeg.finalize();

        rLowerLeg.tempMatrix.set(rUpperLeg.matrix);
        rLowerLeg.finalize();
        rLowerLeg.translate((rLowerLeg.width-rUpperLeg.width)/2, rUpperLeg.height);
        rLowerLeg.finalize();

        rFoot.tempMatrix.set(rLowerLeg.matrix);
        rFoot.finalize();
        rFoot.translate((rLowerLeg.width)/2, rLowerLeg.height-rFoot.height/2);
        rFoot.finalize();

        lUpperLeg.tempMatrix.set(torso.matrix);
        lUpperLeg.finalize();
        lUpperLeg.translate(0, torso.height);
        lUpperLeg.finalize();

        lLowerLeg.tempMatrix.set(lUpperLeg.matrix);
        lLowerLeg.finalize();
        lLowerLeg.translate((-lLowerLeg.width+lUpperLeg.width)/2, lUpperLeg.height);
        lLowerLeg.finalize();

        lFoot.tempMatrix.set(lLowerLeg.matrix);
        lFoot.finalize();
        lFoot.translate(-lFoot.width+lLowerLeg.width/2, lLowerLeg.height-lFoot.height/2);
        lFoot.finalize();
    }

    @Override
    public void reset() {
        for (Part p : parts) {
            p.matrix.reset();
            p.tempMatrix.reset();
        }
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
        Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "Manipulate Human");
        float[] src = new float[2];
        float[] dst = new float[2];
        float degToRotate;
        switch(selectedPart.name) {
            case "Torso":
                torso.temp = true;
                for (Part p : parts) {
                    p.translate(x-firstX, y-firstY);
                    p.temp = true;
                }
                break;
            case "Head":
                head.temp = true;
                // A=(firstX, firstY)
                // B=(head.width/2, head.height)transformed by head.matrix
                // C=(x, y)
                src[0] = head.width/2;
                src[1] = head.height;
                head.matrix.mapPoints(dst, src);
                degToRotate = getDegree(firstX, firstY, dst[0], dst[1], x, y);
                if (Math.abs(head.degToParentAxis + degToRotate) > 50) {
                    break;
                }
                head.tempDegToParentAxis = head.degToParentAxis + degToRotate;
                head.rotateBy(degToRotate, head.width/2, head.height);
                break;
            case "RUpperArm":
                rUpperArm.temp = true;
                rLowerArm.temp = true;
                rHand.temp = true;
                // A=(firstX, firstY)
                // B=(rUpperArm.width/2, 0)transformed by rUpperArm.matrix
                // C=(x, y)
                src[0] = rUpperArm.width/2;
                src[1] = 0;
                rUpperArm.matrix.mapPoints(dst, src);
                degToRotate = getDegree(firstX, firstY, dst[0], dst[1], x, y);
                rUpperArm.tempDegToParentAxis = rUpperArm.degToParentAxis + degToRotate;
                rUpperArm.normDegree();
                rUpperArm.rotateBy(degToRotate, rUpperArm.width/2, 0);
                rLowerArm.rotateWithParentBy(degToRotate, rUpperArm.width/2, rUpperArm.height, rLowerArm.width/2, 0);
                rHand.rotateWithParentBy(degToRotate, rLowerArm.width/2, rLowerArm.height, rHand.width/2, 0);
                break;
            case "RLowerArm":
                rLowerArm.temp = true;
                rHand.temp = true;
                // A=(firstX, firstY)
                // B=(rLowerArm.width/2, 0)transformed by rLowerArm.matrix
                // C=(x, y)
                src[0] = rLowerArm.width/2;
                src[1] = 0;
                rLowerArm.matrix.mapPoints(dst, src);
                degToRotate = getDegree(firstX, firstY, dst[0], dst[1], x, y);
                if (Math.abs(rLowerArm.degToParentAxis + degToRotate) > 135) {
                    break;
                }
                rLowerArm.tempDegToParentAxis = rLowerArm.degToParentAxis + degToRotate;
                rLowerArm.rotateBy(degToRotate, rLowerArm.width/2, 0);
                rHand.rotateWithParentBy(degToRotate, rLowerArm.width/2, rLowerArm.height, rHand.width/2, 0);
                break;
            case "RHand":
                rHand.temp = true;
                // A=(firstX, firstY)
                // B=(rHand.width/2, 0)transformed by rHand.matrix
                // C=(x, y)
                src[0] = rHand.width/2;
                src[1] = 0;
                rHand.matrix.mapPoints(dst, src);
                degToRotate = getDegree(firstX, firstY, dst[0], dst[1], x, y);
                if (Math.abs(rHand.degToParentAxis + degToRotate) > 35) {
                    break;
                }
                rHand.tempDegToParentAxis = rHand.degToParentAxis + degToRotate;
                rHand.rotateBy(degToRotate, rHand.width/2, 0);
                break;
            case "LUpperArm":
                lUpperArm.temp = true;
                lLowerArm.temp = true;
                lHand.temp = true;
                // A=(firstX, firstY)
                // B=(lUpperArm.width/2, 0)transformed by lUpperArm.matrix
                // C=(x, y)
                src[0] = lUpperArm.width/2;
                src[1] = 0;
                lUpperArm.matrix.mapPoints(dst, src);
                degToRotate = getDegree(firstX, firstY, dst[0], dst[1], x, y);
                lUpperArm.tempDegToParentAxis = lUpperArm.degToParentAxis + degToRotate;
                lUpperArm.normDegree();
                lUpperArm.rotateBy(degToRotate, lUpperArm.width/2, 0);
                lLowerArm.rotateWithParentBy(degToRotate, lUpperArm.width/2, lUpperArm.height, lLowerArm.width/2, 0);
                lHand.rotateWithParentBy(degToRotate, lLowerArm.width/2, lLowerArm.height, lHand.width/2, 0);
                break;
            case "LLowerArm":
                lLowerArm.temp = true;
                lHand.temp = true;
                // A=(firstX, firstY)
                // B=(lLowerArm.width/2, 0)transformed by lLowerArm.matrix
                // C=(x, y)
                src[0] = rLowerArm.width/2;
                src[1] = 0;
                lLowerArm.matrix.mapPoints(dst, src);
                degToRotate = getDegree(firstX, firstY, dst[0], dst[1], x, y);
                if (Math.abs(lLowerArm.degToParentAxis + degToRotate) > 135) {
                    break;
                }
                lLowerArm.tempDegToParentAxis = lLowerArm.degToParentAxis + degToRotate;
                lLowerArm.rotateBy(degToRotate, lLowerArm.width/2, 0);
                lHand.rotateWithParentBy(degToRotate, lLowerArm.width/2, lLowerArm.height, lHand.width/2, 0);
                break;
            case "LHand":
                lHand.temp = true;
                // A=(firstX, firstY)
                // B=(lHand.width/2, 0)transformed by lHand.matrix
                // C=(x, y)
                src[0] = lHand.width/2;
                src[1] = 0;
                lHand.matrix.mapPoints(dst, src);
                degToRotate = getDegree(firstX, firstY, dst[0], dst[1], x, y);
                if (Math.abs(lHand.degToParentAxis + degToRotate) > 35) {
                    break;
                }
                lHand.tempDegToParentAxis = lHand.degToParentAxis + degToRotate;
                lHand.rotateBy(degToRotate, lHand.width/2, 0);
                break;
            case "RUpperLeg":
                rUpperLeg.temp = true;
                rLowerLeg.temp = true;
                rFoot.temp = true;
                // A=(firstX, firstY)
                // B=(rUpperLeg.width/2, 0)transformed by rUpperLeg.matrix
                // C=(x, y)
                src[0] = rUpperLeg.width/2;
                src[1] = 0;
                rUpperLeg.matrix.mapPoints(dst, src);
                degToRotate = getDegree(firstX, firstY, dst[0], dst[1], x, y);
                if (Math.abs(rUpperLeg.degToParentAxis + degToRotate) > 90) {
                    break;
                }
                rUpperLeg.tempDegToParentAxis = rUpperLeg.degToParentAxis + degToRotate;
                rUpperLeg.rotateBy(degToRotate, rUpperLeg.width/2, 0);
                rLowerLeg.rotateWithParentBy(degToRotate, rUpperLeg.width/2, rUpperLeg.height, rLowerLeg.width/2, 0);
                rFoot.rotateWithParentBy(degToRotate, rLowerLeg.width/2, rLowerLeg.height, 0, rFoot.height/2);
                break;
            case "RLowerLeg":
                rLowerLeg.temp = true;
                rFoot.temp = true;
                // A=(firstX, firstY)
                // B=(rLowerLeg.width/2, 0)transformed by rLowerLeg.matrix
                // C=(x, y)
                src[0] = rLowerLeg.width/2;
                src[1] = 0;
                rLowerLeg.matrix.mapPoints(dst, src);
                degToRotate = getDegree(firstX, firstY, dst[0], dst[1], x, y);
                if (Math.abs(rLowerLeg.degToParentAxis + degToRotate) > 90) {
                    break;
                }
                rLowerLeg.tempDegToParentAxis = rLowerLeg.degToParentAxis + degToRotate;
                rLowerLeg.rotateBy(degToRotate, rLowerLeg.width/2, 0);
                rFoot.rotateWithParentBy(degToRotate, rLowerLeg.width/2, rLowerLeg.height, 0, rFoot.height/2);
                break;
            case "RFoot":
                rFoot.temp = true;
                // A=(firstX, firstY)
                // B=(0, rFoot.height/2)transformed by rFoot.matrix
                // C=(x, y)
                src[0] = 0;
                src[1] = rFoot.height/2;
                rFoot.matrix.mapPoints(dst, src);
                degToRotate = getDegree(firstX, firstY, dst[0], dst[1], x, y);
                if (Math.abs(rFoot.degToParentAxis + degToRotate) > 35) {
                    break;
                }
                rFoot.tempDegToParentAxis = rFoot.degToParentAxis + degToRotate;
                rFoot.rotateBy(degToRotate, 0, rFoot.height/2);
                break;
            case "LUpperLeg":
                lUpperLeg.temp = true;
                lLowerLeg.temp = true;
                lFoot.temp = true;
                // A=(firstX, firstY)
                // B=(lUpperLeg.width/2, 0)transformed by lUpperLeg.matrix
                // C=(x, y)
                src[0] = lUpperLeg.width/2;
                src[1] = 0;
                lUpperLeg.matrix.mapPoints(dst, src);
                degToRotate = getDegree(firstX, firstY, dst[0], dst[1], x, y);
                if (Math.abs(lUpperLeg.degToParentAxis + degToRotate) > 90) {
                    break;
                }
                lUpperLeg.tempDegToParentAxis = lUpperLeg.degToParentAxis + degToRotate;
                lUpperLeg.rotateBy(degToRotate, lUpperLeg.width/2, 0);
                lLowerLeg.rotateWithParentBy(degToRotate, lUpperLeg.width/2, lUpperLeg.height, lLowerLeg.width/2, 0);
                lFoot.rotateWithParentBy(degToRotate, lLowerLeg.width/2, lLowerLeg.height, lFoot.width, lFoot.height/2);
                break;
            case "LLowerLeg":
                lLowerLeg.temp = true;
                lFoot.temp = true;
                // A=(firstX, firstY)
                // B=(lLowerLeg.width/2, 0)transformed by lLowerLeg.matrix
                // C=(x, y)
                src[0] = lLowerLeg.width/2;
                src[1] = 0;
                lLowerLeg.matrix.mapPoints(dst, src);
                degToRotate = getDegree(firstX, firstY, dst[0], dst[1], x, y);
                if (Math.abs(lLowerLeg.degToParentAxis + degToRotate) > 90) {
                    break;
                }
                lLowerLeg.tempDegToParentAxis = lLowerLeg.degToParentAxis + degToRotate;
                lLowerLeg.rotateBy(degToRotate, lLowerLeg.width/2, 0);
                lFoot.rotateWithParentBy(degToRotate,lLowerLeg.width/2, lLowerLeg.height, lFoot.width, lFoot.height/2);
                break;
            case "LFoot":
                lFoot.temp = true;
                // A=(firstX, firstY)
                // B=(lFoot.width, lFoot.height/2)transformed by lFoot.matrix
                // C=(x, y)
                src[0] = lFoot.width;
                src[1] = lFoot.height/2;
                lFoot.matrix.mapPoints(dst, src);
                degToRotate = getDegree(firstX, firstY, dst[0], dst[1], x, y);
                if (Math.abs(lFoot.degToParentAxis + degToRotate) > 35) {
                    break;
                }
                lFoot.tempDegToParentAxis = lFoot.degToParentAxis + degToRotate;
                lFoot.rotateBy(degToRotate, lFoot.width, lFoot.height/2);
                break;
        }
    }

    @Override
    public void scale(float x1, float y1, float x2, float y2) {
        float dist2 = dist(x1, y1, x2, y2);
        switch(selectedLeg.name) {
            case "RUpperLeg":
                rUpperLeg.temp = true;
                rLowerLeg.temp = true;
                rFoot.temp = true;
                rUpperLeg.scaleBy(dist2/dist1, dist2/dist1, torso.width-rUpperLeg.width, torso.height);
                rLowerLeg.scaleWithParentBy(dist2/dist1, dist2/dist1, rUpperLeg.width/2, rUpperLeg.height, rLowerLeg.width/2, 0);
                rFoot.rotateWithParentBy(0, rLowerLeg.width/2, rLowerLeg.height, 0, rFoot.height/2);
                break;
            case "RLowerLeg":
                rLowerLeg.temp = true;
                rFoot.temp = true;
                rLowerLeg.scaleBy(dist2/dist1, dist2/dist1, lUpperLeg.width/2, lUpperLeg.height);
                rFoot.rotateWithParentBy(0, rLowerLeg.width/2, rLowerLeg.height, 0, rFoot.height/2);
                break;
            case "LUpperLeg":
                lUpperLeg.temp = true;
                lLowerLeg.temp = true;
                lFoot.temp = true;
                lUpperLeg.scaleBy(dist2/dist1, dist2/dist1, 0, torso.height);
                lLowerLeg.scaleWithParentBy(1, dist2/dist1, lUpperLeg.width/2, lUpperLeg.height, lLowerLeg.width/2, 0);
                lFoot.rotateWithParentBy(0,lLowerLeg.width/2, lLowerLeg.height, lFoot.width, lFoot.height/2);
                break;
            case "LLowerLeg":
                lLowerLeg.temp = true;
                lFoot.temp = true;
                lLowerLeg.scaleBy(dist2/dist1, dist2/dist1, lUpperLeg.width/2, lUpperLeg.height);
                lFoot.rotateWithParentBy(0,lLowerLeg.width/2, lLowerLeg.height, lFoot.width, lFoot.height/2);
                break;
        }
    }
}
