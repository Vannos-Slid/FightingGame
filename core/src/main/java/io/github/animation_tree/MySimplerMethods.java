package io.github.animation_tree;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

public class MySimplerMethods {

    public static String createPlatformTexturePath(String strLevelName, String textureName){
        if(textureName == null)
            return "Null";
        return  "Levels/" + strLevelName + "/" + textureName + ".png";
    }

    public static String[] createAnimationPath(String characterName, String[] frameNames){
        String[] frames = new String[frameNames.length];
        for(int i = 0; i < frameNames.length; i++){
            frames[i] = "Characters/" + characterName + "/Default/" +
                frameNames[i] + ".png";
        }
        return frames;
    }

    public static Texture makeTexture(Texture texture){
        return texture != null? texture : createDefaultTexture();
    }

    public static Texture makeTexture(FileHandle fileHandle){
        Texture texture;

        texture = fileHandle.exists() ? new Texture(fileHandle) : createDefaultTexture();

        return texture;
    }

    public static Texture makeTexture(String strTexturePath) {
        return makeTexture(Gdx.files.internal(strTexturePath));
    }

    public static Texture createDefaultTexture(int width, int height, Color color){
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);
        pixmap.setColor(color);
        pixmap.fill();

        Texture newTexture = new Texture(pixmap);
        pixmap.dispose();
        return newTexture;
    }

    public static Texture createDefaultTexture(Color color){
        return createDefaultTexture(100, 100, color);
    }

    public static Texture createDefaultColliderTexture(int width, int height){
        return createDefaultTexture(width, height, Color.CYAN);
    }

    public static Texture createDefaultColliderTexture(){
        return createDefaultTexture(Color.CYAN);
    }

    public static Texture createDefaultTexture(int width, int height){
        return createDefaultTexture(width, height, Color.RED);
    }

    public static Texture createDefaultTexture(){
        return createDefaultTexture(Color.RED);
    }

    public static Sprite createCenteredSprite(String strTexturePath, float x, float y){
        FileHandle fileHandle = Gdx.files.internal(strTexturePath);
        Texture texture = makeTexture(fileHandle);
        return new Sprite(texture, (int) x, (int) y, texture.getWidth(), texture.getHeight());
    }

    public static  Label.LabelStyle generateDefaultLabelStyle(){

        FreeTypeFontGenerator generator =
            new FreeTypeFontGenerator(Gdx.files.internal("data/fonts/Mortal Kombat 3 Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
            new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 9;
        parameter.borderWidth = 1;

        parameter.color = Color.WHITE;
        parameter.shadowColor = new Color(0,0.5f, 0,0.75f);

        BitmapFont bitmapFont = generator.generateFont(parameter);
        generator.dispose();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = bitmapFont;

        return labelStyle;
    }

    public static Label generateDefaultLabel(String text){
        return new Label(text, generateDefaultLabelStyle());
    }

    public static Label generateDefaultLabel(){
        return  generateDefaultLabel("Hello LibGDX");
    }

    public static DragListener createDefaultDragListener(Image image){
        return new DragListener(){
            private float offsetX, offsetY;

            @Override
            public void dragStart(InputEvent event, float x, float y, int pointer) {
                offsetX = event.getStageX() - image.getX();
                offsetY = event.getStageY() - image.getY();
            }

            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                image.setPosition(event.getStageX() - offsetX, event.getStageY() - offsetY);
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                System.out.println("The element has been dragged");
            }
        };
    }

    public static Image createImageObject(String imageName, Sprite sprite, float alpha){
        Image image = new Image(sprite);
        image.setColor(1, 1, 1, alpha);
        image.setName(imageName);
        return image;
    }

    public static Image createImageObject(String imageName, Sprite sprite){
        return createImageObject(imageName, sprite, 1);
    }

    public static Image createImageObject(Sprite sprite, float alpha){
        return createImageObject("noName", sprite, alpha);
    }

    public static Image createImageObject(Sprite sprite){
        return createImageObject(sprite, 1);
    }
}
