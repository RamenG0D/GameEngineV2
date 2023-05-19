package com.demos;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import com.primitives.Vector2;
import com.primitives.Vector3;
import com.primitives.Shapes.Triangle;

public final class Util {
    private Util() {
    }

    public static class Matrix4 {
        float[][] m = new float[4][4];
    }
    public static class returnClip {
        public int numTris;
        public Triangle[] tris;
        public returnClip(int numTris, Triangle[] tris) {
            this.numTris = numTris;
            this.tris = tris;
        }
    }

    public static Vector3 MatrixMultiplyVector(Matrix4 m, Vector3 i) {
        Vector3 v = new Vector3();
        v.x = i.x * m.m[0][0] + i.y * m.m[1][0] + i.z * m.m[2][0] + i.w * m.m[3][0];
        v.y = i.x * m.m[0][1] + i.y * m.m[1][1] + i.z * m.m[2][1] + i.w * m.m[3][1];
        v.z = i.x * m.m[0][2] + i.y * m.m[1][2] + i.z * m.m[2][2] + i.w * m.m[3][2];
        v.w = i.x * m.m[0][3] + i.y * m.m[1][3] + i.z * m.m[2][3] + i.w * m.m[3][3];
        return v;
    }
    public static Matrix4 MatrixMakeIdentity() {
        Matrix4 matrix = new Matrix4();
        matrix.m[0][0] = 1.0f;
        matrix.m[1][1] = 1.0f;
        matrix.m[2][2] = 1.0f;
        matrix.m[3][3] = 1.0f;
        return matrix;
    }
    public static Matrix4 MatrixMakeRotationX(float fAngleRad) {
        Matrix4 matrix = new Matrix4();
        matrix.m[0][0] = 1.0f;
        matrix.m[1][1] = (float) Math.cos(fAngleRad);
        matrix.m[1][2] = (float) Math.sin(fAngleRad);
        matrix.m[2][1] = (float) -Math.sin(fAngleRad);
        matrix.m[2][2] = (float) Math.cos(fAngleRad);
        matrix.m[3][3] = 1.0f;
        return matrix;
    }
    public static Matrix4 MatrixMakeRotationY(float fAngleRad) {
        Matrix4 matrix = new Matrix4();
        matrix.m[0][0] = (float) Math.cos(fAngleRad);
        matrix.m[0][2] = (float) Math.sin(fAngleRad);
        matrix.m[2][0] = (float) -Math.sin(fAngleRad);
        matrix.m[1][1] = 1.0f;
        matrix.m[2][2] = (float) Math.cos(fAngleRad);
        matrix.m[3][3] = 1.0f;
        return matrix;
    }
    public static Matrix4 MatrixMakeRotationZ(float fAngleRad) {
        Matrix4 matrix = new Matrix4();
        matrix.m[0][0] = (float) Math.cos(fAngleRad);
        matrix.m[0][1] = (float) Math.sin(fAngleRad);
        matrix.m[1][0] = (float) -Math.sin(fAngleRad);
        matrix.m[1][1] = (float) Math.cos(fAngleRad);
        matrix.m[2][2] = 1.0f;
        matrix.m[3][3] = 1.0f;
        return matrix;
    }
    public static Matrix4 MatrixMakeTranslation(float x, float y, float z) {
        Matrix4 matrix = new Matrix4();
        matrix.m[0][0] = 1.0f;
        matrix.m[1][1] = 1.0f;
        matrix.m[2][2] = 1.0f;
        matrix.m[3][3] = 1.0f;
        matrix.m[3][0] = x;
        matrix.m[3][1] = y;
        matrix.m[3][2] = z;
        return matrix;
    }
    public static Matrix4 MatrixMakeProjection(float fFovDegrees, float fAspectRatio, float fNear, float fFar) {
        float fFovRad = 1.0f / (float) Math.tan(fFovDegrees * 0.5f / 180.0f * 3.14159f);
        Matrix4 matrix = new Matrix4();
        matrix.m[0][0] = fAspectRatio * fFovRad;
        matrix.m[1][1] = fFovRad;
        matrix.m[2][2] = fFar / (fFar - fNear);
        matrix.m[3][2] = (-fFar * fNear) / (fFar - fNear);
        matrix.m[2][3] = 1.0f;
        matrix.m[3][3] = 0.0f;
        return matrix;
    }
    public static Matrix4 MatrixMultiplyMatrix(Matrix4 m1, Matrix4 m2) {
        Matrix4 matrix = new Matrix4();
        for (int c = 0; c < 4; c++) {
            for (int r = 0; r < 4; r++) {
                matrix.m[r][c] = m1.m[r][0] * m2.m[0][c] + m1.m[r][1] * m2.m[1][c] + m1.m[r][2] * m2.m[2][c] + m1.m[r][3] * m2.m[3][c];
            }
        }
        return matrix;
    }
    public static Matrix4 MatrixPointAt(Vector3 pos, Vector3 target, Vector3 up) {
        // Calculate new forward direction
        Vector3 newForward = VectorSub(target, pos);
        newForward = VectorNormalise(newForward);

        // Calculate new up direction
        Vector3 a = VectorMul(newForward, VectorDotProduct(up, newForward));
        Vector3 newUp = VectorSub(up, a);
        newUp = VectorNormalise(newUp);

        // New Right direction is easy, its just cross product
        Vector3 newRight = VectorCrossProduct(newUp, newForward);

        // Construct Dimensioning and Translation Matrix
        Matrix4 matrix = new Matrix4();
        matrix.m[0][0] = newRight.x;	matrix.m[0][1] = newRight.y;	matrix.m[0][2] = newRight.z;	matrix.m[0][3] = 0.0f;
        matrix.m[1][0] = newUp.x;		matrix.m[1][1] = newUp.y;		matrix.m[1][2] = newUp.z;		matrix.m[1][3] = 0.0f;
        matrix.m[2][0] = newForward.x;	matrix.m[2][1] = newForward.y;	matrix.m[2][2] = newForward.z;	matrix.m[2][3] = 0.0f;
        matrix.m[3][0] = pos.x;			matrix.m[3][1] = pos.y;			matrix.m[3][2] = pos.z;			matrix.m[3][3] = 1.0f;
        return matrix;
    }
    public static Matrix4 MatrixQuickInverse(Matrix4 m) { // Only for Rotation/Translation Matrices
        Matrix4 matrix = new Matrix4();
        matrix.m[0][0] = m.m[0][0]; matrix.m[0][1] = m.m[1][0]; matrix.m[0][2] = m.m[2][0]; matrix.m[0][3] = 0.0f;
        matrix.m[1][0] = m.m[0][1]; matrix.m[1][1] = m.m[1][1]; matrix.m[1][2] = m.m[2][1]; matrix.m[1][3] = 0.0f;
        matrix.m[2][0] = m.m[0][2]; matrix.m[2][1] = m.m[1][2]; matrix.m[2][2] = m.m[2][2]; matrix.m[2][3] = 0.0f;
        matrix.m[3][0] = -(m.m[3][0] * matrix.m[0][0] + m.m[3][1] * matrix.m[1][0] + m.m[3][2] * matrix.m[2][0]);
        matrix.m[3][1] = -(m.m[3][0] * matrix.m[0][1] + m.m[3][1] * matrix.m[1][1] + m.m[3][2] * matrix.m[2][1]);
        matrix.m[3][2] = -(m.m[3][0] * matrix.m[0][2] + m.m[3][1] * matrix.m[1][2] + m.m[3][2] * matrix.m[2][2]);
        matrix.m[3][3] = 1.0f;
        return matrix;
    }

