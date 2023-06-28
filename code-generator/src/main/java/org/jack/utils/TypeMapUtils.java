package org.jack.utils;


import org.apache.commons.lang3.ArrayUtils;
import org.jack.bean.Constants;

// 类型映射
public class TypeMapUtils {
    // 将mysql的类型映射为java的类型
    public static String mysqlType2JavaType(String type) {
        if (ArrayUtils.contains(Constants.SQL_DOUBLE_TYPES, type)) {
            return "BigDecimal";
        } else if (ArrayUtils.contains(Constants.SQL_BOOLEAN_TYPES, type)) {
            return "Boolean";
        } else if (ArrayUtils.contains(Constants.SQL_INTEGER_TYPES, type)) {
            return "Integer";
        } else if (ArrayUtils.contains(Constants.SQL_LONG_TYPES, type)) {
            return "Long";
        } else if (ArrayUtils.contains(Constants.SQL_DATE_TYPES, type) || ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, type)) {
            return "Date";
        } else if (ArrayUtils.contains(Constants.SQL_STRING_TYPES, type)) {
            return "String";
        } else {
            throw new RuntimeException("未知类型" + type);
        }


    }
}
