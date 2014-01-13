package com.bleatware.karnage.util;

import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.collision.Ray;

/**
 * Ator
 * User: vasuman
 * Date: 1/5/14
 * Time: 12:40 PM
 */
public class Projection {
    public static Vector2 getPlanarZ(Ray pickRay) {
        float distance = -pickRay.origin.z / pickRay.direction.z;
        Vector3 vector = new Vector3();
        pickRay.getEndPoint(vector, distance);
        return new Vector2(vector.x, vector.y);
    }

    public static Polygon getFrustrumBounds(Frustum frustum) {
        float[] points = new float[8];
        for (int i = 0; i < 4; i++) {
            Vector2 point = getPlanarZ(constructRay(frustum.planePoints[i], frustum.planePoints[i + 4]));
            points[2 * i] = point.x;
            points[2 * i + 1] = point.y;
        }
        return new Polygon(points);
    }

    private static Ray constructRay(Vector3 a, Vector3 b) {
        return new Ray(a, a.cpy().sub(b));
    }

    public static Vector2[] getSeperatingAxis(Polygon a) {
        Vector2[] axis = new Vector2[6];
        axis[0] = new Vector2(1, 0);
        axis[1] = new Vector2(0, 1);
        for (int i = 2; i < 6; i++) {
            axis[i] = getEdge(a, i - 2);
        }
        return axis;
    }

    private static Vector2 getEdge(Polygon a, int i) {
        float[] v = a.getVertices();
        Vector2 point = new Vector2(v[2 * i], v[2 * i + 1]);
        return point.sub(v[2 * ((i + 1) % 4)], v[2 * ((i + 1) % 4) + 1]);
    }

    public static float[] getProjection(Polygon a, Vector2[] axis) {
        float[] v = a.getVertices();
        float[] p = new float[axis.length];
        for (int i = 0; i < axis.length; i++) {
            for (int j = 0; j < 4; j++) {
                float point = axis[i].dot(new Vector2());
            }
        }
        return null;
    }

    public static float[] getProjection(Rectangle a, Vector2[] axis) {
        return null;
    }

    public static boolean getCollision(float[] projA, float[] projB) {
        return true;
    }
}
