package me.vasuman.ator.entities;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import me.vasuman.ator.Drawable;
import me.vasuman.ator.Drawer;

/**
 * Ator
 * User: vasuman
 * Date: 1/7/14
 * Time: 9:31 PM
 */
public class ModelSpawner extends GameEntity implements Drawable {
    private float height;
    private float rate;
    private boolean done = false;
    private FinishSpawn callback;

    public static interface FinishSpawn {
        public void done(boolean finished);
    }

    public ModelSpawner(final Model model, final Vector2 position, float height, float time, FinishSpawn callback) {
        this.callback = callback;
        this.rate = height / time;
        this.height = height;
        this.drawer = new Drawer() {
            @Override
            public void draw() {
                drawModelAt(model, new Vector3(position, -ModelSpawner.this.height));
            }
        };
    }

    private Drawer drawer;

    @Override
    public Drawer getDrawer() {
        return drawer;
    }

    @Override
    public void destroy() {
        callback.done(done);
    }

    @Override
    public void update(float delT) {
        height -= rate * delT;
        if (height <= 0) {
            done = true;
            this.kill();
        }
    }
}
