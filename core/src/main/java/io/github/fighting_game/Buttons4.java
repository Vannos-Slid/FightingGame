package io.github.fighting_game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Buttons4 extends TextureObjectP {
    public Rectangle upButton;
    public Rectangle downButton;
    public Rectangle leftButton;
    public Rectangle rightButton;

    public boolean isUpPressed;
    public boolean isDownPressed;
    public boolean isLeftPressed;
    public boolean isRightPressed;

    Buttons4(float x, float y, int width, int height, String strTexturePath){
        super(x, y, width, height, strTexturePath);

        upButton = new Rectangle(getX() + (float) getWidth() / 3,
            getY () + (float) getHeight() * 2 / 3, getWidth() / 3,
            getHeight()  / 3);

        downButton = new Rectangle(getX() + (float) getWidth() / 3, getY(),
            getWidth() / 3, getHeight()  / 3);

        leftButton = new Rectangle(getX(), getY () + (float) getHeight() / 3,
            getWidth() / 3, getHeight()  / 3);

        rightButton = new Rectangle(getX() + (float) getWidth() * 2 / 3,
            getY () + (float) getHeight() / 3,
            getWidth() / 3, getHeight()  / 3);

        isDownPressed = false;
        isUpPressed = false;
        isLeftPressed = false;
        isRightPressed = false;
    }

    Buttons4(int width, int height, String strTexturePath){
        this(0, 0, width, height, strTexturePath);
    }

    public void setPosition(float x, float y) {
        super.setPosition(x, y);

        upButton.setPosition(getX() + (float) getWidth() / 3,
            getY () + (float) getHeight() * 2 / 3);

        downButton.setPosition(getX() + (float) getWidth() / 3, getY());

        leftButton.setPosition(getX(), getY () + (float) getHeight() / 3);

        rightButton.setPosition(getX() + (float) getWidth() * 2 / 3,
            getY () + (float) getHeight() / 3);
    }

    @Override
    public void setX(float x) {
        super.setX(x);

        upButton.setX(getX() + (float) getWidth() / 3);

        downButton.setX(getX() + (float) getWidth() / 3);

        leftButton.setX(getX());

        rightButton.setX(getX() + (float) getWidth() * 2 / 3);
    }

    @Override
    public void setY(float y) {
        super.setY(y);

        upButton.setY(getY () + (float) getHeight() * 2 / 3);

        downButton.setY(getY());

        leftButton.setY(getY () + (float) getHeight() / 3);

        rightButton.setY(getY () + (float) getHeight() / 3);
    }

    public boolean is_down_pressed() {
        return isDownPressed;
    }

    public boolean is_up_pressed() {
        return isUpPressed;
    }

    public boolean is_left_pressed(){
        return isLeftPressed;
    }

    public boolean is_right_pressed(){
        return isRightPressed;
    }

    public void unPressAllButtons() {
        isDownPressed = false;
        isUpPressed = false;
        isLeftPressed = false;
        isRightPressed = false;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setColor(1,1,1, 0.25f);
        super.render(batch);
        batch.setColor(1,1,1,1);
    }
}
