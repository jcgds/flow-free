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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by juan_ on 13-Jul-17.
 */

public class MapSelect implements Screen {
    private final String TAG = getClass().getSimpleName();
    Stage stage;
    Game game;
    HashMap<Integer,Color> colorMap;
    private final float MENU_WIDTH = 1080.00f;
    private final float MENU_HEIGHT = 1920.00f;
    private ArrayList<Mapa> content;

    public MapSelect(final Game game, ArrayList<Mapa> mapas) {

        //region ColorMap
        colorMap = new HashMap<Integer, Color>();

        colorMap.put(1,Color.PURPLE);
        colorMap.put(2,Color.ORANGE);
        colorMap.put(3,Color.YELLOW);
        colorMap.put(4,Color.MAGENTA);
        colorMap.put(5,Color.RED);
        colorMap.put(6,Color.PINK);
        colorMap.put(7,Color.BLUE);
        colorMap.put(8,Color.GREEN);
        //endregion

        this.game = game;
        this.content = mapas;

    }


    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
        Viewport viewport = new FitViewport(MENU_WIDTH,MENU_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setSize(MENU_WIDTH,MENU_HEIGHT);
        table.setDebug(true);

        table.top().add(generateTitle());

        //Creamos otra Tabla que cubra la pantalla porque no logro colocar las opciones en el medio
        //con el titulo en el tope de la tabla.
        //TODO: Implementar correctamente en una sola tabla

        Table options = new Table();
        options.setFillParent(true);

        int cont=1;
        for (final Mapa n: content){
            String labelText = String.format("Mapa %d",cont);
            if (n instanceof TableroHexagonos)
                labelText = String.format("Mapa %d Hex",cont);
            Label label =  new Label(labelText,new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeMedium"),colorMap.get(cont)));
            label.addListener(new ClickListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    Gdx.app.log(TAG,"Map selected");
                    MapSelect.this.game.setScreen(new GameScreen(MapSelect.this.game,n,true));
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
        Label l1 = new Label("M", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeBig"),Color.GREEN));
        Label l2 = new Label("a", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeBig"),Color.WHITE));
        Label l3 = new Label("p", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeBig"),Color.RED));
        Label l4 = new Label("s", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeBig"),Color.ORANGE));

        title.add(l1,l2,l3,l4);
        return title;
    }

    @Override
    public void render(float delta) {
        //TODO: Cambiar esto por una manera mas elegante de lidiar con el backbutton. Bugged: Me lleva directamente el mainMenu
        //Sucede porque el boton back sigue presionado cuando creamos el nuevo DimensionSelect y por lo tanto pela

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            game.setScreen(new DimesionSelect(game));
        }

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
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
}