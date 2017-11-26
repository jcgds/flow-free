package com.squidgames.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.squidgames.AssetHandler;
import com.squidgames.Constants;
import com.squidgames.FlowFree;
import com.squidgames.SwitchScreenAction;
import com.squidgames.Transitions;

/**
 * Created by juan_ on 11-Aug-17.
 */

public class LoadingScreen extends ScreenAdapter {
    private Game game;
    private AssetHandler assetHandler;
    private Stage stage;
    private Image logoActor;
    private Screen nextScreen;

    public LoadingScreen(Game game, AssetHandler handler) {
        this.game = game;
        this.assetHandler = handler;
        Viewport viewport = new FitViewport(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT);
        stage = new Stage(viewport);

        Table table = new Table();
        table.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        Texture logo = new Texture(Gdx.files.internal(Constants.LOGO_PATH));
        logoActor = new Image(logo);
        table.add(logoActor);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        if (assetHandler.manager.update()) {
            FlowFree.GAME_FONTS.put("cafeBig", assetHandler.manager.get("cafeBig.ttf",BitmapFont.class));
            FlowFree.GAME_FONTS.put("cafeMedium", assetHandler.manager.get("cafeMedium.ttf",BitmapFont.class));
            FlowFree.GAME_FONTS.put("cafeSmall", assetHandler.manager.get("cafeSmall.ttf",BitmapFont.class));
            FlowFree.GAME_FONTS.put("orangeBig", assetHandler.manager.get("orangeBig.ttf",BitmapFont.class));
            FlowFree.GAME_FONTS.put("orangeMedium", assetHandler.manager.get("orangeMedium.ttf",BitmapFont.class));
            FlowFree.GAME_FONTS.put("orangeSmall", assetHandler.manager.get("orangeSmall.ttf",BitmapFont.class));

            nextScreen = new MainMenu(game);
            hide();
        }

    }

    @Override
    public void show() {
        for (Actor actor: stage.getActors()) {
            actor.addAction(Actions.fadeOut(0));
            actor.addAction(Actions.fadeIn(Constants.TRANSITION_TIME));
        }
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(Actions.fadeOut(Constants.TRANSITION_TIME));
        sequenceAction.addAction(new SwitchScreenAction(game, nextScreen));
        stage.addAction(sequenceAction);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height);
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }
}
