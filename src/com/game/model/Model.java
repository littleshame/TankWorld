package com.game.model;

import java.awt.*;

/**
 * Created by tjc on 2019-3-15.
 *
 * @auther: tjc
 * @Date: 2019-3-15
 */
public abstract class Model {

    protected int x;
    protected int y;

    protected int width;
    protected int length;

    protected int direction;

    protected int life;

    protected int speed;

    protected int breakLevel;


    public abstract void draw(Graphics g);

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getBreakLevel() {
        return breakLevel;
    }

    public void setBreakLevel(int breakLevel) {
        this.breakLevel = breakLevel;
    }
}
