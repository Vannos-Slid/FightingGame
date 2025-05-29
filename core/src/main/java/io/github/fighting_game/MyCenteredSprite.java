package io.github.fighting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class MyCenteredSprite extends  MySprite {
    private float centeredX;
    private float centeredY;

    MyCenteredSprite(float x, float y, int width, int height, Texture textureObject, boolean flip_h) {
        super(textureObject, unCenter(x, width), unCenter(y, height), width, height,
            flip_h);
        this.centeredX = x;
        this.centeredY = y;
    }

    MyCenteredSprite(float x, float y, int width, int height, TextureRegion textureRegion, boolean flip_h) {
        this(x, y, width, height, textureRegion.getTexture(), flip_h);
    }

    MyCenteredSprite(float x, float y, int width, int height, String strTexturePath, boolean flip_h) {
        super(strTexturePath, unCenter(x,width), unCenter(y, height), width, height, flip_h);
        this.centeredX = x;
        this.centeredY = y;
    }

    MyCenteredSprite(String strTexturePath, float x, float y, int width, int height) {
        this(x, y, width, height, strTexturePath, false);
    }

    MyCenteredSprite(String strTexturePath, int width, int height, boolean flip_h) {
        this(0, 0, width, height, strTexturePath, flip_h);
    }

    MyCenteredSprite(String strTexturePath, int width, int height) {
        this(0, 0, width, height, strTexturePath, false);
    }

    MyCenteredSprite(String strTexturePath, float x, float y, boolean flip_h) {
        super(MySimplerMethods.createCenteredSprite(strTexturePath, x, y), flip_h);
        this.centeredX = x;
        this.centeredY = y;
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
        this.centeredX = x;
        this.centeredY = y;
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
        return centeredX;
    }

    public float getCenteredY() {
        return centeredY;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(unCenter(x, getWidth()), unCenter(y, getHeight()));
        this.centeredX = x;
        this.centeredY = y;
    }

    @Override
    public void setX(float x) {
        super.setX(unCenter(x, getWidth()));
        this.centeredX = x;
    }

    @Override
    public void setY(float y) {
        super.setY(unCenter(y, getHeight()));
        this.centeredY = y;
    }

    public static float unCenter(float num, float step){
        return num - step / 2;
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }

    public static Texture makeTexture(Texture texture){
        return texture != null? texture : MySimplerMethods.createDefaultTexture();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    //    @Override
//    public void render(SpriteBatch batch) {
//        super.draw(batch);
//    }
//
//    @Override
//    public  void render(float x, float y, SpriteBatch batch){
//        super.draw(unCenter(x, getWidth()), unCenter(y, getHeight()), batch);
//    }
}
