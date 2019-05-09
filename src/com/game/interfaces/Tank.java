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

    public abstract void draw(Graphics g);

    /**
     * 外接矩形
     * @return
     */
    public abstract Rectangle getRec();

    /**
     * 被击中，扣血
     * @param attack
     */
    public abstract void hurt(int attack);


    public abstract void removeBullet(Bullet bullet);
}
