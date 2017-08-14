package com.squidgames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by juan_ on 01-Jul-17.
 */

public class TableroCuadros extends Mapa {
    private float espacioCasilla;
    private float LINE_WIDTH = 3;
    private ShapeRenderer renderer;
    private float SCREEN_WIDTH = 10;
    private float SCREEN_HEIGHT = 18;
    private float PADDING = 0.25f;
    private float Y_CENTER,X_CENTER;
    private float CASILLA_SIZE;
    private float ACTUAL_SIZE; //Dimensiones despues de agregar el PADDING


    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (Casilla c: this.getCasillas()) {
            renderer.begin(ShapeRenderer.ShapeType.Line);
            renderer.setColor(Color.WHITE);
            Gdx.gl.glLineWidth(LINE_WIDTH);
            renderer.box(c.getOriginX(),c.getOriginY(),0,c.getWidth(),c.getHeight(),0);
            renderer.end();
            Gdx.gl.glLineWidth(1);
        }
    }

    @Override
    public void setVecindad(ArrayList<ArrayList<Casilla>> matriz, Casilla casilla) {
        /*  Lados de la casilla correspondiente a este tipo de tablero:
        *               1
        *            --------
        *           |        |
        *        4  |        | 2
        *           |        |
        *            --------
        *               3
        * */

        HashMap<Integer,Casilla> map = new HashMap<Integer, Casilla>();
        Casilla vecino;
        int i = casilla.getI(), j = casilla.getJ();

        //Lado 1
        try {
            vecino = matriz.get(i-1).get(j);
            map.put(1,vecino);
        } catch (IndexOutOfBoundsException e) {
            map.put(1,null);
        }

        //Lado 2
        try {
            vecino = matriz.get(i).get(j+1);
            map.put(2,vecino);
        } catch (IndexOutOfBoundsException e) {
            map.put(2,null);
        }

        //Lado 3
        try {
            vecino = matriz.get(i+1).get(j);
            map.put(3,vecino);
        } catch (IndexOutOfBoundsException e) {
            map.put(3,null);
        }

        //Lado 4
        try {
            vecino = matriz.get(i).get(j-1);
            map.put(4,vecino);
        } catch (IndexOutOfBoundsException e) {
            map.put(4,null);
        }

        casilla.setVecinos(map);
    }


    public TableroCuadros(FileHandle fileHandle, ShapeRenderer renderer) {
        super();
        this.renderer = renderer;
        ACTUAL_SIZE = SCREEN_WIDTH - 2*PADDING;

        Y_CENTER = (SCREEN_HEIGHT - SCREEN_WIDTH)/2 + PADDING;
        X_CENTER = PADDING;

        this.setOrigin(X_CENTER,Y_CENTER);
        JSONParser parser = new JSONParser();

        try {
            Object obj;
            try {
                obj = parser.parse(fileHandle.reader());
            } catch (ParseException e) {
                Gdx.app.log("ERROR", "PARSE EXCEPTION");
                return;
            }
            JSONObject jsonObject = (JSONObject) obj;


            JSONArray tablero = (JSONArray) jsonObject.get("tablero");
            String dimension = (String) jsonObject.get("dimension");
            this.setDimension(Integer.parseInt(dimension));

            ArrayList<ArrayList<Casilla>> casillas = new ArrayList<ArrayList<Casilla>>();

            /******** Transformacion Pixeles -> StageCoords *******/
            //float rest = (LINE_WIDTH*FlowFree.GAME_SCREEN_WIDTH)/Gdx.graphics.getWidth();

            //this.espacioCasilla = SCREEN_WIDTH/tablero.size() - rest/2;
            //this.setOriginX(rest/2);
            CASILLA_SIZE = ACTUAL_SIZE/Integer.parseInt(dimension);

            HashMap<String, Casilla> sinPareja = new HashMap<String, Casilla>();
            //Pasamos este JSONArray a un ArrayList para acceder mas facilmente a los datos por coordenadas
            for (int i = 0; i < tablero.size(); i++) {
                JSONArray row = (JSONArray) tablero.get(i);
                ArrayList<Casilla> newRow = new ArrayList<Casilla>();
                casillas.add(newRow);
                for (int j = 0; j < row.size(); j++) {
                    String content = row.get(j).toString();
                    boolean ext = false;
                    if (!content.equals("0"))
                        ext = true;

                    Clave toAdd = new Clave(i,j,this.getColorMap().get(content),CASILLA_SIZE,ext,renderer);
                    newRow.add(toAdd);

                    if (!content.equals("0")) {
                        if (sinPareja.get(content) != null) {
                            Casilla p = sinPareja.get(content);
                            toAdd.setPareja(p);
                            p.setPareja(toAdd);
                            sinPareja.remove(content);
                        } else
                            sinPareja.put(content, toAdd);
                    }
                }
            }
            //Aqui ya tenemos la matriz con todas las Casillas con sus respectivas parejas
            //Ahora seteamos las posiciones en el Stage de todas las Casillas

            float yMove = SCREEN_HEIGHT - Y_CENTER - CASILLA_SIZE;
            float xMove = X_CENTER;

            ArrayList<Casilla> finalAtt = new ArrayList<Casilla>();
            for (int i=0; i<casillas.size(); i++) {
                ArrayList<Casilla> row = casillas.get(i);
                for (int j=0; j<row.size(); j++) {
                    Casilla actual = row.get(j);
                    setVecindad(casillas,actual);
                    ((Clave)actual).modifyOrigin(new Vector2(xMove,yMove));
                    finalAtt.add(actual);
                    xMove += CASILLA_SIZE;
                }
                xMove = this.getOriginX();
                yMove = yMove - CASILLA_SIZE;
            }

            this.setCasillas(finalAtt);
        }catch (IOException e) {

        }
    }

}
