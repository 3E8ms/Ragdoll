package net.codebot.ragdoll;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;

public class Part {
    public float x, y;
    public float width, height;
    public Matrix matrix = new Matrix();
    public String name;
    public boolean temp;
    public Matrix tempMatrix = new Matrix();
    public ArrayList<Part> children = new ArrayList<>();
    public float degToParentAxis;
    public float tempDegToParentAxis;
    public Part parent;

    public Part(String _name, float _width, float _height, Part _parent) {
        x = 0;
        y = 0;
        width = _width;
        height = _height;
        name = _name;
        temp = false;
        tempMatrix.set(matrix);
        degToParentAxis = 0;
        tempDegToParentAxis = 0;
        parent = _parent;
        if (parent != null) {
            parent.children.add(this);
        }
    }

    public void translate(float dx, float dy) {
        tempMatrix.set(matrix);
        tempMatrix.postTranslate(dx, dy);
        for (Part p : children) {
            p.translate(dx, dy);
        }
    }

    public void scaleBy(float sx, float sy, float px, float py) {
        tempMatrix.set(matrix);
        Part p = parent;
        float angle = tempDegToParentAxis;
        while (!parent.name.equals("Torso")) {
            angle += parent.tempDegToParentAxis;
            parent = parent.parent;
        }
        parent = p;
        if (Math.sin(angle) == 0) {
            sx = 1;
        } else if (Math.cos(angle) == 0) {
            sy = 1;
        } else {
            sx = Math.abs(sx * (float)Math.sin(angle));
            sy = Math.abs(sy * (float)Math.cos(angle));
        }
        float[] org = {px, py};
        float[] src = new float[2];
        parent.tempMatrix.mapPoints(src, org);
        tempMatrix.postScale(sx, sy, src[0], src[1]);
    }

    public void scaleWithParentBy(float sx, float sy, float px, float py, float x, float y) {
        tempMatrix.set(matrix);
        Part p = parent;
        float angle = tempDegToParentAxis;
        while (!parent.name.equals("Torso")) {
            angle += parent.tempDegToParentAxis;
            parent = parent.parent;
        }
        parent = p;
        if (Math.sin(angle) == 0) {
            sy = 1;
        } else if (Math.cos(angle) == 0) {
            sx = 1;
        } else {
            sx = Math.abs(sx * (float)Math.cos(angle));
            sy = Math.abs(sy * (float)Math.sin(angle));
        }
        float[] org = {px, py};
        float[] src = new float[2]; // transformed by parent's matrix
        float[] dst = new float[2];
        parent.tempMatrix.mapPoints(src, org);
        tempMatrix.postScale(sx, sy, src[0], src[1]);
        org[0] = x;
        org[1] = y;
        tempMatrix.mapPoints(dst, org);
        tempMatrix.postTranslate(src[0]-dst[0], src[1]-dst[1]);
    }

    public void rotateBy(float rd, float x, float y) {
        tempMatrix.set(matrix);
        float[] org = {x, y};
        float[] src = new float[2];
        tempMatrix.mapPoints(src, org);
        tempMatrix.postRotate(rd, src[0], src[1]);
    }

    // x, y from the original point, rd: degree to rotate about (0, 0)
    public void rotateWithParentBy(float rd, float px, float py, float x, float y) {
        tempMatrix.set(matrix);
        float[] org = {px, py};
        float[] src = new float[2]; // transformed by parent's matrix
        float[] dst = new float[2];
        parent.tempMatrix.mapPoints(src, org);
        tempMatrix.postRotate(rd, src[0], org[1]);
        org[0] = x;
        org[1] = y;
        tempMatrix.mapPoints(dst, org);
        tempMatrix.postTranslate(src[0]-dst[0], src[1]-dst[1]);
    }

    public void normDegree() {
        if (tempDegToParentAxis >= 360) {
            tempDegToParentAxis -= 360;
        } else if (tempDegToParentAxis <= -360) {
            tempDegToParentAxis += 360;
        }
    }

    public void finalize() {
        matrix.set(tempMatrix);
        degToParentAxis = tempDegToParentAxis;
        temp = false;
        for (Part p : children) {
            p.finalize();
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        Matrix oldMatrix = canvas.getMatrix();
        if (temp) {
            canvas.setMatrix(tempMatrix);
        } else {
            canvas.setMatrix(matrix);
        }
        if (name.equals("Torso")) {
            canvas.drawRect(x, y, x+width, y+height, paint);
        } else {
            canvas.drawOval(x, y, x + width, y + height, paint);
        }
        canvas.setMatrix(oldMatrix);
    }

    public boolean inPart(float x, float y) {
        Matrix inverse = new Matrix();
        matrix.invert(inverse);
        float[] src = {x, y};
        float[] dst = new float[2];
        inverse.mapPoints(dst, src);
        if (name.equals("Torso")) {
            return ((0 <= dst[0]) && (dst[0] <= width) && (0 <= dst[1]) && (dst[1] <= height));
        } else {
            return (((((dst[0]-width/2)*(dst[0]-width/2))/((width/2)*(width/2)))+(((dst[1]-height/2)*(dst[1]-height/2))/((height/2)*(height/2)))) <= 1);
        }
    }
}
