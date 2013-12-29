package me.vasuman.ator.entities;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import me.vasuman.ator.Drawable;
import me.vasuman.ator.Drawer;

/**
 * Ator
 * User: vasuman
 * Date: 12/27/13
 * Time: 2:12 PM
 */
public class Bullet extends PhysicalBody implements Drawable {
    protected Drawer drawer;

    public Bullet(final Vector3 position, Vector2 velocity, final Model m, final float size) {
        super(position.x, position.y, size, false);
        drawer = new Drawer() {
            @Override
            public void draw() {
                Vector2 drawPos = Bullet.this.getPosition();
                drawModelAt(m, new Vector3(drawPos, position.z));
            }
        };
        setVelocity(velocity);
        identifier = EntityType.BULLET;
    }

    @Override
    public Drawer getDrawer() {
        return drawer;
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void update(float delT) {

    }
}
