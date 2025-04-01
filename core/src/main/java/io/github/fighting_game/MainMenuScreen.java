package io.github.fighting_game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenuScreen implements Screen {
    private final Game game;
    private Stage stage;
    private Button button;
    private SpriteBatch batch;
    private BitmapFont fontSelectTraining;
    private String strSelectTraining;

    public MainMenuScreen(Game game){
        this.game = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        fontSelectTraining = new BitmapFont();
        fontSelectTraining.setColor(Color.WHITE);
        strSelectTraining = "Training";

        TextureObjectP textureObjectP = createTransparentTexture(100, 100);
        ClickListener clickListener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen());
            }
        };
        button = new Button(textureObjectP, clickListener);
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(button).pad(10);

        stage.addActor(table);
    }

    private TextureObjectP createTransparentTexture(int width, int height){
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 0);
        pixmap.fill();

        Texture texture = new Texture(pixmap);
        return new TextureObjectP(0, 0, width, height, texture, false);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        batch.begin();
        fontSelectTraining.draw(batch, strSelectTraining, 0, button.getHeight() / 2 + fontSelectTraining.getCapHeight() /2);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        fontSelectTraining.dispose();
    }
}
