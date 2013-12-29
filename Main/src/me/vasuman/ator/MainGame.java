package me.vasuman.ator;

import com.badlogic.gdx.Game;
import me.vasuman.ator.levels.TheGrid;
import me.vasuman.ator.screens.BaseScreen;

/**
 * Ator
 * User: vasuman
 * Date: 12/2/13
 * Time: 6:29 PM
 */
public class MainGame extends Game {
    @Override
    public void create() {
        BaseScreen.setGame(this);
        (new TheGrid()).setAsCurrent();
    }
}
