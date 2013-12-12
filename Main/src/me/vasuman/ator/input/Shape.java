package me.vasuman.ator.input;

/**
 * Ator
 * User: vasuman
 * Date: 12/8/13
 * Time: 10:15 PM
 */
public interface Shape {
    public boolean within(int x, int y);

    public int getX();

    public int getY();

    public static class Rectangle implements Shape {
        private int x;
        private int y;
        private int w;

        private int h;

        public Rectangle(int x, int y, int w, int h) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }

        @Override
        public int getX() {
            return x;
        }

        @Override
        public int getY() {
            return y;
        }

        @Override
        public boolean within(int x, int y) {
            return (2 * Math.abs(this.x - x) < w) && (2 * Math.abs(this.y - y) < h);
        }
    }

    public static class Circle implements Shape {
        private int x, y, r;

        public Circle(int x, int y, int r) {
            this.x = x;
            this.y = y;
            this.r = r;
        }

        @Override
        public boolean within(int x, int y) {
            return Math.sqrt(square(this.x - x) + square(this.y - y)) < r;
        }

        private int square(int x) {
            return x * x;
        }

        @Override
        public int getX() {
            return x;
        }

        @Override
        public int getY() {
            return y;
        }

    }
}
