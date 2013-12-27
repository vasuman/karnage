package me.vasuman.ator.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import me.vasuman.ator.*;
import me.vasuman.ator.input.RadialControl;
import me.vasuman.ator.input.TouchInput;
import me.vasuman.ator.screens.BaseScreen;
import me.vasuman.ator.util.Point;

import java.util.ArrayList;

/**
 * Ator
 * User: vasuman
 * Date: 12/11/13
 * Time: 8:52 PM
 */
public abstract class Level extends BaseScreen implements Drawable {
    public static final Point STICK_POS = new Point(540, 270);
    public static final int STICK_SIZE = 70;
    private Manager manager;
    private TouchInput input;
    private Physics physics;
    private ArrayList<Drawable> OSD;
    private RadialControl control;

    public static enum VectorType {
        Movement, Firing;
    }


    public Level() {
        super();

        OSD = new ArrayList<Drawable>();

        // "init" all the things!
        Drawer.init(MainGame.resX, MainGame.resY);
        Drawer.getPerspectiveCamera().translate(0, 0, height);

        manager = Manager.getInstance();
        manager.init(this);

        input = new TouchInput();
        Gdx.input.setInputProcessor(input);

        control = new RadialControl(STICK_POS.x, STICK_POS.y, STICK_SIZE);
        OSD.add(control);
        input.addRegion(control);

        physics = Physics.getInstance();
        physics.init();
    }

    @Override
    public void render(float delta) {
        // TODO: fix delta
        physics.update(delta);
        manager.update(delta);
        this.update(delta);

        Drawer.clear();
        Drawer.setupCamera(false);
        // Draw self
        this.getDrawer().draw();
        // Draw all entities
        manager.draw();
        Drawer.setupCamera(true);
        // Draw OSD shit here!
        for (Drawable d : OSD) {
            d.getDrawer().draw();
        }
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
                return new Vector2(-gyro.y, gyro.x);
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