package com.squidgames.MapUtils;

import com.badlogic.gdx.files.FileHandle;
import com.squidgames.Constants;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Created by juan_ on 16-Aug-17.
 */

public class MapFactory {

    public Mapa getMapa(FileHandle fileHandle){

        String type = getType(fileHandle);

        if (type.equals(Constants.JSON_TYPE_CUADRADO)) {
            return new TableroCuadros(fileHandle);
        } else if (type.equals(Constants.JSON_TYPE_HEXAGONO)) {
            return new TableroHexagonos(fileHandle);
        } else
            return null;
    }

    private String getType(FileHandle fileHandle){
        JSONParser parser = new JSONParser();
        String type = null;
        try {
            JSONObject object = (JSONObject) parser.parse(fileHandle.reader());
            type = object.get(Constants.JSON_TYPE_IDENTIFIER).toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return type;
        }
    }
}
