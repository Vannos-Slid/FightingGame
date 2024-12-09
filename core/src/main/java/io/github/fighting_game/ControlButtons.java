package io.github.fighting_game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class ControlButtons {
    public Buttons4 directionButtons;
    private Buttons4 punchesButtons;

    private boolean isDirectionsPressed;
    private boolean isPunchesPressed;
    private boolean wasInputMode;

    private AnimatedCharacter animatedCharacter;

    private final float timeTillNextInput = 0.2f;
    private float time;

    ControlButtons(int dirWidth, int dirHeight, int punchWidth, int punchHeight,
                   AnimatedCharacter animatedCharacter){

        directionButtons = new Buttons4(dirWidth, dirHeight,
            "Buttons/directions_white.png");

        punchesButtons = new Buttons4(punchWidth, punchHeight,
            "Buttons/punches_white.png");

        this.animatedCharacter = animatedCharacter;

        isDirectionsPressed = false;
        isPunchesPressed = false;
        wasInputMode = false;

        time = timeTillNextInput;
    }

    public void setDirectionButtonsPosition(float x, float y){
        directionButtons.setPosition(x, y);
    }

    public void setPunchesButtonsPosition(float x, float y){
        punchesButtons.setPosition(x, y);
    }

    public boolean is_directions_pressed(){
        return isDirectionsPressed;
    }

    public boolean is_punches_pressed(){
        return isPunchesPressed;
    }

    public boolean containsUp(Buttons4 buttons4, float x, float y){
        return buttons4.upButton.contains(x, y);
    }

    public boolean containsDown(Buttons4 buttons4, float x, float y){
        return buttons4.downButton.contains(x, y);
    }

    public boolean containsLeft(Buttons4 buttons4, float x, float y){
        return buttons4.leftButton.contains(x, y);
    }

    public boolean containsRight(Buttons4 buttons4, float x, float y){
        return buttons4.rightButton.contains(x, y);
    }

    public void unPressAllButtons(){
        directionButtons.unPressAllButtons();
        punchesButtons.unPressAllButtons();
    }

    public void touchDownDir(int screenX, int screenY, OrthographicCamera camera){
        time = timeTillNextInput;
        wasInputMode = true;
        checkToTurn(directionButtons, screenX, screenY, camera, true);
    }

    public void touchUpDir(int screenX, int screenY, OrthographicCamera camera){
        checkToTurn(directionButtons, screenX, screenY,camera, false);
    }
    public void touchDraggedDir(int screenX, int screenY, OrthographicCamera camera){
        boolean isSomethingPressed = checkToTurn(directionButtons,screenX, screenY, camera, true);
        if(!isSomethingPressed && !checkButton(punchesButtons, screenX, screenY, camera))
            directionButtons.unPressAllButtons();
    }

    public void touchDownPunch(int screenX, int screenY, OrthographicCamera camera){
        time = timeTillNextInput;
        wasInputMode = true;
        checkToProcess(screenX, screenY, camera);
    }

    public void touchUpPunch(int screenX, int screenY, OrthographicCamera camera){
        checkToTurn(punchesButtons, screenX, screenY,camera, false);
    }
    public void touchDraggedPunch(int screenX, int screenY, OrthographicCamera camera){
        boolean isSomethingPressed = checkToTurn(punchesButtons,screenX, screenY, camera, true);
        if(!isSomethingPressed)
            punchesButtons.unPressAllButtons();
    }

    private boolean checkToProcess(int screenX, int screenY, OrthographicCamera camera){
        Vector3 touchPos = new Vector3(screenX, screenY, 0);
        camera.unproject(touchPos);

        if(containsLeft(punchesButtons, touchPos.x, touchPos.y)){
            animatedCharacter.punchesBuffer.append("1");
//            animatedCharacter.process(false, false, true, false);
            return true;
        }
        else if(containsUp(punchesButtons, touchPos.x, touchPos.y)){
            animatedCharacter.punchesBuffer.append("2");
//            animatedCharacter.process(false, true, false, false);
            return true;
        }
        else if (containsDown(punchesButtons, touchPos.x, touchPos.y)){
            animatedCharacter.punchesBuffer.append("3");
//            animatedCharacter.process(false, false, false, true);
            return true;
        }
        else if(containsRight(punchesButtons, touchPos.x, touchPos.y)){
            animatedCharacter.punchesBuffer.append("4");
//            animatedCharacter.process(true, false, false, false);
            return true;
        }
        return false;
    }

    private boolean checkButton(Buttons4 buttons4, int screenX, int screenY, OrthographicCamera camera){
        Vector3 touchPos = new Vector3(screenX, screenY, 0);
        camera.unproject(touchPos);

        return containsDown(buttons4, touchPos.x, touchPos.y) ||
            containsLeft(buttons4, touchPos.x, touchPos.y) ||
            containsRight(buttons4, touchPos.x, touchPos.y) ||
            containsUp(buttons4, touchPos.x, touchPos.y);

    }

    private boolean checkToTurn(Buttons4 buttons4, int screenX, int screenY,
                                OrthographicCamera camera, boolean on_off){
        Vector3 touchPos = new Vector3(screenX, screenY, 0);
        camera.unproject(touchPos);

        if(containsDown(buttons4, touchPos.x, touchPos.y)){
            buttons4.unPressAllButtons();
            buttons4.isDownPressed = on_off;
            return true;
        }
        else if(containsLeft(buttons4, touchPos.x, touchPos.y)){
            buttons4.unPressAllButtons();
            buttons4.isLeftPressed = on_off;
            return true;
        }
        else if(containsRight(buttons4, touchPos.x, touchPos.y)){
            buttons4.unPressAllButtons();
            buttons4.isRightPressed = on_off;
            return true;
        }
        else if(containsUp(buttons4, touchPos.x, touchPos.y)){
            buttons4.unPressAllButtons();
            buttons4.isUpPressed = on_off;
            return true;
        }
        return false;
    }

    public void movement(Platform platform, AnimatedCharacter enemy) {
        animatedCharacter.movement(directionButtons.isRightPressed, directionButtons.isUpPressed,
            directionButtons.isLeftPressed, directionButtons.isDownPressed, platform, enemy);
    }

    public void process(){
        animatedCharacter.process(punchesButtons.isRightPressed, punchesButtons.isUpPressed,
            punchesButtons.isLeftPressed, punchesButtons.isDownPressed);
    }

    public void updateButtons(OrthographicCamera camera){
        setDirectionButtonsPosition(camera.position.x - 250,camera.position.y / 3);
        setPunchesButtonsPosition(camera.position.x + 150, camera.position.y / 3);
    }

    private void update(float delta,OrthographicCamera camera, Platform platform,
                        AnimatedCharacter enemyChar){
        if(wasInputMode){
            time -= delta;
            if (time < 0){
                wasInputMode = false;
                time = timeTillNextInput;
                punchesButtons.unPressAllButtons();
                animatedCharacter.clearPunchesBuffer();

            }
            else {
                if(!animatedCharacter.punchesBuffer.toString().isEmpty()){
                    animatedCharacter.tryComboAttempt(directionButtons, punchesButtons);
                }
            }
        }
        updateButtons(camera);
        if(animatedCharacter.shouldClearBuffer){
//            animatedCharacter.clearKeysBuffer();
            punchesButtons.unPressAllButtons();
        }
//        process();
        movement(platform, enemyChar);
    }

    public void render(SpriteBatch batch, float delta, OrthographicCamera camera, Platform platform,
                       AnimatedCharacter enemyChar){
        update(delta, camera, platform, enemyChar);
        animatedCharacter.render(batch, delta);
        directionButtons.render(batch);
        punchesButtons.render(batch);
    }

    public void dispose(){
        directionButtons.dispose();
        punchesButtons.dispose();
        animatedCharacter.dispose();
    }
}
