package com.game.model;

import com.game.util.ImageUtil;

import java.awt.*;

/**
 * Created by tjc on 2019-3-21.
 *
 * @auther: tjc
 * @Date: 2019-3-21
 */
public class Wall {

    private int localX = 0;
    private int localY = 0;
    private static final int SIZE_X = 30;
    private static final int SIZE_Y = 30;

    public Rectangle getRec() {
        return new Rectangle(localX, localY, SIZE_X, SIZE_Y);
    }


    public void draw(Graphics g) {
        g.drawImage(ImageUtil.wallImages[0], localX, localY, SIZE_X, SIZE_Y, null);
    }
}
