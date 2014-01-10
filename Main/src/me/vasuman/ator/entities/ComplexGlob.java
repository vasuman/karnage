package me.vasuman.ator.entities;

import com.badlogic.gdx.math.Vector2;
import me.vasuman.ator.Physics;

/**
 * Ator
 * User: vasuman
 * Date: 1/10/14
 * Time: 10:48 AM
 */
public class ComplexGlob extends Glob {
    public static final float CHARGE_FACTOR = 100f;
    public static final float SENSE_RADIUS = 100f;
    public static final float RECOVERY_TIME = 3.5f;

    private static enum ComplexGlobState {
        Seeking, Charging
    }

    private ComplexGlobState state = ComplexGlobState.Seeking;
    private float timeout = RECOVERY_TIME;

    public ComplexGlob(GlobDef def, Vector2 position) {
        super(def, position);
    }

    @Override
    public GlobType getType() {
        return GlobType.Complex;
    }

    @Override
    public void update(float delT) {
        if (state == ComplexGlobState.Seeking) {
            final Vector2 direction = getDirection();
            Physics.getInstance().queryBoundBox(getPosition(), SENSE_RADIUS,
                    new Physics.QueryResult() {
                        @Override
                        public boolean report(GameEntity entA) {
                            if (entA.getIdentifier() == EntityType.PLAYER) {
                                chargeAt(direction);
                                return false;
                            } else if (entA.getIdentifier() == EntityType.BULLET) {
                                dodgeBullet((Bullet) entA);
                                return false;
                            }
                            return true;
                        }
                    });
            if (state != ComplexGlobState.Seeking) {
                return;
            }
            direction.scl(def.speed);
            pushBody(direction);
        } else if (state == ComplexGlobState.Charging) {
            timeout -= delT;
            if (timeout <= 0) {
                state = ComplexGlobState.Seeking;
            }
        }
    }

    private void dodgeBullet(Bullet body) {
        Vector2 danger = body.getVelocity().cpy();
        danger.nor();
        Vector2 disp = getPosition().sub(body.getPosition());
        disp.nor();
        danger.nor().rotate(90);
        danger.scl(Math.signum(disp.dot(danger)));
        chargeAt(danger);
    }

    private void chargeAt(Vector2 direction) {
        state = ComplexGlobState.Charging;
        timeout = RECOVERY_TIME;
        pushBody(direction.scl(CHARGE_FACTOR * def.speed));
    }
}