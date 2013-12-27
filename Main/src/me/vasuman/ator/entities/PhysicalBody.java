package me.vasuman.ator.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
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
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(w / Physics.scale, h / Physics.scale);
        fixtureDef.shape = shape;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = (fixed) ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / Physics.scale, y / Physics.scale);
        body = Physics.getInstance().addBody(bodyDef);
        body.createFixture(fixtureDef);
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
}