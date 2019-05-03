package com.game.main;

import com.game.global.Const;

import javax.swing.*;
import java.awt.*;

/**
 * Created by tjc on 2019-3-8.
 *
 * @auther: tjc
 * @Date: 2019-3-8
 */

public class Main {

    public static void main(String[] args) {
        //事件分派线程
        EventQueue.invokeLater(() -> {
            JFrame client = new GameClient();
            Control control = new Control();

            client.add(control);
            client.setSize(500, 500);
            client.setVisible(true);
        });


    }
}

/**
 * 运行窗口
 */
class GameClient extends JFrame {
    public GameClient() {
        setSize(Const.DEFAULT_WINDOW_WIDTH, Const.DEFAULT_WINDOW_LENGTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        setTitle("坦克大战");
    }
}