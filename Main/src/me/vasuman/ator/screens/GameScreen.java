package me.vasuman.ator.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import me.vasuman.ator.Drawer;
import me.vasuman.ator.Manager;
import me.vasuman.ator.Physics;
import me.vasuman.ator.entities.GameEntity;
import me.vasuman.ator.input.FireControl;
import me.vasuman.ator.input.TouchInput;
import me.vasuman.ator.levels.Level;

/**
 * Ator
 * User: vasuman
 * Date: 12/2/13
 * Time: 6:29 PM
 */
public class GameScreen extends BaseScreen {
    public static final int controlSize = 150;
    public static final int controlSpacing = 20;
    private Manager manager;
    private TouchInput input;
    private Physics physics;
    private Level level;
    private FireControl fireControl;

    public GameScreen(Level level) {
        super();

        manager = Manager.getInstance();
        manager.clear();
        GameEntity.setScreen(this);

        input = new TouchInput();
        // TODO: Add sensor regions
        fireControl = new FireControl(Gdx.graphics.getWidth() - controlSize, Gdx.graphics.getHeight() - controlSize,
                controlSize - controlSpacing);
        input.addRegion(fireControl);
        Gdx.input.setInputProcessor(input);

        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(true, super.width, super.height);
        Drawer.setCamera(camera);

        physics = Physics.getInstance();
        physics.init();

        // TODO: Load some levels
        this.level = level;
        level.init();
    }

    @Override
    public void render(float delta) {
        // TODO: fix delta
        Drawer.clear();
        level.getDrawer().draw();
        physics.update(delta);
        manager.update(delta);
        level.update(delta);
        fireControl.getDrawer().draw();
        // TODO: Update levels
    }

    /**
     * A function that returns a specified <b>input</b> vector that is accessed via an index
     *
     * @param idx [0 : Movement] [1- : Firing]
     * @return A direction vector
     */
    public Vector2 getVector(int idx) {
        switch (idx) {
            case 0:
                Vector3 gyro = input.getGyro();
                return new Vector2(gyro.y, gyro.x);
            case 1:
                return fireControl.getDisplacement();
        }
        return null;
    }

}
