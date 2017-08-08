package com.squidgames;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class FlowFree extends Game implements InputProcessor{
	public static Random rnd;
	public static ShapeRenderer renderer;
	private static StretchViewport viewport;
	static float GAME_SCREEN_WIDTH;
	static float GAME_SCREEN_HEIGHT;
	static float DEVICE_TO_GAME_RATIO;
	public static BitmapFont fontBig,fontMedium;
	public static HashMap<String,BitmapFont> GAME_FONTS;
	public static ArrayList<Animation<TextureRegion>> animaciones;
	public static ArrayList<Mapa> MAPAS;
	public static HashMap<Integer,ArrayList<Mapa>> picado;


	@Override
	public void create () {
		animaciones = new ArrayList<Animation<TextureRegion>>();
		picado = new HashMap<Integer, ArrayList<Mapa>>();
		rnd = new Random();
		this.calculateGameScreen();
		GAME_FONTS = new HashMap<String, BitmapFont>();

		//*** ORANGE TTF GENERATOR ***
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/orange.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter =  new FreeTypeFontGenerator.FreeTypeFontParameter();

		parameter.size = (int)(Gdx.graphics.getWidth()/6f);
		GAME_FONTS.put("orangeBig",generator.generateFont(parameter));

		parameter.size = (int)(Gdx.graphics.getWidth()/8f);
		GAME_FONTS.put("orangeMedium",generator.generateFont(parameter));

		//*** CAFE TTF GENERATOR ***
		generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/cafe.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter0= new FreeTypeFontGenerator.FreeTypeFontParameter();
		FreeTypeFontGenerator.FreeTypeFontParameter parameter1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();

		parameter0.size = Gdx.graphics.getWidth()/5;
		GAME_FONTS.put("cafeBig",generator.generateFont(parameter0));
		parameter1.size = Gdx.graphics.getWidth()/10;
		GAME_FONTS.put("cafeMedium",generator.generateFont(parameter1));
		parameter2.size = Gdx.graphics.getWidth()/12;
		GAME_FONTS.put("cafeSmall",generator.generateFont(parameter2));
		parameter2.size = Gdx.graphics.getWidth()/24;
		GAME_FONTS.put("cafeSmallSmall", generator.generateFont(parameter2));


		Gdx.app.log("Loading_Animations","Starting to load...");

		animaciones = new ArrayList<Animation<TextureRegion>>();
		animaciones.add(GifDecoder.loadGIFAnimation(Animation.PlayMode.NORMAL,Gdx.files.internal("fireworkYellow.gif").read()));
		animaciones.add(GifDecoder.loadGIFAnimation(Animation.PlayMode.NORMAL,Gdx.files.internal("fireworkGreen.gif").read()));
		animaciones.add(GifDecoder.loadGIFAnimation(Animation.PlayMode.NORMAL,Gdx.files.internal("fireworkMorado.gif").read()));

		Gdx.app.log("Loading_Animations","Finished loading");



		OrthographicCamera camera = new OrthographicCamera(GAME_SCREEN_WIDTH,GAME_SCREEN_HEIGHT);
		camera.setToOrtho(false,GAME_SCREEN_WIDTH,GAME_SCREEN_HEIGHT);
		viewport = new StretchViewport(GAME_SCREEN_WIDTH,GAME_SCREEN_HEIGHT,camera);

		renderer = new ShapeRenderer();
		renderer.setProjectionMatrix(viewport.getCamera().combined);

		MAPAS = MapLoader.load();
		picarMapas();

		this.setScreen(new MainMenu(this));

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}

	public void calculateGameScreen() {
		float aspectRatio = (float)Gdx.graphics.getHeight()/Gdx.graphics.getWidth();
		GAME_SCREEN_WIDTH = 10;
		GAME_SCREEN_HEIGHT = GAME_SCREEN_WIDTH * aspectRatio;
	}

	public void picarMapas(){
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

	}

	@Override
	public void resize(int width, int height) {
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
