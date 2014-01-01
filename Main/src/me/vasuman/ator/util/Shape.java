package me.vasuman.ator.util;

import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Ator
 * User: vasuman
 * Date: 12/8/13
 * Time: 10:15 PM
 */
public interface Shape {
    public boolean within(float x, float y);

    public float getX();

    public float getY();

    public Vector2 getRandomPoint(Random r);

    public static class Rectangle implements Shape {
        private float x, y, w, h;

        public Rectangle(float x, float y, float w, float h) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }

        @Override
        public float getX() {
            return x;
        }

        @Override
        public float getY() {
            return y;
        }

        @Override
        public boolean within(float x, float y) {
            return (2 * Math.abs(this.x - x) < w) && (2 * Math.abs(this.y - y) < h);
        }

        @Override
        public Vector2 getRandomPoint(Random r) {
            float posX = x - w / 2 + r.nextFloat() * w;
            float posY = y - h / 2 + r.nextFloat() * h;
            return new Vector2(posX, posY);
        }
    }

    public static class Circle implements Shape {
        private float x, y, r;

        public Circle(float x, float y, float r) {
            this.x = x;
            this.y = y;
            this.r = r;
        }

        @Override
        public boolean within(float x, float y) {
            return Math.sqrt(square(this.x - x) + square(this.y - y)) < r;
        }

        private float square(float x) {
            return x * x;
        }

        @Override
        public float getX() {
            return x;
        }

        @Override
        public float getY() {
            return y;
        }

        @Override
        public Vector2 getRandomPoint(Random random) {
            Vector2 radius = new Vector2(random.nextFloat() * r, 0);
            radius.rotate(random.nextInt(360));
            return radius.add(x, y);
        }
    }
}
