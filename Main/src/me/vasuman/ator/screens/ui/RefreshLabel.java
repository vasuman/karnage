package me.vasuman.ator.screens.ui;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Ator
 * User: vasuman
 * Date: 1/12/14
 * Time: 11:22 PM
 */
public class RefreshLabel extends Label {
    public static interface RefreshTrigger {
        public String getText();
    }

    public RefreshLabel(Skin skin, final RefreshTrigger trigger) {
        super(trigger.getText(), skin);
        addAction(new Action() {
            @Override
            public boolean act(float delta) {
                setText(trigger.getText());
                return false;
            }
        });
    }
}
