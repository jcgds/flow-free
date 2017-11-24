package com.squidgames;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.HashMap;


public class FlowFree extends Game{
	public static HashMap<String,BitmapFont> GAME_FONTS;
	AssetHandler assetHandler;

	@Override
	public void create () {
		GAME_FONTS = new HashMap<String, BitmapFont>();
		assetHandler = new AssetHandler();
		assetHandler.load();
		this.setScreen(new com.squidgames.Screens.LoadingScreen(this,assetHandler));
	}
}
