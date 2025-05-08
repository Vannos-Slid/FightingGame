package io.github.fighting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

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

    void setZIndex(int index){
        directionsTouchpad.setZIndex(index);
        punchesTouchpad.setZIndex(index);
    }
}
