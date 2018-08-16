package cn.lhl.util;

/**
 * Created by Administrator on 2018/8/16.
 */
public class CloseUtil {
    private CloseUtil() {}
    public static void close(AutoCloseable closeObj) {
        if (closeObj != null) {
            try {
                closeObj.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
