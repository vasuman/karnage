package com.bleatware.karnage.screens;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * Ator
 * User: vasuman
 * Date: 1/11/14
 * Time: 12:31 PM
 */
public class Layout {
    public static Vector2 getPinnedPosition(Actor actor, Vector2 position) {
        return position.add(-actor.getWidth() / 2, -actor.getHeight() / 2);
    }

    public static void setActorCenter(Actor actor, float x, float y) {
        setActorCenter(actor, new Vector2(x, y));
    }

    public static void setActorCenter(Actor actor, Vector2 position) {
        getPinnedPosition(actor, position);
        actor.setPosition(position.x, position.y);
    }

    public static Action moveToCenter(Actor actor, float x, float y) {
        return moveToCenter(actor, new Vector2(x, y));
    }

    public static Action moveToCenter(Actor actor, Vector2 position) {
        getPinnedPosition(actor, position);
        return Actions.moveTo(position.x, position.y);
    }

    public static Vector2 getStageCenter(Stage stage) {
        return new Vector2(stage.getWidth() / 2, stage.getHeight() / 2);
    }
}
