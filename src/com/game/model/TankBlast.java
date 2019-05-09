package com.game.model;

import com.game.interfaces.Blast;
import com.game.main.Control;
import com.game.util.ImageUtil;

import java.awt.*;

/**
 * @author: tjc
 * @Date: 2019-5-4
 */
public class TankBlast implements Blast {

    private int centerX;
    private int centerY;
    private int width = 35;
    private int length = 35;

    private int blastStep = 8;

    private Control control = null;

    @Override
    public void draw(Graphics g) {
        if (--blastStep>= 0) {
            g.drawImage(ImageUtil.tankBlastImages[blastStep], paintX(), paintY(), width, length, null);
        }else{
            control.removeBlast(this);
        }
    }

    public void grow() {
        width += 3;
        length += 3;
    }

    public TankBlast(int centerX, int centerY,Control control) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.control = control;
    }

    public int paintX() {
        return centerX - width / 2;
    }

    public int paintY() {
        return centerY - length / 2;
    }
}
