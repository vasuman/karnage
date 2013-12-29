package me.vasuman.ator.entities;

import me.vasuman.ator.Manager;

/**
 * Ator
 * User: vasuman
 * Date: 12/2/13
 * Time: 6:05 PM
 */
public abstract class GameEntity {
    public void kill() {
        _dead = true;
    }

    public static enum EntityType {
        PLAYER, BULLET, OBSTACLE, GUN,
    }

    private boolean _dead;
    protected int index;
    protected EntityType identifier;

    public GameEntity() {
        _dead = false;
        index = Manager.getInstance().registerEntity(this);
    }

    public boolean isDead() {
        return _dead;
    }

    public abstract void destroy();

    public abstract void update(float delT);

    public EntityType getIdentifier() {
        return identifier;
    }

}
