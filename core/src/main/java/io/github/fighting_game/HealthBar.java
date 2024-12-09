package io.github.fighting_game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class HealthBar extends TextureObjectCenteredP{
    ShapeRenderer shapeRenderer;
    private float maxHealth;
    private float currentHealth;

    public HealthBar(float maxHealth, float x, float y, String strTexturePath , boolean flip_h){
        super(x, y, strTexturePath, flip_h);
        this.shapeRenderer = new ShapeRenderer();
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        setWidth(250);
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
        shapeRenderer.rect(getX() + 2 + (flip_h? getWidth() - (currentHealth / maxHealth) * getWidth() : 0), (float) (getY() + 9), (currentHealth / maxHealth) * getWidth() - 4, (float) (getHeight() - 10.2));

        shapeRenderer.end();
        batch.begin();
    }

    public void dispose(){
        super.dispose();
        shapeRenderer.dispose();
    }
}
