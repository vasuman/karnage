package me.vasuman.ator.entities;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import me.vasuman.ator.Drawable;
import me.vasuman.ator.Drawer;
import me.vasuman.ator.MainGame;

/**
 * Ator
 * User: vasuman
 * Date: 12/2/13
 * Time: 6:17 PM
 */
public class Player extends PhysicalBody implements Drawable {
    protected Drawer drawer;

    public float getSize() {
        return def.size;
    }


    public void damage() {

    }

    public static class PlayerDef {
        public Vector2 position;
        public int health;
        public float size;
        public float damp;
        public String modelPath;
        public Extension.ExtensionBuilder[] builders;
    }

    @Override
    public Drawer getDrawer() {
        return drawer;
    }

    protected PlayerDef def;

    public Player(final PlayerDef def) {
        super(def.position.x, def.position.y, makeCircle(def.size), false);
        this.def = def;
        drawer = new Drawer() {
            private Model model = MainGame.assets.get(def.modelPath, Model.class);

            @Override
            public void draw() {
                Vector3 position = new Vector3(getPosition(), 0);
                drawModelAt(model, position);
            }
        };
        super.setDamping(def.damp);
        identifier = EntityType.PLAYER;
        for (Extension.ExtensionBuilder builder : def.builders) {
            Extension extension = builder.build();
            extension.claim(this);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }


    @Override
    public void update(float delT) {

    }
}
