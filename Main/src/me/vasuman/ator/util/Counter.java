package me.vasuman.ator.util;

/**
 * Ator
 * User: vasuman
 * Date: 1/12/14
 * Time: 4:25 PM
 */
public class Counter {
    private float interval;
    private float count = 0;

    public Counter(float interval) {
        this.interval = interval;
    }

    public boolean update(float delT) {
        if ((count -= delT) < 0) {
            count = interval;
            return true;
        }
        return false;
    }

    public void reset() {
        count = interval;
    }
}
