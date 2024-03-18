package org.example.entities;

import org.example.Game;
import org.example.utils.HelpMethods;
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
    private boolean isMoving = false;
    private boolean isAttacking = false;
    private boolean left = false;
    private boolean up = false;
    private boolean right = false;
    private boolean down = false;
    private float xDrawOffset = 21 * Game.SCALE;
    private float yDrawOffset = 4 * Game.SCALE;
    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimation();
        initHitbox(x+xDrawOffset,y+yDrawOffset, 20 * Game.SCALE, 28 * Game.SCALE);
    }

    public void update() {
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][animationIndex], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), width, height, null);
        drawHitbox(g);

    }


    private void updatePosition() {
        isMoving = false;

        //might be a problem when adding gravity
        if(!left && !right && !up && !down)
            return;

        float xSpeed = 0, ySpeed = 0;

        if (left && !right) {
            xSpeed-=playerSpeed;
        } else if (right && !left) {
            xSpeed+=playerSpeed;
        }

        if (up && !down) {
            ySpeed-=playerSpeed;
        } else if (down && !up) {
            ySpeed+=playerSpeed;
        }

        if (HelpMethods.CanMoveHere(hitbox.x+xSpeed, hitbox.y+ySpeed, hitbox.width, hitbox.height, lvlData)) {
            hitbox.x += xSpeed;
            hitbox.y += ySpeed;
            isMoving = true;
        }

    }

    private void setAnimation() {
        int startAnimation = playerAction;
        if (isMoving){
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
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
}
