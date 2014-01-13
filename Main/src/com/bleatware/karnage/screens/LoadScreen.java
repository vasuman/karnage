package com.bleatware.karnage.screens;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.bleatware.karnage.MainGame;

/**
 * Ator
 * User: vasuman
 * Date: 1/2/14
 * Time: 6:46 PM
 */
public class LoadScreen extends BaseScreen {
    public static interface LoadCallback {
        public void doneLoading();
    }

    private LoadCallback callback;

    public LoadScreen(LoadCallback callback, AssetDescriptor... descriptors) {
        for (AssetDescriptor descriptor : descriptors) {
            MainGame.assets.load(descriptor);
        }
        this.callback = callback;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        // Draw something
        if (MainGame.assets.update()) {
            callback.doneLoading();
        }
    }
}
