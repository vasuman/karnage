package me.vasuman.ator;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Ator
 * User: vasuman
 * Date: 12/2/13
 * Time: 6:25 PM
 */
public class Physics {

    private World world;
    public static final float scale = 25;
    private static Physics ourInstance = new Physics();

    public static Physics getInstance() {
        return ourInstance;
    }

    private Physics() {
    }

    public void init() {
        world = new World(new Vector2(), false);
    }

    public Body addBody(BodyDef def) {
        return world.createBody(def);
    }

    public void removeBody(Body b) {
        world.destroyBody(b);
    }


    public void update(float delT) {
        world.step(delT, 10, 10);
        world.clearForces();
    }

}
