package org.jack.bean;

import org.jack.utils.PropertiesUtils;

// 路径信息
public class PathInfo {

    public static String PACKAGE_EXAMPLE;
    public static String EXAMPLE_PATH;
    public static String PACKAGE_EXAMPLE_PATH;


    //vo包
    public static String PACKAGE_VO;
    public static String VO_PATH;
    public static String PACKAGE_VO_PATH;

    //获取生成POJO代码的路径
    public static String PATH_PACKAGE_POJO;
    public static String POJO_PATH;
    public static String POJO_BASE;
    public static String PATH_JAVA = "java";
    public static String PATH_RESOURCES = "resources";

    public static String PACKAGE_BASE;
    public static String ENTITY_POJO;
    public static String ENTITY_PARAM;

    // 工具类和枚举路径
    public static String PACKAGE_UTILS;
    public static String PATH_PACKAGE_UTILS;
    public static String PACKAGE_ENUMS;
    public static String PATH_PACKAGE_ENUMS;


    static {
        PATH_PACKAGE_POJO = PropertiesUtils.getProperty("pojo.package.path");
        // 需要判断是否以/结尾
        if (!PATH_PACKAGE_POJO.endsWith("/")) {
            PATH_PACKAGE_POJO = PATH_PACKAGE_POJO + "/";
        }
        // 拼接包名 org.jack.entity
        PACKAGE_BASE = PropertiesUtils.getProperty("package.base");
        ENTITY_POJO = PropertiesUtils.getProperty("package.pojo");
        ENTITY_PARAM = PropertiesUtils.getProperty("package.param");
        PACKAGE_EXAMPLE = PropertiesUtils.getProperty("package.example");
        PACKAGE_VO = PropertiesUtils.getProperty("package.vo");
        // 拼接包名 org.jack.entity.pojo
        POJO_BASE = PACKAGE_BASE + "." + ENTITY_POJO;
        // org.jack.entity.example
        EXAMPLE_PATH = PACKAGE_BASE + "." + PACKAGE_EXAMPLE;
        // 拼接输出路径
        POJO_PATH = PATH_PACKAGE_POJO + PATH_JAVA + "/" + POJO_BASE.replace(".", "/");
        PACKAGE_EXAMPLE_PATH = PATH_PACKAGE_POJO + PATH_JAVA + "/" + EXAMPLE_PATH.replace(".", "/");
        // 工具类的包名
        PACKAGE_UTILS = PACKAGE_BASE + "." + PropertiesUtils.getProperty("utils.package");
        // 工具类的路径
        PATH_PACKAGE_UTILS = PATH_PACKAGE_POJO + PATH_JAVA + "/" + PACKAGE_UTILS.replace(".", "/") + "/";
        // 枚举类的包名
        PACKAGE_ENUMS = PACKAGE_BASE + "." + PropertiesUtils.getProperty("enums.package");
        // 枚举类路径
        PATH_PACKAGE_ENUMS = PATH_PACKAGE_POJO + PATH_JAVA + "/" + PACKAGE_ENUMS.replace(".", "/") + "/";


    }
}
