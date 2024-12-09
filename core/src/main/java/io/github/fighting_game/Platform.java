package io.github.fighting_game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Platform extends TextureObjectP {
    private final TextureObjectP[] childTextures;
    private float leftBorder;
    private float rightBorder;
    private float leftWall;
    private float rightWall;
    private float floorBorder;

    Platform(float x, float y, String strTexturePath, boolean flip_h, TextureObjectP[] childTextures){
        super(x, y, strTexturePath, flip_h);
        this.childTextures = childTextures;

        leftBorder = 0;
        rightBorder = 0;

        leftWall = 200;
        rightWall = 200;
        floorBorder = 80;
    }

    Platform(float x, float y, String strTexturePath, boolean flip_h){
        this(x, y, strTexturePath, flip_h, null);
    }
    Platform(float x, float y, String strTexturePath){
        this(x, y, strTexturePath, false);
    }

    Platform(String strTexturePath, boolean flip_h){
        this(0, 0, strTexturePath, flip_h);
    }

    Platform(String strTexturePath){
        this(strTexturePath, false);
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        for(TextureObjectP textureObjectP : childTextures){
            float newChildX = getX() + textureObjectP.getX();
            textureObjectP.setX(newChildX);
        }
        setLeftBorder(x);
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        for(TextureObjectP textureObjectP : childTextures){
            float newChildY = getY() + textureObjectP.getY();
            textureObjectP.setY(newChildY);
        }
    }

    public void setLeftBorder(float leftBorder){
        this.leftBorder = leftBorder;
    }

    public void setRightBorder(float rightBorder) {
        this.rightBorder = rightBorder;
    }

    public void setLeftWall(float leftWall) {
        this.leftWall = leftWall;
    }

    public void setRightWall(float rightWall) {
        this.rightWall = rightWall;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        for(TextureObjectP textureObjectP : childTextures){
            float newChildX = getX() + textureObjectP.getX();
            float newChildY = getY() + textureObjectP.getY();
            textureObjectP.setPosition(newChildX, newChildY);
        }
    }

    public float getLeftBorder(){
        return getX() + leftBorder;
    }

    public float getRightBorder(){
        return getX() + getWidth() - rightBorder;
    }

    public float getLeftWall(){
         return getX() + leftBorder + leftWall;
    }

    public float getRightWall(){
        return getX() + getWidth() - rightWall - rightBorder;
    }

    public float getFloorBorder(){
        return getY() + floorBorder;
    }

    public TextureObjectP[] getChildTextures() {
        return childTextures;
    }

    public void render(SpriteBatch batch) {
        super.render(batch);
        for (TextureObjectP childTexture : childTextures){
            childTexture.render(batch);
        }
    }

    @Override
    public void dispose(){
        super.dispose();
        for(TextureObjectP childTexture : childTextures)
            childTexture.dispose();
    }

}
