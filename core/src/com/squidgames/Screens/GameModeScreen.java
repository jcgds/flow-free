package com.squidgames.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
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
import com.squidgames.SwitchScreenAction;
import com.squidgames.Transitions;

/**
 * Created by juan_ on 12-Aug-17.
 */

public class GameModeScreen extends BaseScene {
    private final String TAG = getClass().getSimpleName();
    private final String TITLE = "Select Game mode";
    private float SCREEN_WIDTH = 1080;
    private float SCREEN_HEIGHT = 1920;
    private FileHandle MAP_FOLDER;
    private FileHandle[] GAME_MODES;
    private Stage stage;
    private Screen nextScreen;

    /*TODO:
        Necesitaremos tener una estructura que almacene todos los Labels que son los botones de los
        modos de juego para poder asignar a cada uno su SwicthScreen Apropiado en el fadeOut();
     */
    public GameModeScreen(final Game game) {
        super(game);
        Viewport viewport = new FitViewport(SCREEN_WIDTH,SCREEN_HEIGHT);
        stage = new Stage(viewport);

        MAP_FOLDER = Gdx.files.internal("Mapas/");
        GAME_MODES = MAP_FOLDER.list();
        Gdx.app.log(TAG,"Game modes detected: " + GAME_MODES.length);

        Table title = new Table();
        title.setBounds(0,0,SCREEN_WIDTH,SCREEN_HEIGHT);
        Label title_label = new Label(TITLE,new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeMedium"), Color.CYAN));
        title.top().add(title_label).center();

        Table content = new Table();
        content.setBounds(0,0,SCREEN_WIDTH,SCREEN_HEIGHT);
        content.center();

        for (final FileHandle f: GAME_MODES) {
            Gdx.app.log(TAG,f.name());
            Label label = new Label(f.name(),new Label.LabelStyle(FlowFree.GAME_FONTS.get("cafeMedium"), Color.WHITE));

            label.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    Gdx.app.log(TAG,"Selected mode: " + f.name());

                    nextScreen = new MapSelect(game,f);
                    hide();
                    return true;
                }
            });

            content.add(label).padBottom(SCREEN_HEIGHT/100).row();
        }

        stage.addActor(content);
        stage.addActor(title);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,true);
    }

    @Override
    public void show() {
        for (Actor actor: stage.getActors()) {
            actor.addAction(Actions.fadeOut(0));
            actor.addAction(Actions.fadeIn(Constants.TRANSITION_TIME));
        }
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(Actions.fadeOut(Constants.TRANSITION_TIME));
        sequenceAction.addAction(new SwitchScreenAction(game, nextScreen));
        stage.addAction(sequenceAction);
    }

    @Override
    protected void handleKeyBack() {
        //TODO: Hacer fadeOut de este screen y fadeIn del anterior (MainMenu)
        game.setScreen( new MainMenu(game));
    }
}
