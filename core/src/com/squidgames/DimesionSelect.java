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

import java.util.HashMap;

/**
 * Created by juan_ on 13-Jul-17.
 */

public class DimesionSelect implements Screen {
    private Stage stage;
    private Game game;

    public DimesionSelect(final Game game) {

        //region ColorMap
        HashMap<Integer,Color> colorMap = new HashMap<Integer, Color>();

        colorMap.put(1,Color.CYAN);
        colorMap.put(2,Color.RED);
        colorMap.put(3,Color.GREEN);
        colorMap.put(4,Color.PINK);
        colorMap.put(5,Color.MAGENTA);
        colorMap.put(6,Color.YELLOW);
        colorMap.put(7,Color.BLUE);
        colorMap.put(8,Color.ORANGE);
        //endregion

        stage = new Stage(new ScreenViewport());
        this.game = game;

        Table table = new Table();
        table.setBounds(0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        Table title = new Table();
        title.setPosition(0,0);
        Label l1 = new Label("d", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeMedium"),Color.GREEN));
        Label l2 = new Label("i", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeMedium"),Color.WHITE));
        Label l3 = new Label("m", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeMedium"),Color.RED));
        Label l4 = new Label("e", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeMedium"),Color.ORANGE));
        Label l5 = new Label("n", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeMedium"),Color.PINK));
        Label l6 = new Label("s", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeMedium"),Color.GRAY));
        Label l7 = new Label("i", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeMedium"),Color.WHITE));
        Label l8 = new Label("o", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeMedium"),Color.YELLOW));
        Label l9 = new Label("n", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeMedium"), Color.VIOLET));
        Label l10 = new Label("s", new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeMedium"),Color.SALMON));


        title.add(l1,l2,l3,l4,l5,l6,l7,l8,l9,l10);
        title.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()*0.1f);

        Table options = new Table();
        options.setFillParent(true);

        int cont=1;
        for (final Integer n: FlowFree.picado.keySet()){
            String labelText = String.format("%d x %d",n,n);
            Gdx.app.log("color", cont + " : " + colorMap.get(cont));
            Label label =  new Label(labelText,new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeSmall"),colorMap.get(cont)));
            label.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                    DimesionSelect.this.game.setScreen(new MapSelect(DimesionSelect.this.game,FlowFree.picado.get(n)));
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
                game.setScreen(new MainMenu(game));
                System.out.println("Touched!");
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
