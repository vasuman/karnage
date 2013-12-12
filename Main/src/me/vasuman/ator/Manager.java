package me.vasuman.ator;

import me.vasuman.ator.entities.GameEntity;

import java.util.ArrayList;
import java.util.HashMap;

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

    public static HashMap map = new HashMap();

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
            if (Drawable.class.isInstance(e)) {
                ((Drawable) e).getDrawer().draw();
            }
        }
    }

    public void clear() {
        for (int i = entities.size() - 1; i >= 0; i--) {
            entities.get(i).destroy();
            entities.remove(i);
        }
    }
}
