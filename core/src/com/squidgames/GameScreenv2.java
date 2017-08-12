package com.squidgames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by juan_ on 12-Aug-17.
 */

//TODO: Modificar los Screens DimesionSelect y MapSelect, deberia hacer una pantalla que permita seleccionar entre hexagonos o cuadrados
public class GameScreenv2 extends ScreenAdapter {
    private float GAME_HEIGHT = 18;
    private float GAME_WIDTH = 10;
    private float MENU_WIDTH = 1080;
    private float MENU_HEIGHT = 1920;
    private float GAME_PAD = 1;
    AssetManager manager;
    Stage textStage;
    Stage mapStage;
    ShapeRenderer renderer;

    public GameScreenv2(FileHandle fileHandle) {
        renderer = new ShapeRenderer();
        Viewport textViewport = new FitViewport(MENU_WIDTH,MENU_HEIGHT);
        textStage = new Stage(textViewport);

        Viewport mapViewport = new FitViewport(GAME_WIDTH,GAME_HEIGHT);
        mapStage = new Stage(mapViewport);

        BitmapFont font = new BitmapFont();
        font.getData().setScale(5);
        //font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Table textorg = new Table();
        textorg.top().left();
        textorg.setBounds(0,0,MENU_WIDTH,MENU_HEIGHT);
        //textorg.setDebug(true);

        Label l = new Label("Jugando flow free",new Label.LabelStyle(font, Color.WHITE));
        textorg.add(l);

        TableroCuadros t = new TableroCuadros(fileHandle,renderer);
        t.addToStage(mapStage);
        textStage.addActor(textorg);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(mapStage);
    }

    @Override
    public void render(float delta) {
        FlowFree.clearScreen();
        renderer.setProjectionMatrix(textStage.getViewport().getCamera().combined);
        textStage.draw();
        renderer.setProjectionMatrix(mapStage.getViewport().getCamera().combined);
        FlowFree.renderer.setProjectionMatrix(mapStage.getViewport().getCamera().combined);
        mapStage.act();
        mapStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        textStage.getViewport().update(width,height,true);
        mapStage.getViewport().update(width,height,true);
    }

}
