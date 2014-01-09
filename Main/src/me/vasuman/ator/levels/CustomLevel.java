package me.vasuman.ator.levels;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.environment.BaseLight;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import me.vasuman.ator.Config;
import me.vasuman.ator.Drawer;
import me.vasuman.ator.Physics;
import me.vasuman.ator.entities.GameEntity;
import me.vasuman.ator.entities.Player;
import me.vasuman.ator.entities.TapGun;
import me.vasuman.ator.entities.Wall;

import java.util.Random;

/**
 * Ator
 * User: vasuman
 * Date: 1/6/14
 * Time: 7:41 PM
 */
public class CustomLevel extends Level {
    public static class LevelDef {
        public int width;
        public int height;
        public int tX;
        public int tY;
        public TextureRegion[] tiles;
        public Random seed;
        public Rectangle[] obstacles;
        public Rectangle[] spawnRegions;
        public BaseLight[] lights;
        public Player.PlayerDef playerDef;

        public void buildBoundraies(float size) {
            obstacles = new Rectangle[4];
            obstacles[0] = new Rectangle(0, 0, size, height);
            obstacles[1] = new Rectangle(0, 0, width, size);
            obstacles[2] = new Rectangle(width - size, 0, size, height);
            obstacles[3] = new Rectangle(0, height - size, width, size);
        }
    }

    private static final float RAD_FCT = (float) Math.sqrt(2) / 2;

    private Vector3 offsetPosition = new Vector3();
    private SpawnManager spawnManager;
    protected LevelDef def;
    protected int[] tData;
    private Drawer drawer;

    public CustomLevel(final LevelDef def) {
        super();
        this.def = def;
        final int numX = (def.width / def.tX);
        final int numY = (def.height / def.tY);
        tData = new int[numX * numY];
        for (int i = 0; i < tData.length; i++) {
            tData[i] = def.seed.nextInt(def.tiles.length);
        }
        for (Rectangle obstacle : def.obstacles) {
            new Wall(obstacle);
        }
        player = new Player(def.playerDef);
        spawnManager = new SpawnManager(new Rectangle(0, 0, def.width, def.height));
        drawer = new Drawer() {
            @Override
            public void draw() {
                spriteDraw.begin();
                for (int i = 0; i < numX; i++) {
                    int posX = i * def.tX;
                    for (int j = 0; j < numY; j++) {
                        int posY = j * def.tY;
                        if (perspectiveCamera.frustum.sphereInFrustum(posX + def.tX / 2, posY + def.tY / 2, 0, RAD_FCT * def.tX)) {
                            spriteDraw.draw(def.tiles[tData[i * numY + j]], posX, posY);
                        }
                    }
                }
                spriteDraw.end();
            }
        };
        Drawer.getEnvironment().add(def.lights);
        player.addExtension(new TapGun(1, 8));
        Physics physics = Physics.getInstance();
        physics.registerListener(GameEntity.EntityType.BULLET, new Physics.ContactCallback() {
            @Override
            public boolean handleCollision(GameEntity entA, GameEntity entB) {
                entA.kill();
                if (entB.getIdentifier() == GameEntity.EntityType.GLOB) {
                    entB.kill();
                }
                return true;
            }
        });
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

    @Override
    public Drawer getDrawer() {
        return drawer;
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
