package com.game.model;

import com.game.global.Const;
import com.game.interfaces.Tank;
import com.game.interfaces.Crash;
import com.game.main.Control;
import com.game.util.ImageUtil;

import java.awt.*;
import java.util.List;

/**
 * Created by tjc on 2019-3-16.
 * 子弹类
 *
 * @auther: tjc
 * @Date: 2019-3-16
 */
public class Bullet implements Crash {

    private int localX = 0;
    private int localY = 0;
    private int width = 7;
    private int heigh = 10;
    private int speed = 2;
    private int direction = Const.DIRECTION_UP;

    //攻击力
    private int attack = 1;
    private Tank tk = null;

    private Control control;
    private boolean die = false;
    private int bulletStep = 8;


    public Bullet(int x, int y, int direction, Tank tk, Control control) {
        this.localX = x;
        this.localY = y;
        this.direction = direction;
        this.tk = tk;
        this.control = control;

        new StepThread(this).start();
        new GetCrashThread(this).start();
    }

    /**
     * 子弹攻击函数
     * @param tankList
     * @param wallList
     */
    public void hit(List<Tank> tankList, List<Wall> wallList, List<Bullet> bulletList) {
        for (int i = 0; i < tankList.size(); i++) {
            Tank tank = tankList.get(i);

            if (crash(tank.getRec())) {
                //子弹刚创建出来与本坦克会重合不算
                if (tank.equals(tk)) {
                    continue;
                }
                tank.hurt(attack);
                die();
            }
        }

        for (int i = 0; i < wallList.size(); i++) {
            Wall wall = wallList.get(i);
            if(crash(wall.getRec())) {
                wallList.remove(wall);
                die();
            }
        }
    }

    private void die() {
        die = true;
        control.addBlast(new BulletBlast(localX + width / 2, localY + heigh / 2, control));
        control.removeBullet(this);
        tk.removeBullet(this);
    }

    /**
     * 移动线程
     */
    private class StepThread extends Thread {

        private Bullet bullet = null;

        public StepThread(Bullet bullet) {
            this.bullet = bullet;
        }

        @Override
        public void run() {
            while (!die) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //子弹出窗口边界即死亡
                if (localX < 0 || localX > Const.DEFAULT_WINDOW_WIDTH
                        || localY < 0 || localY > Const.DEFAULT_WINDOW_LENGTH) {
                    die = true;
                    control.removeBullet(bullet);
                    tk.removeBullet(bullet);
                    return;
                }
                move(direction);
            }
        }
    }

    /**
     * 检测碰撞类型
     */
    private class GetCrashThread extends Thread {

        private Bullet bullet = null;

        public GetCrashThread(Bullet bullet) {
            this.bullet = bullet;
        }

        @Override
        public void run() {
            while(!die) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<Tank> tankList = bullet.control.tankList;
                for (int i = 0; i < tankList.size(); i++) {
                    Tank tank = tankList.get(i);

                    if (crash(tank.getRec())) {
                        if (tank.equals(tk)) {
                            continue;
                        }
                        System.out.println("crash");
                    }
                }
            }



        }
    }

    /**
     * 移动函数
     * @param direction
     */
    public void move(int direction) {
        switch (this.direction) {
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
            default:
        }
    }

    /**
     * 画图函数
     * @param g
     */
    public void draw(Graphics g) {
        //子弹击中目标
        if (isDie()) {
            return;
        }
        switch (this.direction) {
            case Const.DIRECTION_UP:
                g.drawImage(ImageUtil.bulletImages[0], localX, localY, width, heigh, null);
                break;
            case Const.DIRECTION_DOWN:
                g.drawImage(ImageUtil.bulletImages[1], localX, localY, width, heigh, null);
                break;
            case Const.DIRECTION_LEFT:
                g.drawImage(ImageUtil.bulletImages[2], localX, localY, heigh, width, null);
                break;
            case Const.DIRECTION_RIGHT:
                g.drawImage(ImageUtil.bulletImages[3], localX, localY, heigh, width, null);
                break;
            default:
        }
    }

    public boolean isDie() {
        return die;
    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(localX, localY, width, heigh);
    }

    @Override
    public boolean crash(Rectangle rect) {
        return getRec().intersects(rect);
    }

    /**
     * 死亡时爆炸
     */
    private void paintBlast(Graphics g) {
        if (bulletStep <= 0) {
            return;
        }
        control.addBlast();
        g.drawImage(ImageUtil.bulletBlastImages[5], localX, localY, width, heigh, null);
        bulletStep--;
    }

}
