package org.example;

import org.example.entities.Player;
import org.example.gamestates.Gamestate;
import org.example.gamestates.Menu;
import org.example.gamestates.Playing;
import org.example.levels.LevelManager;

import java.awt.*;

public class Game implements Runnable{
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;

    private Playing playing;
    private Menu menu;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 1.5f;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    public Game() {
        initClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();
    }

    private void initClasses() {
        menu = new Menu(this);
        playing = new Playing(this);
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double timePerFrame = 1000000000.0/FPS_SET;
        double timePerUpdate = 1000000000.0/UPS_SET;
        long previousTime = System.nanoTime();
        int frames, updates;
        frames = updates = 0;

        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if(System.currentTimeMillis() - lastCheck >= 1000){
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + "  | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }

    }
    public void update() {
        switch (Gamestate.state) {

            case PLAYING -> {
                playing.update();
            }
            case MENU -> {
                menu.update();
            }
            case OPTIONS, QUIT, default -> {
                System.exit(0);
            }
        }
    }

    public void render(Graphics g) {
        switch (Gamestate.state) {

            case PLAYING -> {
                playing.draw(g);
            }
            case MENU -> {
                menu.draw(g);
            }
        }
    }


    public void windowFocusLost() {
        if(Gamestate.state == Gamestate.PLAYING) {
            playing.getPlayer().resetDirBooleans();
        }
    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }
}
