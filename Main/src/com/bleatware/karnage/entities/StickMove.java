package com.bleatware.karnage.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.bleatware.karnage.MainGame;
import com.bleatware.karnage.Manager;
import com.bleatware.karnage.screens.Layout;

/**
 * Karnage
 * User: vasuman
 * Date: 1/17/14
 * Time: 12:50 AM
 */
public class StickMove extends Extension {
    private static final float DEADZONE_RADIUS = 10f;
    private static final float PADDING = 30f;

    public static class StickMoveBuilder implements ExtensionBuilder {
        private float velocity;

        public StickMoveBuilder(float velocity) {
            this.velocity = velocity;
        }

        @Override
        public Extension build() {
            return new StickMove(this);
        }
    }

    private StickMoveBuilder builder;
    private Touchpad stick;

    private StickMove(StickMoveBuilder builder) {
        this.builder = builder;
        Skin skin = MainGame.assets.get("game.json", Skin.class);
        stick = new Touchpad(DEADZONE_RADIUS, skin, "stick-gun");
        Stage stage = Manager.level.getStage();
        Layout.setActorPosition(stick, stage, Layout.Position.BottomLeft);
        Layout.offsetPosition(stick, PADDING, PADDING);
        stage.addActor(stick);
    }

    @Override
    public void destroy() {

    }

    @Override
    public void update(float delT) {
        Vector2 movement = new Vector2(stick.getKnobPercentX(), stick.getKnobPercentY());
        movement.nor().scl(builder.velocity);
        player.pushBody(movement, delT);
    }
}
