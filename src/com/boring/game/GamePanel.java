package com.boring.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static com.boring.game.Direction.*;

/**
 * @Author: jasonhu
 * @Date: 2022/3/13
 * @Description: Game panel class
 * @version: 1.0
 */
public class GamePanel extends JPanel {
    /* Fixed snake step / body block pixel */
    static final int BODY_BLOCK_PIXEL = 25;

    /* Snake attributes */
    int snakeLength;
    int[] snakeX = new int[200];
    int[] snakeY = new int[200];

    /* Food coordinate */
    int foodX;
    int foodY;

    /* Snake run direction */
    Direction direction;

    /* Game state */
    boolean isStart = false;

    /* Timer of the game */
    Timer timer;

    /* Game score */
    int score;

    /* Snake death state */
    boolean isDead = false;

    public GamePanel() {
        initSnake();

        this.setFocusable(true);

        /** [Adaptor design pattern]
         *
         *  Instead of using KeyListener (Interface) which requires to override all methods,
         *  use KeyAdaptor (Abstract class) that implements the KeyListener (Interface),
         *  so that we only need override one method keyPressed.
         *
         *  TODO: can abstract class be instantiated ??
         */
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                onKeyPressed(e);
            }
        });

        /**
         *  Every 100ms scan and handle event.
         */
        timer = new Timer(150, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isStart && !isDead) {
                    moveSnake();
                    checkBound();
                    checkFoodAte();
                    checkDeath();
                    repaint();
                }
            }
        });
        timer.start();
    }

    /** This method is the Main equivalent for GUI, code entry point */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawBackground();
        drawPlayground(g);
        drawHeader(g);
        drawSnake(g);
        drawStartText(g);
        drawFood(g);
        drawScore(g);
        drawOverText(g);
    }

    private void onKeyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_SPACE) {
            if (isDead) {
                initSnake();
                isDead = false;
            } else {
                isStart = !isStart;
                repaint();
            }
        }

        if (keyCode == KeyEvent.VK_UP) {
            direction = UP;
        }

        if (keyCode == KeyEvent.VK_DOWN) {
            direction = DOWN;
        }

        if (keyCode == KeyEvent.VK_LEFT) {
            direction = LEFT;
        }

        if (keyCode == KeyEvent.VK_RIGHT) {
            direction = RIGHT;
        }
    }

    private void initSnake() {
        // snake length
        snakeLength = 3;

        // head
        snakeX[0] = 175;
        snakeY[0] = 275;

        // body 1
        snakeX[1] = 150;
        snakeY[1] = 275;

        // body 2
        snakeX[2] = 125;
        snakeY[2] = 275;

        // direction
        direction = RIGHT;

        // food
        foodX = 300;
        foodY = 200;

        // score
        score = 0;
    }

    private void moveSnake() {
        // reversely shift snake body.
        for (int i = snakeLength - 1; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }

        // set snake head according to the direction.
        switch (direction) {
            case UP:
                snakeY[0] -= BODY_BLOCK_PIXEL;
                break;
            case DOWN:
                snakeY[0] += BODY_BLOCK_PIXEL;
                break;
            case LEFT:
                snakeX[0] -= BODY_BLOCK_PIXEL;
                break;
            case RIGHT:
                snakeX[0] += BODY_BLOCK_PIXEL;
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    private void checkFoodAte() {
        // Snake head overlap with food
        if (snakeX[0] == foodX && snakeY[0] == foodY) {
            snakeLength++;

            /** Random algorithm:
             [25,750] -> [1,30]*25
             [1,30]
             Math.random() -> [0.0,1.0)
             Math.random()*30 -> [0.0,30.0)
             (int)(Math.random()*30) -> [0,29]
             (int)(Math.random()*30)+1 -> [1,30]
             */
            foodX = ((int)(Math.random() * 30) + 1) * 25;
            foodY = ((int)(Math.random() * 26) + 4) * 25;

            score++;
        }
    }

    private void checkDeath() {
        for (int i = 1; i < snakeLength; i++) {
            /* Snake head equals any coordinate of its body, then die */
            if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                isDead = true;
            }
        }
    }

    private void checkBound() {
        if (snakeX[0] > 750) {
            snakeX[0] = BODY_BLOCK_PIXEL;
        }

        if (snakeX[0] < BODY_BLOCK_PIXEL) {
            snakeX[0] = 750;
        }

        if (snakeY[0] < 100) {
            snakeY[0] = 725;
        }

        if (snakeY[0] > 725) {
            snakeY[0] = 100;
        }
    }

    private void drawBackground() {
        this.setBackground(new Color(126, 151, 148));
    }

    private void drawPlayground(Graphics g) {
        g.setColor(new Color(231, 244, 255));
        g.fillRect(10, 70, 770, 685);
    }

    private void drawHeader(Graphics g) {
        Images.headerImg.paintIcon(this, g, 10, 10); // 'g' is like a paint pen
    }

    private void drawStartText(Graphics g) {
        if (!isStart) {
            g.setColor(new Color(151, 11, 8));
            g.setFont(new Font("Consolas", Font.BOLD, 40));
            g.drawString("Press SPACE to start", 160, 400);
        }
    }

    private void drawFood(Graphics g) {
        Images.foodImg.paintIcon(this, g, foodX, foodY);
    }

    private void drawScore(Graphics g) {
        g.setColor(new Color(2, 151, 32));
        g.setFont(new Font("Consolas", Font.BOLD, 30));
        g.drawString(String.valueOf(score), 725, 35);
    }

    private void drawOverText(Graphics g) {
        if (isDead) {
            g.setColor(new Color(151, 19, 7));
            g.setFont(new Font("Consolas", Font.BOLD, 40));
            g.drawString("Game over!", 280,330);
        }
    }

    private void drawSnake(Graphics g) {
        // Snake head
        Images.rightImg.paintIcon(this, g, snakeX[0], snakeY[0]);
        for (int i = 1; i < snakeLength; i++) {
            Images.bodyImg.paintIcon(this, g, snakeX[i], snakeY[i]);
        }

        // Snake body
        switch (direction) {
            case UP:
                Images.upImg.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
            case DOWN:
                Images.downImg.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
            case LEFT:
                Images.leftImg.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
            case RIGHT:
                Images.rightImg.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }
}
