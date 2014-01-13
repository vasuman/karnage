package com.bleatware.karnage.entities;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bleatware.karnage.Drawable;
import com.bleatware.karnage.Drawer;

/**
 * Ator
 * User: vasuman
 * Date: 12/27/13
 * Time: 2:12 PM
 */
public class Bullet extends PhysicalBody implements Drawable {
    public static float HOVER = 4f;

    public static class BulletDef {
        public float speed;
        public Model model;
        public float size;
    }

    protected Drawer drawer;

    public Bullet(Vector2 origin, Vector2 direction, final BulletDef def) {
        super(origin.x, origin.y, makeCircle(def.size), false);
        drawer = new Drawer() {
            @Override
            public void draw() {
                Vector2 drawPos = getPosition();
                drawModelAt(def.model, new Vector3(drawPos, HOVER));
            }
        };
        setVelocity(direction.nor().scl(def.speed));
        body.setBullet(true);
        identifier = EntityType.BULLET;
    }

    @Override
    public Drawer getDrawer() {
        return drawer;
    }

    @Override
    public void destroy() {
        // TODO bullet shatter
        super.destroy();
    }

    @Override
    public void update(float delT) {
    }
}
