package com.game.interfaces;

import java.awt.*;

/**
 * @author: tjc
 * @Date: 2019-5-3
 */
public interface TouchAble {

    /**
     * 返回外接矩型
     * @return
     */
    Rectangle getRec();

    /**
     * 是否碰撞
     * @param rect
     * @return
     */
    boolean crash(Rectangle rect);
}
