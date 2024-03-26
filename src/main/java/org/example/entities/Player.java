package org.example.entities;

import org.example.Game;
import static org.example.utils.HelpMethods.*;
import org.example.utils.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static org.example.utils.Constants.Directions.*;
import static org.example.utils.Constants.Directions.DOWN;
import static org.example.utils.Constants.PlayerConstants.*;

public class Player extends Entity{

    private BufferedImage[][] animations;
    private int animationTick, animationIndex, animationSpeed = 20;
    private int playerAction = IDLE;
    private int playerDir = -1;
    private float playerSpeed = 2.0f;
    private int[][] lvlData;
    private boolean left, up, right, down, jump;
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;
    private boolean isMoving = false;
    private boolean isAttacking = false;
    private float xDrawOffset = 21 * Game.SCALE;
    private float yDrawOffset = 4 * Game.SCALE;
    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimation();
        initHitbox(x+xDrawOffset,y+yDrawOffset, 20 * Game.SCALE, 27 * Game.SCALE);
    }

    public void update() {
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][animationIndex], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), width, height, null);
        //drawHitbox(g);

    }


    private void updatePosition() {
        isMoving = false;

        if(jump) {
            jump();
        }

        //might be a problem when adding gravity
        if(!left && !right && !inAir)
            return;

        float xSpeed = 0;

        if (left)
            xSpeed-=playerSpeed;

        if (right) {
            xSpeed+=playerSpeed;
        }

        if(!inAir){
            if(!IsEntityOnFloor(hitbox, lvlData)){
                inAir = true;
            }
        }


        if (inAir) {
            if(CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width,  hitbox.height, lvlData)){
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if (airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }

        } else {
            updateXPos(xSpeed);
        }

        isMoving = true;
    }

    private void jump() {
        if(inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;

    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {
        if (CanMoveHere(hitbox.x+xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
        }
    }

    private void setAnimation() {
        int startAnimation = playerAction;
        if (isMoving){
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }

        if(inAir){
            if(airSpeed < 0)
                playerAction = JUMP;
            else
                playerAction = FALLING;
        }

        if(isAttacking) {
            playerAction = ATTACK_1;
        }

        if(startAnimation != playerAction) {
            resetAnimationTick();
        }
    }

    private void resetAnimationTick() {
        animationTick = 0;
        animationIndex = 0;
    }

    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if(animationIndex >= GetSpriteAmount(playerAction)) {
                animationIndex = 0;
                isAttacking = false;
            }
        }

    }

    private void loadAnimation() {

            BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

            animations = new BufferedImage[9][6];
            for(int j = 0; j < animations.length; j++){
                for (int i = 0; i < animations[j].length; i++) {
                    animations[j][i] = img.getSubimage(i*64, j*40, 64, 40);
                }
            }
        }

        public void loadLevelData(int[][] lvlData) {

        this.lvlData = lvlData;

        if(!IsEntityOnFloor(hitbox, lvlData)){
            inAir = true;
        }
        }


    public void resetDirBooleans() {
        left = false;
        right = false;
        up = false;
        down = false;
    }

    public void setIsAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }
}
