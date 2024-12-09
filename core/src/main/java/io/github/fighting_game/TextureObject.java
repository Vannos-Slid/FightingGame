package io.github.fighting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureObject {
    //Fields

    protected Texture texture;
    protected TextureRegion textureRegion;
    protected boolean flip_h;

    //Constructors

    TextureObject(int width, int height, Color color) {
        this.flip_h = false;

        texture = createDefaultTexture(width, height, color);

        textureRegion = new TextureRegion(texture);
    }

    TextureObject(int width, int height) {
        this(width, height, Color.RED);
    }

    TextureObject(Pixmap textureObject) {
        this.flip_h = false;

        if(textureObject != null)
            texture = new Texture(textureObject);
        else
            texture = createDefaultTexture();

        textureRegion = new TextureRegion(texture);
    }

    TextureObject(Texture textureObject, boolean flip_h){
        this.flip_h = flip_h;

        texture = textureObject;

        textureRegion = new TextureRegion(textureObject);
        textureRegion.flip(flip_h, false);
    }

    TextureObject(TextureRegion textureRegion, boolean flip_h){
        this(textureRegion.getTexture(), flip_h);
    }

    TextureObject(String strTexturePath, boolean flip_h){
        this.flip_h = flip_h;
        this.texture = makeTexture(strTexturePath);

        textureRegion = new TextureRegion(this.texture);
        textureRegion.flip(flip_h, false);
    }

    TextureObject(String strTexturePath){
        this(strTexturePath, false );
    }


    TextureObject(float x, float y, String strTexturePath, boolean flip_h){
        this.flip_h = flip_h;
        this.texture = makeTexture(strTexturePath);
    }

    //Getters

    public TextureRegion getTexture(){
        return textureRegion;
    }

    //Setters

    public void setTexture(Texture newTexture, boolean flip_h) {
        texture = makeTexture(newTexture);
        this.flip_h = flip_h;
        textureRegion = new TextureRegion(texture);
        this.textureRegion.flip(this.flip_h, false);
    }

    public void setTexture(Texture texture){
        setTexture(texture, false);
    }

    public void setTexture(TextureRegion textureRegion, boolean flip_h){
        setTexture(textureRegion.getTexture(), flip_h);
    }

    public void setTexture(TextureRegion textureRegion){
        setTexture(textureRegion.getTexture(), false);
    }

    //Methods

    public void flip(boolean flip_h){
        if (this.flip_h == !flip_h){
            textureRegion.flip(flip_h, false);
            this.flip_h = flip_h;
        }
    }

    public void flip() {
        flip(!flip_h);
    }

    public void dispose() {
        texture.dispose();
    }


    protected Texture makeTexture(Texture texture){
        if(texture == null) {
            texture = createDefaultTexture();
        }
        return texture;
    }

    protected Texture makeTexture(FileHandle fileHandle){
        Texture texture;
        if(fileHandle.exists()) {
            texture = new Texture(fileHandle);
        }
        else {
            texture = createDefaultTexture();
        }
        return texture;
    }

    protected Texture makeTexture(String strTexturePath) {
        return makeTexture(Gdx.files.internal(strTexturePath));
    }

    protected static Texture createDefaultTexture(int width, int height, Color color){
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);
        pixmap.setColor(color);
        pixmap.fill();

        Texture newTexture = new Texture(pixmap);
        pixmap.dispose();
        return newTexture;
    }

    protected Texture createDefaultTexture(int width, int height){
        return createDefaultTexture(width, height, Color.RED);
    }

    protected Texture createDefaultTexture(Color color){
        return createDefaultTexture(100, 100, color);
    }

    protected Texture createDefaultTexture(){
        return createDefaultTexture(100, 100, Color.RED);
    }
}
