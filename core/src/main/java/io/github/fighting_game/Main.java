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
        isTestingMode = false;

        setScreen(isTestingMode? (testScreen = new TestScreen()) : (gameScreen = new GameScreen()));
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
