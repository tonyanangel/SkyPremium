package com.skypremiuminternational.app.app.utils;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectUtil {
    public static boolean isEmptyStr(String string) {
        return TextUtils.isEmpty(string);
    }

    public static boolean isEmptyBytes(byte[] bytes) {
        return bytes == null || bytes.length == 0;
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isEmptyList(List list) {
        return list == null || list.isEmpty();
    }

    public static boolean isEmptyHashMap(HashMap hashMap) {
        return hashMap == null || hashMap.isEmpty();
    }

    public static boolean isEmptyMap(Map map) {
        return map == null || map.isEmpty();
    }

    public static Integer getIdNotification(String idNotification){
        int id = 1;
        try {
            id = Integer.parseInt(idNotification);
        } catch (Exception ex){}
        return id;
    }
}
