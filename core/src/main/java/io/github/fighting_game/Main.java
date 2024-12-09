package io.github.fighting_game;

import com.badlogic.gdx.Game;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    GameScreen gameScreen;

    @Override
    public void create() {
        gameScreen = new GameScreen();
        setScreen(gameScreen);
    }

    @Override
    public void dispose() {
        gameScreen.dispose();
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
