package me.vasuman.ator.iface;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import me.vasuman.ator.Drawer;

/**
 * Ator
 * User: vasuman
 * Date: 12/30/13
 * Time: 1:06 AM
 */
public class LabelButton extends Actor {
    private static final int padW = 50;
    private static final int padH = 50;
    private final Texture texture;

    public LabelButton(final String text, int x, int y) {
        final BitmapFont.TextBounds bounds = Drawer.getBounds(text);
        final float width = bounds.width + padW;
        final float height = bounds.height + padH;
        setBounds(x - width / 2, y - height / 2, width, height);
        texture = Drawer.preDraw(new Drawer() {
            @Override
            public void draw() {
                debugColor(Color.GRAY);
                debugBox(0, 0, width, height);
                drawText(text, 0, 0);
            }
        }, (int) width, (int) height);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }
}
