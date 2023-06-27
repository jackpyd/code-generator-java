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
}
