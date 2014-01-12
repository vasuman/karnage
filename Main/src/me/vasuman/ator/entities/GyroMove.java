package me.vasuman.ator.entities;

import com.badlogic.gdx.math.Vector2;
import me.vasuman.ator.MainGame;

/**
 * Ator
 * User: vasuman
 * Date: 1/9/14
 * Time: 10:54 PM
 */
public class GyroMove extends Extension {
    public static class GyroMoveBuilder implements ExtensionBuilder {
        private float speed;

        public GyroMoveBuilder(float speed) {
            this.speed = speed;
        }

        @Override
        public Extension build() {
            return new GyroMove(this);
        }
    }

    private GyroMoveBuilder builder;

    private GyroMove(GyroMoveBuilder builder) {
        this.builder = builder;
    }

    @Override
    public void destroy() {
    }

    @Override
    public void update(float delT) {
        Vector2 movement = MainGame.rotation.getVector();
        movement.scl(-builder.speed);
        player.setVelocity(movement);
    }
}
