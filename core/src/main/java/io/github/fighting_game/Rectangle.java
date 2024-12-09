package io.github.fighting_game;

public class Rectangle {
    public float x;
    public float y;
    public int width;
    public int height;

    Rectangle(float numX, float numY, int numWidth, int numHeight){
        x = numX;
        y = numY;
        width = numWidth;
        height = numHeight;
    }

    public float getX(){
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setX(float numX) {
        x = numX;
    }

    public void setY(float numY) {
        y = numY;
    }

    public void setWidth(int numWidth){
        width = numWidth;
    }

    public void setHeight(int numHeight) {
        height = numHeight;
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }

    public boolean contains(float x, float y){
        return (getX() + getWidth()  >= x && getX() <= x) &&
            (getY() + getHeight() >= y && getY() <= y);
    }
}
