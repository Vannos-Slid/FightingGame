package io.github.fighting_game;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class MyLabel extends Label {

    MyLabel(String text){
        super(text, MySimplerMethods.generateDefaultLabelStyle());
    }
    MyLabel(){
        this("Hello LibGDX!");

    }

    public void makeItalic(){
        
    }

}
