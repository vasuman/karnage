package me.vasuman.ator.levels;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import me.vasuman.ator.Drawable;
import me.vasuman.ator.Drawer;
import me.vasuman.ator.Manager;
import me.vasuman.ator.Physics;
import me.vasuman.ator.iface.InputController;
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
    private InputController input;
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

        input = new InputController();

        control = new RadialControl(STICK_POS.x, STICK_POS.y, STICK_SIZE);
        stage.addActor(control);

        physics = Physics.getInstance();
        physics.init();
    }

    @Override
    public void render(float delta) {
        // TODO: fix delta
        physics.update(delta);
        manager.update(delta);
        update(delta);
        Drawer.clear();
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
                Vector3 gyro = input.getGyro();
                return new Vector2(-gyro.y, +gyro.x);
            case Firing:
                return control.getDisplacement();
        }
        assert false;
        return null;
    }

    @Override
    public void dispose() {
        super.dispose();
        manager.clear();
    }

    public abstract void update(float delT);
}