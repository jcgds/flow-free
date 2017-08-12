package com.squidgames;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by juan_ on 12-Aug-17.
 */

public class ClaveV2 extends Actor {
    ShapeRenderer renderer;
    Circle circle;


    public ClaveV2(float radius, Color color, ShapeRenderer renderer){
        this.circle = new Circle(0,0,radius);
        this.setColor(color);
        this.renderer = renderer;
        this.setBounds(-radius,-radius,radius*2,radius*2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(this.getColor());
        renderer.circle(this.getX() + circle.radius,this.getY() + circle.radius,this.circle.radius,64);
        renderer.end();
    }
}
