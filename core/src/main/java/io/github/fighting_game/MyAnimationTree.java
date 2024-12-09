package io.github.fighting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MyAnimationTree {

    String name;
    public Map<String, Transition[]> transitionMap;
    public Map<String, AnimatedTexture> animationMap;
    public Map<String, MyCollider> hitColliderMap;
    public Map<String, MyCollider> bodyColliderMap;
    public Map<String, String> moveSetMap;

    MyAnimationTree(String name) {
        transitionMap = new HashMap<>();
        animationMap = new HashMap<>();
        hitColliderMap = new HashMap<>();
        bodyColliderMap = new HashMap<>();
        moveSetMap = new HashMap<>();

        this.name = name;

        loadAnimations("data/animationStats.json");
        loadColliders("data/colliders.json");
        loadMoveSetMap("data/moveSetList.json");
    }

    MyAnimationTree() {
        transitionMap = new HashMap<>();
        animationMap = new HashMap<>();
        hitColliderMap = new HashMap<>();
        bodyColliderMap = new HashMap<>();
        moveSetMap = new HashMap<>();

        name = "None";
    }

    public boolean loadMoveSetMap(FileHandle fileHandle){
        if(fileHandle.exists()){
            JsonReader jsonReader = new JsonReader();
            JsonValue root = jsonReader.parse(fileHandle);

            JsonValue moveSets = root.get("Basic moves");

            for (int i = 0; i < moveSets.size; i++){
                JsonValue moveSet = moveSets.get(i);
                addMoveSet(moveSet);
            }

            moveSets = root.get(name + " moves");

            for (int i = 0; i < moveSets.size; i++){
                JsonValue moveSet = moveSets.get(i);
                addMoveSet(moveSet);
            }
            return true;
        }
        return false;
    }

    public boolean loadMoveSetMap(String dataPath){
        return loadMoveSetMap(Gdx.files.internal(dataPath));
    }

    private void addMoveSet(JsonValue moveSet){
        String input = moveSet.getString("input");
        String moveName = moveSet.getString("move name");

        moveSetMap.put(input, moveName);
    }


    public boolean loadAnimations(FileHandle fileHandle){
        if (fileHandle.exists()){
            JsonReader jsonReader = new JsonReader();
            JsonValue root = jsonReader.parse(fileHandle);

            JsonValue animations = root.get(name + " animations");

            for(int i = 0; i < animations.size; i++){
                JsonValue animation = animations.get(i);

                addAnimationNode(animation);
            }
            return true;
        }
        return false;
    }

    public boolean loadAnimations(String dataPath) {
        return loadAnimations(Gdx.files.internal(dataPath));
    }

    public boolean loadColliders(FileHandle fileHandle) {
        if(fileHandle.exists()){
            JsonReader jsonReader = new JsonReader();
            JsonValue root = jsonReader.parse(fileHandle);

            JsonValue colliders = root.get(name + " colliders");

            for(int i = 0; i < colliders.size; i++){
                JsonValue collider = colliders.get(i);

                addCollidersNode(collider);
            }
            return true;
        }
        return false;
    }

    public boolean loadColliders(String dataPath) {
        return loadColliders(Gdx.files.internal(dataPath));
    }

    private void addCollidersNode(JsonValue collider){
        String name = collider.getString("name");
        addBodyCollider(collider, name);
        addHitCollider(collider, name);
    }

    private void addBodyCollider(JsonValue collider, String name){
        MyCollider newCollider = createCollider(collider, "body collider");
        bodyColliderMap.put(name, newCollider);
    }

    private void addHitCollider(JsonValue collider, String name){
        MyCollider newCollider = createCollider(collider, "hit collider");
        hitColliderMap.put(name, newCollider);
    }

    private MyCollider createCollider(JsonValue collider, String name){
        JsonValue subCollider = collider.get(name);

        float x = subCollider.getFloat("x");
        float y = subCollider.getFloat("y");
        int width = subCollider.getInt("width");
        int height = subCollider.getInt("height");
        boolean isDisabled = subCollider.getBoolean("isDisabled");

        return new MyCollider(x, y, width, height, isDisabled);
    }

    private void addAnimationNode(JsonValue animation) {
        addAnimation(animation);
        addTransition(animation);
    }

    private void addAnimation(JsonValue animation){
        JsonValue animationStats = animation.get("stats");
        String name = animationStats.getString("name");

        String directoryName = "Characters/" + this.name + "/" +
            animation.getString("directory name") + "/";

        String textureName = directoryName + animationStats.getString("texture");
        int countOfFrames = animationStats.getInt("count");
        int at = animationStats.getInt("from number");

        String[] framePaths = {"Null"};

        try {
            if (name.charAt(0) == 'a') {
                framePaths =  createDuplicated(textureName, at, countOfFrames);
            }
            else {
                framePaths = createFramePaths(textureName, at, countOfFrames);
            }

            float duration = animationStats.getFloat("duration");

            boolean isLooped = animationStats.getBoolean("isLooped");

            boolean isAbleToStun = animationStats.getBoolean("isAbleToStun");

            boolean isReversed = animationStats.getBoolean("isReversed");

            boolean flip_h = animationStats.getBoolean("flip h");

            if (framePaths != null){
                AnimatedTexture animatedTexture = new AnimatedTexture(name, framePaths, duration,
                    flip_h, isReversed, isLooped, isAbleToStun);
                animationMap.put(name, animatedTexture);
            }
            else System.out.println("Frame was Null");

        }
        catch (Exception e){
            System.out.println("Something went wrong with frames");
        }


    }

    private void addTransition(JsonValue animation){
        try{
            JsonValue animationStats = animation.get("stats");

            String name = animationStats.getString("name");

            JsonValue transitionsJson = animation.get("transitions");

            Transition[] transitions = new Transition[transitionsJson.size];
            for (int j = 0; j < transitionsJson.size; j++){
                String to = transitionsJson.get(j).getString("name");
                boolean isImmediate = transitionsJson.get(j).getBoolean("immediate");
                transitions[j] = new Transition(to, isImmediate);
            }

            transitionMap.put(name, transitions);
        }
        catch (Exception e){
            System.out.println("Bad");
        }

    }

    private String[] createFramePaths(String textureName, int at,  int countOfFrames){
        String[] framePaths = new String[countOfFrames];
        try {
            for (int i = 0; i < countOfFrames; i++){
                framePaths[i] = textureName + "-" + (i + at) + ".png";
            }
            return framePaths;
        }
        catch (Exception e){
            System.out.println("I hate this");
        }

        return null;
    }
    private String[] createDuplicated(String textureName, int at, int countOfFrames){
        String[] framePaths = new String[countOfFrames];
//        for (String framePath: framePaths) {
//            framePath = textureName + "-" + (at) + ".png";
//        }
        Arrays.fill(framePaths, textureName + "-" + (at) + ".png");
        return framePaths;
    }
}
