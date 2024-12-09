package io.github.fighting_game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyCenteredSprite extends  MySprite {
    float x;
    float y;

    MyCenteredSprite(float x, float y, int width, int height, Texture textureObject, boolean flip_h) {
        super(textureObject, unCenter(x, width), unCenter(y, height), width, height,
            flip_h);
        this.x = x;
        this.y = y;
    }

    MyCenteredSprite(float x, float y, int width, int height, TextureRegion textureRegion, boolean flip_h) {
        this(x, y, width, height, textureRegion.getTexture(), flip_h);
    }

    MyCenteredSprite(float x, float y, int width, int height, String strTexturePath, boolean flip_h) {
        super(strTexturePath, unCenter(x,width), unCenter(y, height), width, height, flip_h);
        this.x = x;
        this.y = y;
    }

    MyCenteredSprite(String strTexturePath, float x, float y, int width, int height) {
        this(x, y, width, height, strTexturePath, false);
    }

    MyCenteredSprite(String strTexturePath, int width, int height, boolean flip_h) {
        this(0, 0, width, height, strTexturePath, flip_h);
    }

    MyCenteredSprite(String strTexturePath, int width, int height) {
        this(strTexturePath, width, height, false);
    }

    MyCenteredSprite(String strTexturePath, float x, float y, boolean flip_h) {
        super(strTexturePath, unCenter(x,new Texture(strTexturePath).getWidth()),
            unCenter(y, new Texture(strTexturePath).getHeight()), flip_h);
        this.x = x;
        this.y = y;
    }

    MyCenteredSprite(String strTexturePath, float x, float y) {
        this(strTexturePath, x, y, false);
    }

    MyCenteredSprite(String strTexturePath, boolean flip_h) {
        this(strTexturePath, 0f, 0f, flip_h);
    }

    MyCenteredSprite(String strTexturePath) {
        this(strTexturePath, false);
    }

    MyCenteredSprite(float x, float y, int width, int height, Color color){
        super(color, unCenter(x,width), unCenter(y, height), width, height);
        this.x = x;
        this.y = y;
    }

    MyCenteredSprite(float x, float y, int width, int height){
        this(x, y, width, height, Color.GREEN);
    }

    MyCenteredSprite(int width, int height, Color color){
        this(0, 0, width, height, color);
    }

    MyCenteredSprite(int width, int height){
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
        super.setPosition(unCenter(x, getWidth()), unCenter(y, getHeight()));
        this.x = x;
        this.y = y;
    }

    @Override
    public void setX(float x) {
        super.setX(unCenter(x, getWidth()));
        this.x = x;
    }

    @Override
    public void setY(float y) {
        super.setY(unCenter(y, getHeight()));
        this.y = y;
    }

    public static float unCenter(float num, float step){
        return num - step / 2;
    }

/*    @Override
    public void render(SpriteBatch batch) {
        super.draw(batch);
    }*/

    /*@Override
    public  void render(float x, float y, SpriteBatch batch){
        super.draw(unCenter(x, getWidth()), unCenter(y, getHeight()), batch);
    }*/
}
