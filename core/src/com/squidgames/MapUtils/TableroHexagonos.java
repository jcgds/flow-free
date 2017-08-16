package com.squidgames.MapUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.squidgames.Casilla;
import com.squidgames.Hexagono;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by juan_ on 03-Jul-17.
 */

public class TableroHexagonos extends Mapa {
    private final String TAG = this.getClass().getSimpleName();
    private ShapeRenderer renderer;
    private float dCentroEsquina;
    private float LINE_WIDTH = 3;
    private float SCREEN_WIDTH = 10;
    private float SCREEN_HEIGHT = 18;
    private float PADDING = 0.25f;
    private float Y_CENTER,X_CENTER;

    @Override
    public void draw(Batch batch, float parentAlpha) {
        renderer.setProjectionMatrix(batch.getProjectionMatrix());

        for (Casilla c: this.getCasillas()) {
            renderer.begin(ShapeRenderer.ShapeType.Line);
            renderer.setColor(Color.WHITE);
            Gdx.gl.glLineWidth(LINE_WIDTH);
            renderer.polygon(((Hexagono) c).getGraphic().getVertices());
            renderer.end();
            Gdx.gl.glLineWidth(1);
        }
    }


    public  TableroHexagonos(FileHandle fileHandle) {
        super();
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);

        Y_CENTER = (SCREEN_HEIGHT - SCREEN_WIDTH)/2 + PADDING;
        X_CENTER = 0;
        this.setOrigin(X_CENTER,Y_CENTER);

        JSONParser parser = new JSONParser();
        try {
            Object obj;
            try {
                obj = parser.parse(fileHandle.reader());
                Gdx.app.log("Hexagonal", obj.toString());
            } catch (ParseException e) {
                Gdx.app.log("ERROR", "PARSE EXCEPTION");
                return;
            }
            JSONObject jsonObject = (JSONObject) obj;
            String dimension = (String) jsonObject.get("dimension");
            this.setDimension(Integer.parseInt(dimension));
            JSONArray tablero = (JSONArray) jsonObject.get("tablero");
            ArrayList<ArrayList<Casilla>> matriz = new ArrayList<ArrayList<Casilla>>();

            float width = (SCREEN_WIDTH-0.4f)/(this.getDimension() + 0.5f);
            this.dCentroEsquina = Hexagono.calculateDcentroEsquina(width);

            HashMap<String, Casilla> sinPareja = new HashMap<String, Casilla>();

            float yMove = SCREEN_HEIGHT - this.getOriginY() - dCentroEsquina*2;
            Gdx.app.log(TAG,String.format("Origin Y: %f   yMove: %f",this.getOriginY(),yMove));

            float xMove = this.getOriginX() + 0.2f;

            ArrayList<Casilla> finalAtt = new ArrayList<Casilla>();
            boolean odd = false;

            //Pasamos este JSONArray a un ArrayList para acceder mas facilmente a los datos por coordenadas
            for (int i=0; i<tablero.size();i++) {
                JSONArray row = (JSONArray) tablero.get(i);
                ArrayList<Casilla> newRow = new ArrayList<Casilla>();
                matriz.add(newRow);

                for (int j = 0; j < row.size(); j++) {
                    String content = row.get(j).toString();
                    Casilla newOne;
                    if (!content.equals("")) {
                        boolean ext = false;
                        if (!content.equals("0"))
                            ext = true;

                        Vector2 actorOrigin = new Vector2(xMove,yMove);
                        newOne = new Hexagono(actorOrigin,dCentroEsquina,i,j,ext,this.getColorMap().get(content),null, renderer);
                        newRow.add(newOne);
                        finalAtt.add(newOne);
                        xMove += Hexagono.calculateWidth(dCentroEsquina);

                        if (!content.equals("0")) {
                            if (sinPareja.get(content) != null) {
                                Casilla p = sinPareja.get(content);
                                newOne.setPareja(p);
                                p.setPareja(newOne);
                                sinPareja.remove(content);
                            } else
                                sinPareja.put(content, newOne);
                        }
                    }else
                        newRow.add(null);
                }


                if (odd) {
                    xMove = this.getOriginX() + 0.2f;
                    odd = false;
                } else  {
                    xMove = Hexagono.calculateWidth(dCentroEsquina)/2 + 0.2f;
                    odd = true;
                }

                yMove = yMove - (Hexagono.calculateHeight(dCentroEsquina)*(0.75f));
            }


            for (ArrayList<Casilla> row: matriz) {
                for(Casilla h: row){
                    if (h != null) {
                        setVecindad(matriz,h);
                    }
                }
            }
            this.setCasillas(finalAtt);

        } catch (IOException e) {

        }

    }

    @Override
    public void setVecindad(ArrayList<ArrayList<Casilla>> casillas, Casilla casilla) {
        /* Lados:
        *            #
        *     (6)  #  # (1)
        *        #     #
        *       #       #
        *  (5)  #       # (2)
        *       #       #
        *        #     #
         *   (4)  #  # (3)
        *          #
        *
        * */


        HashMap<Integer,Casilla> map = new HashMap<Integer, Casilla>();
        int i = casilla.getI(), j = casilla.getJ();

        try {
            Casilla l1 = casillas.get(i - 1).get(j + 1);
            if (l1 == null)
                throw  new IndexOutOfBoundsException();
            map.put(1,l1);
        } catch (IndexOutOfBoundsException e) {
            map.put(1,null);
        }

        try {
            Casilla l2 = casillas.get(i).get(j + 1);
            if (l2 == null)
                throw  new IndexOutOfBoundsException();
            map.put(2,l2);
        } catch (IndexOutOfBoundsException e) {
            map.put(2,null);
        }

        try {
            Casilla l3 = casillas.get(i + 1).get(j);
            if (l3 == null)
                throw  new IndexOutOfBoundsException();
            map.put(3,l3);
        } catch (IndexOutOfBoundsException e) {
            map.put(3,null);
        }

        try {
            Casilla l4 = casillas.get(i+1).get(j-1);
            if (l4 == null)
                throw  new IndexOutOfBoundsException();
            map.put(4,l4);
        } catch (IndexOutOfBoundsException e) {
            map.put(4,null);
        }

        try {
            Casilla l5 = casillas.get(i).get(j - 1);
            if (l5 == null)
                throw  new IndexOutOfBoundsException();
            map.put(5,l5);
        } catch (IndexOutOfBoundsException e) {
            map.put(5,null);
        }

        try {
            Casilla l6 = casillas.get(i-1).get(j);
            if (l6 == null)
                throw  new IndexOutOfBoundsException();
            map.put(6,l6);
        } catch (IndexOutOfBoundsException e) {
            map.put(6,null);
        }

        casilla.setVecinos(map);
    }
}
