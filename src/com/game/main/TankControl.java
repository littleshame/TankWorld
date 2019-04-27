package com.game.main;

import com.game.entity.Bullet;
import com.game.entity.Model;
import com.game.entity.Tank;
import com.game.global.Const;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by tjc on 2019-3-15.
 * 坦克操作控制器
 * @auther: tjc
 * @Date: 2019-3-15
 */
public class TankControl extends JComponent implements KeyListener {

    private Tank hero = null;

    private List<Tank> tankList = new LinkedList<>();

    public TankControl (Tank tank) {
        this.hero = tank;
    }


    public TankControl(){
            hero = new Tank(100, 100, Const.DIRECTION_UP);
            tankList.add(hero);

            //初始化地图
            Tank enermy1 = new Tank(30, 30, Const.DIRECTION_DOWN);
            tankList.add(enermy1);

            addKeyListener(new TankControl(hero));
            setFocusable(true); //获取焦点
            new paintThread().start();
    }

    //移动的方法用线程来调用
    @Override
    public void keyPressed(KeyEvent e) {
        hero.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        hero.keyReleased(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void paintComponent(Graphics g) {
        for (int i = 0; i < tankList.size(); i++) {
            Tank tank = tankList.get(i);
            tank.draw(g);
            List<Bullet> bullets = tank.getBulletList();
            for (int j = 0; j < bullets.size(); j++) {
                Bullet b = bullets.get(i);
                b.draw(g);
            }
        }
    }

    /**
     * 刷新图像线程
     */
    private class paintThread extends Thread {
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
}
