package io.github.fighting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Objects;

public class AnimatedTexture extends TextureObjectP {

    private String name;
    private Animation<TextureObjectP> animation;
    private float stateTime;
    private final TextureObjectP[] frames;
    public boolean isReversed;
    public boolean isLooped;
    public boolean isAbleToStun;
    private final float endTime;
    private boolean isFinished;

    AnimatedTexture(String name, String[] framePaths, float frameDuration, boolean flip_h,
                    boolean isReversed, boolean isLooped, boolean isAbleToStun) {
        super(framePaths[0], flip_h);
        this.name = name;
        frames = new TextureObjectP[framePaths.length];

        for (int i = 0; i < framePaths.length; i++){
            frames[i] = new TextureObjectP(framePaths[i], flip_h);
            frames[i].setPosition(getX(), getY());
        }

        animation = new Animation<>(frameDuration, frames);

        endTime = framePaths.length * frameDuration;

        this.isReversed = isReversed;
        this.isLooped = isLooped;
        this.isAbleToStun = isAbleToStun;

        stateTime = (isReversed)? endTime : 0f;
    }

    AnimatedTexture(String name, String[] framePaths, float frameDuration, boolean flip_h,
                    boolean isReversed){
        this(name, framePaths, frameDuration, flip_h, isReversed, true, false);
    }

    AnimatedTexture(String name, String[] framePaths, float frameDuration, boolean flip_h) {
        this(name, framePaths, frameDuration, flip_h, false);
    }

    AnimatedTexture(String name, String[] framePaths, float frameDuration) {
        this(name, framePaths, frameDuration, false, false);
    }

    AnimatedTexture(String[] framePaths, float frameDuration) {
        this("NONE", framePaths, frameDuration, false, false);
    }

    public void setName(String name){
        this.name = name;
    }

    public void setX(float x) {
        super.setX(x);
        for (TextureObjectP frame : frames){
            frame.setX(x);
        }
    }

    public void setY(float y) {
        super.setY(y);
        for (TextureObjectP frame : frames){
            frame.setY(y);
        }
    }

    public void setPosition(float x, float y){
        super.setPosition(x, y);
        for (TextureObjectP frame : frames){
            frame.setPosition(x, y);
        }
    }

    public String getName(){
        return name;
    }

    public boolean is_reversed(){
        return isReversed;
    }

    public boolean is_looped(){
        return isLooped;
    }

    public boolean is_finished(){return isFinished;}

    @Override
    public void flip(boolean flip_h) {
        super.flip(flip_h);
        for (TextureObjectP frame : frames){
            frame.flip(flip_h);
        }
    }

    public void reverse(boolean isReversed){
        this.isReversed = isReversed;
    }

    public void reverse(){
        isReversed = !isReversed;
    }

    public void resetState(){
        stateTime = (isReversed)? endTime : 0f;
        isFinished = false;
    }

    private void update(float delta){
        if(isReversed){
            stateTime -= delta;
            if (stateTime <= 0f) {
                stateTime = isLooped ? endTime : 0f;
                if(!isLooped)
                    isFinished = true;
            }
        }
        else {
            stateTime += delta;
            if(stateTime >= endTime) {
                stateTime = isLooped ? 0f :
                    endTime;
                if(!isLooped)
                    isFinished = true;
            }
        }
    }

    public void render(SpriteBatch batch, float delta) {
        TextureObjectP currentFrame = animation.getKeyFrame(stateTime, isLooped);
        currentFrame.render(batch);
        update(delta);
    }

    public void dispose(){
        super.dispose();
        for(TextureObjectP frame : frames){
            frame.dispose();
        }
    }
}
