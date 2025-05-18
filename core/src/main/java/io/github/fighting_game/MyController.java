package io.github.fighting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MyController {
    private Skin controllerSkin;

    private Touchpad directionsTouchpad;
    private Touchpad punchesTouchpad;

    MyController(Skin controllerSkin){
        this.controllerSkin =  controllerSkin;

        try{
            directionsTouchpad = new Touchpad(10, controllerSkin.get("default",
                Touchpad.TouchpadStyle.class));
            punchesTouchpad = new Touchpad(10, controllerSkin.get("punches",
                Touchpad.TouchpadStyle.class));

            directionsTouchpad.setBounds(400,25,100,100);
            punchesTouchpad.setBounds(785,25,100,100);

        }
        catch (Exception e){
            System.out.println("There was an error appeared with buttons init");
        }

    }

    MyController(FileHandle fileHandle){
        this(new Skin(fileHandle));
    }

    MyController(String skinFilePath){
        this(new Skin(Gdx.files.internal(skinFilePath)));
    }

    MyController(){
        this(new Skin(Gdx.files.internal("data/UI-skins/Controller/Controller.json")));
    }

    public void dispose(){
        controllerSkin.dispose();
    }

    public Skin getControllerSkin() {
        return controllerSkin;
    }

    public Touchpad getDirectionsTouchpad() {
        return directionsTouchpad;
    }

    public Touchpad getPunchesTouchpad() {
        return punchesTouchpad;
    }

    public void setControllerSkin(Skin controllerSkin) {
        this.controllerSkin = controllerSkin;
    }

    public void setDirectionsTouchpad(Touchpad directionsTouchpad) {
        this.directionsTouchpad = directionsTouchpad;
    }

    public void setPunchesTouchpad(Touchpad punchesTouchpad) {
        this.punchesTouchpad = punchesTouchpad;
    }

    public void setPosition(float x, float y){

    };

    public void setDirButtonsPos(float x, float y){
        directionsTouchpad.setPosition(x, y);
    }

    public  void setPunchButtonsPos(float x, float y){
        punchesTouchpad.setPosition(x, y);
    }

    public void addToStage(Stage stage){
        stage.addActor(directionsTouchpad);
        stage.addActor(punchesTouchpad);
    }
}
