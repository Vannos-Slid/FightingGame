package io.github.fighting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

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

    private static String createPlatformTexturePath(String strLevelName, String textureName){
        if(textureName == null)
            return "Null";
        return  "Levels/" + strLevelName + "/" + textureName + ".png";
    }

    private static String[] createAnimationPath(String characterName, String[] frameNames){
        String[] frames = new String[frameNames.length];
        for(int i = 0; i < frameNames.length; i++){
            frames[i] = "Characters/" + characterName + "/Default/" +
                frameNames[i] + ".png";
        }
        return frames;
    }
}
