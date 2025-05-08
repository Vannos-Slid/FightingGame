package io.github.fighting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.github.tommyettinger.textra.Font;
import com.github.tommyettinger.textra.Styles;

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
            new FreeTypeFontGenerator(Gdx.files.internal("data/UI-skins/Fonts/mortalkombat3.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
            new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 13;
        parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS;

        BitmapFont bitmapFont = generator.generateFont(parameter);

        Font font = new Font(bitmapFont);

        generator.dispose();

        return font;
    }

    public static com.github.tommyettinger.textra.Styles.LabelStyle generateDefaultTetraStyle2(){
        return new Styles.LabelStyle(generateDefaultFont(), Color.WHITE);
    }

    // AI-generated - not needed so far
    public static Styles.LabelStyle generateDefaultTetraStyle() {
        try {
//            FreeTypeFontGenerator generator =
//                new FreeTypeFontGenerator(Gdx.files.internal("data/UI-skins/Fonts/mortalkombat3.ttf"));
//            FileHandle fontTTFFile = Gdx.files.internal("data/UI-skins/Fonts/mortalkombat3.ttf");
            FileHandle jsonFile = Gdx.files.internal("data/UI-skins/Fonts/mk3FontSkin.json");
            FileHandle fontFile = Gdx.files.internal("data/UI-skins/Fonts/mortalkombat3.png");
            FileHandle textureFile = Gdx.files.internal("data/UI-skins/Fonts/mk3FontSkin.png");
            if (!jsonFile.exists()) {
                throw new IllegalStateException("JSON file not found: " + jsonFile.path());
            }
            if (!fontFile.exists()) {
                throw new IllegalStateException("Font file not found: " + fontFile.path());
            }
            if (!textureFile.exists()) {
                throw new IllegalStateException("Texture file not found: " + textureFile.path());
            }

            Skin skin = new Skin(jsonFile);
            BitmapFont bitmapFont = skin.get("mortalkombat3", BitmapFont.class);
            System.out.println("BitmapFont loaded: " + (bitmapFont != null));
            System.out.println("BitmapFont regions: " + bitmapFont.getRegions().size);

            Font font = new Font(bitmapFont);
            Styles.LabelStyle style = new Styles.LabelStyle(font, Color.WHITE);
            System.out.println("TextraStyle created: " + style);
            return style;
        } catch (Exception e) {
            System.err.println("Error in generateDefaultTetraStyle: " + e.getMessage());
            e.printStackTrace();
            // Fallback style
            BitmapFont fallbackFont = new BitmapFont();
            Font fallbackTextraFont = new Font(fallbackFont);
            return new Styles.LabelStyle(fallbackTextraFont, Color.WHITE);
        }
    }

    public static Label generateDefaultLabel(String text){
        return new Label(text, generateDefaultLabelStyle());
    }

    public static Label generateDefaultLabel(){
        return  generateDefaultLabel("Hello LibGDX");
    }
}
