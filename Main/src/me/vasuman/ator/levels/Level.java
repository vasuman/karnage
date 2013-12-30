package me.vasuman.ator.levels;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import me.vasuman.ator.*;
import me.vasuman.ator.iface.RadialControl;
import me.vasuman.ator.screens.BaseScreen;
import me.vasuman.ator.util.Point;

/**
 * Ator
 * User: vasuman
 * Date: 12/11/13
 * Time: 8:52 PM
 */
public abstract class Level extends BaseScreen implements Drawable {
    public static final Point STICK_POS = new Point(1100, 120);
    public static final int STICK_SIZE = 110;
    private Manager manager;
    private Physics physics;
    private RadialControl control;

    public static enum VectorType {
        Movement, Firing
    }


    public Level() {
        super();
        // "init" all the things!
        manager = Manager.getInstance();
        manager.init(this);

        control = new RadialControl(STICK_POS.x, STICK_POS.y, STICK_SIZE);
        stage.addActor(control);

        physics = Physics.getInstance();
        physics.init();

        MainGame.rotationProvider.calibrate();

        //DEBUG!!
        Actor button = new Actor();
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
            }
        });
    }

    @Override
    public void render(float delta) {
        // TODO: fix delta
        physics.update(delta);
        manager.update(delta);
        update(delta);
        Drawer.clearScreen();
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
            case Movement:
                float[] rotation = MainGame.rotationProvider.getRotation();
                return new Vector2(rotation[0], -rotation[1]);
            case Firing:
                return control.getDisplacement();
        }

        return null;
    }

    @Override
    public void dispose() {
        super.dispose();
        manager.clear();
    }

    public abstract void update(float delT);
}