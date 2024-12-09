package io.github.fighting_game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyAnimation extends MySprite{

    private String name;

    private Animation<MySprite> animation;
    private final MySprite[] frames;

    private float stateTime;
    private final float endTime;

    public boolean isReversed;
    public boolean isLooped;
    public boolean isAbleToStun;
    private boolean isFinished;

    public MyAnimation(String name, String[] framePaths, float frameDuration, boolean flip_h,
                                boolean isReversed, boolean isLooped, boolean isAbleToStun) {
        super(framePaths[0], flip_h);
        this.name = name;
        frames = new MySprite[framePaths.length];

        for (int i = 0; i < framePaths.length; i++){
            frames[i] = new MySprite(framePaths[i], flip_h);
            frames[i].setPosition(getX(), getY());
        }

        animation = new Animation<>(frameDuration, frames);

        endTime = framePaths.length * frameDuration;

        this.isReversed = isReversed;
        this.isLooped = isLooped;
        this.isAbleToStun = isAbleToStun;

        stateTime = (isReversed)? endTime : 0f;

        this.isFinished = false;
    }

    MyAnimation(String name, String[] framePaths, float frameDuration, boolean flip_h,
                    boolean isReversed){
        this(name, framePaths, frameDuration, flip_h, isReversed, true, false);
    }

    MyAnimation(String name, String[] framePaths, float frameDuration, boolean flip_h) {
        this(name, framePaths, frameDuration, flip_h, false);
    }

    MyAnimation(String name, String[] framePaths, float frameDuration) {
        this(name, framePaths, frameDuration, false, false);
    }

    MyAnimation(String[] framePaths, float frameDuration) {
        this("NONE", framePaths, frameDuration, false, false);
    }

    public void setName(String name){
        this.name = name;
    }

    public void setX(float x) {
        super.setX(x);
        for (MySprite frame : frames){
            frame.setX(x);
        }
    }

    public void setY(float y) {
        super.setY(y);
        for (MySprite frame : frames){
            frame.setY(y);
        }
    }

    public void setPosition(float x, float y){
        super.setPosition(x, y);
        for (MySprite frame : frames){
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
        for (MySprite frame : frames){
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
        MySprite currentFrame = animation.getKeyFrame(stateTime, isLooped);
        currentFrame.draw(batch);
        update(delta);
    }

    public void dispose(){
        super.dispose();
        for(MySprite frame : frames){
            frame.dispose();
        }
    }

}
