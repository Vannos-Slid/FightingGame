package io.github.fighting_game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private MainMenuScreen mainMenuScreen;
    private GameScreen gameScreen;

    boolean isTestingMode;

    @Override
    public void create() {
        //set false to play the game
        isTestingMode = false;

        setScreen(isTestingMode? (new TestScreen()) : (new MainMenuScreen(this)));

//        setScreen(isTestingMode? (testScreen = new TestScreen()) : (gameScreen = new GameScreen()));
    }


    @Override
    public void dispose() {
        if (gameScreen != null){
            gameScreen.dispose();
        }
//        if (testScreen != null){
//            testScreen.dispose();
//        }
        if (mainMenuScreen != null){
            mainMenuScreen.dispose();
        }
        if (getScreen() != null){
            getScreen().dispose();
        }
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        if (getScreen() != null) {
            getScreen().resize(width, height);
        }
    }

    public void switchScreen(Screen newScreen){
        Screen oldScreen = getScreen();
        setScreen(newScreen);
        if (oldScreen != null){
            oldScreen.dispose();
        }
    }

    public void showGameScreen(){
        setScreen(gameScreen);
    }

    public void showMainMenuScreen(){
        setScreen(mainMenuScreen);
    }
}
