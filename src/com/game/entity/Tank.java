package com.game.entity;

import com.game.global.Const;
import com.game.main.TankControl;
import com.game.util.ImageUtil;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by tjc on 2019-3-15.
 *
 * @auther: tjc
 * @Date: 2019-3-15
 */
public class Tank {

    private int localX = 0;
    private int localY = 0;
    private static final int sizeX = 30;
    private static final int sizeY = 30;
    private int speed = 2;
    private int direction = Const.DIRECTION_UP;

    private boolean keepMove = false;
    private int shoot_cmd = 0;
    private TankControl tc = null;

    private int MAX_bullet_NUM = 1;

    private List<Bullet> bulletList = new LinkedList();

    /**
     *
     */
    public void move() {
        if (!keepMove) {
            return;
        }
        switch (this.direction) {
            case Const.DIRECTION_UP:
                localY = localY - speed;
                break;
            case Const.DIRECTION_DOWN:
                localY = localY + speed;
                break;
            case Const.DIRECTION_LEFT:
                localX = localX - speed;
                break;
            case Const.DIRECTION_RIGHT:
                localX = localX + speed;
                break;
        }
    }

    /**
     * 坦克移动线程
     */
    private class stepThread extends Thread {
        @Override
        public void run() {
            while (true) {
                move();
                try {
                    Thread.sleep(12);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private class shootThread extends Thread {
        @Override
        public void run() {
            while (true) {
                if (shoot_cmd > 0 && bulletList.size() < MAX_bullet_NUM) {
                    shoot();
                }
                try {
                    Thread.sleep(12);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void shoot() {
        Bullet bullet = null;
        switch (direction) {
            case Const.DIRECTION_UP:
                bullet = new Bullet(localX + sizeX/2, localY, direction,this);
                break;
            case Const.DIRECTION_DOWN:
                bullet = new Bullet(localX + sizeX/2, localY + sizeY, direction,this);
                break;
            case Const.DIRECTION_LEFT:
                bullet = new Bullet(localX, localY + sizeY/2, direction,this);
                break;
            case Const.DIRECTION_RIGHT:
                bullet = new Bullet(localX + sizeX, localY + sizeY/2, direction,this);
                break;
        }
        bulletList.add(bullet);
        shoot_cmd--;
    }

    public void draw(Graphics g) {
        switch (this.direction) {
            case Const.DIRECTION_UP:
                g.drawImage(ImageUtil.heroImages[0], localX, localY, sizeX, sizeY, null);
                break;
            case Const.DIRECTION_DOWN:
                g.drawImage(ImageUtil.heroImages[1], localX, localY, sizeX, sizeY, null);
                break;
            case Const.DIRECTION_LEFT:
                g.drawImage(ImageUtil.heroImages[2], localX, localY, sizeX, sizeY, null);
                break;
            case Const.DIRECTION_RIGHT:
                g.drawImage(ImageUtil.heroImages[3], localX, localY, sizeX, sizeY, null);
                break;
        }
    }

    public List<Bullet> getBulletList() {
        return bulletList;
    }


    public Tank(int localX, int localY, int direction) {
        this.localX = localX;
        this.localY = localY;
        this.direction = direction;

        new stepThread().start();
        new shootThread().start();
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_J) {
            shoot_cmd = 1;
        }
        // 按上下左右坦克移动
        if (e.getKeyCode() == KeyEvent.VK_W) {
            keepMove = true;
            direction = Const.DIRECTION_UP;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            keepMove = true;
            direction = Const.DIRECTION_DOWN;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            keepMove = true;
            direction = Const.DIRECTION_LEFT;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            keepMove = true;
            direction = Const.DIRECTION_RIGHT;
        }
    }

    public void keyReleased(KeyEvent e) {
        keepMove = false;
    }

    public void keyTyped(KeyEvent e) {
    }
}
