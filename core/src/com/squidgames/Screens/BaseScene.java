package com.squidgames.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.squidgames.FlowFree;

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
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
