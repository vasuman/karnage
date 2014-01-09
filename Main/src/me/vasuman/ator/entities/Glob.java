package me.vasuman.ator.entities;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Shape;
import me.vasuman.ator.Drawable;
import me.vasuman.ator.Drawer;
import me.vasuman.ator.levels.SpawnManager;

import java.util.Random;

/**
 * Ator
 * User: vasuman
 * Date: 1/1/14
 * Time: 1:22 PM
 */
public abstract class Glob extends PhysicalBody implements Drawable {
    public static enum GlobType {
        Retard, Simple, Normal, Complex
    }

    public static class GlobDef {
        public Model model;
        public Shape shape;
        public float speed;
        public float damp;
        public Random r;
        public SpawnManager manager;
    }

    protected GlobDef def;
    protected Drawer drawer;

    protected Vector2 seek;

    protected Glob(final GlobDef def, Vector2 position) {
        super(position.x, position.y, def.shape, false);
        this.def = def;
        drawer = new Drawer() {
            @Override
            public void draw() {
                drawModelAt(def.model, new Vector3(getPosition(), 0));
            }
        };
        setDamping(def.damp);
        identifier = EntityType.GLOB;
    }

    public abstract GlobType getType();

    @Override
    public Drawer getDrawer() {
        return drawer;
    }

    @Override
    public void destroy() {
        super.destroy();
        def.manager.registerKill(this);
    }

    public void setSeek(Vector2 seek) {
        this.seek = seek;
    }

}