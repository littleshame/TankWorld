package com.game.main;

import com.game.interfaces.Tank;
import com.game.model.Bullet;
import com.game.model.Hero;
import com.game.model.Enemy;
import com.game.global.Const;
import com.game.model.Wall;
import com.game.util.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by tjc on 2019-3-15.
 * 坦克操作控制器
 *
 * @author : 谭嘉诚
 * @Date: 2019-3-15
 */
public class Control extends JComponent {

    private Hero hero = null;

    private boolean load = false;

    public List<Tank> tankList = new LinkedList<>();

    public List<Wall> wallList = new LinkedList<>();

    public Control() {
        onLoad();
    }

    @Override
    public void paintComponent(Graphics g) {
        if(!load) {
            ImageUtil.loadAllImgs(g);
            load = true;
        }
        for (int i = 0; i < tankList.size(); i++) {
            Tank tank = tankList.get(i);
            tank.draw(g);
            tank.drawBullets(g);
        }
        for (int i = 0; i < wallList.size(); i++) {
            wallList.get(i).draw(g);
        }
    }

    /**
     * 刷新图像线程
     */
    private class PaintThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                repaint();
            }
        }
    }


    public void onLoad() {
        hero = new Hero(100, 100, Const.DIRECTION_UP, this);
        tankList.add(hero);

        //初始化地图
        Enemy enermy1 = new Enemy(30, 30, Const.DIRECTION_DOWN, this);

        Enemy enermy2 = new Enemy(60, 60, Const.DIRECTION_DOWN, this);

        tankList.add(enermy1);
        tankList.add(enermy2);

        Wall wall1 = new Wall();
        wallList.add(wall1);

        addKeyListener(hero);
        //获取焦点
        setFocusable(true);
        new PaintThread().start();
    }

    public List<Tank> getTankList() {
        return tankList;
    }

    public List<Wall> getWallList() {
        return wallList;
    }
}
