package me.vasuman.ator.levels;

import com.badlogic.gdx.math.Vector2;
import me.vasuman.ator.entities.BasicGlob;
import me.vasuman.ator.util.Shape;

import java.util.ArrayList;
import java.util.Random;

/**
 * Ator
 * User: vasuman
 * Date: 1/1/14
 * Time: 1:56 PM
 */
public class SpawnManager {

    // TODO: variable sizes
    private static final float SIZE = 10;
    private Shape[] regions;
    private ArrayList<BasicGlob> globs;
    private Random random;

    public void setMaxSpawn(int maxSpawn) {
        this.maxSpawn = maxSpawn;
    }

    private int maxSpawn;

    public SpawnManager(int maxSpawn, Shape... regions) {
        this.regions = regions;
        this.maxSpawn = maxSpawn;
        globs = new ArrayList<BasicGlob>();
        random = new Random();
    }

    public void update(float delT, Vector2 converge) {
        for (int i = globs.size() - 1; i >= 0; i--) {
            BasicGlob glob = globs.get(i);
            if (glob.isDead()) {
                globs.remove(i);
                continue;
            }
            glob.seek(converge);
        }
        for (int i = 0; i < maxSpawn - globs.size(); i++) {
            spawn();
        }
    }

    private void spawn() {
        Shape region = regions[random.nextInt(regions.length)];
        Vector2 point = region.getRandomPoint(random);
        BasicGlob glob = new BasicGlob(point.x, point.y, SIZE);
        globs.add(glob);
    }
}
