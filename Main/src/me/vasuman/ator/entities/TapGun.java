package me.vasuman.ator.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import me.vasuman.ator.Drawable;
import me.vasuman.ator.Drawer;
import me.vasuman.ator.MainGame;
import me.vasuman.ator.Manager;

/**
 * Ator
 * User: vasuman
 * Date: 12/3/13
 * Time: 7:55 PM
 */

// TODO: implement Drawable
public class TapGun extends Extension implements Drawable {
    public static class TapGunBuilder implements ExtensionBuilder {
        @Override
        public TapGun build() {
            return new TapGun();
        }
    }

    protected final Vector2 direction = new Vector2(1, 0);
    private Drawer drawer;
    private static Bullet.BulletDef def = new Bullet.BulletDef();

    private TapGun() {
        initBulletDef();
        drawer = new Drawer() {
            private Model model = MainGame.assets.get("canon.g3db", Model.class);

            @Override
            public void draw() {
                drawModelAt(model, new Vector3(player.getPosition(), 0), Vector3.Z, direction.angle());
            }
        };
        identifier = EntityType.GUN;
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

    @Override
    public Drawer getDrawer() {
        return drawer;
    }
}
