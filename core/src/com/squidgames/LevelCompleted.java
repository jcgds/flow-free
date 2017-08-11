package com.squidgames;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by juan_ on 09-Jul-17.
 */

public class LevelCompleted implements Screen {
    private Stage stage;
    private Game game;
    private Label.LabelStyle labelStyle;
    private Label[] message = new Label[14];
    float elapsed;
    private Mapa map;
    private Table firstLine, secondLine;

    public LevelCompleted(Game game, Mapa mapa){
        this.map = mapa;
        stage = new Stage(new ScreenViewport());
        this.game = game;

        labelStyle = new Label.LabelStyle();
        labelStyle.font = FlowFree.GAME_FONTS.get("orangeBig");
        labelStyle.fontColor = Color.WHITE;

        Table table = new Table();
        table.setTouchable(Touchable.enabled);
        table.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //LevelCompleted.this.game.setScreen(new GameScreen(LevelCompleted.this.game,LevelCompleted.this.map, true));
                LevelCompleted.this.game.setScreen(new DimesionSelect(LevelCompleted.this.game));

                return true;
            }
        });

        table.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        //table.setDebug(true);
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
        int i = FlowFree.rnd.nextInt(20);
        if (i == 0) {
            Animation<TextureRegion> anim = FlowFree.animaciones.get(FlowFree.rnd.nextInt(FlowFree.animaciones.size()));
            Firework f = new Firework(FlowFree.rnd.nextFloat()*Gdx.graphics.getWidth(),FlowFree.rnd.nextFloat()*Gdx.graphics.getHeight(),anim);
            stage.addActor(f);
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
        stage.dispose();
    }
}
