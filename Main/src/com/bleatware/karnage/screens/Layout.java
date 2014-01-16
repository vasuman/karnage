package com.bleatware.karnage.screens;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Ator
 * User: vasuman
 * Date: 1/11/14
 * Time: 12:31 PM
 */
public class Layout {
    public static enum Position {
        Top, TopLeft, TopRight, Bottom, BottomLeft, BottomRight, Center, Left, Right
    }

    public static Vector2 getPinnedPosition(Actor actor, Vector2 position) {
        return position.add(-actor.getWidth() / 2, -actor.getHeight() / 2);
    }

    public static void setActorCenter(Actor actor, float x, float y) {
        setActorCenter(actor, new Vector2(x, y));
    }

    public static void offsetPosition(Actor actor, float offsetX, float offsetY) {
        actor.setPosition(actor.getX() + offsetX, actor.getY() + offsetY);
    }

    public static void setActorPosition(Actor actor, Stage stage, Position alignment) {
        Vector2 position = new Vector2();
        switch (alignment) {
            case TopLeft:
            case TopRight:
            case Top:
                position.y = stage.getHeight() - actor.getHeight();
                break;
            case Left:
            case Right:
            case Center:
                position.y = stage.getHeight() / 2 - actor.getHeight() / 2;
                break;
        }
        switch (alignment) {
            case Bottom:
            case Center:
            case Top:
                position.x = stage.getWidth() / 2 - actor.getWidth() / 2;
                break;
            case BottomRight:
            case TopRight:
            case Right:
                position.x = stage.getWidth() - actor.getWidth();
                break;
        }
        actor.setPosition(position.x, position.y);
    }

    public static void setActorCenter(Actor actor, Vector2 position) {
        getPinnedPosition(actor, position);
        actor.setPosition(position.x, position.y);
    }

}
