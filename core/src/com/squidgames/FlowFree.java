package com.squidgames;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class FlowFree extends Game implements InputProcessor{
	public static Random rnd;

	public static HashMap<String,BitmapFont> GAME_FONTS;
	public static ArrayList<Animation<TextureRegion>> animaciones;
	public static ArrayList<Mapa> MAPAS;
	public static HashMap<Integer,ArrayList<Mapa>> picado;

	AssetHandler assetHandler;
	@Override
	public void create () {
		animaciones = new ArrayList<Animation<TextureRegion>>();
		picado = new HashMap<Integer, ArrayList<Mapa>>();
		rnd = new Random();
		GAME_FONTS = new HashMap<String, BitmapFont>();

		assetHandler = new AssetHandler();
		assetHandler.load();

		this.setScreen(new LoadingScreen(this,assetHandler));
	}

	public static void clearScreen() {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	public static void picarMapas(){
		for (Mapa m: MAPAS) {
			Gdx.app.log("MAP","" + m + "  Dimension: " + m.getDimension());
			if (picado.get(m.getDimension()) == null){
				//Creamos lista
				picado.put(m.getDimension(),new ArrayList<Mapa>());
			}
			picado.get(m.getDimension()).add(m);
		}

		Gdx.app.log("PICADO", "" + picado );
	}

	@Override
	public void dispose () {
		super.dispose();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width,height);
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
