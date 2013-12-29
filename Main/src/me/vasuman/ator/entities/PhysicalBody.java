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

    protected PhysicalBody(float x, float y, float w, float h, boolean fixed) {
        body = createBody(x, y, fixed);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(w / Physics.scale, h / Physics.scale);
        createFixture(body, shape);
        body.setUserData(this);
    }

    protected PhysicalBody(float x, float y, float r, boolean fixed) {
        body = createBody(x, y, fixed);
        CircleShape shape = new CircleShape();
        shape.setRadius(r / Physics.scale);
        createFixture(body, shape);
        body.setUserData(this);
    }

    private Fixture createFixture(Body b, Shape shape) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        return b.createFixture(fixtureDef);
    }

    private Body createBody(float x, float y, boolean fixed) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = (fixed) ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / Physics.scale, y / Physics.scale);
        return Physics.getInstance().addBody(bodyDef);
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
        return body.getPosition().scl(Physics.scale);
    }

    protected void setVelocity(Vector2 velocity) {
        body.setLinearVelocity(velocity.scl(Physics.scale));
    }

    @Override
    public void destroy() {
        Physics.getInstance().removeBody(body);
    }
}