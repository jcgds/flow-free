package com.squidgames;

import com.badlogic.gdx.Game;
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
public class GameScreen extends ScreenAdapter {
    private float GAME_HEIGHT = 18;
    private float GAME_WIDTH = 10;
    private float MENU_WIDTH = 1080;
    private float MENU_HEIGHT = 1920;
    private float GAME_PAD = 1;
    AssetManager manager;
    Stage textStage;
    Stage mapStage;
    ShapeRenderer renderer;
    Mapa gameMap;
    Game game;

    public GameScreen(Game game, FileHandle fileHandle, String mapType) {
        this.game = game;
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

        Label l = new Label("Jugando flow free",new Label.LabelStyle(font, Color.WHITE));
        textorg.add(l);

        //TODO: Crear una buena capa de abstraccion que me permita crear un nuevo mapa solo pasandole un filehandle
        if (mapType.equals("MapasCuadrados"))
            gameMap = new TableroCuadros(fileHandle,renderer);
        else if (mapType.equals("MapasHexagonales")) {
            gameMap= new TableroHexagonos(fileHandle,renderer);
        } else
            gameMap = null;

        if (gameMap != null)
            gameMap.addToStage(mapStage);
        textStage.addActor(textorg);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(mapStage);
    }

    @Override
    public void render(float delta) {
        FlowFree.clearScreen();
        if (gameMap.isCompleted()) {
            game.setScreen(new LevelCompleted(game,gameMap));
        }
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
