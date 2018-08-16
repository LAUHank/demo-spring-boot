package cn.lhl.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Administrator on 2018/8/16.
 */
public class JsonUtil {
    private static final ObjectMapper om = new ObjectMapper();
    private JsonUtil() {}
    public static String objectToJsonString(Object obj) throws Exception {
        return om.writeValueAsString(obj);
    }
}
