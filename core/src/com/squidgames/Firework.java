package com.squidgames;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by juan_ on 10-Jul-17.
 */

public class Firework extends Actor {
    Animation<TextureRegion> animation;
    private float elapsed;
    private float x,y;

    public Firework(float x, float y, Animation<TextureRegion> anim){
        this.x = x;
        this.y = y;
        animation = anim;
    }

    @Override
    public void act(float delta) {
        elapsed += delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(animation.getKeyFrame(elapsed),x,y);
    }
}
