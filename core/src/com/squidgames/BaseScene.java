package com.squidgames;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;

/**
 * Created by juan_ on 14-Aug-17.
 */

public class BaseScene extends ScreenAdapter {
    protected Game game;
    private boolean keyHandled;

    public BaseScene(Game game) {
        this.game = game;
        keyHandled = false;
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);
    }

    @Override
    public void render(float delta) {
        FlowFree.clearScreen();
        super.render(delta);

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            if (keyHandled)
                return;
            handleKeyBack();
            keyHandled = true;
        } else
            keyHandled = false;
    }

    protected void handleKeyBack() {
        Gdx.app.log(getClass().getSimpleName(),"Back key pressed");
    }
}
