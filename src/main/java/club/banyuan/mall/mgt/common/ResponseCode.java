package club.banyuan.mall.mgt.common;

public enum ResponseCode {
    SUCCESS(200,"操作成功"),
    FORBIDDEN(403,"用户未授权"),
    REQUEST_FAIL(400,"操作失败"),
    SERVER_FAIL(500,"服务器状态异常")
    ;
    private final int code;
    private final String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
