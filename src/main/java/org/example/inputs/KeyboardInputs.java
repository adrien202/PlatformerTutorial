package org.example.inputs;

import org.example.GamePanel;
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
        switch (e.getKeyCode()) {
            case KeyEvent.VK_Z:gamePanel.getGame().getPlayer().setUp(true);
                break;
            case KeyEvent.VK_Q:gamePanel.getGame().getPlayer().setLeft(true);
                break;
            case KeyEvent.VK_S:gamePanel.getGame().getPlayer().setDown(true);
                break;
            case KeyEvent.VK_D: gamePanel.getGame().getPlayer().setRight(true);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_Z:gamePanel.getGame().getPlayer().setUp(false);
                break;
            case KeyEvent.VK_Q:gamePanel.getGame().getPlayer().setLeft(false);
                break;
            case KeyEvent.VK_S:gamePanel.getGame().getPlayer().setDown(false);
                break;
            case KeyEvent.VK_D: gamePanel.getGame().getPlayer().setRight(false);
            break;
        }

    }
}
