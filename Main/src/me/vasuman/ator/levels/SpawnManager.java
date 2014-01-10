package me.vasuman.ator.levels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import me.vasuman.ator.Drawer;
import me.vasuman.ator.MainGame;
import me.vasuman.ator.entities.*;

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
    private Glob.GlobDef simpleDef = new Glob.GlobDef();
    private Glob.GlobDef complexDef = new Glob.GlobDef();

    private void initGlobDef() {
        retardDef.damp = 0.7f;
        retardDef.speed = 350f;
        retardDef.r = random;
        retardDef.bounds = 16;
        retardDef.model = MainGame.assets.get("tetrahedron.g3db", Model.class);
        retardDef.shape = PhysicalBody.makeTriangle(16);
        retardDef.manager = this;

        simpleDef.damp = 0.8f;
        simpleDef.speed = 9.5f;
        simpleDef.r = random;
        simpleDef.bounds = 16;
        simpleDef.model = Drawer.basicCube(16, ColorAttribute.createDiffuse(Color.RED));
        simpleDef.shape = PhysicalBody.makeBox(16, 16);
        simpleDef.manager = this;


        complexDef.damp = 0.65f;
        complexDef.speed = 5;
        complexDef.r = random;
        complexDef.bounds = 16;
        complexDef.model = MainGame.assets.get("icosahedron.g3db", Model.class);
        complexDef.shape = PhysicalBody.makeCircle(16);
        complexDef.manager = this;
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
        Vector2 point = getRandomPoint(region, random);
        Glob.GlobType type = getSpawnType();
        Glob spawnedGlob = null;
        if (type == Glob.GlobType.Retard) {
            spawnedGlob = new RetartdedGlob(retardDef, point);
        } else if (type == Glob.GlobType.Simple) {
            spawnedGlob = new SimpleGlob(simpleDef, point);
        } else if (type == Glob.GlobType.Normal) {
            // TODO: Spawn normal
        } else if (type == Glob.GlobType.Complex) {
            spawnedGlob = new ComplexGlob(complexDef, point);
        }
        globs.add(spawnedGlob);
    }

    private Rectangle getRegion() {
        // TODO: random select regions
        return bounds;
    }

    private Glob.GlobType getSpawnType() {
        float rand = random.nextFloat() * difficulty;
        if (rand < 0.33) {
            return Glob.GlobType.Simple;
        } else if (rand < 0.66) {
            return Glob.GlobType.Retard;
        } else {
            return Glob.GlobType.Complex;
        }
    }

    private Vector2 getRandomPoint(Rectangle region, Random random) {
        Vector2 point = new Vector2(region.getX(), region.getY());
        point.add(random.nextFloat() * region.getWidth(), random.nextFloat() * region.getHeight());
        return point;
    }


    public void registerKill(Glob glob) {
        //new Explosion(glob.getPosition().cpy(), Color.WHITE);
    }
}
