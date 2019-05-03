package top.haibaraai.wx_login_pay.domain;

public class JsonData {

    /**
     * 状态码，0表示成功，-1表示处理失败
     */
    private Integer code;
    /**
     * 数据内容
     */
    private Object data;
    /**
     * 描述
     */
    private String message;

    public JsonData() {

    }

    public JsonData(Integer code, Object data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    /**
     * 成功
     */
    public static JsonData buildSuccess() {
        return new JsonData(0, null, null);
    }

    public static JsonData buildSuccess(Object data) {
        return new JsonData(0, data, null);
    }

//    public static JsonData buildSuccess(String message) {
//        return new JsonData(0, null, message);
//    }

    public static JsonData buildSuccess(Object data, String message) {
        return new JsonData(0, data, message);
    }

    /**
     * 失败
     */
    public static JsonData buildError() {
        return new JsonData(-1, null, null);
    }

//    public static JsonData buildError(Object data) {
//        return new JsonData(-1, data, null);
//    }

    public static JsonData buildError(String message) {
        return new JsonData(-1, null, message);
    }

    public static JsonData buildError(Object data, String message) {
        return new JsonData(-1, data, message);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "JsonData [code=" + code + ", data=" + data + ", message=" + message + "]";
    }

}
