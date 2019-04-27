package com.game.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by tjc on 2019-3-16.
 *
 * @auther: tjc
 * @Date: 2019-3-16
 */
public class ImagePropertiesUtil {

    private final static String IMAGE_PATH_PROPERTIES = "imagePath.properties";

    private static Map<String,String> configCache = null;

    public static String load(String key) {
        init();
        String val = configCache.get(key);

        return val == null ? "" : val;
    }

    private static void init() {
        if(configCache != null) {
            return;
        }
        configCache = new HashMap<>();
        Properties properties = new Properties();
        // 使用ClassLoader加载properties配置文件生成对应的输入流
        InputStream in = ImagePropertiesUtil.class.getClassLoader().getResourceAsStream(IMAGE_PATH_PROPERTIES);
        // 使用properties对象加载输入流
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Set<String> set = properties.stringPropertyNames();

        Iterator<String> iter = set.iterator();
        while(iter.hasNext()){
            String key = iter.next();
            String val = (String) properties.get(key);
            configCache.put(key, val);
        }

    }
}
