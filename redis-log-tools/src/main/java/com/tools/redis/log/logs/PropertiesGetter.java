package com.tools.redis.log.logs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by yuyu on 2018/3/12.
 * 用于参数配置文件中的数据获取
 */
public class PropertiesGetter {
    /**
     * 获取配置文件的配置信息
     *
     * @param name
     * @return
     */
    public synchronized static Properties get(String name) {
        String file = name + ".properties";
        Properties prop = new Properties();
        PropertiesGetter propertiesGetter = new PropertiesGetter();
        InputStream in = propertiesGetter.getClass().getClassLoader().getResourceAsStream(file);
        try {
            prop.load(in);
        } catch (IOException e) {
            System.out.println("获取配置文件异常！-file-" + file);
        }
        return prop;
    }
}