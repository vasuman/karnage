package com.bleatware.karnage.entities;

/**
 * Ator
 * User: vasuman
 * Date: 12/3/13
 * Time: 7:50 PM
 */
public abstract class Extension extends GameEntity {
    public static interface ExtensionBuilder {
        public Extension build();
    }

    protected Player player;

    public Extension() {
        super();
    }

    public void claim(Player player) {
        this.player = player;
    }
}
