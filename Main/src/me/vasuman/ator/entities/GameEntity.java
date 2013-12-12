package me.vasuman.ator.entities;

import me.vasuman.ator.Manager;
import me.vasuman.ator.screens.GameScreen;

/**
 * Ator
 * User: vasuman
 * Date: 12/2/13
 * Time: 6:05 PM
 */
public abstract class GameEntity {

    private boolean _dead;
    protected int index;
    protected static GameScreen screen;


    public GameEntity() {
        _dead = false;
        index = Manager.getInstance().registerEntity(this);
    }


    public static void setScreen(GameScreen screen) {
        GameEntity.screen = screen;
    }

    public boolean isDead() {
        return _dead;
    }

    public void destroy() {

    }

    public abstract void update(float delT);


}
