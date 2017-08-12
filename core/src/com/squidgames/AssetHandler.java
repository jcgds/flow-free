package com.squidgames;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;

/**
 * Created by juan_ on 11-Aug-17.
 */

public class AssetHandler {
    public AssetManager manager = new AssetManager();

    public void load(){

        FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf",new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter cafeBig_param = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        cafeBig_param.fontFileName = "Fonts/cafe.ttf";
        cafeBig_param.fontParameters.size = 200;
        manager.load("cafeBig.ttf",BitmapFont.class,cafeBig_param);


        FreetypeFontLoader.FreeTypeFontLoaderParameter cafeMed_param = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        cafeMed_param.fontFileName = "Fonts/cafe.ttf";
        cafeMed_param.fontParameters.size = 100;
        manager.load("cafeMedium.ttf",BitmapFont.class,cafeMed_param);

        FreetypeFontLoader.FreeTypeFontLoaderParameter cafeSm_param = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        cafeSm_param.fontFileName = "Fonts/cafe.ttf";
        cafeSm_param.fontParameters.size = 50;
        manager.load("cafeSmall.ttf",BitmapFont.class,cafeSm_param);

        FreetypeFontLoader.FreeTypeFontLoaderParameter orangeBig_param = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        orangeBig_param.fontFileName = "Fonts/orange.ttf";
        orangeBig_param.fontParameters.size = 200;
        manager.load("orangeBig.ttf",BitmapFont.class,orangeBig_param);

        FreetypeFontLoader.FreeTypeFontLoaderParameter orangeMed_param = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        orangeMed_param.fontFileName = "Fonts/orange.ttf";
        orangeMed_param.fontParameters.size = 200;
        manager.load("orangeMedium.ttf",BitmapFont.class,orangeMed_param);

        FreetypeFontLoader.FreeTypeFontLoaderParameter orangeSm_param = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        orangeSm_param.fontFileName = "Fonts/orange.ttf";
        orangeSm_param.fontParameters.size = 200;
        manager.load("orangeSmall.ttf",BitmapFont.class,orangeSm_param);

    }
}
