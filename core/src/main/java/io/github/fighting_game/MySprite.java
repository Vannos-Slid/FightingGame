package io.github.fighting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MySprite extends Sprite {
    protected boolean flip_h;

    public MySprite(Color color, float x, float y, float width, float height){
        super(createDefaultTexture((int) width, (int) height, color));

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
        super(makeTexture(texture));
        this.setPosition(x, y);
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
        super(createDefaultTexture());
        this.flip_h = false;
    }

    public MySprite(String strTexturePath, float x, float y, float width, float height, boolean flip_h){
        this(makeTexture(strTexturePath), x, y, width, height, flip_h);
    }

    public MySprite(String strTexturePath, float x, float y, float width, float height){
        this(makeTexture(strTexturePath), x, y, width, height, false);
    }

    public MySprite(String strTexturePath, float width, float height){
        this(makeTexture(strTexturePath), 0, 0, width, height, false);
    }

    public MySprite(String strTexturePath, boolean flip_h){
        this(makeTexture(strTexturePath), false);
    }

    public MySprite(Texture texture, int srcWidth, int srcHeight) {
        super(makeTexture(texture), srcWidth, srcHeight);
        this.flip_h = false;
    }

    public MySprite(Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(makeTexture(texture), srcX, srcY, srcWidth, srcHeight);
        this.flip_h = false;
    }

    public MySprite(TextureRegion region, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(makeTexture(region.getTexture()), srcX, srcY, srcWidth, srcHeight);
        this.flip_h = false;
    }

    public MySprite(Sprite sprite) {
        super(sprite);
        this.flip_h = false;
    }

    public MySprite(String strTexturePath, float x, float y, boolean flipH) {
        this(makeTexture(strTexturePath), x, y, 0, 0, flipH);
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


    protected static Texture makeTexture(Texture texture){
        return texture != null? texture : createDefaultTexture();
    }

    protected static Texture makeTexture(FileHandle fileHandle){
        Texture texture;

        texture = fileHandle.exists() ? new Texture(fileHandle) : createDefaultTexture();

        return texture;
    }

    protected static Texture makeTexture(String strTexturePath) {
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

    protected static Texture createDefaultTexture(Color color){
        return createDefaultTexture(100, 100, color);
    }

    protected static Texture createDefaultTexture(int width, int height){
        return createDefaultTexture(width, height, Color.RED);
    }

    protected static Texture createDefaultTexture(){
        return createDefaultTexture(Color.RED);
    }

    public void dispose() {
        getTexture().dispose();
    }

/*    protected void draw(float center, float center1, SpriteBatch batch) {

    }*/
}

