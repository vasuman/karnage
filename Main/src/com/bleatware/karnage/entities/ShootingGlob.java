package com.bleatware.karnage.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Vector2;
import com.bleatware.karnage.Drawer;
import com.bleatware.karnage.util.Counter;

/**
 * Karnage
 * User: vasuman
 * Date: 1/15/14
 * Time: 9:36 PM
 */
public class ShootingGlob extends Glob {
    public static final float BULLET_SIZE = 8;
    public static final int NUM_SPURT = 5;
    private Bullet.BulletDef bulletDef = new Bullet.BulletDef();

    private static enum State {
        Resting, Shooting
    }

    public static class ShootingGlobDef extends GlobDef {
        public float fireRate;
        public float bulletSpeed;
        public float restTime;
    }

    private State state = State.Resting;
    private int bCount = 0;
    private Counter rateLimit;
    private Counter cooldown;

    public ShootingGlob(ShootingGlobDef def, Vector2 position) {
        super(def, position);
        cooldown = new Counter(def.restTime);
        cooldown.reset();
        rateLimit = new Counter(1 / def.fireRate);
        bulletDef.model = Drawer.basicCube(BULLET_SIZE, ColorAttribute.createDiffuse(Color.WHITE));
        bulletDef.size = BULLET_SIZE;
        bulletDef.speed = def.bulletSpeed;
        bulletDef.type = Bullet.BulletType.Hostile;
    }

    @Override
    public GlobType getType() {
        return GlobType.Shooting;
    }

    @Override
    public void update(float delT) {
        Vector2 dir = getDirection();
        pushBody(dir.cpy().scl(def.speed));
        if (state == State.Shooting) {
            if (rateLimit.update(delT)) {
                Vector2 position = getPosition().add(dir.cpy().scl(2 * def.bounds));
                new Bullet(position, dir, bulletDef);
                if (++bCount >= NUM_SPURT) {
                    bCount = 0;
                    state = State.Resting;
                }
            }
        } else {
            if (cooldown.update(delT)) {
                state = State.Shooting;
            }
        }
    }
}
