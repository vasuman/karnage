package com.bleatware.karnage;

import com.bleatware.karnage.entities.GameEntity;
import com.bleatware.karnage.levels.Level;

import java.util.ArrayList;

/**
 * Ator
 * User: vasuman
 * Date: 12/2/13
 * Time: 6:05 PM
 */
public class Manager {
    private final ArrayList<GameEntity> entities = new ArrayList<GameEntity>();
    private int globalIdx = 0;
    private static Manager ourInstance = new Manager();
    public static Level level;

    public static Manager getInstance() {
        return ourInstance;
    }

    private Manager() {
    }

    public int registerEntity(GameEntity e) {
        entities.add(e);
        return globalIdx++;
    }

    public void update(float delT) {
        for (int i = entities.size() - 1; i >= 0; i--) {
            GameEntity e = entities.get(i);
            if (e.isDead()) {
                e.destroy();
                entities.remove(i);
                continue;
            }
            e.update(delT);
        }
    }

    public void draw() {
        for (GameEntity e : entities) {
            if (e instanceof Drawable) {
                ((Drawable) e).getDrawer().draw();
            }
        }
    }

    public void init(Level level) {
        clear();
        Manager.level = level;
    }

    public void clear() {
        entities.clear();
    }
}
