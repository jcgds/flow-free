package com.squidgames;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.HashMap;

/**
 * Created by juan_ on 13-Jul-17.
 */

public class DimesionSelect implements Screen {
    private final String TAG = getClass().getSimpleName();
    private Stage stage;
    private Game game;
    private HashMap<Integer,Color> colorMap;
    private final float MENU_WIDTH = 1080.00f;
    private final float MENU_HEIGHT = 1920.00f;

    public DimesionSelect(final Game game) {
        this.game = game;

        colorMap = new HashMap<Integer, Color>();
        colorMap.put(1,Color.CYAN);
        colorMap.put(2,Color.RED);
        colorMap.put(3,Color.GREEN);
        colorMap.put(4,Color.PINK);
        colorMap.put(5,Color.MAGENTA);
        colorMap.put(6,Color.YELLOW);
        colorMap.put(7,Color.BLUE);
        colorMap.put(8,Color.ORANGE);
    }


    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
        Viewport viewport = new FitViewport(MENU_WIDTH,MENU_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setSize(MENU_WIDTH,MENU_HEIGHT);
        //table.setDebug(true);

        table.top().add(generateTitle()).center();

        //Creamos otra Tabla que cubra la pantalla porque no logro colocar las opciones en el medio
        //con el titulo en el tope de la tabla.
        //TODO: Implementar correctamente en una sola tabla

        Table options = new Table();
        options.setFillParent(true);

        int cont=1;
        for (final Integer n: FlowFree.picado.keySet()){
            String labelText = String.format("%d x %d",n,n);
            Gdx.app.log("color", cont + " : " + colorMap.get(cont));
            Label label =  new Label(labelText,new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeMedium"),colorMap.get(cont)));
            label.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    Gdx.app.log(TAG,"Touched dimension option");
                    //DimesionSelect.this.game.setScreen(new MapSelect(DimesionSelect.this.game,FlowFree.picado.get(n)));
                    return true;
                }
            });
            cont++;
            options.add(label).padBottom(10).row();
        }

        stage.addActor(table);
        stage.addActor(options);
    }

    private Table generateTitle() {
        Table title = new Table();

        title.add(new Label("d", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeBig"),Color.GREEN)));
        title.add(new Label("i", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeBig"),Color.WHITE)));
        title.add(new Label("m", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeBig"),Color.RED)));
        title.add(new Label("e", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeBig"),Color.ORANGE)));
        title.add(new Label("n", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeBig"),Color.PINK)));
        title.add(new Label("s", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeBig"),Color.GRAY)));
        title.add(new Label("i", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeBig"),Color.WHITE)));
        title.add(new Label("o", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeBig"),Color.YELLOW)));
        title.add(new Label("n", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeBig"), Color.VIOLET)));
        title.add( new Label("s", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeBig"),Color.SALMON)));

        return title;
    }

    @Override
    public void render(float delta) {
        //TODO: Cambiar esto por una manera mas elegante de lidiar con el backbutton
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            game.setScreen(new MainMenu(game));
        }

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log(TAG,"Resizing. New dimensions: " + width + "," + height);
        stage.getViewport().update(width,height,true);
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
}
