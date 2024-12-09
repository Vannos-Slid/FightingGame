package io.github.fighting_game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyCollider extends MyCenteredSprite {

    //Fields
    private boolean isDisabled;
    public boolean isVisible;

    //Constructors

    MyCollider(float x, float y, int width, int height, boolean boolIsDisabled, boolean boolIsVisible) {
        super(x, y, width, height, Color.CYAN);
        isDisabled = boolIsDisabled;
        isVisible = boolIsVisible;
    }

    MyCollider(float x, float y, int width, int height, boolean boolIsDisabled) {
        this(x, y, width, height, boolIsDisabled, false);
    }

    MyCollider(float x, float y, int width, int height) {
        this(x, y, width, height, true);
    }

    MyCollider(Collider collider){
        this(collider.getX(), collider.getY(), collider.getWidth(), collider.getHeight());
    }

    //Getters

    @Override
    public float getX() {
        return super.getX();
    }

    @Override
    public float getY() {
        return super.getY();
    }

    //Setters

    public void disable() {
        isDisabled = false;
    }

    public void enable() {
        isDisabled = true;
    }

    //Methods

    public boolean is_disabled() {
        return isDisabled;
    }

    public boolean is_visible(){
        return isVisible;
    }

    public void draw(SpriteBatch batch) {
        if(isVisible)
            super.draw(batch);
    }

}
