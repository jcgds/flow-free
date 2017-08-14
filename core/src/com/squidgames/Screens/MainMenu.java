package com.squidgames.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.squidgames.FlowFree;

/**
 * Created by juan_ on 11-Aug-17.
 */

public class MainMenu extends BaseScene {
    private final String TAG = getClass().getSimpleName();
    private final float MENU_WIDTH = 1080.00f;
    private final float MENU_HEIGHT = 1920.00f;
    Stage stage;

    public MainMenu(Game game) {
        super(game);
    }

    @Override
    public void show() {

        OrthographicCamera camera = new OrthographicCamera();
        Viewport viewport = new FitViewport(MENU_WIDTH,MENU_HEIGHT,camera);
        stage = new Stage(viewport);

        Table table = new Table();
        table.setSize(MENU_WIDTH,MENU_HEIGHT);
        //table.setDebug(true);

        Table title = new Table();
        Label l1 = new Label("F", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeBig"), Color.GREEN));
        Label l2 = new Label("L", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeBig"),Color.BLUE));
        Label l3 = new Label("O", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeBig"),Color.RED));
        Label l4 = new Label("W", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeBig"),Color.ORANGE));
        title.add(l1,l2,l3,l4);
        table.add(title).row();

        Label play_message = new Label("Start",new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeMedium"),Color.WHITE));
        play_message.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log(TAG,"Play button clicked");
                game.setScreen(new GameModeScreen(game));
                return true;
            }

        });
        table.add(play_message).padTop(50);

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
       super.render(delta);
        FlowFree.clearScreen();
        stage.act();
        stage.draw();
    }

    @Override
    protected void handleKeyBack() {
        game.dispose();
        Gdx.app.exit();
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log(TAG,"Resizing. New dimensions: " + width + "," + height);
        stage.getViewport().update(width,height,true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

