package cn.lhl.util;

/**
 * Created by Administrator on 2018/8/16.
 */
public class JsonResponsePojo {
    private int code;
    private String text;
    private Object result;

    public JsonResponsePojo() {
    }

    public JsonResponsePojo(int code, String text, Object result) {
        this.code = code;
        this.text = text;
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "JsonResponsePojo{" +
                "code=" + code +
                ", text='" + text + '\'' +
                ", result=" + result +
                '}';
    }
}
