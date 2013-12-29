package me.vasuman.ator.entities;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import me.vasuman.ator.Drawable;
import me.vasuman.ator.Drawer;
import me.vasuman.ator.Manager;
import me.vasuman.ator.levels.Level;

import java.util.LinkedList;

/**
 * Ator
 * User: vasuman
 * Date: 12/2/13
 * Time: 6:17 PM
 */
public class Player extends PhysicalBody implements Drawable {
    protected LinkedList<Extension> extensions = new LinkedList<Extension>();
    protected Drawer drawer;

    @Override
    public Drawer getDrawer() {
        return drawer;
    }

    public static final float speed = 0.7f;
    public static final int size = 16;

    public Player(float x, float y) {
        super(x, y, size, size, false);
        drawer = new Drawer() {
            private Model model = basicCube(size, ColorAttribute.createDiffuse(0, 0, 1, 1));

            @Override
            public void draw() {
                Vector3 position = new Vector3(getPosition(), 0);
                drawModelAt(model, position);
            }
        };
        super.setDamping(0.5f);
        identifier = EntityType.PLAYER;
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    public void addExtension(Extension ext) {
        extensions.add(ext);
        ext.claim(this);
    }

    @Override
    public void update(float delT) {
        Vector2 movement = Manager.level.getVector(Level.VectorType.Movement);
        movement.scl(speed);
        pushBody(movement);
    }
}
