package org.example.gamestates;

import org.example.Game;
import org.example.entities.Player;
import org.example.levels.LevelManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static org.example.Game.SCALE;

public class Playing extends State implements Statemethods {

    private Player player;
    private LevelManager levelManager;
    private boolean paused;

    public Playing(Game game) {
        super(game);
        initClasses();
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        player = new Player(200, 200, (int) (64 * SCALE), (int) (40 * SCALE));
        player.loadLevelData(levelManager.getCurrentLevel().getLevelData());

    }



    @Override
    public void update() {
        levelManager.update();
        player.update();
    }

    @Override
    public void draw(Graphics g) {
        levelManager.draw(g);
        player.render(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            player.setIsAttacking(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {

            case KeyEvent.VK_Q:player.setLeft(true);
                break;
            case KeyEvent.VK_D: player.setRight(true);
                break;
            case KeyEvent.VK_SPACE: player.setJump(true);
                break;
            case KeyEvent.VK_BACK_SPACE: Gamestate.state = Gamestate.MENU;
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_Z:player.setUp(false);
                break;
            case KeyEvent.VK_Q:player.setLeft(false);
                break;
            case KeyEvent.VK_S:player.setDown(false);
                break;
            case KeyEvent.VK_D: player.setRight(false);
                break;
            case KeyEvent.VK_SPACE: player.setJump(false);
                break;
        }

    }

    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    public Player getPlayer(){
        return player;
    }
}
