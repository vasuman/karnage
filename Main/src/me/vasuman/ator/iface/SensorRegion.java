package me.vasuman.ator.iface;

import com.badlogic.gdx.math.Vector2;
import me.vasuman.ator.util.Shape;

/**
 * Ator
 * User: vasuman
 * Date: 12/3/13
 * Time: 10:29 PM
 */
public abstract class SensorRegion {

    protected Shape shape;
    protected int pointer = -1;
    protected int x, y;

    public SensorRegion(Shape shape) {
        this.shape = shape;
        reset();
    }

    public boolean touch(int x, int y, int pointer) {
        if (getPointer() == -1 && shape.within(x, y)) {
            this.pointer = pointer;
            update(x, y);
            return true;
        }
        return false;
    }

    protected void reset() {
        this.x = shape.getX();
        this.y = shape.getY();
    }

    public int getPointer() {
        return pointer;
    }

    public void update(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void up(int x, int y) {
        this.pointer = -1;
        if (shape.within(x, y)) {
            click();
        }
        reset();
    }

    public Vector2 getOffset() {
        return new Vector2(x, y);
    }

    public abstract boolean click();
}
