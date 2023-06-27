package org.jack.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

// 读取配置文件
public class PropertiesUtils {
    private static Properties props = new Properties();
    private static Map<String, String> PROPS_MAP = new HashMap<String, String>();

    static {
        InputStream is = null;
        // 读取文件
        try {
            is = PropertiesUtils.class.getClassLoader().getResourceAsStream("application.properties");
            props.load(is);
            // 获取key的列表
            Iterator<Object> iterator = props.keySet().iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String value = props.getProperty(key);
                // 映射k、v
                PROPS_MAP.put(key, value);
            }
        } catch (Exception e) {
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //    读取属性
    public static String getProperty(String key) {
        return PROPS_MAP.get(key);
    }

    public static void main(String[] args) {
        System.out.println(getProperty("db.password"));
        System.out.println(getProperty("db.username"));
    }
}
