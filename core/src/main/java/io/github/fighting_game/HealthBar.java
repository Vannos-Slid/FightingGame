package io.github.fighting_game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.github.tommyettinger.textra.Styles;
import com.github.tommyettinger.textra.TypingLabel;

public class HealthBar extends TextureObjectCenteredP{
    ShapeRenderer shapeRenderer;

    private final float maxHealth;
    private float currentHealth;

    private final TypingLabel label;

    public void dispose(){
        super.dispose();
        shapeRenderer.dispose();
    }

    public HealthBar(String name, float maxHealth, float x, float y, String strTexturePath,
                     boolean flip_h){
        super(x, y, strTexturePath, flip_h);
        this.shapeRenderer = new ShapeRenderer();
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        Styles.LabelStyle textraStyle = MySimplerMethods.generateDefaultTetraStyle2();

        label = new TypingLabel("[/]" + name,(textraStyle));
        label.skipToTheEnd();
        setWidth(250);
    }

    public HealthBar(float maxHealth, float x, float y, String strTexturePath, boolean flip_h){
        this("NoName",maxHealth, x, y, strTexturePath, flip_h);
    }

    public void setHealth(float health){
        this.currentHealth = Math.max(0, Math.min(health, maxHealth));
    }

    public void render(SpriteBatch batch, OrthographicCamera camera){
        super.render(getCenteredX(), getCenteredY(), batch);
        batch.end();

        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.BLUE);

        final float HEALTH_BAR_OFFSET_X = 2f;
        final float HEALTH_BAR_OFFSET_Y = 9f;
        final float HEALTH_BAR_HEIGHT_OFFSET = 10.2f;

        float healthWidth = (currentHealth / maxHealth) * getWidth() - HEALTH_BAR_OFFSET_X * 2;
        float x = getX() + HEALTH_BAR_OFFSET_X + (flip_h ? getWidth() - healthWidth - HEALTH_BAR_OFFSET_X * 2 : 0);
        float y = getY() + HEALTH_BAR_OFFSET_Y;
        float healthHeight = getHeight() - HEALTH_BAR_HEIGHT_OFFSET;

//        shapeRenderer.rect(getX() + 2 + (flip_h? getWidth() - (currentHealth / maxHealth) * getWidth() : 0),
//            (float) (getY() + 9), (currentHealth / maxHealth) * getWidth() - 4, (float) (getHeight() - 10.2));

        shapeRenderer.rect(x, y, healthWidth, healthHeight);
        shapeRenderer.end();
        batch.begin();

        final float HEALTH_BAR_LABEL_OFFSET_X = 10f;
        final float HEALTH_BAR_LABEL_OFFSET_Y = 6f;

        if (flip_h) {

            label.setPosition(getX() + getWidth() -
                label.getWidth() - HEALTH_BAR_LABEL_OFFSET_X, getY() + HEALTH_BAR_LABEL_OFFSET_Y);
        } else {
            label.setPosition(getX() + HEALTH_BAR_LABEL_OFFSET_X, getY() +
                HEALTH_BAR_LABEL_OFFSET_Y);
        }

        label.draw(batch, 1);
    }
}
