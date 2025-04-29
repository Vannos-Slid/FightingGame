package io.github.fighting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class TestScreen implements Screen {
    private Stage stage;

    private Touchpad touchpad;

    private MyCharacter character;

//    private Button testButton;

    private com.badlogic.gdx.scenes.scene2d.ui.Button testButton;

    private Skin skin;


    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    private void init(){

        skin = new Skin(Gdx.files.internal("data/UI-skins/Test Skin/button.json"));

//        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

//        touchpad = new Touchpad(10, skin);

       testButton =
            new com.badlogic.gdx.scenes.scene2d.ui.Button(skin.get("default", Button.ButtonStyle.class));

       testButton.setPosition(Gdx.graphics.getWidth()-150,50);
       testButton.setSize(100, 100);

        stage.addActor(testButton);

        testButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Gay");
            }
        });
    }

    TestScreen(){
        init();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(255,0,0,1);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void hide() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resize(int width, int height) {

    }


    @Override
    public void show() {

    }
}
