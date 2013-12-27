package me.vasuman.ator.entities;

import com.badlogic.gdx.math.Vector2;

/**
 * Ator
 * User: vasuman
 * Date: 12/3/13
 * Time: 7:55 PM
 */
public class Gun extends Extension {
    private final int cooldown;
    private int counter;

    private static final float SQRT2 = (float) Math.sqrt(2);

    public Gun(float fireRate, float damage) {
        this.counter = 0;
        this.cooldown = Math.round(1 / fireRate);

    }


    @Override
    public void destroy() {
    }

    @Override
    public void update(float delT) {
        if (this.counter < 0) {
            this.counter--;
            return;
        }
        // TODO: Call fire!
    }

    private void fire(Vector2 direction) {
    }
}