    public static Vector3 VectorAdd(Vector3 v1, Vector3 v2) {
        return new Vector3(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
    }
    public static Vector3 VectorSub(Vector3 v1, Vector3 v2) {
        return new Vector3(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
    }
    public static Vector3 VectorMul(Vector3 v1, float k) {
        return new Vector3(v1.x * k, v1.y * k, v1.z * k);
    }
    public static Vector3 VectorDiv(Vector3 v1, float k) {
        return new Vector3(v1.x / k, v1.y / k, v1.z / k );
    }
    public static float VectorDotProduct(Vector3 v1, Vector3 v2) {
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    }
    public static float VectorLength(Vector3 v) {
        return (float) Math.sqrt(VectorDotProduct(v, v));
    }
    public static Vector3 VectorNormalise(Vector3 v) {
        float l = VectorLength(v);
        return new Vector3(v.x / l, v.y / l, v.z / l);
    }
    public static Vector3 VectorCrossProduct(Vector3 v1, Vector3 v2) {
        Vector3 v = new Vector3();
        v.x = v1.y * v2.z - v1.z * v2.y;
        v.y = v1.z * v2.x - v1.x * v2.z;
        v.z = v1.x * v2.y - v1.y * v2.x;
        return v;
    }
    public static Vector3 VectorIntersectPlane(Vector3 planeP, Vector3 planeN, Vector3 lineStart, Vector3 lineEnd, float[] t) {
        planeN = VectorNormalise(planeN);
        float planeD = -VectorDotProduct(planeN, planeP);
        float ad = VectorDotProduct(lineStart, planeN);
        float bd = VectorDotProduct(lineEnd, planeN);
        t[0] = (-planeD - ad) / (bd - ad);
        Vector3 lineStartToEnd = VectorSub(lineEnd, lineStart);
        Vector3 lineToIntersect = VectorMul(lineStartToEnd, t[0]);
        return VectorAdd(lineStart, lineToIntersect);
    }

    interface Dist {
        float dist(Vector3 p);
    }
    public static returnClip TriangleClipAgainstPlane(Vector3 planeP, Vector3 planeN, Triangle inTri) {
        Triangle outTri1 = new Triangle();
        Triangle outTri2 = new Triangle();

        // Make sure plane normal is indeed normal
        planeN = VectorNormalise(planeN);

        // Return signed shortest distance from point to plane, place normal must be normalised
        Vector3 finalPlaneN = planeN;
        Dist d = (Vector3 p) -> {
            // Vector3 n = VectorNormalise(p);
            return finalPlaneN.x * p.x + finalPlaneN.y * p.y + finalPlaneN.z * p.z - VectorDotProduct(finalPlaneN, planeP);
        };

        // Create two temporary storage arrays to classify points either side of plane
        // If distance sign is positive, point lies on "inside" of plane
        Vector3[] insidePoints  = new Vector3[3]; int nInsidePointCount  = 0;
        Vector3[] outsidePoints = new Vector3[3]; int nOutsidePointCount = 0;
        Vector2[] insideTex  = new Vector2[3];
        Vector2[] outsideTex = new Vector2[3];

        // Get signed distance of each point in Triangle to plane
        float d0 = d.dist(inTri.p[0]);
        float d1 = d.dist(inTri.p[1]);
        float d2 = d.dist(inTri.p[2]);

        if (d0 >= 0) {
            insidePoints[nInsidePointCount] = inTri.p[0]; insideTex[nInsidePointCount++] = inTri.t[0];
        }
        else {
            outsidePoints[nOutsidePointCount] = inTri.p[0]; outsideTex[nOutsidePointCount++] = inTri.t[0];
        }
        if (d1 >= 0) {
            insidePoints[nInsidePointCount] = inTri.p[1]; insideTex[nInsidePointCount++] = inTri.t[1];
        }
        else {
            outsidePoints[nOutsidePointCount] = inTri.p[1]; outsideTex[nOutsidePointCount++] = inTri.t[1];
        }
        if (d2 >= 0) {
            insidePoints[nInsidePointCount] = inTri.p[2]; insideTex[nInsidePointCount++] = inTri.t[2];
        }
        else {
            outsidePoints[nOutsidePointCount] = inTri.p[2]; outsideTex[nOutsidePointCount++] = inTri.t[2];
        }

        // Now classify Triangle points, and break the input Triangle into
        // smaller output Triangles if required. There are four possible
        // outcomes...

        if (nInsidePointCount == 0) {
            // All points lie on the outside of plane, so clip whole Triangle
            // It ceases to exist

            return new returnClip(0, new Triangle[]{null, null}); // No returned Triangles are valid

        } else if (nInsidePointCount == 3) {
            // All points lie on the inside of plane, so do nothing
            // and allow the Triangle to simply pass through
            outTri1 = inTri;

            return new returnClip(1, new Triangle[]{outTri1.clone(), null}); // Just the one returned original Triangle is valid

        } else if (nOutsidePointCount == 2) {
            // Triangle should be clipped. As two points lie outside
            // the plane, the Triangle simply becomes a smaller Triangle

            // Copy appearance info to new Triangle
            outTri1.col = inTri.col;
//            outTri1.col = Color.BLUE;

            // The inside point is valid, so keep that...
            outTri1.p[0] = insidePoints[0];
            outTri1.t[0] = insideTex[0];

            // but the two new points are at the locations where the
            // original sides of the Triangle (lines) intersect with the plane
            float[] t = {0};
            outTri1.p[1] = VectorIntersectPlane(planeP, planeN, insidePoints[0], outsidePoints[0], t);
            outTri1.t[1].u = t[0] * (outsideTex[0].u - insideTex[0].u) + insideTex[0].u;
            outTri1.t[1].v = t[0] * (outsideTex[0].v - insideTex[0].v) + insideTex[0].v;

            outTri1.p[2] = VectorIntersectPlane(planeP, planeN, insidePoints[0], outsidePoints[1], t);
            outTri1.t[2].u = t[0] * (outsideTex[1].u - insideTex[0].u) + insideTex[0].u;
            outTri1.t[2].v = t[0] * (outsideTex[1].v - insideTex[0].v) + insideTex[0].v;

            return new returnClip(1, new Triangle[]{outTri1, null}); // Return the newly formed single Triangle

        } else {
            // Triangle should be clipped. As two points lie inside the plane,
            // the clipped Triangle becomes a "quad". Fortunately, we can
            // represent a quad with two new Triangles

            // Copy appearance info to new Triangles
            outTri1.col =  inTri.col;
            outTri2.col =  inTri.col;
//            outTri1.col = Color.GREEN;
//            outTri2.col = Color.RED;

            // The first Triangle consists of the two inside points and a new
            // point determined by the location where one side of the Triangle
            // intersects with the plane
            outTri1.p[0] = insidePoints[0];
            outTri1.p[1] = insidePoints[1];
            outTri1.t[0] = insideTex[0];
            outTri1.t[1] = insideTex[1];

            float[] t = {0};
            outTri1.p[2] = VectorIntersectPlane(planeP, planeN, insidePoints[0], outsidePoints[0], t);
            outTri1.t[2].u = t[0] * (outsideTex[0].u - insideTex[0].u) + insideTex[0].u;
            outTri1.t[2].v = t[0] * (outsideTex[0].v - insideTex[0].v) + insideTex[0].v;

            // The second Triangle is composed of one of the inside points, a
            // new point determined by the intersection of the other side of the
            // Triangle and the plane, and the newly created point above
            outTri2.p[0] = insidePoints[1];
            outTri2.t[0] = insideTex[1];
            outTri2.p[1] = outTri1.p[2];
            outTri2.t[1] = outTri1.t[2];
            outTri2.p[2] = VectorIntersectPlane(planeP, planeN, insidePoints[1], outsidePoints[0], t);
            outTri2.t[2].u = t[0] * (outsideTex[0].u - insideTex[1].u) + insideTex[1].u;
            outTri2.t[2].v = t[0] * (outsideTex[0].v - insideTex[1].v) + insideTex[1].v;

            return new returnClip(2, new Triangle[]{outTri1, outTri2}); // Return two newly formed triangles which form a quad
        }
    }

    public static Color getColor(float lum){
        int col = (int)Math.abs(lum * 255);
        return new Color(col, col, col);
    }

    public static void TexturedTriangle(ArrayList<Integer> x, ArrayList<Integer> y, ArrayList<Float> u, ArrayList<Float> v, ArrayList<Float> w, Graphics g, BufferedImage tex) {

        if (y.get(1) < y.get(0)) {
            Collections.swap(y, 0, 1);
            Collections.swap(x, 0, 1);
            Collections.swap(u, 0, 1);
            Collections.swap(v, 0, 1);
            Collections.swap(w, 0, 1);
        }
        if (y.get(2) < y.get(0)) {
            Collections.swap(y, 0, 2);
            Collections.swap(x, 0, 2);
            Collections.swap(u, 0, 2);
            Collections.swap(v, 0, 2);
            Collections.swap(w, 0, 2);
        }
        if (y.get(2) < y.get(1)) {
            Collections.swap(y, 1, 2);
            Collections.swap(x, 1, 2);
            Collections.swap(u, 1, 2);
            Collections.swap(v, 1, 2);
            Collections.swap(w, 1, 2);
        }


        int dy1 = y.get(1) - y.get(0);
        int dx1 = x.get(1) - x.get(0);
        float dv1 = v.get(1) - v.get(0);
        float du1 = u.get(1) - u.get(0);
        float dw1 = w.get(1) - w.get(0);

        int dy2 = y.get(2) - y.get(0);
        int dx2 = x.get(2) - x.get(0);
        float dv2 = v.get(2) - v.get(0);
        float du2 = u.get(2) - u.get(0);
        float dw2 = w.get(2) - w.get(0);

        float texU, texV, texW;

        float daxStep = 0, dbxStep = 0,
                du1Step = 0, dv1Step = 0,
                du2Step = 0, dv2Step = 0,
                dw1Step = 0, dw2Step = 0;

        if (dy1 != 0) daxStep = dx1 / (float)Math.abs(dy1);
        if (dy2 != 0) dbxStep = dx2 / (float)Math.abs(dy2);

        if (dy1 != 0) du1Step = du1 / (float)Math.abs(dy1);
        if (dy1 != 0) dv1Step = dv1 / (float)Math.abs(dy1);
        if (dy1 != 0) dw1Step = dw1 / (float)Math.abs(dy1);

        if (dy2 != 0) du2Step = du2 / (float)Math.abs(dy2);
        if (dy2 != 0) dv2Step = dv2 / (float)Math.abs(dy2);
        if (dy2 != 0) dw2Step = dw2 / (float)Math.abs(dy2);

        if (dy1 != 0) {
            for (int i = y.get(0); i <= y.get(1); i++) {

                int ax = (int) (x.get(0) + (i - y.get(0)) * daxStep);
                int bx = (int) (x.get(0) + (i - y.get(0)) * dbxStep);

                float texSu = u.get(0) + (i - y.get(0)) * du1Step;
                float texSv = v.get(0) + (i - y.get(0)) * dv1Step;
                float texSw = w.get(0) + (i - y.get(0)) * dw1Step;

                float texEu = u.get(0) + (i - y.get(0)) * du2Step;
                float texEv = v.get(0) + (i - y.get(0)) * dv2Step;
                float texEw = w.get(0) + (i - y.get(0)) * dw2Step;

                if (ax > bx) {
                    int temp1 = ax; ax = bx; bx = temp1;
                    float temp2 = texSu; texSu = texEu; texEu = temp2;
                    float temp3 = texSv; texSv = texEv; texEv = temp3;
                    float temp4 = texSw; texSw = texEw; texEw = temp4;
                }

                float tStep = 1.0f / ((float) (bx - ax));
                float t = 0.0f;

                for (int j = ax; j < bx; j++) {
                    texU = (1.0f - t) * texSu + t * texEu;
                    texV = (1.0f - t) * texSv + t * texEv;
                    texW = (1.0f - t) * texSw + t * texEw;
                    int texX = (tex.getWidth() - 1) - (int) ((texU / texW) * (tex.getWidth() - 1));
                    int texY = (int) ((texV / texW) * (tex.getHeight() - 1));
                    int color = 0;
                    try{color = tex.getRGB(texX, texY);}catch(Exception e){};
                    //g.setColor(new Color((color & 0xff0000) >> 16, (color & 0xff00) >> 8, color & 0xff));
                    g.setColor(new Color(color));
                    g.drawLine(j, i, j, i);

                    t += tStep;
                }
            }

        }

        dy1 = y.get(2) - y.get(1);
        dx1 = x.get(2) - x.get(1);
        dv1 = v.get(2) - v.get(1);
        du1 = u.get(2) - u.get(1);
        dw1 = w.get(2) - w.get(1);

        if (dy1 != 0) daxStep = dx1 / (float)Math.abs(dy1);
        if (dy2 != 0) dbxStep = dx2 / (float)Math.abs(dy2);

        du1Step = 0; dv1Step = 0;
        if (dy1 != 0) du1Step = du1 / (float)Math.abs(dy1);
        if (dy1 != 0) dv1Step = dv1 / (float)Math.abs(dy1);
        if (dy1 != 0) dw1Step = dw1 / (float)Math.abs(dy1);

        if (dy1 != 0) {
            for (int i = y.get(1); i <= y.get(2); i++) {
                int ax = (int) (x.get(1) + (i - y.get(1)) * daxStep);
                int bx = (int) (x.get(0) + (i - y.get(0)) * dbxStep);

                float texSu = u.get(1) + (i - y.get(1)) * du1Step;
                float texSv = v.get(1) + (i - y.get(1)) * dv1Step;
                float texSw = w.get(1) + (i - y.get(1)) * dw1Step;

                float texEu = u.get(0) + (i - y.get(0)) * du2Step;
                float texEv = v.get(0) + (i - y.get(0)) * dv2Step;
                float texEw = w.get(0) + (i - y.get(0)) * dw2Step;

                if (ax > bx) {
                    int temp1 = ax; ax = bx; bx = temp1;
                    float temp2 = texSu; texSu = texEu; texEu = temp2;
                    float temp3 = texSv; texSv = texEv; texEv = temp3;
                    float temp4 = texSw; texSw = texEw; texEw = temp4;
                }

                float tStep = 1.0f / ((float)(bx - ax));
                float t = 0.0f;

                for (int j = ax; j < bx; j++) {
                    texU = (1.0f - t) * texSu + t * texEu;
                    texV = (1.0f - t) * texSv + t * texEv;
                    texW = (1.0f - t) * texSw + t * texEw;
                    int texX = (tex.getWidth() - 1) - (int) ((texU / texW) * (tex.getWidth() - 1));
                    int texY = (int) ((texV / texW) * (tex.getHeight() - 1));
                    int color = 0;
                    try{color = tex.getRGB(texX, texY);}catch(Exception e){}
                    //g.setColor(new Color((color & 0xff0000) >> 16, (color & 0xff00) >> 8, color & 0xff));
                    g.setColor(new Color(color));
                    g.drawLine(j, i, j, i);

                    t += tStep;
                }
            }
        }
    }
}
