package com.bleatware.karnage;

/**
 * Ator
 * User: vasuman
 * Date: 1/9/14
 * Time: 5:15 PM
 */
public class GameState {
    public static class Powerup {
        public static enum Weapon {
            Standard, Automatic, Impact, DualCannon
        }

        public static enum Aux {
            Radar
        }

        public static enum Movement {
            Brake
        }
    }

    public int coins;

}
