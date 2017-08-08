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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by juan_ on 13-Jul-17.
 */

public class MapSelect implements Screen {
    Stage stage;
    Game game;
    private ArrayList<Mapa> content;

    public MapSelect(final Game game, ArrayList<Mapa> mapas) {

        //region ColorMap
        HashMap<Integer,Color> colorMap = new HashMap<Integer, Color>();

        colorMap.put(1,Color.PURPLE);
        colorMap.put(2,Color.ORANGE);
        colorMap.put(3,Color.YELLOW);
        colorMap.put(4,Color.MAGENTA);
        colorMap.put(5,Color.RED);
        colorMap.put(6,Color.PINK);
        colorMap.put(7,Color.BLUE);
        colorMap.put(8,Color.GREEN);
        //endregion

        stage = new Stage(new ScreenViewport());
        this.game = game;
        this.content = mapas;

        Table table = new Table();
        table.setBounds(0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        Table title = new Table();
        title.setPosition(0,0);
        Label l1 = new Label("M", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeMedium"),Color.GREEN));
        Label l2 = new Label("a", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeMedium"),Color.WHITE));
        Label l3 = new Label("p", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeMedium"),Color.RED));
        Label l4 = new Label("s", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeMedium"),Color.ORANGE));

        title.add(l1,l2,l3,l4);
        title.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()*0.1f);

        Table options = new Table();
        options.setFillParent(true);

        int cont=1;
        for (final Mapa n: content){
            String labelText = String.format("Mapa %d",cont);
            if (n instanceof TableroHexagonos)
                labelText = String.format("Mapa %d Hex",cont);
            Label label =  new Label(labelText,new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeSmall"),colorMap.get(cont)));
            label.addListener(new ClickListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    MapSelect.this.game.setScreen(new GameScreen(MapSelect.this.game,n,true));
                    return true;
                }
            });
            cont++;
            options.add(label).row();
        }


        Label backButton = new Label("<--", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeSmall"),Color.WHITE));
        title.setWidth(Gdx.graphics.getWidth());
        backButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new DimesionSelect(game));
                return true;

            }

        });

        Table back = new Table();
        back.setFillParent(true);
        back.add(backButton).expand().top().left();


        table.add(title).expand().top();
        table.row();

        stage.addActor(table);
        stage.addActor(back);
        stage.addActor(options);

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