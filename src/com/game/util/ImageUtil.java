package com.game.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

/**
 * Created by tjc on 2019-3-16.
 * 加载图片类
 *
 * @auther: tjc
 * @Date: 2019-3-16
 */
public class ImageUtil {
    private static Logger log = LoggerFactory.getLogger(ImageUtil.class);

    public static final Toolkit tk = Toolkit.getDefaultToolkit();

    private static final String[] heroImagePaths =
            {
                    "images/tank/hero_up.gif",
                    "images/tank/hero_down.gif",
                    "images/tank/hero_left.gif",
                    "images/tank/hero_right.gif",
                    "images/tank/e_1_u.gif",
                    "images/tank/e_1_d.gif",
                    "images/tank/e_1_l.gif",
                    "images/tank/e_1_r.gif",
            };

    private static final String[] bulletImagePaths = {
            "images/bullet/bullet_up.png",
            "images/bullet/bullet_down.png",
            "images/bullet/bullet_left.png",
            "images/bullet/bullet_right.png",

    };

    private static final String[] wallImagePaths = {
            "images/wall/normal.gif",
    };

    private static final String[] tankBlastImagePaths = {
            "images/blast/tank_1.gif",
            "images/blast/tank_2.gif",
            "images/blast/tank_3.gif",
            "images/blast/tank_4.gif",
            "images/blast/tank_5.gif",
            "images/blast/tank_6.gif",
            "images/blast/tank_7.gif",
            "images/blast/tank_8.gif",
    };

    private static final String[] bulletBlastImagePaths = {
            "images/blast/bullet_1.png",
            "images/blast/bullet_2.png",
            "images/blast/bullet_3.png",
            "images/blast/bullet_4.png",
            "images/blast/bullet_5.png",
            "images/blast/bullet_6.png",
    };


    public static final Image[] heroImages = ImageUtil.readImg(heroImagePaths);
    public static final Image[] bulletImages = ImageUtil.readImg(bulletImagePaths);
    public static final Image[] wallImages = ImageUtil.readImg(wallImagePaths);
    public static final Image[] tankBlastImages = ImageUtil.readImg(tankBlastImagePaths);
    public static final Image[] bulletBlastImages = ImageUtil.readImg(bulletBlastImagePaths);

    public static Image[] readImg(String[] paths) {
        log.info("开始加载模型图片资源....");
        Image[] res = new Image[paths.length];
        for (int i = 0; i < paths.length; i++) {
            log.info("加载图片资源： " + ImageUtil.class.getClassLoader().getResource(paths[i]));
            res[i] = tk.getImage(ImageUtil.class.getClassLoader().getResource(paths[i]));
        }
        return res;
    }

    public static void loadAllImgs(Graphics g){
        for (int i = 0; i < tankBlastImages.length; i++) {
            g.drawImage(tankBlastImages[i],Integer.MAX_VALUE,Integer.MAX_VALUE,null);
        }
        for (int i = 0; i < bulletImages.length; i++) {
            g.drawImage(bulletImages[i],Integer.MAX_VALUE,Integer.MAX_VALUE,null);
        }
        for (int i = 0; i < bulletBlastImages.length; i++) {
            g.drawImage(bulletBlastImages[i],Integer.MAX_VALUE,Integer.MAX_VALUE,null);
        }

    }
}
