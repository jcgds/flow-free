package com.squidgames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by juan_ on 15-Jun-17.
 */

public class Clave extends Casilla {

    private float espacioCasilla;

    public Clave(int tableroI, int tableroJ, Color color, float espacioCasilla, boolean extremo) {
        super();
        this.setI(tableroI);
        this.setJ(tableroJ);
        this.espacioCasilla = espacioCasilla;
        this.setOrigin(0,0);
        this.setCircle(new Circle(0,0,0));
        if (color != null)
            this.setColor(color);
        else
            this.setColor(new Color());

        this.setExtremo(extremo);
        this.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Vector2 basePosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
                Gdx.app.log("IsConnected?", Clave.this.toString() + " - " + Clave.this.isConnected());
                Clave.this.setLastTouchCasilla(Clave.this);
                Clave.this.getLastTouchCasilla().liberar();
                if (Clave.this.getLastTouchCasilla().getPareja() != null){
                    Clave.this.getLastTouchCasilla().getPareja().liberar();
                    Gdx.app.log("LIBERAR", Clave.this.getLastTouchCasilla().getPareja().toString());
                }

                return true;
            }
        });
    }

    public void modifyOrigin(Vector2 newOrigin){
        this.setOrigin(newOrigin.x,newOrigin.y);
        this.setBounds(newOrigin.x,newOrigin.y,espacioCasilla,espacioCasilla);
        if (this.isExtremo())
            this.setTouchable(Touchable.enabled);
        else
            this.setTouchable(Touchable.disabled);

        float radio = calcularRadio();
        Vector2 posCirculo = new Vector2(this.getOriginX() + espacioCasilla/2,this.getOriginY() + espacioCasilla/2);

        if (this.getColor() != null) {
            this.setCircle(new Circle(posCirculo,radio));
        } else
            this.setCircle(new Circle(posCirculo,0));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        FlowFree.renderer.setColor(this.getColor());
        FlowFree.renderer.begin(ShapeRenderer.ShapeType.Filled);
        FlowFree.renderer.circle(this.getCircle().x, this.getCircle().y, this.getCircle().radius, 100);
        FlowFree.renderer.end();

        if (this.getSucesor() != null) {
            FlowFree.renderer.begin(ShapeRenderer.ShapeType.Filled);
            FlowFree.renderer.setColor(this.getColor());
            FlowFree.renderer.rectLine(this.getOriginX()+ this.getWidth()/2,this.getOriginY()+ this.getWidth()/2,
                    getSucesor().getOriginX() + this.getWidth()/2,getSucesor().getOriginY() + this.getWidth()/2,this.getWidth()/3f);
            FlowFree.renderer.end();
        }
    }


    @Override
    public void ejecutarMovimiento(Casilla objetivo){
        if (objetivo != null) {
            Casilla lastTouchClave = this.getLastTouchCasilla();
            //No permitir movernos a claves extremos de diferente color
            if (objetivo.isExtremo() && lastTouchClave.getPareja() != null && !lastTouchClave.getColor().equals(objetivo.getColor())) {
                Gdx.app.log("Movimiento","No te puedes mover a una clave de diferente color!");
                return;
            }

            if (lastTouchClave.isExtremo() && lastTouchClave.getPredecesor() != null) {
                Gdx.app.log("Unknown","Class: Clave. Line: 81");
                return;
            }


            boolean connect = false;
            if (objetivo.equals(this.getPareja())) {
                Gdx.app.log("completing", lastTouchClave.toString());
                //lastTouchClave.caminoCompleted();
                connect = true;
            }

            //Si estamos tratando de intersectar (POR AHORA)
            //if (objetivo.getPredecesor() != null && objetivo.getSucesor() != null)
            //    return;

            Gdx.app.log("MOVIMIENTO",String.format("Inicio: %s  Final: %s",this.toString(),objetivo.toString()));

            if (lastTouchClave.getPredecesor() != null && lastTouchClave.getPredecesor().equals(objetivo)) {
                lastTouchClave.desconectar();
            }else if (objetivo.getPredecesor() != null || objetivo.getSucesor() != null) {
                //Regresar e ir avanzndo por encima de otro camino
                objetivo.liberar();
                //INTERSECCION
                if (!lastTouchClave.getColor().equals(objetivo.getColor()) && objetivo.getPredecesor() != null){
                    objetivo.getPredecesor().liberar();
                    lastTouchClave.conectar(objetivo);
                }
            } else
                lastTouchClave.conectar(objetivo);

            this.setLastTouchCasilla(objetivo);
            objetivo.setTouchable(Touchable.enabled);
            //Si el camino fue completado
            if (connect)
                objetivo.caminoCompleted();
        }
    }

    public Casilla getObjetivo(Vector2 current) {
        //NOTA: El parametro current se considera traducido, es decir, se considera que ya esta pasado a coordenadas del stage

        Casilla lastTouchClave = this.getLastTouchCasilla();
        Casilla objetivo = null;
        if (current.x >= lastTouchClave.getOriginX() + espacioCasilla && current.y <= lastTouchClave.getOriginY() + espacioCasilla) {
            //Accederiamos a el hashtable con la clave 2 y checkeamos si la clave que nos devuelve
            //contiene a la posicion actual (dragged)
            objetivo = lastTouchClave.getVecinos().get(2);
        }

        if (current.x < lastTouchClave.getOriginX() && current.y <= lastTouchClave.getOriginY() + espacioCasilla) {
            objetivo = lastTouchClave.getVecinos().get(4);
        }

        if (current.x <= lastTouchClave.getOriginX() + espacioCasilla
                && current.x >= lastTouchClave.getOriginX()
                && current.y > lastTouchClave.getOriginY() + espacioCasilla) {
            objetivo = lastTouchClave.getVecinos().get(1);
        }

        if (current.x <= lastTouchClave.getOriginX() + espacioCasilla
                && current.x >= lastTouchClave.getOriginX()
                && current.y < lastTouchClave.getOriginY()) {
            objetivo = lastTouchClave.getVecinos().get(3);
        }

        return objetivo;
    }

    @Override
    public  float calcularRadio( ) {
        float areaCasilla = espacioCasilla*espacioCasilla;
        //El area de los circulos es el 25% del area de la casilla
        float areaCirculo = (0.25f) * areaCasilla;
        //Despejamos el radio de la formula del area del circulo (PI*radio^2)
        float radio = (float)Math.sqrt(areaCirculo/Math.PI);
        return radio;
    }
}
