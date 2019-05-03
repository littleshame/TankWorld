package com.game.model;

import com.game.global.Const;
import com.game.interfaces.Tank;
import com.game.main.Control;
import com.game.util.ImageUtil;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by tjc on 2019-3-15.
 *
 * @auther: tjc
 * @Date: 2019-3-15
 */
public class Enemy extends Tank {

    private int localX = 0;
    private int localY = 0;
    private static final int SIZE_X = 30;
    private static final int SIZE_Y = 30;
    private int speed = 2;
    private int direction = Const.DIRECTION_UP;
    private int health = 1;
    private boolean die = false;

    private boolean blasting = false;
    private int blastStep = 8;

    private boolean keepMove = false;
    private int shootCmd = 0;
    private Control tc = null;

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
            default:
        }
    }

    @Override
    public void runout(Bullet bullet) {
        bulletList.remove(bullet);
    }

    @Override
    public void drawBullets(Graphics g) {
        for (int i = 0; i < bulletList.size(); i++) {
            bulletList.get(i).draw(g);
        }
    }

    /**
     * 坦克移动线程
     */
    private class stepThread extends Thread {
        @Override
        public void run() {
            while (isDie()) {
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
                if (shootCmd > 0 && bulletList.size() < MAX_bullet_NUM) {
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
                bullet = new Bullet(localX + SIZE_X / 2, localY, direction, this);
                break;
            case Const.DIRECTION_DOWN:
                bullet = new Bullet(localX + SIZE_X / 2, localY + SIZE_Y, direction, this);
                break;
            case Const.DIRECTION_LEFT:
                bullet = new Bullet(localX, localY + SIZE_Y / 2, direction, this);
                break;
            case Const.DIRECTION_RIGHT:
                bullet = new Bullet(localX + SIZE_X, localY + SIZE_Y / 2, direction, this);
                break;
            default:
        }
        bulletList.add(bullet);
        shootCmd--;
    }

    @Override
    public void draw(Graphics g) {
        if (isDie()) {
            if(blastStep <= 0) {
                tc.getTankList().remove(this);
                return;
            }
            paintBlast(g);
            return;
        }
        switch (this.direction) {
            case Const.DIRECTION_UP:
                g.drawImage(ImageUtil.blastImages[0], localX, localY, SIZE_X, SIZE_Y, null);
                break;
            case Const.DIRECTION_DOWN:
                g.drawImage(ImageUtil.heroImages[1], localX, localY, SIZE_X, SIZE_Y, null);
                break;
            case Const.DIRECTION_LEFT:
                g.drawImage(ImageUtil.heroImages[2], localX, localY, SIZE_X, SIZE_Y, null);
                break;
            case Const.DIRECTION_RIGHT:
                g.drawImage(ImageUtil.heroImages[3], localX, localY, SIZE_X, SIZE_Y, null);
                break;
            default:
        }
    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(localX, localY, SIZE_X, SIZE_Y);
    }

    @Override
    public void hurt(int attack) {
        health = health - attack;
        if (health <= 0) {
            die();
        }
    }

    private void die() {
        die = true;
        blasting = true;
    }

    /**
     * 死亡时爆炸
     */
    private void paintBlast(Graphics g) {
        if (blastStep <= 0) {
            return;
        }
        g.drawImage(ImageUtil.blastImages[--blastStep], localX, localY, SIZE_X, SIZE_Y, null);
    }

    public List<Bullet> getBulletList() {
        return bulletList;
    }

    public boolean isDie() {
        return die;
    }

    public Enemy(int localX, int localY, int direction,Control tc) {
        this.localX = localX;
        this.localY = localY;
        this.direction = direction;
        this.tc = tc;
        new stepThread().start();
        new shootThread().start();
    }



}
