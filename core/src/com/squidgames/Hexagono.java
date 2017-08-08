package com.squidgames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.HashMap;

/**
 * Created by juan_ on 30-Jun-17.
 *
 *
 * Referencia sobre geometria de los Hexagonos: http://www.redblobgames.com/grids/hexagons
 */

public class Hexagono extends Casilla{

    private Polygon graphic;
    private float dCentroEsquina;
    private Hexagono lastTouchClave;

    public Hexagono(Vector2 origenActor, float dCentroEsquina,int i, int j, boolean isExtremo, Color c, Hexagono pareja) {
        super(origenActor,c,i,j,isExtremo,pareja);
        this.dCentroEsquina = dCentroEsquina;
        this.setHeight(calculateHeight(dCentroEsquina));
        this.setWidth(calculateWidth(dCentroEsquina));

        //Calculamos la posicion del centro respecto al origen del actor
        Vector2 centro = new Vector2(origenActor.x + this.getWidth()/2, origenActor.y + this.getHeight()/2);
        graphic = new Polygon(corner(centro.x,centro.y,dCentroEsquina));
        graphic.setOrigin(centro.x,centro.y);

        if (isExtremo) {
            this.setColor(c);
            this.setCircle(new Circle(centro,calcularRadio()));
        } else {
            this.setCircle(new Circle(centro, 0));
            this.setColor(new Color());
        }

        //Seteamos la posicion del actor respecto al rectangulo que contiene al hexagono
        Rectangle box = graphic.getBoundingRectangle();
        this.setBounds(box.getX(),box.getY(),box.getWidth(),box.getHeight());

        this.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Vector2 basePosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
                Vector2 traduced = Hexagono.this.getStage().screenToStageCoordinates(basePosition);

                if (Hexagono.this.graphic.contains(traduced)) {
                    Gdx.app.log("Connected","" +Hexagono.this.isConnected());
                    for (int i=1;i<=6;i++) {
                        Casilla v = Hexagono.this.getVecinos().get(i);
                        if (v == null)
                            continue;
                        Gdx.app.log("Vecinos", "(" + i + ") " + v.getI() + "," + v.getJ());
                    }
                    lastTouchClave = Hexagono.this;
                    //TODO DES COMENTAR LOS LIBERAR
                    lastTouchClave.liberar();

                    if (lastTouchClave.getPareja() != null) {
                        lastTouchClave.getPareja().liberar();
                        Gdx.app.log("LIBERAR_hex","" );//Hexagono.this.getLastTouchCasilla().getPareja().toString());
                    }
                }

                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                Vector2 basePosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
                Vector2 traduced = Hexagono.this.getStage().screenToStageCoordinates(basePosition);

                if (lastTouchClave == null) {
                    Gdx.app.log("DRAG", "LastTouch is NULL");
                    return;
                }

                Casilla OBJETIVO = getObjetivo(traduced);
                ejecutarMovimiento(OBJETIVO);
            }
        });
    }

    @Override
    public void ejecutarMovimiento(Casilla objetivo) {
        if (objetivo != null) {
            //No permitir movernos a claves extremos de diferente color
            if (objetivo.isExtremo() && lastTouchClave.getPareja() != null && !lastTouchClave.getColor().equals(objetivo.getColor()))
                return;

            if (lastTouchClave.isExtremo() && lastTouchClave.getPredecesor() != null) {
                return;
            }

            boolean connect = false;
            if (objetivo.equals(this.getPareja())) {
                Gdx.app.log("completing", lastTouchClave.toString());
                //lastTouchClave.caminoCompleted();
                connect = true;
            }

            Gdx.app.log("MOVIMIENTO",String.format("Inicio: %s  Final: %s",this.toString(),objetivo.toString()));

            if (lastTouchClave.getPredecesor() != null && lastTouchClave.getPredecesor().equals(objetivo)) {
                lastTouchClave.desconectar();
            }else if (objetivo.getPredecesor() != null || objetivo.getSucesor() != null) {
                //Regresar e ir avanzndo por encima de otro camino
                Gdx.app.log("LIB1","");
                objetivo.liberar();
                //INTERSECCION
                if (!lastTouchClave.getColor().equals(objetivo.getColor()) && objetivo.getPredecesor() != null){
                    Gdx.app.log("LIB2","");
                    objetivo.desconectarCamino();
                    objetivo.getPredecesor().liberar();
                    lastTouchClave.conectar(objetivo);
                }
            } else
                lastTouchClave.conectar(objetivo);

            //this.setLastTouchCasilla(objetivo);
            lastTouchClave = (Hexagono) objetivo;
            objetivo.setTouchable(Touchable.enabled);
            if (connect)
                objetivo.caminoCompleted();
        } else
            Gdx.app.log("Null","Clase: Hexagono | Metodo: Ejecutar Movimiento | Linea 133");
    }


    public static float[] corner(float x, float y, float size){

        float[] res = new float[12];
        for (int i =0, j = 0; i<6; i++,j++) {
            float angle_deg = (60 * i) + 30;
            float angle_rad = (float) Math.PI / 180 * angle_deg;

            res[j] = (float) (x + size * Math.cos(angle_rad));
            res[j+1] = (float) (y + size * Math.sin(angle_rad));
            j++;
        }

        return res;
    }

    public static float calculateHeight(float dCentroEsquina) {
        return dCentroEsquina*2;
    }

    public static float calculateWidth(float dCentroEsquina) {
        /*
        * Triangulo rectangulo entre la recta centro lado (Width/2) y la recta dCentroLado que buscamos,
        * con un angulo de pi/6 entre ellas
        * */
        return (float)(Math.sqrt(3)/2 * calculateHeight(dCentroEsquina));
    }

    public static float calculateDcentroEsquina(float width){
        return  (float)(width/Math.sqrt(3));
    }

    @Override
    public float calcularRadio() {
        float area = graphic.area();
        float areaCirculo = (0.20f) * area;
        //Despejamos el radio de la formula del area del circulo (PI*radio^2)
        float radio = (float)Math.sqrt(areaCirculo/Math.PI);
        return radio;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //Es muy parecido al de Clave, pero cambian las dimensiones de los circulos
        //Dibujar circulo interno
        FlowFree.renderer.setColor(this.getColor());
        FlowFree.renderer.begin(ShapeRenderer.ShapeType.Filled);
        FlowFree.renderer.circle(this.getCircle().x, this.getCircle().y, this.getCircle().radius, 100);
        FlowFree.renderer.end();

        if (this.getSucesor() != null) {
            FlowFree.renderer.begin(ShapeRenderer.ShapeType.Filled);
            FlowFree.renderer.setColor(this.getColor());
            FlowFree.renderer.rectLine(this.getCircle().x ,this.getCircle().y,
                    this.getSucesor().getCircle().x,this.getSucesor().getCircle().y, this.getWidth()/3.5f);
            FlowFree.renderer.end();
        }
    }

    @Override
    public void conectar(Casilla other) {
        //Reescribimos el metodo aqui para ajustar el size de los circulos y las lineas
        this.setSucesor(other);
        if (!other.isExtremo()) {
            other.setPareja(this.getPareja());
            other.setColor(this.getColor());
            other.getCircle().setRadius(this.getWidth()/7f);
        }
        other.setPredecesor(this);
    }

    public Polygon getGraphic() {
        return graphic;
    }

    public void setGraphic(Polygon graphic) {
        this.graphic = graphic;
    }


    @Override
    public Casilla getObjetivo(Vector2 traducedCoordinates) {
        Gdx.app.log("Traduced", " " + traducedCoordinates);
        if (lastTouchClave != null) {
            HashMap<Integer, Casilla> map = lastTouchClave.getVecinos();
            for (int i = 1; i <= 6; i++) {
                Hexagono vecino = (Hexagono) map.get(i);
                if (vecino != null)
                    Gdx.app.log("Vecino", "N" + i + " - " + vecino.toString());

                if (vecino != null) {
                    if (vecino.graphic.contains(traducedCoordinates)) {
                        Gdx.app.log("Objetivo(vecino)", "N" + i + " - " + vecino );
                        return vecino;
                    }
                }
            }
        }



        return null;
    }


}
