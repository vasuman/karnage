package me.vasuman.ator.entities;

import com.badlogic.gdx.graphics.Color;
import me.vasuman.ator.Drawable;
import me.vasuman.ator.Drawer;

/**
 * Ator
 * User: vasuman
 * Date: 12/11/13
 * Time: 5:28 PM
 */
public class Wall extends PhysicalBody implements Drawable {
    protected Drawer drawer;

    @Override
    public void destroy() {

    }

    // Top-left positioning
    public Wall(final float x, final float y, final float w, final float h) {
        super(x + w / 2, y + h / 2, w, h, true);
        drawer = new Drawer() {
            @Override
            public void draw() {
                debugColor(Color.WHITE);
                debugBox(x + w / 2, y + h / 2, w, h);
            }
        };
    }

    @Override
    public Drawer getDrawer() {
        return drawer;
    }

    @Override
    public void update(float delT) {

    }
}
