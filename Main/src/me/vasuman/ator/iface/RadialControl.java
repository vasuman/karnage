package me.vasuman.ator.iface;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import me.vasuman.ator.Drawer;

/**
 * Ator
 * User: vasuman
 * Date: 12/11/13
 * Time: 4:05 PM
 */
public class RadialControl extends Actor {
    private float x, y;
    private float limit;
    private Texture texture;

    public RadialControl(final int centerX, final int centerY, final int limit) {
        setBounds(centerX - limit, centerY - limit, 2 * limit, 2 * limit);
        x = getWidth() / 2;
        y = getHeight() / 2;
        this.limit = limit;
        texture = Drawer.preDraw(new Drawer() {
            @Override
            public void draw() {
                debugColor(new Color(0.55f, 0.2f, 0.4f, 0.5f));
                debugCircle(0, 0, limit);
            }
        }, 2 * limit, 2 * limit);
        addListener(new InputListener() {
            private float magnitude(float x, float y) {
                return (float) Math.sqrt(x * x + y * y);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (magnitude(x - limit, y - limit) > limit) {
                    return false;
                }
                RadialControl.this.x = x;
                RadialControl.this.y = y;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                RadialControl.this.x = limit;
                RadialControl.this.y = limit;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                RadialControl.this.x = x;
                RadialControl.this.y = y;
            }
        });

    }


    public Vector2 getDisplacement() {
        return new Vector2(x - limit, y - limit);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }

}
