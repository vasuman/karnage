package me.vasuman.ator.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import me.vasuman.ator.Drawable;
import me.vasuman.ator.Drawer;

import java.util.LinkedList;

/**
 * Ator
 * User: vasuman
 * Date: 12/2/13
 * Time: 6:17 PM
 */
public class Player extends PhysicalBody implements Drawable {
    protected LinkedList<Extension> extensions = new LinkedList();
    protected Drawer drawer;
    protected Vector2 fire = new Vector2();

    @Override
    public Drawer getDrawer() {
        return drawer;
    }

    public static final float speed = 10;
    public static final int size = 16;

    public Player(float x, float y) {
        super(x, y, size, size, false);
        drawer = new Drawer() {
            @Override
            public void draw() {
                offset(true);
                Vector2 position = Player.this.getPosition();
                debugColor(Color.ORANGE);
                debugBox(position.x, position.y, size, size);
                offset(true);
                debugColor(Color.WHITE);
                debugLine(position.x, position.y, position.x + fire.x, position.y + fire.y);
            }
        };
    }

    public void addExtension(Extension ext) {
        extensions.add(ext);
        ext.claim(this);
    }

    @Override
    public void update(float delT) {
        pushBody(screen.getVector(0).nor().scl(speed));
        fire.set(screen.getVector(1));
    }
}
