package io.github.fighting_game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.github.tommyettinger.textra.Styles;
import com.github.tommyettinger.textra.TypingLabel;

public class MyHealthBar extends MyCenteredSprite{
    private ShapeRenderer shapeRenderer;

    private final float maxHealth;
    private float currentHealth;

    private final TypingLabel label;

    private final float SCALE_SPRITE_OFFSET;

    public void dispose(){
        super.dispose();
        shapeRenderer.dispose();
        label.getFont().dispose();
    }

    public MyHealthBar(String name, float maxHealth, float x, float y, String strTexturePath,
                     boolean flip_h){
        super(strTexturePath, x, y, flip_h);
        this.shapeRenderer = new ShapeRenderer();
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        Styles.LabelStyle textraStyle = MySimplerMethods.generateDefaultTetraStyle2();

        label = new TypingLabel("[/]" + name,(textraStyle));
        label.skipToTheEnd();

        SCALE_SPRITE_OFFSET = 250 / getWidth();
        setSize(getWidth() * SCALE_SPRITE_OFFSET, getHeight() * SCALE_SPRITE_OFFSET);
//        setScale(SCALE_SPRITE_OFFSET);
    }

    public MyHealthBar(float maxHealth, float x, float y, String strTexturePath, boolean flip_h){
        this("No Name",maxHealth, x, y, strTexturePath, flip_h);
    }

    public MyHealthBar(String name, float maxHealth, float x, float y, boolean flip_h){
        this(name, maxHealth, x, y, "LifeBar/lifeBar1.png", flip_h);
    }

    public MyHealthBar(String name, float maxHealth, float x, float y){
        this(name,maxHealth, x, y, false);
    }

    public MyHealthBar(String name, float maxHealth){
        this(name, maxHealth, 0, 0);
    }

    public MyHealthBar(String name, boolean flip_h){
        this(name, 100, 0, 0, flip_h);
    }

    public MyHealthBar(String name){
        this(name, 100);
    }

    public MyHealthBar(){
        this("No Name");
    }

    public void setHealth(float health){
        this.currentHealth = Math.max(0, Math.min(health, maxHealth));
    }

    public void draw(Batch batch, OrthographicCamera camera, float alphaModulation) {
        super.draw(batch, alphaModulation);
        batch.end();

        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.BLUE);

        final float HEALTH_BAR_OFFSET_X = SCALE_SPRITE_OFFSET;
        final float HEALTH_BAR_OFFSET_Y = 6f * SCALE_SPRITE_OFFSET;
        final float HEALTH_BAR_HEIGHT_OFFSET = 7f * SCALE_SPRITE_OFFSET;

        float healthWidth = (currentHealth / maxHealth) * getWidth() - HEALTH_BAR_OFFSET_X * 2;
        float x = getX() + HEALTH_BAR_OFFSET_X + (flip_h ? getWidth() - healthWidth - HEALTH_BAR_OFFSET_X * 2 : 0);
        float y = getY() + HEALTH_BAR_OFFSET_Y;
        float healthHeight = getHeight() - HEALTH_BAR_HEIGHT_OFFSET;

        shapeRenderer.rect(x, y, healthWidth, healthHeight);
        shapeRenderer.end();
        batch.begin();

        final float HEALTH_BAR_LABEL_OFFSET_X = 10f;
        final float HEALTH_BAR_LABEL_OFFSET_Y = 7f;

        if (flip_h) {
            label.setPosition(getX() + getWidth() -
                label.getWidth() - HEALTH_BAR_LABEL_OFFSET_X, getY() +
                HEALTH_BAR_LABEL_OFFSET_Y);
        } else {
            label.setPosition(getX() + HEALTH_BAR_LABEL_OFFSET_X, getY() +
                HEALTH_BAR_LABEL_OFFSET_Y);
        }

        label.draw(batch, 1);
    }
}
