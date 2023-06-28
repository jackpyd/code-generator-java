package org.jack.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
    private static Logger logger = LoggerFactory.getLogger(DateTimeUtils.class);

    // 日期格式化样式
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DATEFORMATYYYYMMDD = "yyyy-MM-dd";
    public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMAT_NYR = "yyyy年-MM月-dd日 a hh:mm";


    public static String format(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static Date parse(String format, String parseDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(parseDate);
        } catch (ParseException e) {
            logger.error("日期解析异常:{}", e.getMessage());
        } finally {
            return null;
        }
    }
}
