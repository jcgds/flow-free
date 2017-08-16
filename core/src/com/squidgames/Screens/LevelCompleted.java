package com.squidgames.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.squidgames.FlowFree;
import com.squidgames.MapUtils.Mapa;

/**
 * Created by juan_ on 09-Jul-17.
 */

public class LevelCompleted extends BaseScene {
    private Stage stage;
    private Label.LabelStyle labelStyle;
    private Label[] message = new Label[14];
    private Mapa map;
    private Table firstLine, secondLine;
    private FileHandle MAP_FOLDER;

    public LevelCompleted(Game game, Mapa mapa, FileHandle MAP_FOLDER){
        super(game);
        this.MAP_FOLDER = MAP_FOLDER;
        this.map = mapa;
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));

        labelStyle = new Label.LabelStyle();
        labelStyle.font = FlowFree.GAME_FONTS.get("orangeBig");
        labelStyle.fontColor = Color.WHITE;

        Table table = new Table();
        table.setTouchable(Touchable.enabled);
        table.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                LevelCompleted.this.game.setScreen(new GameModeScreen(LevelCompleted.this.game));
                return true;
            }
        });

        table.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        createTitle();

        table.add(this.firstLine);
        table.row();
        table.add(this.secondLine);

        stage.addActor(table);
    }

    public RepeatAction labelEfecto(int i) {
        float movementPar = Gdx.graphics.getWidth()/30f; //27
        float movementOdd = Gdx.graphics.getWidth()/21.6f;
        SequenceAction sequenceAction;
        if (i % 2 == 0) {
            sequenceAction = new SequenceAction(Actions.moveBy(0,movementPar,2f),Actions.moveBy(0,-movementPar,2f));
        } else {
            sequenceAction = new SequenceAction(Actions.moveBy(0,movementOdd,2f),Actions.moveBy(0,-movementOdd,2f));
        }

        RepeatAction repeatAction = new RepeatAction();
        repeatAction.setCount(RepeatAction.FOREVER);
        repeatAction.setAction(sequenceAction);
        return repeatAction;
    }

    private void createTitle() {

        //region Create labels for each letter
        message[0] = new Label("L", labelStyle);
        message[1] = new Label("e", labelStyle);
        message[2] = new Label("v", labelStyle);
        message[3] = new Label("e", labelStyle);
        message[4] = new Label("l", labelStyle);
        message[5] = new Label("C", labelStyle);
        message[6] = new Label("o", labelStyle);
        message[7] = new Label("m", labelStyle);
        message[8] = new Label("p", labelStyle);
        message[9] = new Label("l", labelStyle);
        message[10] = new Label("e", labelStyle);
        message[11] = new Label("t", labelStyle);
        message[12] = new Label("e", labelStyle);
        message[13] = new Label("d", labelStyle);
        //endregion

        firstLine = new Table();
        secondLine = new Table();

        for (int i =1; i<=5; i++) {
            message[i-1].addAction(labelEfecto(i));
            firstLine.add(message[i-1]);
        }

        for (int i =6; i<=14; i++) {
            message[i-1].addAction(labelEfecto(i));
            secondLine.add(message[i-1]);
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        FlowFree.clearScreen();

        stage.act();
        stage.draw();
    }

    @Override
    protected void handleKeyBack() {
        game.setScreen(new MapSelect(game,MAP_FOLDER));
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
