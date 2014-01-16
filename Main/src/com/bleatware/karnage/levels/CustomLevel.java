package com.bleatware.karnage.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.environment.BaseLight;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.bleatware.karnage.Config;
import com.bleatware.karnage.Drawer;
import com.bleatware.karnage.MainGame;
import com.bleatware.karnage.Physics;
import com.bleatware.karnage.entities.Bullet;
import com.bleatware.karnage.entities.GameEntity;
import com.bleatware.karnage.entities.Player;
import com.bleatware.karnage.entities.Wall;
import com.bleatware.karnage.screens.ui.GameOverWindow;
import com.bleatware.karnage.screens.ui.RefreshLabel;
import com.bleatware.karnage.util.Counter;

import java.util.Random;

/**
 * Ator
 * User: vasuman
 * Date: 1/6/14
 * Time: 7:41 PM
 */
public class CustomLevel extends Level {
    private static final float SHAKE_FACTOR = 7f;

    public static class LevelDef {
        public int width;
        public int height;
        public int tX;
        public int tY;
        public TextureRegion[] tiles;
        public Random seed;
        public Rectangle[] obstacles;
        public BaseLight[] lights;
        public Player.PlayerDef playerDef;
        public float pad;

        public void buildBoundraies(float size) {
            obstacles = new Rectangle[4];
            obstacles[0] = new Rectangle(0, 0, size, height);
            obstacles[1] = new Rectangle(0, 0, width, size);
            obstacles[2] = new Rectangle(width - size, 0, size, height);
            obstacles[3] = new Rectangle(0, height - size, width, size);
            pad = 4 * size;
        }
    }

    private static final float RAD_FCT = (float) Math.sqrt(2) / 2;

    private Counter shakeTimer = new Counter(0.5f);
    private boolean shaking = false;

    private Vector3 offsetPosition = new Vector3();
    public Table onScreenDisplay;
    private SpawnManager spawnManager;
    protected LevelDef def;
    protected int[] tData;
    private Drawer drawer;

    public CustomLevel(final LevelDef def) {
        super();
        shakeTimer.reset();
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
        spawnManager = new SpawnManager(new Rectangle(def.pad, def.pad, def.width - def.pad, def.height - def.pad));
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
                //Vector2 touch = inputListener.getPosition();
                //debugBox(touch.x, touch.y, 10, 10);
            }
        };
        Drawer.getEnvironment().add(def.lights);
        Physics physics = Physics.getInstance();
        physics.registerListener(GameEntity.EntityType.BULLET, new Physics.ContactCallback() {
            @Override
            public boolean handleCollision(GameEntity entA, GameEntity entB) {
                Bullet bullet = (Bullet) entA;
                if (entB.getIdentifier() == GameEntity.EntityType.BULLET) {
                    //entA.kill();
                    //entB.kill();
                    return true;
                }

                if (bullet.getType() == Bullet.BulletType.Friendly) {
                    entA.kill();
                    if (entB.getIdentifier() == GameEntity.EntityType.GLOB) {
                        entB.kill();
                    }
                } else if (bullet.getType() == Bullet.BulletType.Hostile) {
                    if (entB.getIdentifier() != GameEntity.EntityType.GLOB) {
                        entA.kill();
                        if (entB.getIdentifier() == GameEntity.EntityType.PLAYER) {
                            player.damage();
                        }
                    }
                }
                return true;
            }
        });
        physics.registerListener(GameEntity.EntityType.GLOB, new Physics.ContactCallback() {
            @Override
            public boolean handleCollision(GameEntity entA, GameEntity entB) {
                if (entB.getIdentifier() == GameEntity.EntityType.PLAYER) {
                    ((Player) entB).damage();
                    entA.kill();
                    return true;
                }
                return false;
            }
        });
        initOSD();

    }

    private void initOSD() {
        Skin skin = MainGame.assets.get("game.json", Skin.class);

        onScreenDisplay = new Table();
        onScreenDisplay.setFillParent(true);
        stage.addActor(onScreenDisplay);

        RefreshLabel scoreLabel = new RefreshLabel(skin, new RefreshLabel.RefreshTrigger() {
            @Override
            public String getText() {
                return "Score: " + spawnManager.getScore();
            }
        });

        RefreshLabel healthLabel = new RefreshLabel(skin, new RefreshLabel.RefreshTrigger() {
            @Override
            public String getText() {
                return "Health: " + player.getHealth();
            }
        });

        //DEBUG!!
        RefreshLabel fpsCounter = new RefreshLabel(skin, new RefreshLabel.RefreshTrigger() {
            @Override
            public String getText() {
                return "FPS: " + Gdx.graphics.getFramesPerSecond();
            }
        });

        onScreenDisplay.add(scoreLabel).expand().top().left();
        onScreenDisplay.add(healthLabel).expand().top().left();
        onScreenDisplay.row();
        onScreenDisplay.add(fpsCounter).colspan(2).center();
    }

    @Override
    public void update(float delT) {
        if (player.isDead()) {
            endGame();
            return;
        }
        if (player.isDamaged()) {
            shaking = true;
        }
        spawnManager.update(delT, player.getPosition());
    }

    private void endGame() {
        Skin skin = MainGame.assets.get("game.json", Skin.class);
        paused = true;
        shaking = true;
        onScreenDisplay.remove();
        stage.addActor(new GameOverWindow(skin, spawnManager.getScore(), this));
    }

    @Override
    public void updateCamera(float delT) {
        PerspectiveCamera camera = Drawer.getPerspectiveCamera();
        Vector3 playerPosition = new Vector3(player.getPosition(), 0);
        Vector2 moveVector = MainGame.rotation.getVector();
        if (shaking) {
            if (shakeTimer.update(delT)) {
                shaking = false;
            }
            float dispX = ((float) Math.random() - 0.5f) * SHAKE_FACTOR;
            float dispY = ((float) Math.random() - 0.5f) * SHAKE_FACTOR;
            moveVector.add(dispX, dispY);
        }
        moveVector.scl(Config.MOTION_IMPACT * Config.CAM_ELEVATION);
        LPFSet(offsetPosition, wrapSphere(moveVector, Config.CAM_ELEVATION));
        camera.position.set(playerPosition.cpy().add(offsetPosition));
        camera.lookAt(playerPosition);
        camera.up.set(0, 1, 0);
        camera.update();
    }

    public static void LPFSet(Vector3 vecA, Vector3 vecB) {
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
