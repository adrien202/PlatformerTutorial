package org.example.inputs;

import org.example.GamePanel;
import org.example.gamestates.Gamestate;
import org.example.utils.Constants;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static org.example.utils.Constants.Directions.*;

public class KeyboardInputs implements KeyListener {
    private GamePanel gamePanel;

    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (Gamestate.state){

            case PLAYING -> {
                gamePanel.getGame().getPlaying().keyPressed(e);
            }
            case MENU -> {
                gamePanel.getGame().getMenu().keyPressed(e);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (Gamestate.state){

            case PLAYING -> {
                gamePanel.getGame().getPlaying().keyReleased(e);
            }
            case MENU -> {
                gamePanel.getGame().getMenu().keyReleased(e);
            }
        }


    }
}
