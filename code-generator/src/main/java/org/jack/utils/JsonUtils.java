package org.jack.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonUtils {
    //    将对象转换为json
    public static String obj2Json(Object obj) {
        if (obj == null) return null;
        return JSON.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect);
    }
}
