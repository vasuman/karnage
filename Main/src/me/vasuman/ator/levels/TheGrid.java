package me.vasuman.ator.levels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import me.vasuman.ator.Config;
import me.vasuman.ator.Drawer;
import me.vasuman.ator.Physics;
import me.vasuman.ator.debug.TightPlayer;
import me.vasuman.ator.entities.GameEntity;
import me.vasuman.ator.entities.Gun;
import me.vasuman.ator.entities.Player;
import me.vasuman.ator.entities.Wall;
import me.vasuman.ator.util.Shape;

/**
 * Ator
 * User: vasuman
 * Date: 12/3/13
 * Time: 7:16 PM
 */
public class TheGrid extends Level {

    private SpawnManager spawnManager;
    private Drawer drawer;
    private static final int VERTICAL_SP = 128;
    private static final int HORIZONTAL_SP = 128;
    public static final int L_WIDTH = 5000;
    public static final int L_HEIGHT = 3000;
    private Vector3 offsetPosition;

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
                //debugBox(touchPosition.x, touchPosition.y, 5, 5);
            }
        };

        Environment environment = Drawer.getEnvironment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(1, 0.6f, 0.3f, 0.6f, 1, 0));
        environment.add(new DirectionalLight().set(0.7f, 1, 1, -0.6f, -1, 0));

        offsetPosition = new Vector3();
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
                if (entB.getIdentifier() == GameEntity.EntityType.GLOB) {
                    entB.kill();
                }
                return true;
            }
        };
        Physics.getInstance().registerListener(GameEntity.EntityType.BULLET, bulletHit);

        spawnManager = new SpawnManager(10, new Shape.Rectangle(500, 500, 500, 500), new Shape.Rectangle(L_WIDTH - 500, L_HEIGHT - 500, 500, 500));
    }

    @Override
    public Drawer getDrawer() {
        return drawer;
    }

    @Override
    public void update(float delT) {
        spawnManager.update(delT, player.getPosition());
    }

    @Override
    public void updateCamera() {
        PerspectiveCamera camera = Drawer.getPerspectiveCamera();
        Vector3 playerPosition = new Vector3(player.getPosition(), 0);
        Vector2 moveVector = getVector(VectorType.CamShift);
        moveVector.scl(Config.MOTION_IMPACT * Config.CAM_ELEVATION);
        LPFSet(offsetPosition, wrapSphere(moveVector, Config.CAM_ELEVATION));
        camera.position.set(playerPosition.cpy().add(offsetPosition));
        camera.lookAt(playerPosition);
        camera.up.set(0, 1, 0);
        camera.update();
    }

    private void LPFSet(Vector3 vecA, Vector3 vecB) {
        vecA.scl(Config.LPF_ALPHA);
        vecA.add(new Vector3(vecB).scl(1 - Config.LPF_ALPHA));
    }


    private Vector3 wrapSphere(Vector2 shift, float elevation) {
        Vector3 boundVector = new Vector3(shift, elevation);
        boundVector.limit(elevation);
        return boundVector;
    }

}
