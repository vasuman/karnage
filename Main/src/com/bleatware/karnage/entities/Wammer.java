package com.bleatware.karnage.entities;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.bleatware.karnage.MainGame;
import com.bleatware.karnage.Manager;

/**
 * Karnage
 * User: vasuman
 * Date: 1/14/14
 * Time: 12:43 PM
 */
public class Wammer extends Extension {
    public static class WammerBuilder implements ExtensionBuilder {
        private float omega;

        public WammerBuilder(float omega) {
            this.omega = omega;
        }

        @Override
        public Extension build() {
            return new Wammer(this);
        }
    }

    private WammerBuilder builder;
    private Button rightButton;
    private Button leftButton;

    private Wammer(WammerBuilder builder) {
        this.builder = builder;
        Skin skin = MainGame.assets.get("game.json");
        rightButton = new Button(skin, "ui/wammer-right");
        leftButton = new Button(skin, "ui/wammer-left");
        Stage stage = Manager.level.getStage();
        leftButton.setPosition(0, 0);
        rightButton.setPosition(stage.getWidth() - rightButton.getWidth(), 0);

    }

    @Override
    public void destroy() {
    }

    @Override
    public void update(float delT) {

    }
}
