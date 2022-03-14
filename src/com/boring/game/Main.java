package com.boring.game;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        JFrame jf = new JFrame();

        int windowWidth = 800;
        int windowHeight = 800;

        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

        jf.setTitle("How much shit can you eat?");
        jf.setBounds((screenWidth - windowWidth) / 2, (screenHeight - windowHeight) / 2, windowWidth, windowHeight);

        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setResizable(false);

        GamePanel gp = new GamePanel();
        jf.add(gp);

        // By default the window is invisible, this line should be at the end of the 'set' methods, to make sure previous settings were took effect.
        jf.setVisible(true);

        MusicPlayer.playMusic();
    }
}
