package org.jack.bean;

import org.jack.utils.PropertiesUtils;

public class Constants {
    private static Boolean IGNORE_TABLE_PREFIX;
    private static String BEAN_PARAM_QUERY_SUFFIX;

    static {
        IGNORE_TABLE_PREFIX = Boolean.valueOf(PropertiesUtils.getProperty("ignore.table.prefix"));
        BEAN_PARAM_QUERY_SUFFIX = String.valueOf(PropertiesUtils.getProperty("bean.param.query.suffix"));
    }

    public static String getBeanParamQuerySuffix() {
        return BEAN_PARAM_QUERY_SUFFIX;
    }

    public static Boolean getIgnoreTablePrefix() {
        return IGNORE_TABLE_PREFIX;
    }

    // 可转为time的类型
    public static final String[] SQL_DATE_TIME_TYPES = {"datetime", "timestamp", "time", "year"};
    // 可转为date的类型
    public static final String[] SQL_DATE_TYPES = {"date"};
    //可以转成Integer类型的
    public static final String[] SQL_INTEGER_TYPES = {"int", "integer"};
    // 可转为bool的类型
    public static final String[] SQL_BOOLEAN_TYPES = {"bit", "boolean", "tinyint"};
    //可以转成Double类型的
    public static final String[] SQL_DOUBLE_TYPES = {"double", "float", "decimal", "numeric"};
    //可以转成Long类型的
    public static final String[] SQL_LONG_TYPES = {"bigint"};
    //可以转成String类型的
    public static final String[] SQL_STRING_TYPES = {"char", "varchar", "tinytext", "text", "mediumtext", "longtext", "blob", "tinyblob", "mediumblob", "longblob"};

}
