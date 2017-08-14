package com.squidgames;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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

public class GameScreen extends BaseScene {
    private float GAME_HEIGHT = 18;
    private float GAME_WIDTH = 10;
    private float MENU_WIDTH = 1080;
    private float MENU_HEIGHT = 1920;
    private float GAME_PAD = 1;
    Stage textStage;
    Stage mapStage;
    ShapeRenderer renderer;
    Mapa gameMap;
    FileHandle MAP_FOLDER;

    public GameScreen(Game game, FileHandle fileHandle, FileHandle MAP_FOLDER) {
        super(game);
        this.MAP_FOLDER = MAP_FOLDER;
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
        if (MAP_FOLDER.name().equals("MapasCuadrados"))
            gameMap = new TableroCuadros(fileHandle,renderer);
        else if (MAP_FOLDER.name().equals("MapasHexagonales")) {
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
        super.render(delta);
        FlowFree.clearScreen();
        if (gameMap.isCompleted()) {
            game.setScreen(new LevelCompleted(game,gameMap,MAP_FOLDER));
        }
        renderer.setProjectionMatrix(textStage.getViewport().getCamera().combined);
        textStage.draw();
        renderer.setProjectionMatrix(mapStage.getViewport().getCamera().combined);
        mapStage.act();
        mapStage.draw();
    }

    @Override
    protected void handleKeyBack() {
        game.setScreen(new MapSelect(game,MAP_FOLDER));
    }

    @Override
    public void resize(int width, int height) {
        textStage.getViewport().update(width,height,true);
        mapStage.getViewport().update(width,height,true);
    }

}
