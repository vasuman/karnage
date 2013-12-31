package me.vasuman.ator.levels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import me.vasuman.ator.Drawer;
import me.vasuman.ator.Physics;
import me.vasuman.ator.debug.TightPlayer;
import me.vasuman.ator.entities.GameEntity;
import me.vasuman.ator.entities.Gun;
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
    private static final int VERTICAL_SP = 32;
    private static final int HORIZONTAL_SP = 32;
    public static final int L_WIDTH = 5000;
    public static final int L_HEIGHT = 3000;

    public TheGrid(boolean flag) {
        super();
        drawer = new Drawer() {
            @Override
            public void draw() {
                debugColor(Color.GREEN);
                for (int i = 0; i < (L_WIDTH / HORIZONTAL_SP); i++) {
                    float posX = i * HORIZONTAL_SP;
                    debugLine(posX, 0, posX, L_HEIGHT);
                }
                for (int i = 0; i < (L_HEIGHT / VERTICAL_SP); i++) {
                    float posY = i * VERTICAL_SP;
                    debugLine(0, posY, L_WIDTH, posY);
                }
                debugBox(tapVector.x, tapVector.y, 5, 5);
            }
        };

        Environment environment = Drawer.getEnvironment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(1, 0.6f, 0.3f, 0.6f, 1, 0));
        environment.add(new DirectionalLight().set(0.7f, 1, 1, -0.6f, -1, 0));

        // Init Walls
        new Wall(0, 0, 5, L_HEIGHT);
        new Wall(0, 0, L_WIDTH, 5);
        new Wall(L_WIDTH, 0, 5, L_HEIGHT);
        new Wall(0, L_HEIGHT, L_WIDTH, 5);

        if (flag) {
            player = new TightPlayer(L_WIDTH / 2, L_HEIGHT / 2);
        } else {

            player = new Player(L_WIDTH / 2, L_HEIGHT / 2);
        }
        player.addExtension(new Gun(5, 0));
        Physics.ContactCallback bulletHit = new Physics.ContactCallback() {
            @Override
            public boolean handleCollision(GameEntity entA, GameEntity entB) {
                entA.kill();
                return true;
            }
        };
        Physics.getInstance().registerListener(GameEntity.EntityType.BULLET, bulletHit);
    }

    @Override
    public Drawer getDrawer() {
        return drawer;
    }

    @Override
    public void update(float delT) {

    }
}
