package me.vasuman.ator.levels;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import me.vasuman.ator.MainGame;
import me.vasuman.ator.entities.Glob;
import me.vasuman.ator.entities.PhysicalBody;
import me.vasuman.ator.entities.RetartdedGlob;

import java.util.ArrayList;
import java.util.Random;

/**
 * Ator
 * User: vasuman
 * Date: 1/1/14
 * Time: 1:56 PM
 */
public class SpawnManager {

    private static final int basSpawn = 10;

    private Glob.GlobDef retardDef = new Glob.GlobDef();

    private void initGlobDef() {
        retardDef.damp = 0.7f;
        retardDef.speed = 300f;
        retardDef.r = random;
        retardDef.model = MainGame.assets.get("tetrahedron.g3db", Model.class);
        retardDef.shape = PhysicalBody.makeTriangle(16);
        retardDef.manager = this;
    }

    // TODO: variable sizes
    private Rectangle bounds;
    private ArrayList<Glob> globs = new ArrayList<Glob>();
    private Random random = new Random();

    private float difficulty = 1;

    public SpawnManager(Rectangle bounds) {
        this.bounds = bounds;
        initGlobDef();
    }

    public void update(float delT, Vector2 converge) {
        int maxSpawn = getSpawnCount();
        for (int i = globs.size() - 1; i >= 0; i--) {
            Glob glob = globs.get(i);
            if (glob.isDead()) {
                globs.remove(i);
                continue;
            }
            glob.setSeek(converge);
        }
        for (int i = 0; i < maxSpawn - globs.size(); i++) {
            spawn();
        }
    }

    private int getSpawnCount() {
        // TODO: Function with current `difficulty` as parameter
        return 10;
    }

    private void spawn() {
        Rectangle region = getRegion();
        Vector2 point = getRandomPoint(bounds, random);
        Glob.GlobType type = getSpawnType();
        Glob spawnedGlob = null;
        if (type == Glob.GlobType.Retard) {
            spawnedGlob = new RetartdedGlob(retardDef, point);
        } else if (type == Glob.GlobType.Simple) {
            // TODO: Spawn simple
        } else if (type == Glob.GlobType.Normal) {
            // TODO: Spawn normal
        } else if (type == Glob.GlobType.Complex) {
            // TODO: Spawn complex
        }
        globs.add(spawnedGlob);
    }

    private Rectangle getRegion() {
        // TODO: random select regions
        return bounds;
    }

    private Glob.GlobType getSpawnType() {
        //TODO: Parametric random function
        return Glob.GlobType.Retard;
    }

    private Vector2 getRandomPoint(Rectangle region, Random random) {
        Vector2 point = new Vector2(region.getX(), region.getY());
        point.add(random.nextFloat() * region.getWidth(), random.nextFloat() * region.getHeight());
        return point;
    }


    public void registerKill(Glob glob) {

    }

    public ArrayList<Glob> getGlobs() {
        return globs;
    }
}
