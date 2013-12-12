package me.vasuman.ator.levels;

import me.vasuman.ator.Drawable;

/**
 * Ator
 * User: vasuman
 * Date: 12/11/13
 * Time: 8:52 PM
 */
public abstract class Level implements Drawable {
    // Don't set shit up in the constructor, use this instead!
    public abstract void init();

    public abstract void update(float delT);
}
