package io.github.fighting_game;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Button extends com.badlogic.gdx.scenes.scene2d.ui.Button {
    private Skin skin;
    public TextureObject buttonTexture;

    Button(TextureObject buttonTexture, ClickListener clickListener) {
        this.buttonTexture = buttonTexture;

        skin = new Skin();

        ButtonStyle buttonStyle = new ButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(buttonTexture.getTexture());

        addListener(clickListener);
    }

}
