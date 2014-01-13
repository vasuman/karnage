package com.bleatware.karnage.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.bleatware.karnage.Drawable;
import com.bleatware.karnage.Drawer;

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
        super.destroy();
    }

    // Top-left positioning
    public Wall(final float x, final float y, final float w, final float h) {
        super(x + w / 2, y + h / 2, makeBox(w, h), true);
        //TODO: Build models
        drawer = new Drawer() {
            @Override
            public void draw() {
                debugColor(Color.WHITE);
                debugBox(x + w / 2, y + h / 2, w, h);
            }
        };
        identifier = EntityType.OBSTACLE;
    }

    public Wall(Rectangle rect) {
        this(rect.x, rect.y, rect.width, rect.height);
    }

    @Override
    public Drawer getDrawer() {
        return drawer;
    }

    @Override
    public void update(float delT) {

    }
}
