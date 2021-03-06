package com.bleatware.karnage.levels;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.bleatware.karnage.MainGame;
import com.bleatware.karnage.entities.*;
import com.bleatware.karnage.util.Counter;

import java.util.ArrayList;
import java.util.Random;

/**
 * Ator
 * User: vasuman
 * Date: 1/1/14
 * Time: 1:56 PM
 */
public class SpawnManager {

    private static final float MAX_DIFFICULTY = 25f;
    private static final float DIFFICULTY_FACTOR = 0.03f;

    private static final float POLL_INTERVAL = 10f;
    private static final float QUEUE_TIMER = 1f;

    private static final float BASE_DISTANCE = 450;
    private static final float BASE_SEPERATION = 70;

    private static final int MAX_SPAWN = 120;
    private static final int MIN_SINGLE_SPAWN = 2;
    private static final int MAX_SINGLE_SPAWN = 12;

    private static final int RETARD_REWARD = 5;
    private static final int SIMPLE_REWARD = 7;
    private static final int COMPLEX_REWARD = 10;
    private static final int SHOOT_REWARD = 12;

    private static final float BASE_WEIGHT = 1.6f;
    private static final float SIMPLE_BIAS = 14f;
    private static final float COMPLEX_BIAS = 6f;
    private static final float SHOOT_BIAS = 3f;

    private Glob.GlobDef retardDef = new Glob.GlobDef();
    private int numRetard = 0;
    private Glob.GlobDef simpleDef = new Glob.GlobDef();
    private int numSimple = 0;
    private ShootingGlob.ShootingGlobDef shootDef = new ShootingGlob.ShootingGlobDef();
    private int numShoot = 0;
    private Glob.GlobDef complexDef = new Glob.GlobDef();
    private int numComplex = 0;

    private int numKilled = 0;
    private int score = 0;

    private Rectangle bounds;
    private ArrayList<Glob> globs = new ArrayList<Glob>();
    private Random random = new Random();

    private float difficulty = 2;
    private Counter counter = new Counter(POLL_INTERVAL);
    private Counter spawnTimer = new Counter(QUEUE_TIMER);

    private static class DeferSpawner {
        private Vector2 position;
        private Glob.GlobType type;

        private DeferSpawner(Vector2 position, Glob.GlobType type) {
            this.position = position;
            this.type = type;
        }
    }

    private ArrayList<DeferSpawner> spawnQueue = new ArrayList<DeferSpawner>();

    public SpawnManager(Rectangle bounds) {
        this.bounds = bounds;
        initGlobDef();
    }

    private void initGlobDef() {
        retardDef.damp = 0.7f;
        retardDef.speed = 400f;
        retardDef.r = random;
        retardDef.bounds = 16;
        retardDef.model = MainGame.assets.get("tetrahedron.g3db", Model.class);
        retardDef.shape = PhysicalBody.makeTriangle(16);
        retardDef.manager = this;

        simpleDef.damp = 0.8f;
        simpleDef.speed = 9.5f;
        simpleDef.r = random;
        simpleDef.bounds = 16;
        simpleDef.model = MainGame.assets.get("simple-glob.g3db", Model.class);
        simpleDef.shape = PhysicalBody.makeBox(16, 16);
        simpleDef.manager = this;


        complexDef.damp = 0.65f;
        complexDef.speed = 7;
        complexDef.r = random;
        complexDef.bounds = 16;
        complexDef.model = MainGame.assets.get("icosahedron.g3db", Model.class);
        complexDef.shape = PhysicalBody.makeCircle(8);
        complexDef.manager = this;

        shootDef.damp = 0.7f;
        shootDef.speed = 5;
        shootDef.r = random;
        shootDef.bounds = 16;
        shootDef.model = MainGame.assets.get("shoot-glob.g3db", Model.class);
        shootDef.shape = PhysicalBody.makeCircle(16);
        shootDef.manager = this;
        shootDef.fireRate = 4;
        shootDef.bulletSpeed = 300;
        shootDef.restTime = 1.5f;
    }

    public void update(float delT, Vector2 converge) {
        for (int i = globs.size() - 1; i >= 0; i--) {
            Glob glob = globs.get(i);
            if (glob.isDead()) {
                globs.remove(i);
                continue;
            }
            glob.setSeek(converge);
        }
        if (counter.update(delT)) {
            int numSpawn = getSpawnCount();
            if (numSpawn == 0) {
                return;
            }
            spawn(numSpawn, converge);
        }
        if (!spawnQueue.isEmpty()) {
            if (spawnTimer.update(delT)) {
                DeferSpawner spawner = spawnQueue.remove(0);
                makeGlob(spawner);
            }
        }
    }

    private int getSpawnCount() {
        int deficit = (int) (MAX_SPAWN * (difficulty / MAX_DIFFICULTY)) - globs.size();
        if (deficit < MIN_SINGLE_SPAWN) {
            return 0;
        }
        return Math.min(deficit, MAX_SINGLE_SPAWN);
    }

