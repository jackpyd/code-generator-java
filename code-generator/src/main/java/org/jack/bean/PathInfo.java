package org.jack.bean;

import org.jack.utils.PropertiesUtils;

// 路径信息
public class PathInfo {

    public static String PACKAGE_EXAMPLE;
    public static String EXAMPLE_PATH;
    public static String PACKAGE_EXAMPLE_PATH;

    // 项目的基础路径
    public static String PATH_PACKAGE_BASE;


    //vo包
    public static String PACKAGE_VO;
    public static String VO_PATH;
    public static String PACKAGE_VO_PATH;

    //获取生成POJO代码的路径
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

    // 查询类路径
    public static String PACKAGE_QUERY;
    public static String PATH_PACKAGE_QUERY;
    // 查询类的后悔
    public static String SUFFIX_NAME_QUERY_CLASS;
    // 模糊查询字段后缀
    public static String SUFFIX_NAME_QUERY_FUZZY;
    // 查询日期开始字段后缀
    public static String SUFFIX_NAME_START_QUERY_DATE;
    // 查询日期结束字段后缀
    public static String SUFFIX_NAME_END_QUERY_DATE;


    static {
        PATH_PACKAGE_BASE = PropertiesUtils.getProperty("base.package.path");
        // 需要判断是否以/结尾
        if (!PATH_PACKAGE_BASE.endsWith("/")) {
            PATH_PACKAGE_BASE = PATH_PACKAGE_BASE + "/";
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
        POJO_PATH = PATH_PACKAGE_BASE + PATH_JAVA + "/" + POJO_BASE.replace(".", "/");
        PACKAGE_EXAMPLE_PATH = PATH_PACKAGE_BASE + PATH_JAVA + "/" + EXAMPLE_PATH.replace(".", "/");

        // 查询类路径

        PACKAGE_QUERY = PACKAGE_BASE + "." + PropertiesUtils.getProperty("query.package");
        PATH_PACKAGE_QUERY = PATH_PACKAGE_BASE + PATH_JAVA + "/" + PACKAGE_QUERY.replace(".", "/");

        SUFFIX_NAME_QUERY_CLASS = PropertiesUtils.getProperty("db.bean.class.suffix.name");
        SUFFIX_NAME_QUERY_FUZZY = PropertiesUtils.getProperty("db.bean.query.suffix.fuzzy");

        SUFFIX_NAME_START_QUERY_DATE = PropertiesUtils.getProperty("db.bean.query.date.suffix.start");
        SUFFIX_NAME_END_QUERY_DATE = PropertiesUtils.getProperty("db.bean.query.date.suffix.end");

        // 工具类的包名
        PACKAGE_UTILS = PACKAGE_BASE + "." + PropertiesUtils.getProperty("utils.package");
        // 工具类的路径
        PATH_PACKAGE_UTILS = PATH_PACKAGE_BASE + PATH_JAVA + "/" + PACKAGE_UTILS.replace(".", "/") + "/";
        // 枚举类的包名
        PACKAGE_ENUMS = PACKAGE_BASE + "." + PropertiesUtils.getProperty("enums.package");
        // 枚举类路径
        PATH_PACKAGE_ENUMS = PATH_PACKAGE_BASE + PATH_JAVA + "/" + PACKAGE_ENUMS.replace(".", "/") + "/";


    }
}
