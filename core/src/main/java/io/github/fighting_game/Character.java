package io.github.fighting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Character extends TextureObjectP {

    Character(float x, float y, String strTexturePath, boolean flip_h){
        super(x, y, strTexturePath, flip_h);
    }

    Character(float x, float y, String strTexturePath){
        this(x, y, strTexturePath, false);
    }

    Character(String strTexturePath){
        this(0f, 0f, strTexturePath);
    }

    public void movement(){
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            setX(getX() - 2);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            setX(getX() + 2);
        }
    }

    public void render(SpriteBatch batch){
        movement();
        super.render(batch);
    }

}
