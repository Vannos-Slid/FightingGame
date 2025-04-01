package io.github.fighting_game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureObjectP extends TextureObject {
    //Fields

    public Rectangle stats;

    //Constructors

    TextureObjectP(float x, float y, int width, int height, Texture textureObject, boolean flip_h){
        super(textureObject, flip_h);

        this.stats = new Rectangle(x, y, width, height);
    }

    TextureObjectP(float x, float y, int width, int height, TextureRegion textureRegion, boolean flip_h){
        this(x, y, width, height, textureRegion.getTexture(), flip_h);
    }

    TextureObjectP(float x, float y, int width, int height, String strTexturePath, boolean flip_h){
        super(strTexturePath, flip_h);

        this.stats = new Rectangle(x, y, width, height);
    }

    TextureObjectP(float x, float y, int width, int height, String strTexturePath){
        this(x, y, width, height, strTexturePath, false );
    }

    TextureObjectP(int width, int height, String strTexturePath, boolean flip_h){
        this(0, 0, width, height, strTexturePath, flip_h);
    }

    TextureObjectP(int width, int height, String strTexturePath){
        this(0, 0, width, height, strTexturePath, false);
    }

    TextureObjectP(float x, float y, String strTexturePath, boolean flip_h){
        super(strTexturePath, flip_h);

        this.stats = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

    TextureObjectP(float x, float y, String strTexturePath) {
        this(x, y, strTexturePath, false);
    }

    TextureObjectP(String strTexturePath, boolean flip_h){
        this(0f, 0f, strTexturePath, flip_h);
    }

    TextureObjectP(String strTexturePath){
        this(strTexturePath, false);
    }

    TextureObjectP(float x, float y, int width, int height, Color color){
        super(width, height, color);
        stats = new Rectangle(x, y, width, height);
    }

    TextureObjectP(float x, float y, int width, int height){
        this(x, y, width, height, Color.RED);
    }

    TextureObjectP(int width, int height){
        this(0, 0, width, height);
    }

    //Getters

    public float getX(){
        return stats.x;
    }

    public float getY() {
        return stats.y;
    }

    public int getWidth(){
        return stats.width;
    }

    public int getHeight() {
        return stats.height;
    }

    //Setters

    public void setX(float x) {
        stats.setX(x);
    }

    public void setY(float y) {
        stats.setY(y);
    }

    public void setWidth(int width) {
        float scale = (float)  width / stats.width;
        stats.setHeight((int) (stats.getHeight() * scale));
        stats.setWidth(width);
    }

    public void setHeight(int height){
        float scale = (float) height / stats.height;
        stats.setWidth((int) (stats.getWidth() * scale));
        stats.setHeight(height);
    }

    public void setWidthHeight(int width, int height){
        stats.setWidth(width);
        stats.setHeight(height);
    }

    public void setPosition(float x, float y){
        stats.setPosition(x, y);
    }

    public void setTextureObject(TextureObjectP textureObjectP){
        if(textureObjectP == null) return;
        setX(textureObjectP.getX());
        setY(textureObjectP.getY());
        setWidth(textureObjectP.getWidth());
        setHeight(textureObjectP.getHeight());
        flip_h = textureObjectP.flip_h;
        texture = textureObjectP.texture;
        textureRegion = textureObjectP.textureRegion;
    }

    //Methods

    public boolean contains(float x, float y){
        return stats.contains(x, y);
    }

    public void render(float x, float y, SpriteBatch batch){
        batch.draw(getTexture(), x, y, getWidth(), getHeight());
    }

    public void render(SpriteBatch batch){
        render(getX(), getY(), batch);
    }
}
