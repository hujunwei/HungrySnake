package com.boring.game;

import javax.swing.*;
import java.net.URL;

/**
 * @Author: jasonhu
 * @Date: 2022/3/13
 * @Description: Images static class
 * @version: 1.0
 */
public class Images {
    // Image URLs
    public static URL bodyURL = Images.class.getResource("/images/body.png");
    public static URL foodURL = Images.class.getResource("/images/food.png");
    public static URL headerURL = Images.class.getResource("/images/header.png");
    public static URL upURL = Images.class.getResource("/images/up.png");
    public static URL downURL = Images.class.getResource("/images/down.png");
    public static URL leftURL = Images.class.getResource("/images/left.png");
    public static URL rightURL = Images.class.getResource("/images/right.png");

    // Load image from URL
    public static ImageIcon bodyImg = new ImageIcon(bodyURL);
    public static ImageIcon foodImg = new ImageIcon(foodURL);
    public static ImageIcon headerImg = new ImageIcon(headerURL);
    public static ImageIcon upImg = new ImageIcon(upURL);
    public static ImageIcon downImg = new ImageIcon(downURL);
    public static ImageIcon leftImg = new ImageIcon(leftURL);
    public static ImageIcon rightImg = new ImageIcon(rightURL);
}
