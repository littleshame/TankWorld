package com.game.model;

import com.game.global.Const;
import com.game.interfaces.Tank;
import com.game.interfaces.TouchAble;
import com.game.util.ImageUtil;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by tjc on 2019-3-16.
 * 子弹类
 *
 * @auther: tjc
 * @Date: 2019-3-16
 */
public class Bullet implements TouchAble{

    private int localX = 0;
    private int localY = 0;
    private int sizeX = 5;
    private int sizeY = 5;
    private int speed = 2;
    private int direction = Const.DIRECTION_UP;

    //攻击力
    private int attack = 1;

    private Tank tk = null;
    private boolean die = false;


    public Bullet(int x, int y, int direction, Tank tk) {
        this.localX = x;
        this.localY = y;
        this.direction = direction;
        new stepThread(this).start();
        this.tk = tk;
    }

    public void hit(List<Tank> tankList, List<Wall> wallList) {
        for (int i = 0; i < tankList.size(); i++) {
            Tank tank = tankList.get(i);
            if(crash(tank.getRec())){
                tank.hurt(attack);
            }

        }
    }

    /**
     *
     */
    private class stepThread extends Thread {

        private Bullet bullet = null;

        public stepThread(Bullet bullet) {
            this.bullet = bullet;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //子弹出窗口边界即死亡
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
            default:
        }
    }

    public void draw(Graphics g) {
        //子弹击中目标
        if(isDie()) {
            tk.runout(this);
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
            default:
        }
    }

    public boolean isDie() {
        return die;
    }


    public Rectangle getRec(){
        return new Rectangle(localX,localY,sizeX,sizeY);
    }

    @Override
    public boolean crash(Rectangle rect) {
        return getRec().intersects(rect);
    }
}
