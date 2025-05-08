package io.github.fighting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.github.tommyettinger.textra.Font;

public class MySimplerMethods {

    //Load all objects on the level

    public static Platform loadPlatform(String strLevelName) {

        final String strDataPath = "data/maps.json";

        FileHandle fileHandle = Gdx.files.internal(strDataPath);
        if(fileHandle.exists()){
            JsonReader jsonReader = new JsonReader();
            JsonValue root = jsonReader.parse(fileHandle);

            JsonValue platformStats = root.get(strLevelName);

            if(platformStats == null){
                System.out.println("Can't find level with this name");
                return new Platform("Null");
            }

            String platformTexturePath = createPlatformTexturePath(strLevelName, platformStats.getString("texture"));
            float x = platformStats.getFloat("x");
            float y = platformStats.getFloat("y");
            boolean flip_h = platformStats.getBoolean("flip_h");

            JsonValue levelObjects = platformStats.get("child_objects");

            if(levelObjects == null){
                return new Platform(x, y, platformTexturePath, flip_h);
            }

            TextureObjectP[] textureObjectPS = new TextureObjectP[levelObjects.size];

            for(int i = 0; i < levelObjects.size; i++) {
                JsonValue objData = levelObjects.get(i);
                String texturePath = "Levels/" + strLevelName + "/" +
                    objData.getString("texture") + ".png";
                float childX = objData.getFloat("x");
                float childY = objData.getFloat("y");
                boolean child_flip_h1 = objData.getBoolean("flip_h");

                textureObjectPS[i] = new TextureObjectP(x + childX, y + childY,
                    texturePath, child_flip_h1);
            }

            return new Platform(x, y, platformTexturePath, flip_h, textureObjectPS);
        }
        System.out.print("Invalid path: " + strDataPath + " to json file");
        return new Platform("Null");
    }

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

//    public static  Label.LabelStyle generateDefaultLabelStyle(){
//        Label.LabelStyle labelStyle = new Label.LabelStyle();
//
//        BitmapFont bitmapFont =
//            new BitmapFont(Gdx.files.internal("data/fonts/Mortal Kombat 3 Regular.ttf"));
//        labelStyle.font = bitmapFont;
//        labelStyle.fontColor = Color.WHITE;
//        bitmapFont.dispose();
//
//        return labelStyle;
//    }

    public static  Label.LabelStyle generateDefaultLabelStyle(){

        FreeTypeFontGenerator generator =
            new FreeTypeFontGenerator(Gdx.files.internal("data/fonts/Mortal Kombat 3 Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
            new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 9;
        parameter.borderWidth = 1;

        parameter.color = Color.WHITE;
//        parameter.shadowOffsetX = 3;
//        parameter.shadowOffsetY = 3;
        parameter.shadowColor = new Color(0,0.5f, 0,0.75f);

        BitmapFont bitmapFont = generator.generateFont(parameter);
        generator.dispose();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = bitmapFont;

        return labelStyle;
    }

    public static Font generateDefaultFont(){
        FreeTypeFontGenerator generator =
            new FreeTypeFontGenerator(Gdx.files.internal("data/fonts/Mortal Kombat 3 Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
            new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 16;
        parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS;

        BitmapFont bitmapFont = generator.generateFont(parameter);

        Font font = new Font(bitmapFont);

        generator.dispose();

        return font;
    }

    public static Label generateDefaultLabel(String text){
        return new Label(text, generateDefaultLabelStyle());
    }

    public static Label generateDefaultLabel(){
        return  generateDefaultLabel("Hello LibGDX");
    }
}
