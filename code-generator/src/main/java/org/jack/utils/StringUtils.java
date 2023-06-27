package org.jack.utils;

// 下划线转驼峰
public class StringUtils {

    public static String NametoCamelCase(char field, String name, boolean isUpperFirst) {
        if (name == null || name.length() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(name.length());
        boolean upperCase = false;
        if (isUpperFirst) {
            upperCase = true;
        }
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (c == field) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    // 去除括号
    public static String removeBrackets(String str) {
        if (str == null || str.length() == 0 || !str.contains("(")) {
            return str;
        }
        return str.substring(0, str.indexOf("("));
    }

}
