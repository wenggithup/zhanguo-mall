package club.banyuan.mall.mgt.common;

public class RequestFailException extends RuntimeException {
    public RequestFailException() {
        super ();
    }

    public RequestFailException(FailReason failReason){
        this(failReason.getMessage ());
    }
    public RequestFailException(String message) {
        super (message);
    }

    public RequestFailException(String message, Throwable cause) {
        super (message, cause);
    }

    public RequestFailException(Throwable cause) {
        super (cause);
    }

    protected RequestFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super (message, cause, enableSuppression, writableStackTrace);
    }
}
