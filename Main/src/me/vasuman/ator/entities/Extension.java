package me.vasuman.ator.entities;

/**
 * Ator
 * User: vasuman
 * Date: 12/3/13
 * Time: 7:50 PM
 */
public abstract class Extension extends GameEntity {
    protected Player player;

    public void claim(Player player) {
        this.player = player;
    }
}
