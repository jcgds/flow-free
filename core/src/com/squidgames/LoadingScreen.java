package com.squidgames;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by juan_ on 11-Aug-17.
 */
//TODO: Implementar Viewport y dibujar correctamente el logo centrado y que entre en la pantalla

public class LoadingScreen extends ScreenAdapter {
    Game game;
    AssetHandler assetHandler;
    Texture logo;
    SpriteBatch batch;

    public LoadingScreen(Game game, AssetHandler handler) {
        this.game = game;
        this.assetHandler = handler;
        logo = new Texture(Gdx.files.internal("logo.png"));
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255,255,255,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        //Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2
        batch.draw(logo,0,0);//,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.end();
        if (assetHandler.manager.update()) {
            FlowFree.MAPAS = MapLoader.load();
            FlowFree.picarMapas();

            FlowFree.GAME_FONTS.put("cafeBig", assetHandler.manager.get("cafeBig.ttf",BitmapFont.class));
            FlowFree.GAME_FONTS.put("cafeMedium", assetHandler.manager.get("cafeMedium.ttf",BitmapFont.class));
            FlowFree.GAME_FONTS.put("cafeSmall", assetHandler.manager.get("cafeSmall.ttf",BitmapFont.class));
            FlowFree.GAME_FONTS.put("orangeBig", assetHandler.manager.get("orangeBig.ttf",BitmapFont.class));
            FlowFree.GAME_FONTS.put("orangeMedium", assetHandler.manager.get("orangeMedium.ttf",BitmapFont.class));
            FlowFree.GAME_FONTS.put("orangeSmall", assetHandler.manager.get("orangeSmall.ttf",BitmapFont.class));

            game.setScreen(new MainMenu(game));
        }
    }
}
