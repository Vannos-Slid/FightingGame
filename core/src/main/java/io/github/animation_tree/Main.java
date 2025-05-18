package io.github.animation_tree;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private EditScreen editScreen;

    @Override
    public void create() {
        editScreen = new EditScreen();

        setScreen(editScreen);
    }

    @Override
    public void render() {
//        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        super.render();
    }

    @Override
    public void dispose() {
        editScreen.dispose();
    }

    @Override
    public void resize(int width, int height) {
        if (editScreen != null){
            editScreen.resize(width, height);
        }
    }
}
