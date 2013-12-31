package me.vasuman.ator.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import me.vasuman.ator.Drawer;
import me.vasuman.ator.Manager;
import me.vasuman.ator.levels.Level;

/**
 * Ator
 * User: vasuman
 * Date: 12/3/13
 * Time: 7:55 PM
 */

// TODO: implement Drawable
public class Gun extends Extension {
    public static final float bulletSize = 5;
    private final int timeout;
    private final float height;
    protected final Vector2 direction = new Vector2(1, 0);
    private final Model bulletModel = Drawer.basicSphere(bulletSize, ColorAttribute.createDiffuse(Color.YELLOW));
    public static final float bulletSpeed = 500f;
    private int counter;

    public Gun(int timeout, float height) {
        super();
        counter = 0;
        this.timeout = timeout;
        this.height = height;
        identifier = EntityType.GUN;
    }


    @Override
    public void destroy() {
    }

    @Override
    public void update(float delT) {
        if (counter-- > 0) {
            return;
        }
        Vector2 rotation = Manager.level.getVector(Level.VectorType.Firing);
        if (rotation.x == 0 && rotation.y == 0) {
            return;
        }
        rotation.nor();
        direction.set(rotation);
        counter = timeout;
        this.fire();
    }

    // Can be overridden
    protected void fire() {
        Vector2 posProjection = direction.cpy().scl(2 * player.size);
        Vector3 position = new Vector3(posProjection, height);
        position.add(new Vector3(player.getPosition(), 0));
        Vector2 bulletDir = direction.cpy().scl(bulletSpeed).add(player.getVelocity());
        new Bullet(position, bulletDir, bulletModel, bulletSize);
    }
}
