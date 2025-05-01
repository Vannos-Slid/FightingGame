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

    //Direction controller
    private Touchpad touchpad;

    private MyCharacter character;

    private com.badlogic.gdx.scenes.scene2d.ui.Button testButton;

    //Button skin
    private Skin skin;

    private Skin touchpadSkin;



    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    private void init(){

        skin = new Skin(Gdx.files.internal("data/UI-skins/skin/arcade-ui.json"));

        touchpadSkin = new Skin(Gdx.files.internal("data/UI-skins/Test Skin/TouchPad/touchpad.json"));

//        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        touchpad = new Touchpad(10, touchpadSkin);
//        touchpad.setBounds(50, 50, 200, 200);

       testButton =
            new com.badlogic.gdx.scenes.scene2d.ui.Button(skin.get("default", Button.ButtonStyle.class));

       testButton.setPosition(Gdx.graphics.getWidth()-150,50);
       testButton.setSize(100, 100);

       //Adding Controller stuff to the screen
        stage.addActor(testButton);
        stage.addActor(touchpad);

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

        float touchpadKnobPercentX = touchpad.getKnobPercentX();
        float touchpadKnobPercentY = touchpad.getKnobPercentY();

        System.out.println("TouchPad X: " + touchpadKnobPercentX + '\n' + "TouchPad Y: " +
            touchpadKnobPercentY + '\n');

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
