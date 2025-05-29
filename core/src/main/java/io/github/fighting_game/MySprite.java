package io.github.fighting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class MySprite extends Sprite implements Disposable {
    protected boolean flip_h;

    public MySprite(Color color, float x, float y, float width, float height){
        super(MySimplerMethods.createDefaultTexture((int) width, (int) height, color));

        this.setPosition(x, y);
        this.setSize(width, height);

        this.flip_h = false;
    }

    public MySprite(Color color, float width, float height){
        this(color,0, 0, width, height);
    }

    public MySprite(int width, int height){
        this(Color.RED ,width, height);
    }

    public MySprite(Texture texture, float x, float y, float width, float height, boolean flip_h){
        super(MySimplerMethods.makeTexture(texture));
        super.setPosition(x, y);
        if (width != 0.f && height != 0){
            this.setSize(width, height);
        }
        else this.setSize(texture.getWidth(),texture.getHeight());
        this.flip(flip_h, false);
    }

    public MySprite(Texture texture, float x, float y, float width, float height){
        this(texture, x, y, width, height, false);
    }

    public MySprite(Texture texture, float width, float height, boolean flip_h){
        this(texture, 0, 0, width, height, flip_h);
    }

    public MySprite(Texture texture, float width, float height){
        this(texture, width, height, false);
    }

    public MySprite(TextureRegion region, float x, float y, float width, float height, boolean flip_h){
        this(region.getTexture(), x, y, width, height, flip_h);
    }

    public MySprite(TextureRegion region, float x, float y, float width, float height){
        this(region.getTexture(), x, y, width, height, false);
    }

    public MySprite(TextureRegion region, float width, float height, boolean flip_h){
        this(region.getTexture(), 0, 0, width, height, flip_h);
    }

    public MySprite(TextureRegion region, float width, float height){
        this(region.getTexture(), width, height, false);
    }

    public MySprite(Texture texture, boolean flip_h){
        this(texture, 0 ,0, flip_h);
    }

    public MySprite(Texture texture){
        this(texture, false);
    }

    public MySprite(TextureRegion region, boolean flip_h) {
        this(region.getTexture(), flip_h);
    }

    public MySprite(TextureRegion region) {
        this(region.getTexture());
    }

    public MySprite() {
        super(MySimplerMethods.createDefaultTexture());
        this.flip_h = false;
    }

    public MySprite(String strTexturePath, float x, float y, float width, float height, boolean flip_h){
        this(MySimplerMethods.makeTexture(strTexturePath), x, y, width, height, flip_h);
    }

    public MySprite(String strTexturePath, float x, float y, float width, float height){
        this(strTexturePath, x, y, width, height, false);
    }

    public MySprite(String strTexturePath, float width, float height){
        this(strTexturePath, 0, 0, width, height, false);
    }

    public MySprite(String strTexturePath, boolean flip_h){
        this(strTexturePath,0,0,0,0, false);
    }

    public MySprite(Texture texture, int srcWidth, int srcHeight) {
        super(MySimplerMethods.makeTexture(texture), srcWidth, srcHeight);
        this.flip_h = false;
    }

    public MySprite(Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(MySimplerMethods.makeTexture(texture), srcX, srcY, srcWidth, srcHeight);
        this.flip_h = false;
    }

    public MySprite(TextureRegion region, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(MySimplerMethods.makeTexture(region.getTexture()), srcX, srcY, srcWidth, srcHeight);
        this.flip_h = false;
    }

    public MySprite(Sprite sprite, boolean flip_h){
        super(sprite);
        this.flip_h = flip_h;
    }

    public MySprite(Sprite sprite) {
        this(sprite, false);
    }

    public MySprite(String strTexturePath, float x, float y, boolean flipH) {
        this(MySimplerMethods.makeTexture(strTexturePath), x, y, 0, 0, flipH);
    }

    //Methods

    public void flip(boolean flip_h){
        if (this.flip_h == !flip_h){
            flip();
            this.flip_h = flip_h;
        }
    }

    public void flip() {
        this.flip(true, false);
    }

    public boolean isFlipped(){
        return flip_h;
    }

    public void dispose() {
        getTexture().dispose();
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }

    @Override
    public void draw(Batch batch, float alphaModulation) {
        super.draw(batch, alphaModulation);
    }
}

