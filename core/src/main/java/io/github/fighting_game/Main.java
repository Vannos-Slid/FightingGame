package io.github.fighting_game;

import com.badlogic.gdx.Game;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
     GameScreen gameScreen;
     TestScreen testScreen;
     boolean isTestingMode;

    @Override
    public void create() {
        //set false to play the game
        isTestingMode = true;

        if(isTestingMode){
            testScreen = new TestScreen();
        }
        else {
            gameScreen = new GameScreen();
        }

        setScreen(isTestingMode? testScreen : gameScreen);
    }

    @Override
    public void dispose() {
        gameScreen.dispose();
        testScreen.dispose();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        if (gameScreen != null) {
            gameScreen.resize(width, height);
        }
    }

}
