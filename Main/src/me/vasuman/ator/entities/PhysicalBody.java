package me.vasuman.ator.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import me.vasuman.ator.Physics;

/**
 * Ator
 * User: vasuman
 * Date: 12/10/13
 * Time: 7:48 PM
 */
public abstract class PhysicalBody extends GameEntity {
    protected Body body;


    protected static Shape makeCircle(float r) {
        CircleShape shape = new CircleShape();
        shape.setRadius(r / Physics.scale);
        return shape;
    }

    protected static Shape makeBox(float w, float h) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(w / (2 * Physics.scale), h / (2 * Physics.scale));
        return shape;
    }

    public static Shape makeTriangle(float size) {
        size /= Physics.scale;
        float x = (float) Math.cos(Math.PI / 6) * size;
        float y = (float) Math.sin(Math.PI / 6) * size;
        PolygonShape shape = new PolygonShape();
        shape.set(new Vector2[]{
                new Vector2(0, size),
                new Vector2(x, -y),
                new Vector2(-x, -y)
        });
        return shape;
    }

    protected PhysicalBody(float x, float y, Shape shape, boolean fixed) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = (fixed) ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / Physics.scale, y / Physics.scale);
        body = Physics.getInstance().addBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
        body.setUserData(this);
    }

    // TODO: Add constructor with BodyDef and FixtureDef

    public Vector2 getPosition() {
        return body.getPosition().scl(Physics.scale);
    }

    protected void pushBody(Vector2 force) {
        body.applyForceToCenter(force, true);
    }

    public void setDamping(float v) {
        body.setLinearDamping(v);
    }

    public Vector2 getVelocity() {
        return body.getLinearVelocity().scl(Physics.scale);
    }

    protected void setVelocity(Vector2 vel) {
        Vector2 velocity = new Vector2(vel);
        velocity.scl(1 / Physics.scale);
        body.setLinearVelocity(velocity);
    }

    @Override
    public void destroy() {
        Physics.getInstance().removeBody(body);
    }
}