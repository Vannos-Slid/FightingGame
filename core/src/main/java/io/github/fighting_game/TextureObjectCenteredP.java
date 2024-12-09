package io.github.fighting_game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureObjectCenteredP extends TextureObjectP {
    float x;
    float y;

    TextureObjectCenteredP(float x, float y, int width, int height, Texture textureObject, boolean flip_h) {
        super(toCenter(x, width), toCenter(y, height), width, height, textureObject,
            flip_h);
        this.x = x;
        this.y = y;
    }

    TextureObjectCenteredP(float x, float y, int width, int height, TextureRegion textureRegion, boolean flip_h) {
        this(x, y, width, height, textureRegion.getTexture(), flip_h);
    }

    TextureObjectCenteredP(float x, float y, int width, int height, String strTexturePath, boolean flip_h) {
        super(toCenter(x,width), toCenter(y, height), width, height, strTexturePath, flip_h);
        this.x = x;
        this.y = y;
    }

    TextureObjectCenteredP(float x, float y, int width, int height, String strTexturePath) {
        this(x, y, width, height, strTexturePath, false);
    }

    TextureObjectCenteredP(int width, int height, String strTexturePath, boolean flip_h) {
        this(0, 0, width, height, strTexturePath, flip_h);
    }

    TextureObjectCenteredP(int width, int height, String strTexturePath) {
        this(width, height, strTexturePath, false);
    }

    TextureObjectCenteredP(float x, float y, String strTexturePath, boolean flip_h) {
        super(toCenter(x,new Texture(strTexturePath).getWidth()),
            toCenter(y, new Texture(strTexturePath).getHeight()), strTexturePath, flip_h);
        this.x = x;
        this.y = y;
    }

    TextureObjectCenteredP(float x, float y, String strTexturePath) {
        this(x, y, strTexturePath, false);
    }

    TextureObjectCenteredP(String strTexturePath, boolean flip_h) {
        this(0f, 0f,strTexturePath, flip_h);
    }

    TextureObjectCenteredP(String strTexturePath) {
        this(strTexturePath, false);
    }

    TextureObjectCenteredP(float x, float y, int width, int height, Color color){
        super(toCenter(x,width), toCenter(y, height), width, height, color);
        this.x = x;
        this.y = y;
    }

    TextureObjectCenteredP(float x, float y, int width, int height){
        this(x, y, width, height, Color.GREEN);
    }

    TextureObjectCenteredP(int width, int height, Color color){
        this(0, 0, width, height, color);
    }

    TextureObjectCenteredP(int width, int height){
        this(0, 0, width, height);
    }

    public float getCenteredX() {
        return x;
    }

    public float getCenteredY() {
        return y;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(toCenter(x, getWidth()), toCenter(y, getHeight()));
        this.x = x;
        this.y = y;
    }

    @Override
    public void setX(float x) {
        super.setX(toCenter(x, getWidth()));
        this.x = x;
    }

    @Override
    public void setY(float y) {
        super.setY(toCenter(y, getHeight()));
        this.y = y;
    }

    public static float toCenter(float num, float step){
        return num - step / 2;
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
    }

    @Override
    public  void render(float x, float y, SpriteBatch batch){
        super.render(toCenter(x, getWidth()), toCenter(y, getHeight()), batch);
    }

}
