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
 * @author: tjc
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

    private boolean keepMove = false;
    private int shootCmd = 0;
    private Control ct = null;

    private int MAX_bullet_NUM = 1;

    private List<Bullet> shotBullets = new LinkedList();

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
                if (shootCmd > 0 && shotBullets.size() < MAX_bullet_NUM) {
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
                bullet = new Bullet(localX + SIZE_X / 2, localY, direction, this, ct);
                break;
            case Const.DIRECTION_DOWN:
                bullet = new Bullet(localX + SIZE_X / 2, localY + SIZE_Y, direction, this, ct);
                break;
            case Const.DIRECTION_LEFT:
                bullet = new Bullet(localX, localY + SIZE_Y / 2, direction, this, ct);
                break;
            case Const.DIRECTION_RIGHT:
                bullet = new Bullet(localX + SIZE_X, localY + SIZE_Y / 2, direction, this, ct);
                break;
            default:
        }
        ct.addBullet(bullet);
        shotBullets.add(bullet);
        shootCmd--;
    }

    @Override
    public void draw(Graphics g) {
        if (isDie()) {
            ct.getTankList().remove(this);
            return;
        }
        switch (this.direction) {
            case Const.DIRECTION_UP:
                g.drawImage(ImageUtil.tankBlastImages[0], localX, localY, SIZE_X, SIZE_Y, null);
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

    @Override
    public void removeBullet(Bullet bullet) {
        shotBullets.remove(bullet);
    }


    private void die() {
        die = true;
        ct.addBlast(new TankBlast(localX + SIZE_X / 2, localY + SIZE_Y / 2,ct));
    }

    public List<Bullet> getBulletList() {
        return shotBullets;
    }

    public boolean isDie() {
        return die;
    }

    public Enemy(int localX, int localY, int direction, Control ct) {
        this.localX = localX;
        this.localY = localY;
        this.direction = direction;
        this.ct = ct;
        new stepThread().start();
        new shootThread().start();
    }


}
