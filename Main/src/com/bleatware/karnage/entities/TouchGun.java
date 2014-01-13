package com.bleatware.karnage.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Vector2;
import com.bleatware.karnage.Drawer;
import com.bleatware.karnage.Manager;
import com.bleatware.karnage.util.Counter;

/**
 * Ator
 * User: vasuman
 * Date: 1/11/14
 * Time: 1:38 PM
 */
public class TouchGun extends Extension {
    public static class TouchGunBuilder implements Extension.ExtensionBuilder {
        private float fireRate;

        public TouchGunBuilder(float fireRate) {
            this.fireRate = fireRate;
        }

        @Override
        public TouchGun build() {
            return new TouchGun(this);
        }
    }

    protected final Vector2 direction = new Vector2(1, 0);
    private Bullet.BulletDef def = new Bullet.BulletDef();
    private Counter cooldown;
    private TouchGunBuilder builder;

    private TouchGun(TouchGunBuilder builder) {
        this.builder = builder;
        cooldown = new Counter(1 / builder.fireRate);
        initBulletDef();
        identifier = GameEntity.EntityType.GUN;
    }

    private void initBulletDef() {
        def.size = 5;
        def.speed = 1000;
        def.model = Drawer.basicSphere(def.size, ColorAttribute.createDiffuse(Color.YELLOW));
    }


    @Override
    public void destroy() {
    }

    @Override
    public void update(float delT) {
        if (!cooldown.update(delT)) {
            return;
        }
        if (Manager.level.inputListener.getTouch() == 0) {
            return;
        }
        Vector2 rotation = Manager.level.inputListener.getPosition(0).sub(player.getPosition());
        rotation.nor();
        direction.set(rotation);
        this.fire();
    }

    // Can be overridden
    protected void fire() {
        Vector2 position = new Vector2(direction);
        position.scl(2 * player.getSize());
        position.add(player.getPosition());
        new Bullet(position, direction.cpy(), def);
    }

}
