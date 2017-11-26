package com.squidgames.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.squidgames.Constants;
import com.squidgames.FlowFree;
import com.squidgames.MapUtils.Mapa;
import com.squidgames.SwitchScreenAction;
import com.squidgames.Transitions;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by juan_ on 13-Jul-17.
 */

public class MapSelect extends BaseScene {
    private final String TAG = getClass().getSimpleName();
    private Stage stage;
    private HashMap<Integer,Color> colorMap;
    private final float MENU_WIDTH = 1080.00f;
    private final float MENU_HEIGHT = 1920.00f;
    private FileHandle MAP_FOLDER;
    private Screen nextScreen;

    public MapSelect(final Game game, FileHandle MapFolder) {
        super(game);
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

        this.MAP_FOLDER = MapFolder;

        Viewport viewport = new FitViewport(MENU_WIDTH,MENU_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport);

        Table table = new Table();
        table.setSize(MENU_WIDTH,MENU_HEIGHT);
        //table.setDebug(true);

        table.top().add(generateTitle());

        ArrayList<Mapa> content = new ArrayList<Mapa>();
        FileHandle[] jsonMaps = MAP_FOLDER.list();

        Table options = new Table();
        options.setFillParent(true);

        for (final FileHandle fileHandle: jsonMaps) {
            Label label = new Label(fileHandle.nameWithoutExtension(),new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeMedium"), Color.WHITE));
            label.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    Gdx.app.log(TAG,"Selected map: " + fileHandle.name());
                    nextScreen = new GameScreen(game,fileHandle, MAP_FOLDER);
                    hide();
                    return true;
                }
            });
            options.add(label).row();
        }


        stage.addActor(table);
        stage.addActor(options);

    }

    @Override
    public void show() {
        for (Actor actor: stage.getActors()) {
            actor.addAction(Actions.fadeOut(0));
            actor.addAction(Actions.fadeIn(Constants.TRANSITION_TIME));
        }
        Gdx.input.setInputProcessor(stage);
    }

    //TODO: Ver porque no se esta haciendo el cambio de pantalla
    @Override
    public void hide() {
        Gdx.app.log(TAG,"Hiding screen");
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(Actions.fadeOut(Constants.TRANSITION_TIME));
        sequenceAction.addAction(new SwitchScreenAction(game, nextScreen));
        stage.addAction(sequenceAction);
        /*
        for (Actor actor: stage.getActors()) {
            SequenceAction sequenceAction = new SequenceAction();
            sequenceAction.addAction(Actions.fadeOut(Constants.TRANSITION_TIME));
            sequenceAction.addAction(new SwitchScreenAction(game, nextScreen));
            actor.addAction(sequenceAction);
        }*/
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
    protected void handleKeyBack() {
        game.setScreen(new GameModeScreen(game));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,true);
    }


    @Override
    public void dispose() {
        stage.dispose();
    }

}