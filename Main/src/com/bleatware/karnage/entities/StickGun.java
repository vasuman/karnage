package com.bleatware.karnage.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.bleatware.karnage.Drawer;
import com.bleatware.karnage.MainGame;
import com.bleatware.karnage.Manager;
import com.bleatware.karnage.screens.Layout;
import com.bleatware.karnage.util.Counter;

/**
 * Karnage
 * User: vasuman
 * Date: 1/15/14
 * Time: 12:23 PM
 */
public class StickGun extends Extension {
    public static final float DEADZONE_RADIUS = 10f;
    public static final float PADDING = 35;
    private Vector2 direction = new Vector2(0, 1);

    public static class StickGunBuilder implements ExtensionBuilder {
        private float fireRate;

        public StickGunBuilder(float fireRate) {
            this.fireRate = fireRate;
        }

        @Override
        public Extension build() {
            return new StickGun(this);
        }
    }

    private Counter cooldown;
    private Touchpad stickControl;
    private StickGunBuilder builder;
    private static Bullet.BulletDef def = new Bullet.BulletDef();

    private StickGun(StickGunBuilder builder) {
        this.builder = builder;
        cooldown = new Counter(1 / builder.fireRate);
        initBulletDef();
        Stage stage = Manager.level.getStage();
        Skin skin = MainGame.assets.get("game.json", Skin.class);
        stickControl = new Touchpad(DEADZONE_RADIUS, skin, "stick-gun");
        Layout.setActorPosition(stickControl, stage, Layout.Position.BottomRight);
        Layout.offsetPosition(stickControl, -PADDING, PADDING);
        stage.addActor(stickControl);
    }

    private void initBulletDef() {
        def.size = 5;
        def.speed = 1000;
        def.model = Drawer.basicSphere(def.size, ColorAttribute.createDiffuse(Color.YELLOW));
        def.type = Bullet.BulletType.Friendly;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void update(float delT) {
        boolean flag = cooldown.update(delT);
        if (!stickControl.isTouched()) {
            return;
        }
        direction.set(stickControl.getKnobPercentX(), stickControl.getKnobPercentY());
        direction.nor();
        if (flag && direction.x != 0 && direction.y != 0) {
            fire();
        }
    }

    private void fire() {
        Vector2 position = player.getPosition();
        position.add(direction.cpy().scl(2 * player.getSize()));
        new Bullet(position, direction, def);
    }
}
