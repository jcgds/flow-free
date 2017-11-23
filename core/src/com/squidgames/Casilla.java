package com.squidgames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.HashMap;

/**
 * Created by juan_ on 01-Jul-17.
 */

public abstract class Casilla extends Actor{
    private Circle circle;
    private Casilla predecesor, sucesor; //Estos representaran el camino
    private int i,j; //Posicion tablero
    private boolean isExtremo;
    private boolean isConnected;
    private HashMap<Integer,Casilla> vecinos;
    private Casilla pareja;
    private Casilla lastTouchCasilla;

    //region Getters and setters
    public Circle getCircle() {
        return circle;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public Casilla getPredecesor() {
        return predecesor;
    }

    public void setPredecesor(Casilla predecesor) {
        this.predecesor = predecesor;
    }

    public Casilla getSucesor() {
        return sucesor;
    }

    public void setSucesor(Casilla sucesor) {
        this.sucesor = sucesor;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public boolean isExtremo() {
        return isExtremo;
    }

    public void setExtremo(boolean extremo) {
        isExtremo = extremo;
    }

    public HashMap<Integer, Casilla> getVecinos() {
        return vecinos;
    }

    public void setVecinos(HashMap<Integer, Casilla> vecinos) {
        this.vecinos = vecinos;
    }

    public Casilla getPareja() {
        return pareja;
    }

    public void setPareja(Casilla pareja) {
        this.pareja = pareja;
    }

    public Casilla getLastTouchCasilla() {
        return lastTouchCasilla;
    }

    public void setLastTouchCasilla(Casilla lastTouchCasilla) {
        this.lastTouchCasilla = lastTouchCasilla;
    }
    //endregion

    /*
    *  En cualquier casilla el touchDragged es el mismo, el cual dependera del getObjetivo y ejecutarMovimiento
    *  de la subclase que lo llame. El touchDown es distinto pues por ejemplo en el caso de las casillas hexagonales
    *  el actor es un rectangulo que contiene al hexagono, pero el toque solo se detecta dentro de la figura hexagonal
    * */

    public Casilla() {
        this.setPredecesor(null);
        this.setSucesor(null);
        this.isConnected = false;

        this.addListener(new ClickListener() {
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                Vector2 basePosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
                Vector2 traduced = Casilla.this.getStage().screenToStageCoordinates(basePosition);

                if (lastTouchCasilla == null) {
                    Gdx.app.log("DRAG", "LastTouch is NULL");
                    return;
                }

                Casilla OBJETIVO = getObjetivo(traduced);
                ejecutarMovimiento(OBJETIVO);
            }
        });
    }

    public Casilla(Vector2 actorOrigin, Color c,int i, int j, boolean extremo, Casilla pareja) {
        this.setPareja(pareja);
        this.setExtremo(extremo);
        this.setConnected(false);
        this.setOrigin(actorOrigin.x,actorOrigin.y);
        this.setI(i);
        this.setJ(j);
        if (c != null)
            this.setColor(c);
        else
            this.setColor(new Color());

        if (extremo)
            this.setTouchable(Touchable.enabled);
        else
            this.setTouchable(Touchable.disabled);

        this.setPredecesor(null);
        this.setSucesor(null);
        this.isConnected = false;


        this.addListener(new ClickListener() {
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                Vector2 basePosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
                Vector2 traduced = Casilla.this.getStage().screenToStageCoordinates(basePosition);
                Gdx.app.log("DEBUGgggg",traduced.x + "," + traduced.y);
                if (lastTouchCasilla == null) {
                    Gdx.app.log("DRAG", "LastTouch is NULL");
                    return;
                }

                Casilla OBJETIVO = getObjetivo(traduced);
                ejecutarMovimiento(OBJETIVO);
            }
        });
    }

    public abstract Casilla getObjetivo(Vector2 traducedCoordinates);
    public abstract void ejecutarMovimiento(Casilla objetivo);
    public abstract float calcularRadio();

    public boolean equals(Casilla other){
        if (other == null)
            return false;

        if (this.getI() == other.getI() && this.getJ() == other.getJ())
            return true;

        return false;
    }

    public String toString() {
        return String.format("[%s(%d,%d)]",this.getColor(),this.i,this.j);
    }

    public void conectar(Casilla other) {
        this.setSucesor(other);
        if (!other.isExtremo()) {
            other.setPareja(this.getPareja());
            other.setColor(this.getColor());
            other.getCircle().setRadius(this.getWidth()/6f);
        }
        other.setPredecesor(this);
    }

    public void desconectar() {
        if (!this.isExtremo()){
            this.setTouchable(Touchable.disabled);
            this.setPareja(null);
            this.getCircle().setRadius(0); //Para que no se dibuje
        }

        if (this.getSucesor() != null) {
            this.getSucesor().setPredecesor(null);
            this.setSucesor(null);
        }
        if (this.getPredecesor() != null) {
            this.getPredecesor().setSucesor(null);
            this.setPredecesor(null);
        }
    }

    public void liberar() {
        this.setConnected(false);
        if (this.getSucesor() != null) {
            this.getSucesor().liberar();
            this.getSucesor().desconectar();
        }
    }

    public void caminoCompleted() {
        /*Una vez que lleguemos a un extremo volvemos por los predecesores seteando el
        * isConnected a true
        * */
        this.setConnected(true);
        if (this.getPredecesor() != null) {
            this.getPredecesor().caminoCompleted();
        }
    }

    public void desconectarCamino() {
        //La parte del camino que va por los sucesores se desconecta en la interseccion con el liberar()
        this.setConnected(false);
        if (this.getPredecesor() != null)
            this.getPredecesor().desconectarCamino();

    }

}
