package me.vasuman.ator.screens.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import me.vasuman.ator.screens.BaseScreen;
import me.vasuman.ator.screens.Layout;

/**
 * Ator
 * User: vasuman
 * Date: 1/13/14
 * Time: 12:20 AM
 */
public class OverlayWindow extends Window {
    public static float PREF_WIDTH = 800;
    public static float PREF_HEIGHT = 480;
    public static float TOP_PAD = 100;
    public static final float SPACE = 50;

    public OverlayWindow(String title, Skin skin, BaseScreen screen) {
        super(title, skin);
        setSize(PREF_WIDTH, PREF_HEIGHT);
        padTop(TOP_PAD);
        setMovable(false);
        setResizable(false);
        setKeepWithinStage(false);
        Layout.setActorCenter(this, Layout.getStageCenter(screen.getStage()));
    }
}
