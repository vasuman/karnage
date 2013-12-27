package me.vasuman.ator.levels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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
    public static final float CAM_ELEVATION = 100f;
    public static final float MOTION_IMPACT = -8f;
    public static final float LPF_ALPHA = 0.8f;
    private Drawer drawer;
    private Player player;
    private static final int VERTICAL_SP = 32;
    private static final int HORIZONTAL_SP = 32;
    public static final int L_WIDTH = 1280;
    public static final int L_HEIGHT = 720;

    public TheGrid() {
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
            }
        };

        Environment environment = Drawer.getEnvironment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(1, 0.6f, 0.3f, 1, 1, 0));
        environment.add(new DirectionalLight().set(0.7f, 1, 1, -1, -1, 0));

        // Init Walls
        player = new Player(L_WIDTH / 2, L_HEIGHT / 2);
        Wall[] walls = new Wall[4];
        walls[0] = new Wall(0, 0, 5, L_HEIGHT);
        walls[1] = new Wall(0, 0, L_WIDTH, 5);
        walls[2] = new Wall(L_WIDTH, 0, 5, L_HEIGHT);
        walls[3] = new Wall(0, L_HEIGHT, L_WIDTH, 5);
        updateCamera();
    }

    @Override
    public Drawer getDrawer() {
        return drawer;
    }

    private void updateCamera() {
        PerspectiveCamera camera = Drawer.getPerspectiveCamera();
        Vector2 playerPosition = player.getPosition();
        Vector2 moveVector = getVector(VectorType.Movement);
        moveVector.scl(MOTION_IMPACT);
        Vector3 newPosition = wrapSphere(playerPosition, moveVector, CAM_ELEVATION);
        LPFSet(camera.position, newPosition);
        camera.lookAt(new Vector3(playerPosition, 0));
        camera.up.set(0, 1, 0);
    }

    private void LPFSet(Vector3 vecA, Vector3 vecB) {
        vecA.scl(LPF_ALPHA);
        vecA.add(new Vector3(vecB).scl(1 - LPF_ALPHA));
    }


    private Vector3 wrapSphere(Vector2 center, Vector2 shift, float elevation) {
        Vector3 basePosition = new Vector3(center, 0);
        Vector3 boundVector = new Vector3(shift, elevation);
        boundVector.limit(elevation);
        basePosition.add(boundVector);
        return basePosition;
    }

    @Override
    public void update(float delT) {
        updateCamera();
    }
}
