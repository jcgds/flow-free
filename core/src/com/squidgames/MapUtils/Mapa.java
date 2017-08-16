package com.squidgames.MapUtils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.squidgames.Casilla;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by juan_ on 30-Jun-17.
 *
 * Extiende actor para que podamos dibujar el grid primero y despues las conecciones queden por encima del grid
 */

//TODO: Implementar aqui la parte del constructor con fileHandle o algo por ele stilo para poder conectar los mapas de otros tipos

public abstract class Mapa extends Actor{
    private int dimension;
    private ArrayList<Casilla> casillas;
    private HashMap<String,Color> colorMap;
    private Vector2 origin, end; //Son comunes para cualquier tipo de Mapa, pues siempre tendra que estar centrado verticalmente

    public boolean isCompleted() {
        for (Casilla c: casillas) {
            if (!c.isConnected() || c.getCircle().radius == 0)
                return false;
        }

        return true;
    }

    public abstract void setVecindad(ArrayList<ArrayList<Casilla>> matriz,Casilla casilla);

    public void addToStage(Stage stage) {
        stage.addActor(this);
        for (Casilla c: this.getCasillas()) {
            stage.addActor(c);
        }
    }



    public Mapa() {
        //TODO: Quitar esta dependencia por el las variables GAME_SCREEN HEIGHT y WIDTH, pues estan semi-deprecated
        //Esquina inferior izquierda, donde debe comenzar a dibujarse el tablero para que quede centrado verticalmente
        //this.origin = new Vector2(0,FlowFree.GAME_SCREEN_HEIGHT/2 - FlowFree.GAME_SCREEN_WIDTH/2);
        //Esquina superior derecha, representa la maxima X e Y que puede utilizarse, es decir, maximas coordenadas del Mapa
        //this.end = new Vector2(FlowFree.GAME_SCREEN_WIDTH,origin.y + FlowFree.GAME_SCREEN_WIDTH);

        colorMap = new HashMap<String, Color>();
        //region Agregar valores/colores a colorMap
        colorMap.put("1",Color.CYAN);
        colorMap.put("2",Color.RED);
        colorMap.put("3",Color.BLUE);
        colorMap.put("4",Color.PINK);
        colorMap.put("5",Color.MAGENTA);
        colorMap.put("6",Color.YELLOW);
        colorMap.put("7",Color.GREEN);
        colorMap.put("8",Color.ORANGE);
        colorMap.put("9",Color.BROWN);
        colorMap.put("10",Color.CORAL);
        colorMap.put("11",Color.FIREBRICK);
        //endregion

    }

    //region Getters and Setters

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public ArrayList<Casilla> getCasillas() {
        return casillas;
    }

    public void setCasillas(ArrayList<Casilla> casillas) {
        this.casillas = casillas;
    }

    public HashMap<String, Color> getColorMap() {
        return colorMap;
    }

    public void setColorMap(HashMap<String, Color> colorMap) {
        this.colorMap = colorMap;
    }

    public Vector2 getOrigin() {
        return origin;
    }

    public void setOrigin(Vector2 origin) {
        this.origin = origin;
    }

    public Vector2 getEnd() {
        return end;
    }

    public void setEnd(Vector2 end) {
        this.end = end;
    }
    //endregion
}
