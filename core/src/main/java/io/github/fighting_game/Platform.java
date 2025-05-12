package io.github.fighting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class Platform extends TextureObjectP {
    private final static String strDataPath = "data/maps.json";

    private String name;
    private TextureObjectP[] childTextures;
    private float leftBorder;
    private float rightBorder;
    private float leftWall;
    private float rightWall;
    private final float floorBorder;

    Platform(float x, float y, String strTexturePath, boolean flip_h, TextureObjectP[] childTextures){
        super(x, y, strTexturePath, flip_h);
        this.childTextures = childTextures;

        leftBorder = 0;
        rightBorder = 0;

        leftWall = 200;
        rightWall = 200;
        floorBorder = 80;
    }

    Platform(float x, float y, String strTexturePath, boolean flip_h){
        this(x, y, strTexturePath, flip_h, null);
    }
    Platform(float x, float y, String strTexturePath){
        this(x, y, strTexturePath, false);
    }

    Platform(String strTexturePath, boolean flip_h){
        this(0, 0, strTexturePath, flip_h);
    }

    Platform(String strTexturePath){
        this(strTexturePath, false);
    }

    Platform(String strDataPath, int Gay){
        super("Null");
        loadPlatform();

        leftBorder = 0;
        rightBorder = 0;

        leftWall = 200;
        rightWall = 200;
        floorBorder = 80;
    }

    private String createTexturePath(String textureName){
        if(textureName == null)
            return "Null";
        return  "Levels/" + name + "/" + textureName + ".png";
    }

    public boolean loadPlatform() {
        FileHandle fileHandle = Gdx.files.internal(strDataPath);
        if(fileHandle.exists()){
            JsonReader jsonReader = new JsonReader();
            JsonValue root = jsonReader.parse(fileHandle);

            JsonValue platformStats = root.get(name);

            if(platformStats == null){
                System.out.println("Can't find level with this name");
                return false;
            }

            String platformTexturePath = createPlatformTexturePath(name, platformStats.getString("texture"));
            float x = platformStats.getFloat("x");
            float y = platformStats.getFloat("y");
            boolean flip_h = platformStats.getBoolean("flip_h");

            JsonValue levelObjects = platformStats.get("child_objects");

            if(levelObjects == null){
                return false;
            }

            TextureObjectP[] textureObjectPS = new TextureObjectP[levelObjects.size];

            for(int i = 0; i < levelObjects.size; i++) {
                JsonValue objData = levelObjects.get(i);
                String texturePath = "Levels/" + name + "/" +
                    objData.getString("texture") + ".png";
                float childX = objData.getFloat("x");
                float childY = objData.getFloat("y");
                boolean child_flip_h1 = objData.getBoolean("flip_h");

                textureObjectPS[i] = new TextureObjectP(x + childX, y + childY,
                    texturePath, child_flip_h1);
            }

            setPlatform(x, y, platformTexturePath, flip_h, childTextures);
            return true;
        }
        System.out.print("Invalid path: " + strDataPath + " to json file");
        return false;
    }

    private String createPlatformTexturePath(String strLevelName, String textureName){
        if(textureName == null)
            return "Null";
        return  "Levels/" + strLevelName + "/" + textureName + ".png";
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        for(TextureObjectP textureObjectP : childTextures){
            float newChildX = getX() + textureObjectP.getX();
            textureObjectP.setX(newChildX);
        }
        setLeftBorder(x);
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        for(TextureObjectP textureObjectP : childTextures){
            float newChildY = getY() + textureObjectP.getY();
            textureObjectP.setY(newChildY);
        }
    }

    public void setLeftBorder(float leftBorder){
        this.leftBorder = leftBorder;
    }

    public void setRightBorder(float rightBorder) {
        this.rightBorder = rightBorder;
    }

    public void setLeftWall(float leftWall) {
        this.leftWall = leftWall;
    }

    public void setRightWall(float rightWall) {
        this.rightWall = rightWall;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        for(TextureObjectP textureObjectP : childTextures){
            float newChildX = getX() + textureObjectP.getX();
            float newChildY = getY() + textureObjectP.getY();
            textureObjectP.setPosition(newChildX, newChildY);
        }
    }

    private void setChildTextures(TextureObjectP[] childTextures){
        this.childTextures = childTextures;
    }

    private void setPlatform(float x, float y, String strTexturePath, boolean flip_h,
                            TextureObjectP[] childTextures) {
        setX(x);
        setY(y);
        setTextureObject(new TextureObjectP(strTexturePath));
        flip(flip_h);
        setChildTextures(childTextures);
    }

    public float getLeftBorder(){
        return getX() + leftBorder;
    }

    public float getRightBorder(){
        return getX() + getWidth() - rightBorder;
    }

    public float getLeftWall(){
         return getX() + leftBorder + leftWall;
    }

    public float getRightWall(){
        System.out.println(getX() + getWidth() - rightWall - rightBorder);
        return getX() + getWidth() - rightWall - rightBorder;
    }

    public float getFloorBorder(){
        return getY() + floorBorder;
    }

    public TextureObjectP[] getChildTextures() {
        return childTextures;
    }

    public void render(SpriteBatch batch) {
        super.render(batch);
        for (TextureObjectP childTexture : childTextures){
            childTexture.render(batch);
        }
    }

    @Override
    public void dispose(){
        super.dispose();
        for(TextureObjectP childTexture : childTextures)
            childTexture.dispose();
    }

}
