package com.game.interfaces;

import com.game.model.Bullet;

import java.awt.*;

/**
 * Created by tjc on 2019-4-29.
 *
 * @auther: tjc
 * @Date: 2019-4-29
 */
public abstract class Tank{

    /**
     * 射出去的子弹没了
     * @param bullet
     */
    public abstract void runout(Bullet bullet);

    public abstract void drawBullets(Graphics g);

    public abstract void draw(Graphics g);

    public abstract Rectangle getRec();

    /**
     * 被击中，扣血
     * @param attack
     */
    public abstract void hurt(int attack);
}
