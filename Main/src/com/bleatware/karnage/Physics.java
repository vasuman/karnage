package com.bleatware.karnage;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.bleatware.karnage.entities.GameEntity;

import java.util.HashMap;

/**
 * Ator
 * User: vasuman
 * Date: 12/2/13
 * Time: 6:25 PM
 */
public class Physics {

    private World world;
    public static final float scale = 25;
    private static Physics ourInstance = new Physics();

    public void registerListener(GameEntity.EntityType type, ContactCallback callback) {
        callbackMap.put(type, callback);
    }

    public static interface ContactCallback {
        public boolean handleCollision(GameEntity entA, GameEntity entB);
    }

    public static interface QueryResult {
        public boolean report(GameEntity entA);
    }

    protected HashMap<GameEntity.EntityType, ContactCallback> callbackMap;

    public static Physics getInstance() {
        return ourInstance;
    }

    private Physics() {
    }

    public void init() {
        callbackMap = new HashMap<GameEntity.EntityType, ContactCallback>();
        world = new World(new Vector2(), false);
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
                GameEntity entA = ((GameEntity) contact.getFixtureB().getBody().getUserData());
                GameEntity entB = ((GameEntity) contact.getFixtureA().getBody().getUserData());
                if (callbackMap.containsKey(entA.getIdentifier())) {
                    boolean flag = callbackMap.get(entA.getIdentifier()).handleCollision(entA, entB);
                    if (flag) {
                        return;
                    }
                }
                if (callbackMap.containsKey(entB.getIdentifier())) {
                    boolean flag = callbackMap.get(entB.getIdentifier()).handleCollision(entB, entA);
                    if (flag) {
                        return;
                    }
                }
            }
        });
    }

    public Body addBody(BodyDef def) {
        return world.createBody(def);
    }

    public void removeBody(Body b) {
        world.destroyBody(b);
    }


    public void update(float delT) {
        world.step(delT, 10, 10);
        world.clearForces();
    }

    public void queryBoundBox(Vector2 center, float radius, final QueryResult callback) {
        center.scl(1 / scale);
        radius /= scale;
        world.QueryAABB(new QueryCallback() {
            @Override
            public boolean reportFixture(Fixture fixture) {
                return callback.report((GameEntity) fixture.getBody().getUserData());
            }
        }, center.x - radius, center.y - radius, center.x + radius, center.y + radius);
    }

}
