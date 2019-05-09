package com.game.model;

import com.game.interfaces.Blast;
import com.game.main.Control;
import com.game.util.ImageUtil;

import java.awt.*;

/**
 * 子弹爆炸类
 * @author: tjc
 * @Date: 2019-5-4
 */
public class BulletBlast implements Blast {

    private int localX;
    private int localY;
    private int width = 15;
    private int length = 15;

    private int blastStep = 5;

    private Control control = null;

    public BulletBlast(int localX, int localY, Control control) {
        this.localX = localX;
        this.localY = localY;
        this.control = control;
    }

    @Override
    public void draw(Graphics g) {
        if (--blastStep>= 0) {
            g.drawImage(ImageUtil.bulletBlastImages[blastStep], localX, localY, width, length, null);
        }else{
            control.removeBlast(this);
        }
    }
}
