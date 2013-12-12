package me.vasuman.ator.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import me.vasuman.ator.Drawer;
import me.vasuman.ator.entities.Player;
import me.vasuman.ator.entities.Wall;

/**
 * Ator
 * User: vasuman
 * Date: 12/3/13
 * Time: 7:16 PM
 */
public class TheGrid extends Level {
    private Drawer drawer;
    private Player player;
    private static final int vSpace = 32;
    private static final int hSpace = 32;
    public static final int lWidth = 1280;
    public static final int lHeight = 720;

    public TheGrid() {
    }

    @Override
    public void init() {
        // Draw the entire scene to a framebuffer
        FrameBuffer buffer = new FrameBuffer(Pixmap.Format.RGB565, lWidth, lHeight, false);
        Drawer.clear();
        buffer.begin();
        (new Drawer() {
            @Override
            public void draw() {
                offset(false);
                Color color = new Color(0.0f, 1.0f, 0.0f, 0.5f);
                debugColor(color);
                for (int i = 0; i <= Math.floor(lWidth / vSpace); i++) {
                    float posX = i * vSpace + 1;
                    debugLine(posX, 2, posX, lHeight - 1);
                }
                for (int i = 0; i <= Math.floor(lHeight / hSpace); i++) {
                    float posY = i * hSpace + 2;
                    debugLine(1, posY, lWidth - 1, posY);
                }
            }
        }).draw();
        buffer.end();
        final Texture texture = buffer.getColorBufferTexture();
        //buffer.dispose();
        final int sWidth = Gdx.graphics.getWidth();
        final int sHeight = Gdx.graphics.getHeight();
        drawer = new Drawer() {
            @Override
            public void draw() {
                offset(false);
                sprite.begin();
                sprite.draw(texture, 0, 0, (int) camera.position.x - sWidth / 2, -(int) camera.position.y + sHeight / 2, sWidth, sHeight);
                sprite.end();
            }
        };
        // Init Walls
        player = new Player(lWidth / 2, lHeight / 2);
        Wall[] walls = new Wall[4];
        walls[0] = new Wall(0, 0, 5, lHeight);
        walls[1] = new Wall(0, 0, lWidth, 5);
        walls[2] = new Wall(lWidth, 0, 5, lHeight);
        walls[3] = new Wall(0, lHeight, lWidth, 5);

    }

    @Override
    public Drawer getDrawer() {
        return drawer;
    }

    @Override
    public void update(float delT) {
        Drawer.setViewport(player.getPosition());
    }
}
