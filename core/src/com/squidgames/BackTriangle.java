package com.squidgames;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by juan_ on 13-Jul-17.
 */

public class BackTriangle extends Actor {
    private float dLado;
    private Screen screen;
    private Vector2 primerVertice;
    private Polygon triangle;
    private ShapeRenderer shapeRenderer;

    public BackTriangle(Vector2 primerVertice, float dLado, final GameScreen screen, ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
        this.dLado = dLado;
        this.screen = screen;
        this.primerVertice = primerVertice;
        this.triangle = new Polygon(verticesToArray());
        Rectangle bonding = triangle.getBoundingRectangle();
        this.setBounds(bonding.x,bonding.y - 0.5f,bonding.getWidth()*3,bonding.getHeight()+1);

        this.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                int clave = screen.getMap().getDimension();
                screen.getGame().setScreen(new MapSelect(screen.getGame(),FlowFree.picado.get(clave)));
                return true;
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //Vector2 primerVertice = new Vector2(0.25f,FlowFree.GAME_SCREEN_HEIGHT - 0.8f);
        Vector2 segundoVertice= new Vector2(primerVertice.x + dLado + 0.1f, primerVertice.y + dLado/2);
        Vector2 tercerVertice = new Vector2(segundoVertice.x,segundoVertice.y - dLado);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.triangle(primerVertice.x,primerVertice.y,segundoVertice.x,segundoVertice.y,tercerVertice.x,tercerVertice.y);
        shapeRenderer.rectLine(segundoVertice.x,segundoVertice.y - dLado/2,segundoVertice.x +0.5f,segundoVertice.y - dLado/2,primerVertice.x/2);
        shapeRenderer.end();

    }

    public float[] verticesToArray() {
        Vector2 segundoVertice= new Vector2(primerVertice.x + dLado + 0.1f, primerVertice.y + dLado/2);
        Vector2 tercerVertice = new Vector2(segundoVertice.x,segundoVertice.y - dLado);

        float[] resultado = new float[6];

        resultado[0] = primerVertice.x;
        resultado[1] = primerVertice.y;
        resultado[2] = segundoVertice.x;
        resultado[3] = segundoVertice.y;
        resultado[4] = tercerVertice.x;
        resultado[5] = tercerVertice.y;
        return resultado;
    }
}
