package com.squidgames;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by juan_ on 13-Jul-17.
 */

public class MainMenu implements Screen {
    private Stage stage;
    private Game game;

    public MainMenu(Game game) {
        stage = new Stage(new ScreenViewport());
        this.game = game;

        Table table = new Table();
        //table.setDebug(true);

        table.setBounds(0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        Label l1 = new Label("F", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeBig"), Color.GREEN));
        Label l2 = new Label("L", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeBig"),Color.BLUE));
        Label l3 = new Label("O", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeBig"),Color.RED));
        Label l4 = new Label("W", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeBig"),Color.ORANGE));

        Table title = new Table();
        title.add(l1,l2,l3,l4);

        Label m2 = new Label("play", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeSmall"),Color.WHITE));

        m2.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Touched!");
                MainMenu.this.game.setScreen(new DimesionSelect(MainMenu.this.game));
                return true;
            }
        });

        table.add(title).padBottom(Gdx.graphics.getWidth()/7.2f);
        table.row();
        table.add(m2).padBottom(Gdx.graphics.getWidth()/2.16f);
        table.row();

        Table autor1 = new Table(), autor2 = new Table();
        autor1.setFillParent(true);
        autor2.setFillParent(true);
        Label pedro = new Label("Pedro A. Pacheco",new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeSmallSmall"),Color.WHITE));
        Label juan = new Label("Juan C. Goncalves",new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeSmallSmall"),Color.WHITE));


        autor1.add(juan).expand().bottom().left().padLeft(50);
        autor2.add(pedro).expand().bottom().right().padRight(50);
        stage.addActor(table);
        stage.addActor(autor1);
        stage.addActor(autor2);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
