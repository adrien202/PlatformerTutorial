package org.example;

import org.example.inputs.KeyboardInputs;
import org.example.inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static org.example.utils.Constants.Directions.*;
import static org.example.utils.Constants.PlayerConstants.*;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private int xDelta = 100, yDelta = 100;
    private BufferedImage img;
    private BufferedImage[][] animations;
    private int animationTick, animationIndex, animationSpeed = 15;
    private int playerAction = IDLE;
    private int playerDir = -1;
    private boolean isMoving = false;

    public GamePanel() {
        mouseInputs = new MouseInputs(this);
        importImg();
        loadAnimation();

        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void loadAnimation() {
        animations = new BufferedImage[9][6];
        for(int j = 0; j < animations.length; j++){
        for (int i = 0; i < animations[j].length; i++) {
            animations[j][i] = img.getSubimage(i*64, j*40, 64, 40);
        }
        }
    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/player_sprites.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    public void setDirection(int direction) {
        this.playerDir = direction;
        isMoving = true;
    }

    public void setMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        updateAnimationTick();
        setAnimation();
        updatePosition();

        g.drawImage(animations[playerAction][animationIndex], xDelta , yDelta,128,80, null);


    }

    private void updatePosition() {
        if (isMoving){
            switch (playerDir) {
                case LEFT -> xDelta-=5;
                case UP -> yDelta-=5;
                case RIGHT -> xDelta+=5;
                case DOWN -> yDelta+=5;
            }
        }
    }

    private void setAnimation() {
        if (isMoving){
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }
    }

    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if(animationIndex >= GetSpriteAmount(playerAction)) {
                animationIndex = 0;
            }
        }

    }

    public void setMoving(int direction) {

    }


}
