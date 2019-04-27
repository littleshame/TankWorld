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
                    "images/tank/h_u.gif",
                    "images/tank/h_d.gif",
                    "images/tank/h_l.gif",
                    "images/tank/h_r.gif",
                    "images/tank/e_1_u.gif",
                    "images/tank/e_1_d.gif",
                    "images/tank/e_1_l.gif",
                    "images/tank/e_1_r.gif",
            };

    private static final String[] bulletImagePaths = {
            "images/bullet/u.gif",
            "images/bullet/d.gif",
            "images/bullet/l.gif",
            "images/bullet/r.gif",

    };

    public static final Image[] heroImages = ImageUtil.readImg(heroImagePaths);
    public static final Image[] bulletImages = ImageUtil.readImg(bulletImagePaths);


    public static Image[] readImg(String[] paths) {
        log.info("开始加载模型图片资源....");
        Image[] res = new Image[paths.length];
        for (int i = 0; i < paths.length; i++) {
            log.info("加载图片资源： " + ImageUtil.class.getClassLoader().getResource(paths[i]));
            res[i] = tk.getImage(ImageUtil.class.getClassLoader().getResource(paths[i]));
        }
        return res;
    }
}
