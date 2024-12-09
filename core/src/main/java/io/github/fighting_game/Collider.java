package io.github.fighting_game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Collider extends TextureObjectCenteredP  {

    //Fields
    private boolean isDisabled;
    public boolean isVisible;

    //Constructors

    Collider(float x, float y, int width, int height, boolean boolIsDisabled, boolean boolIsVisible) {
        super(x, y, width, height, Color.CYAN);
        isDisabled = boolIsDisabled;
        isVisible = boolIsVisible;
    }

    Collider(float x, float y, int width, int height, boolean boolIsDisabled) {
        this(x, y, width, height, boolIsDisabled, false);
    }

    Collider(float x, float y, int width, int height) {
        this(x, y, width, height, true);
    }

    Collider(Collider collider){
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

    @Override
    public int getWidth() {
        return super.getWidth();
    }

    @Override
    public int getHeight() {
        return super.getHeight();
    }


    //Setters

    public void setX(int x) {
        stats.setX(x);
    }

    public void setY(int y) {
        stats.setY(y);
    }

    public void setWidth(int width){
        stats.setWidth(width);
    }

    public void setHeight(int height) {
        stats.setHeight(height);
    }

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


    @Override
    public void render(SpriteBatch batch) {
        if(isVisible)
            super.render(batch);
    }
}
