package me.vasuman.ator.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import me.vasuman.ator.Drawable;
import me.vasuman.ator.Drawer;

/**
 * Ator
 * User: vasuman
 * Date: 1/1/14
 * Time: 1:22 PM
 */
public class BasicGlob extends PhysicalBody implements Drawable {
    private static final float SPEED = 8f;
    protected Drawer drawer;
    protected Vector2 seekPos;

    public BasicGlob(float x, float y, final float size) {
        super(x, y, size, size, false);
        seekPos = new Vector2();
        drawer = new Drawer() {
            private Model model = basicCube(size, ColorAttribute.createDiffuse(Color.GREEN));

            @Override
            public void draw() {
                drawModelAt(model, new Vector3(getPosition(), 0));
            }
        };
        setDamping(0.7f);
        identifier = EntityType.GLOB;
    }

    @Override
    public void update(float delT) {
        Vector2 director = new Vector2(seekPos);
        director.sub(getPosition());
        director.nor();
        director.scl(SPEED);
        pushBody(director);
    }

    public void seek(Vector2 position) {
        seekPos.set(position);
    }

    @Override
    public Drawer getDrawer() {
        return drawer;
    }
}
