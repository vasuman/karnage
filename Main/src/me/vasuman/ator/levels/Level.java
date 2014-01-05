package me.vasuman.ator.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
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


    public static final int STICK_SIZE = 110;
    private Manager manager;
    private Physics physics;
    protected Player player;
    private Vector2 touchPosition;
    private boolean touched;
    private boolean tap;
    private Vector2 baseVector;


    public static enum VectorType {
        Movement, Firing, CamShift
    }


    public Level() {
        super();

        tap = false;
        touched = false;
        touchPosition = new Vector2();

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

        InputListener tapListen = new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                touched = false;
                tap = true;
                super.touchUp(event, x, y, pointer, button);
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                super.touchDragged(event, x, y, pointer);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                touched = true;
                tap = false;
                setTouch(x, y);
                return true;
            }

            private void setTouch(float x, float y) {
                touchPosition.set(getPlanarZ(Drawer.getPerspectiveCamera().getPickRay(x, height - y, 0, 0, resX, resY)));
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
                return getTouchPosition();
        }
        return new Vector2();
    }

    private Vector2 getTouchPosition() {
        if (!tap) {
            return new Vector2(0, 0);
        }
        tap = false;
        Vector2 result = new Vector2(touchPosition);
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

    public abstract void updateCamera();
}