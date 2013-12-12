package me.vasuman.ator.input;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import me.vasuman.ator.Drawable;
import me.vasuman.ator.Drawer;

/**
 * Ator
 * User: vasuman
 * Date: 12/11/13
 * Time: 4:05 PM
 */
public class FireControl extends SensorRegion implements Drawable {
    private Drawer drawer;
    private int limit;

    public FireControl(final int x, final int y, final int size) {
        super(new Shape.Circle(x, y, size));
        limit = size;
        drawer = new Drawer() {
            private final Color color = new Color(1, 0, 0, 0.1f);

            @Override
            public void draw() {
                offset(false);
                debugColor(color);
                debugCircle(x, y, size);
            }
        };
    }

    @Override
    public Drawer getDrawer() {
        return drawer;
    }

    @Override
    public boolean click() {
        return false;
    }

    public Vector2 getDisplacement() {
        return new Vector2(x - shape.getX(), y - shape.getY()).limit(limit);
    }
}
