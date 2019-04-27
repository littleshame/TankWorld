package com.game.entity;

import com.game.global.Const;
import com.game.util.ImageUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by tjc on 2019-3-16.
 * 子弹类
 *
 * @auther: tjc
 * @Date: 2019-3-16
 */
public class Bullet {

    private int localX = 0;
    private int localY = 0;
    private int sizeX = 5;
    private int sizeY = 5;
    private int speed = 2;
    private int direction = Const.DIRECTION_UP;

    private Tank tk = null;
    private boolean die = false;

    public Bullet(int x, int y, int direction, Tank tk) {
        this.localX = x;
        this.localY = y;
        this.direction = direction;
        new moveThread().start();
        this.tk = tk;
    }

    /**
     *
     */
    private class moveThread extends Thread {
        @Override
        public void run() {
            while (!die) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(localX < 0 || localX > Const.DEFAULT_WINDOW_WIDTH
                        || localY < 0 || localY > Const.DEFAULT_WINDOW_LENGTH)
                {
                    die = true;
                    return;
                }
                move(direction);
            }



        }
    }


    public void move(int direction) {
        switch(this.direction){
            case Const.DIRECTION_UP:
                this.direction = direction;
                localY = localY - speed;
                break;
            case Const.DIRECTION_DOWN:
                this.direction = direction;
                localY = localY + speed;
                break;
            case Const.DIRECTION_LEFT:
                this.direction = direction;
                localX = localX - speed;
                break;
            case Const.DIRECTION_RIGHT:
                this.direction = direction;
                localX = localX + speed;
                break;
        }
    }

    public void draw(Graphics g) {
        if(isDie()) {
            tk.getBulletList().remove(this);
            return;
        }
        switch (this.direction) {
            case Const.DIRECTION_UP:
                g.drawImage(ImageUtil.bulletImages[0], localX, localY, sizeX, sizeY, null);
                break;
            case Const.DIRECTION_DOWN:
                g.drawImage(ImageUtil.bulletImages[1], localX, localY, sizeX, sizeY, null);
                break;
            case Const.DIRECTION_LEFT:
                g.drawImage(ImageUtil.bulletImages[2], localX, localY, sizeX, sizeY, null);
                break;
            case Const.DIRECTION_RIGHT:
                g.drawImage(ImageUtil.bulletImages[3], localX, localY, sizeX, sizeY, null);
                break;
        }
    }

    public boolean isDie() {
        return die;
    }
}
