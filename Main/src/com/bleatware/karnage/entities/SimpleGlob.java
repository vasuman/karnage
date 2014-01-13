package com.bleatware.karnage.entities;

import com.badlogic.gdx.math.Vector2;

/**
 * Ator
 * User: vasuman
 * Date: 1/7/14
 * Time: 6:12 PM
 */
public class SimpleGlob extends Glob {
    public SimpleGlob(GlobDef def, Vector2 position) {
        super(def, position);
    }

    @Override
    public GlobType getType() {
        return GlobType.Simple;
    }

    @Override
    public void update(float delT) {
        pushBody(getDirection().scl(def.speed));
    }
}
