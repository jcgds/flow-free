package com.squidgames;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Created by juan_ on 10-Jul-17.
 */

public class GameScreen implements Screen {
    private Stage stage;
    private Game game;
    private Mapa map;
    private boolean showing;

    public GameScreen(Game game,Mapa mapa, boolean showing) {
        this.showing = showing;
        this.game = game;
        OrthographicCamera camera = new OrthographicCamera(FlowFree.GAME_SCREEN_WIDTH,FlowFree.GAME_SCREEN_HEIGHT);
        camera.setToOrtho(false,FlowFree.GAME_SCREEN_WIDTH,FlowFree.GAME_SCREEN_HEIGHT);
        StretchViewport viewport = new StretchViewport(FlowFree.GAME_SCREEN_WIDTH,FlowFree.GAME_SCREEN_HEIGHT,camera);

        stage = new Stage(viewport);
        map = mapa;
        mapa.addToStage(stage);
        stage.addActor(new BackTriangle(new Vector2(0.25f,FlowFree.GAME_SCREEN_HEIGHT - 0.8f),0.5f,this));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void render(float delta) {
        if (map.isCompleted() && showing == false) {
            Gdx.app.log("GG", "ez");
            game.setScreen(new LevelCompleted(game, map));
        } else if(!map.isCompleted()){
            showing = false;
        }
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

    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Mapa getMap() {
        return map;
    }

    public void setMap(Mapa map) {
        this.map = map;
    }

    public boolean isShowing() {
        return showing;
    }

    public void setShowing(boolean showing) {
        this.showing = showing;
    }
}
