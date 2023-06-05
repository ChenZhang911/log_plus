package com.tools.redis.log.logs;

import com.fasterxml.jackson.databind.ObjectMapper;
public class JsonBuilder {
    /**
     * 将一个实体类转换成json字符串
     *
     * @param object
     * @return
     */
    public static String getString(Object object) {
        //安全判断
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        String back = null;
        try {
            back = mapper.writeValueAsString(object);
        } catch (Exception e) {
            //抛出一个自定义异常
        }
        return back;
    }
}