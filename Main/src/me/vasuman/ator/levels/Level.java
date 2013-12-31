package me.vasuman.ator.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import me.vasuman.ator.*;
import me.vasuman.ator.entities.Player;
import me.vasuman.ator.screens.BaseScreen;
import me.vasuman.ator.screens.MenuScreen;

/**
 * Ator
 * User: vasuman
 * Date: 12/11/13
 * Time: 8:52 PM
 */
public abstract class Level extends BaseScreen implements Drawable {

    public static final float CAM_ELEVATION = 180f;
    public static final float MOTION_IMPACT = -0.3f;
    public static final float LPF_ALPHA = 0.93f;
    public static final int STICK_SIZE = 110;
    private Manager manager;
    private Physics physics;
    protected Player player;
    protected Vector2 tapVector;
    private boolean tap;
    private Vector2 baseVector;


    public static enum VectorType {
        Movement, Firing, CamShift
    }


    public Level() {
        super();

        tap = false;
        tapVector = new Vector2();

        baseVector = new Vector2();
        baseVector = getVector(VectorType.CamShift);

        manager = Manager.getInstance();
        manager.init(this);
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);

        InputListener backListen = new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.BACK) {
                    new MenuScreen();
                    return true;
                }
                return false;
            }
        };
        stage.addListener(backListen);

        ActorGestureListener tapListen = new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                // Touch axis is Y-Flipped
                tapVector.set(getPlanarZ(Drawer.getPerspectiveCamera().getPickRay(x, height - y, 0, 0, resX, resY)));
                tap = true;
            }
        };
        stage.addListener(tapListen);

        physics = Physics.getInstance();
        physics.init();

        //DEBUG!!
    }

    private Vector2 getPlanarZ(Ray pickRay) {
        float distance = -pickRay.origin.z / pickRay.direction.z;
        Vector3 vector = new Vector3();
        pickRay.getEndPoint(vector, distance);
        return new Vector2(vector.x, vector.y);
    }


    @Override
    public void render(float delta) {
        // TODO: fix delta
        physics.update(delta);
        manager.update(delta);
        update(delta);
        Drawer.clearScreen();
        updateCamera();
        Drawer.setupCamera();
        // Draw self
        getDrawer().draw();
        // Draw all entities
        manager.draw();
        stage.draw();
        super.render(delta);
    }

    /**
     * A function that returns a specified <b>input</b> vector that is accessed via an index
     *
     * @param idx [Movement] [Firing]
     * @return A direction vector
     */
    public Vector2 getVector(VectorType idx) {
        switch (idx) {
            case CamShift:
            case Movement:
                float[] rotation = MainGame.rotation.getRotation();
                return new Vector2(rotation[1], -rotation[2]).sub(baseVector);
            case Firing:
                return getTapVector();
        }
        return new Vector2();
    }

    private Vector2 getTapVector() {
        if (!tap) {
            return new Vector2(0, 0);
        }
        tap = false;
        Vector2 result = new Vector2(tapVector);
        result.sub(player.getPosition());
        return result;
    }

    @Override
    public void dispose() {
        super.dispose();
        Gdx.input.setCatchBackKey(false);
        Gdx.input.setCatchMenuKey(false);
        manager.clear();
    }

    public abstract void update(float delT);

    private void updateCamera() {
        PerspectiveCamera camera = Drawer.getPerspectiveCamera();
        Vector2 playerPosition = player.getPosition();
        Vector2 moveVector = getVector(VectorType.CamShift);
        moveVector.scl(MOTION_IMPACT * CAM_ELEVATION);
        Vector3 newPosition = wrapSphere(playerPosition, moveVector, CAM_ELEVATION);
        LPFSet(camera.position, newPosition);
        camera.lookAt(new Vector3(playerPosition, 0));
        camera.up.set(0, 1, 0);
        camera.update();
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

}