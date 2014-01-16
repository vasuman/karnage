package com.bleatware.karnage.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Vector2;
import com.bleatware.karnage.Drawer;
import com.bleatware.karnage.Manager;

/**
 * Ator
 * User: vasuman
 * Date: 12/3/13
 * Time: 7:55 PM
 */

// TODO: implement Drawable
public class TapGun extends Extension {
    public static class TapGunBuilder implements ExtensionBuilder {
        @Override
        public TapGun build() {
            return new TapGun();
        }
    }

    protected final Vector2 direction = new Vector2(1, 0);
    private static Bullet.BulletDef def = new Bullet.BulletDef();

    private TapGun() {
        initBulletDef();
    }

    private void initBulletDef() {
        def.size = 5;
        def.speed = 1000;
        def.model = Drawer.basicSphere(def.size, ColorAttribute.createDiffuse(Color.YELLOW));
        def.type = Bullet.BulletType.Friendly;
    }


    @Override
    public void destroy() {
    }

    @Override
    public void update(float delT) {
        if (!Manager.level.inputListener.getTap()) {
            return;
        }
        Vector2 rotation = Manager.level.inputListener.getTapPosition().sub(player.getPosition());
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
