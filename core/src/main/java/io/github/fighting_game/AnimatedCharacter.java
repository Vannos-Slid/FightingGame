package io.github.fighting_game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class AnimatedCharacter extends TextureObjectCenteredP {
    private final String name;
    private String currentMove;

    public StringBuffer punchesBuffer;
    public StringBuffer directionsBuffer;

    private boolean isStunned;
    private boolean isCrouching;
    private boolean isOnFloor;
    public boolean shouldClearBuffer;
    private boolean isLeft;
    private boolean isActive;

    private final AnimationTree animationTree;
    private AnimatedTexture currentAnimation;
    public List<String> animationBuffer;

    private Collider bodyCollider;
    private Collider hitCollider;

    private float health;
    boolean flag;


    AnimatedCharacter(String name, float x, float y, String strTexturePath, boolean flip_h,
                      boolean isLeft) {
        super(x, y, strTexturePath, flip_h);
        this.name = name;
        currentMove = "";

        punchesBuffer = new StringBuffer();
        directionsBuffer = new StringBuffer();

        currentAnimation = new AnimatedTexture("Start", new String[]{strTexturePath},
            0, flip_h);
        currentAnimation.setPosition(x, y);

        animationTree = new AnimationTree(this.name);

        animationBuffer = new ArrayList<>();

        bodyCollider = new Collider(getCenteredX(), getCenteredY(), getWidth(), getHeight(),
            false);
        hitCollider = new Collider(getCenteredX(), getCenteredY(), 50, 50,
            true);

        shouldClearBuffer = false;
        isOnFloor = false;
        this.isLeft = isLeft;
        isActive = true;

        flag = true;

        health = 100;
    }

    AnimatedCharacter(float x, float y, String strTexturePath, boolean flip_h, boolean isLeft){
        this("Scorpion", x, y, strTexturePath, flip_h, isLeft);
    }

    AnimatedCharacter(String name){
        this(name, 0, 0, "Characters/" + name + "/Default/idle-pose-1.png",
            false, true);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        resetCurrentAnimationPos();
    }

    public void setState(String newState){

        if(!currentAnimation.getName().equals(newState)){

            AnimatedTexture newAnimation = animationTree.animationMap.get(newState);
            Collider newBodyCollider = animationTree.bodyColliderMap.get(newState);
            Collider newHitCollider = animationTree.hitColliderMap.get(newState);

            if (newAnimation != null && newBodyCollider != null && newHitCollider != null){
                shouldClearBuffer = false;
                currentAnimation = newAnimation;
                currentAnimation.resetState();
                currentAnimation.flip(flip_h);
                resetCurrentAnimationPos();

                isStunned = currentAnimation.isAbleToStun;

                bodyCollider = newBodyCollider;
                hitCollider = newHitCollider;
            }
        }
    }

    public void setHealth(float health){
        this.health = health;
        if(this.health <= 0) this.health = 100;
    }

    public Collider getBodyCollider() {
        return bodyCollider;
    }

    public Collider getHitCollider() {
        return hitCollider;
    }

    public float getHealth(){
        return health;
    }


    public void switchLeft(){
        isLeft = true;
        flip_h = false;
    }

    public void switchRight(){
        isLeft = false;
        flip_h = true;
    }

    public void addAnimation() {

    }

    public void clearBuffer(){
        clearKeysBuffer();
        animationBuffer.clear();
        shouldClearBuffer = false;
    }

    public void clearPunchesBuffer(){
        punchesBuffer = new StringBuffer();
    }
    public void clearDirectionsBuffer(){
        punchesBuffer = new StringBuffer();
    }
    public void clearKeysBuffer(){
        clearDirectionsBuffer();
        clearPunchesBuffer();
    }

    public boolean is_on_floor(){
        return isOnFloor;
    }

    @Override
    public void flip(boolean flip_h) {
        super.flip(flip_h);
        currentAnimation.flip(flip_h);
    }

    private void resetCurrentAnimationPos(){
        currentAnimation.setPosition(toCenter(getCenteredX(), currentAnimation.getWidth()),
            toCenter(getCenteredY(), currentAnimation.getHeight()));
    }


    public void showColliders(boolean isVisible) {
        bodyCollider.isVisible = isVisible;
        hitCollider.isVisible = isVisible;
    }

    public void showColliders(){
        showColliders(true);
    }

    public void moveLeft(Platform platform, AnimatedCharacter enemyChar) {
        move(platform, enemyChar, false, -2, 3);
    }

    public void  moveRight(Platform platform, AnimatedCharacter enemyChar) {
        move(platform, enemyChar, true, 3, -2);
    }

    private void move(Platform platform, AnimatedCharacter enemyChar, boolean toRight,
                      float pushValueLeft, float pushValueLRight) {

        float newPos = bodyCollider.getCenteredX() + getCenteredX() +
            (toRight? 1 : -1) * (float) bodyCollider.getWidth() / 2 +
            (isLeft ? pushValueLeft : pushValueLRight);

        if((toRight? newPos <= platform.getRightWall() : newPos >= platform.getLeftWall()) &&
            (isLeft? newPos <= enemyChar.getBodyCollider().getX() +
            enemyChar.getCenteredX() : newPos >= enemyChar.getBodyCollider().getX() +
            (toRight? -1 : 1) * enemyChar.getCenteredX()))

            setPosition(getCenteredX() + (isLeft ? pushValueLeft : pushValueLRight), getCenteredY());
    }

    public void moveDown(Platform platform, AnimatedCharacter enemyChar) {

    }

    public void moveUp(Platform platform, AnimatedCharacter enemyChar) {

    }

    private boolean catTransit(String newMove){
        Transition[] transitions = animationTree.transitionMap.get(currentAnimation.getName());
        for (Transition transition:
                transitions ) {
            if(newMove.equals(transition.to)) return true;
        }
        return false;
    }

    public boolean containCollider(AnimatedCharacter enemy){
        float colliderPos = hitCollider.getX() + getCenteredX() + hitCollider.getWidth();
        float enemyColliderPos = enemy.getCenteredX() - enemy.bodyCollider.getX() -
            (float) enemy.bodyCollider.getWidth() / 2;
        return isLeft? colliderPos >= enemyColliderPos :
            getCenteredX() -hitCollider.getCenteredX() - (float) hitCollider.getWidth() / 2 <=
            enemy.bodyCollider.getX() + enemy.getCenteredX() +
                (float) enemy.bodyCollider.getWidth() / 2;
    }

    private void hitEnemy(AnimatedCharacter enemy, float damage, float push){
        enemy.setHealth(enemy.health - damage);
        enemy.setPosition(enemy.getCenteredX() + (isLeft ? push : -push),
            enemy.getCenteredY());
        enemy.clearBuffer();
        enemy.setState("hit_stun");
        isActive = false;
    }

    public void collideWithEnemy(AnimatedCharacter enemy) {
        if (currentAnimation.getName().charAt(0) == 'a' && isActive && containCollider(enemy)) {
            switch (currentAnimation.getName()) {
                case "a_light_punch":
                    hitEnemy(enemy, 4.8f, 3);
                    break;
                case "a_heavy_punch":
                    hitEnemy(enemy, 6.6f, 3);
                    break;
                case "a_light_kick":
                    hitEnemy(enemy, 12.7f, 8);
                    break;
                case "a_heavy_kick":
                    hitEnemy(enemy, 14.5f, 8);
                    break;
                case "a_upper_cut":
                    hitEnemy(enemy, 15.5f, 15);
                    break;
            }
        }
    }

    private void reset(){
        isStunned = false;
        shouldClearBuffer = true;
        currentMove = "";
        isActive = true;
        clearBuffer();
    }

    private boolean tryResetAnimations(){
        if(isStunned){
            if(currentAnimation.is_finished()){
                if(currentAnimation.getName().charAt(0) == 'r'){
                    reset();
                    return true;
                }
                else if(currentAnimation.getName().charAt(0) != 'a' &&
                    currentAnimation.getName().charAt(0) != 'r'){
                    setState("a_"+currentAnimation.getName());
                }
                else if(currentAnimation.getName().charAt(0) == 'a') {
                    if(!animationBuffer.isEmpty()){
                        animationBuffer.remove(0);
                        if (!animationBuffer.isEmpty() && catTransit(animationBuffer.get(0))){
                            setState(animationBuffer.get(0));
                        }
                    } else
                        setState(currentAnimation.getName().replaceFirst("a", "r"));
                }
            }
            return false;
        }
        return true;
    }

    public void movement(boolean isRightPressed, boolean isUpPressed, boolean isLeftPressed,
                         boolean isDownPressed, Platform platform, AnimatedCharacter enemyChar) {

        if(isStunned){
            if(currentAnimation.getName().equals("hit_stun") && currentAnimation.is_finished())
                reset();
            else if (!tryResetAnimations()) return;

        }

        if(isDownPressed){
            setState("crouching");
            isCrouching = true;
            return;
        }
        else isCrouching = false;

        if(isRightPressed){
            setState("forward_walking");
            moveRight(platform, enemyChar);
        } else if (isUpPressed) {
            moveUp(platform, enemyChar);
        } else if (isLeftPressed) {
            moveLeft(platform, enemyChar);
            setState("backward_walking");
        } else {
            setState("default");
//            currentAnimation.flip(flip_h);
        }

        isOnFloor = !(bodyCollider.getY() + getY() > platform.getY() + platform.getHeight());
        if(bodyCollider.getY() < platform.getFloorBorder()) {
            setPosition(getCenteredX(),bodyCollider.getCenteredY() + platform.getFloorBorder());
        }
    }

    public void process(boolean isRightPressed, boolean isUpPressed, boolean isLeftPressed,
                        boolean isDownPressed){

        if (isStunned) return;

        if(!animationBuffer.isEmpty()){
            setState(animationBuffer.get(0));
//            currentMove = currentAnimation.getName();
        }
        else return;
    }

    private boolean isAnimationValid(String moveName){
        if(moveName == null || moveName.isEmpty()) return false;
        AnimatedTexture newAnimation = animationTree.animationMap.get(moveName);
        Collider newBodyCollider = animationTree.bodyColliderMap.get(moveName);
        Collider newHitCollider = animationTree.hitColliderMap.get(moveName);
        return newAnimation != null && newBodyCollider != null && newHitCollider != null;
    }

    public void tryComboAttempt(Buttons4 directionButtons, Buttons4 punchesButtons) {
        String moveName = null;
        if (punchesBuffer.length() == 1) {
            if (isCrouching){
                moveName = animationTree.moveSetMap.get("D" + punchesBuffer.toString());
            }
            else if(directionButtons.isLeftPressed){
                moveName = animationTree.moveSetMap.get(flip_h ? "B" : "F" + punchesBuffer.toString());
            }
            else if(directionButtons.isRightPressed){
                moveName = animationTree.moveSetMap.get(flip_h ? "F" : "B" + punchesBuffer.toString());
            }
            if(!isAnimationValid(moveName))
                moveName = animationTree.moveSetMap.get(punchesBuffer.toString());
            if(isAnimationValid(moveName)){
                if (currentMove.equals(moveName))
                    return;
                currentMove = moveName;
                animationBuffer.add(currentMove);
            }
        }
        else {
            clearKeysBuffer();
        }
    }

    public void update(){
        if(shouldClearBuffer){
            clearBuffer();
        }
        process(false, false, false, false);
        showColliders(false);
    }

    public void render(SpriteBatch batch, float delta){
        update();

        /*batch.setColor(1,1,1, 0.5f);
        bodyCollider.render(getCenteredX() + bodyCollider.getCenteredX() * (flip_h ? -1 : 1),
            getCenteredY() + bodyCollider.getCenteredY(), batch);
        hitCollider.render(getCenteredX() + hitCollider.getCenteredX() * (flip_h ? -1 : 1),
            getCenteredY() + hitCollider.getCenteredY(), batch);
        batch.setColor(1,1,1, 1);*/

        if(currentAnimation == null){
            super.render(batch);
        }
        else
            currentAnimation.render(batch, delta);

    }

    public void dispose() {
        super.dispose();
        currentAnimation.dispose();
        hitCollider.dispose();
        bodyCollider.dispose();
    }
}
