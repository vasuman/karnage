package me.vasuman.ator.debug;

import com.badlogic.gdx.math.Vector2;
import me.vasuman.ator.Manager;
import me.vasuman.ator.entities.Player;
import me.vasuman.ator.levels.Level;

/**
 * Ator
 * User: vasuman
 * Date: 12/30/13
 * Time: 1:01 AM
 */
public class TightPlayer extends Player {
    public static final float velocity = 1.1f;

    public TightPlayer(float x, float y) {
        super(x, y);
    }

    @Override
    public void update(float delT) {
        Vector2 movement = Manager.level.getVector(Level.VectorType.Movement);
        movement.scl(velocity);
        setVelocity(movement);
    }
}
