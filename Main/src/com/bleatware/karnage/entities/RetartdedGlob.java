package com.bleatware.karnage.entities;

import com.badlogic.gdx.math.Vector2;

/**
 * Ator
 * User: vasuman
 * Date: 1/7/14
 * Time: 9:59 PM
 */
public class RetartdedGlob extends Glob {
    private static final float FORCE_FACTOR = 483;
    public static final float DIRECTION_BIAS = 0.37f;
    private float minRatio;


    @Override
    public GlobType getType() {
        return GlobType.Retard;
    }

    public RetartdedGlob(final GlobDef def, Vector2 postion) {
        super(def, postion);
        minRatio = def.r.nextFloat() * 0.2f + 0.2f;
    }


    @Override
    public void update(float delT) {
        if (getVelocity().len() < def.speed * minRatio) {
            float weight = def.r.nextFloat() * DIRECTION_BIAS;
            Vector2 direct = getDirection();
            direct.scl(weight * def.speed);
            Vector2 random = new Vector2(def.r.nextFloat() - 0.5f, def.r.nextFloat() - 0.5f);
            random.nor();
            random.scl((1 - weight) * FORCE_FACTOR);
            random.add(direct);
            pushBody(random);
        }
    }

}