    private void spawn(int num, Vector2 converge) {
        if (random.nextBoolean()) {
            Vector2 point = getRandomPoint(bounds);
            for (int i = 0; i < num; i++) {
                spawnQueue.add(new DeferSpawner(point, getSpawnType()));
            }
        } else {
            Vector2[] spawnPoints = getSpawnPoints(num, converge);
            for (Vector2 point : spawnPoints) {
                if (!bounds.contains(point)) {
                    point = getRandomPoint(bounds);
                }
                makeGlob(getSpawnType(), point);
            }
        }
    }

    private void makeGlob(DeferSpawner spawner) {
        makeGlob(spawner.type, spawner.position);
    }

    private void makeGlob(Glob.GlobType type, Vector2 point) {
        Glob spawnedGlob = null;
        if (type == Glob.GlobType.Retard) {
            spawnedGlob = new RetartdedGlob(retardDef, point);
            numRetard++;
        } else if (type == Glob.GlobType.Simple) {
            spawnedGlob = new SimpleGlob(simpleDef, point);
            numSimple++;
        } else if (type == Glob.GlobType.Complex) {
            spawnedGlob = new ComplexGlob(complexDef, point);
            numComplex++;
        } else if (type == Glob.GlobType.Shooting) {
            spawnedGlob = new ShootingGlob(shootDef, point);
            numShoot++;
        }
        globs.add(spawnedGlob);
    }

    private Vector2[] getSpawnPoints(int num, Vector2 position) {
        Vector2[] result = new Vector2[num];
        switch (random.nextInt(2)) {
            case 0:
                spawnCircle(result, position);
                break;
            case 1:
                spawnLine(result, position);
                break;
            //TODO: spawn Rect
        }
        return result;
    }

    private float getMinDistance() {
        float factor = MAX_DIFFICULTY / (MAX_DIFFICULTY + difficulty);
        return factor * BASE_DISTANCE;
    }

    private void spawnCircle(Vector2[] result, Vector2 position) {
        float radius = getMinDistance() * (0.9f + 0.2f * random.nextFloat());
        float angleSeperation = 360 / result.length;
        Vector2 spawnDisplacement = new Vector2(radius, 0);
        for (int i = 0; i < result.length; i++) {
            result[i] = spawnDisplacement.cpy().add(position);
            spawnDisplacement.rotate(angleSeperation);
        }
    }

    private void spawnLine(Vector2[] result, Vector2 position) {
        float distance = getMinDistance();
        float angle = random.nextFloat() * 360;
        Vector2 displacement = new Vector2(distance, 0).rotate(angle);
        Vector2 direction = displacement.cpy().rotate(90).nor();
        for (int i = 0; i < result.length; i++) {
            float seperation = (i - result.length / 2) * BASE_SEPERATION;
            result[i] = direction.cpy().scl(seperation);
            result[i].add(displacement).add(position);
        }
    }

    private float[] weights = new float[]{BASE_WEIGHT, 0, 0, 0};

    private Glob.GlobType getSpawnType() {
        if (numRetard == 0) {
            return Glob.GlobType.Retard;
        }
        float v = difficulty / MAX_DIFFICULTY * BASE_WEIGHT;
        weights[1] = SIMPLE_BIAS * v - numSimple / numRetard;
        weights[2] = COMPLEX_BIAS * v - numComplex / numRetard;
        weights[3] = SHOOT_BIAS * v - numShoot / numRetard;
        int select = weightedRandom(weights);
        if (select == 0) {
            return Glob.GlobType.Retard;
        } else if (select == 1) {
            return Glob.GlobType.Simple;
        } else if (select == 2) {
            return Glob.GlobType.Complex;
        } else {
            return Glob.GlobType.Shooting;
        }
    }

    private int weightedRandom(float[] weights) {
        float sum = 0;
        for (float weight : weights) {
            sum += weight;
        }
        float r = random.nextFloat() * sum;
        for (int i = 0; i < weights.length; i++) {
            if (r < weights[i]) {
                return i;
            }
            r -= weights[i];
        }
        return -1;
    }

    private Vector2 getRandomPoint(Rectangle region) {
        Vector2 point = new Vector2(region.getX(), region.getY());
        point.add(random.nextFloat() * region.getWidth(), random.nextFloat() * region.getHeight());
        return point;
    }


    public void registerKill(Glob glob) {
        //new Explosion(glob.getPosition().cpy(), Color.WHITE);
        Glob.GlobType type = glob.getType();
        if (type == Glob.GlobType.Retard) {
            numRetard--;
            score += RETARD_REWARD;
        } else if (type == Glob.GlobType.Simple) {
            numSimple--;
            score += SIMPLE_REWARD;
        } else if (type == Glob.GlobType.Complex) {
            numComplex--;
            score += COMPLEX_REWARD;
        } else if (type == Glob.GlobType.Shooting) {
            numShoot--;
            score += SHOOT_REWARD;
        }
        numKilled++;
        difficulty += MAX_DIFFICULTY / (MAX_DIFFICULTY + difficulty) * DIFFICULTY_FACTOR;
        difficulty = Math.min(difficulty, MAX_DIFFICULTY);
    }

    public int getScore() {
        return score;
    }

}
