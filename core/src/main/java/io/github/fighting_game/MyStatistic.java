package io.github.fighting_game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.github.tommyettinger.textra.TypingLabel;

public class MyStatistic extends Actor {
    private TypingLabel stats;

    MyStatistic(int fontSize){
        super();

        final String strStats =
                "TouchpadKnoxX : 0\n" +
                "TouchpadKnoxY : 0\n";

        stats = new TypingLabel(strStats, MySimplerMethods.generateTetraStyle(fontSize));
        stats.skipToTheEnd();
    }

    MyStatistic(){
        this(13);
    }

    public void setPosition(float x, float y){
        stats.setPosition(x,y);
    };

    public void setStats(Touchpad touchpad){

        final float touchpadKnobPercentX = touchpad.getKnobPercentX();
        final float touchpadKnobPercentY = touchpad.getKnobPercentY();

        final String strStats =
                "TouchpadKnoxX : " + touchpadKnobPercentX + "\n" +
                "TouchpadKnoxY : " + touchpadKnobPercentY + "\n";

        stats.setText(strStats);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stats.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        stats.draw(batch, parentAlpha);
    }
}
