package me.vasuman.ator.entities;

import com.badlogic.gdx.math.Vector2;

/**
 * Ator
 * User: vasuman
 * Date: 1/7/14
 * Time: 9:59 PM
 */
public class RetartdedGlob extends Glob {
    private static final float period = 1.1732f;
    private float counter;


    @Override
    public GlobType getType() {
        return GlobType.Retard;
    }

    public RetartdedGlob(final GlobDef def, Vector2 postion) {
        super(def, postion);
        counter = def.r.nextFloat() * period / 2;
    }


    @Override
    public void update(float delT) {
        counter += delT;
        if (counter > period) {
            float weight = def.r.nextFloat();
            Vector2 direct = getPosition().sub(seek);
            direct.scl(-1);
            direct.nor();
            direct.scl((1 - weight) * def.speed);
            Vector2 random = new Vector2(def.r.nextFloat() - 0.5f, def.r.nextFloat() - 0.5f);
            random.nor();
            random.scl(weight * def.speed);
            random.add(direct);
            pushBody(random);
            counter = def.r.nextFloat() * period / 2;
        }
    }

}
