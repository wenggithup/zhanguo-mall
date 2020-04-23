package club.banyuan.mall.mgt.common;

import cn.hutool.json.JSONUtil;

import java.util.List;

import static club.banyuan.mall.mgt.common.ResponseCode.REQUEST_FAIL;
import static club.banyuan.mall.mgt.common.ResponseCode.SERVER_FAIL;


public class ResponseResult {

    /**
     * code : 200
     * message : 操作成功
     * data : {}
     */

    private int code;
    private String message;
    private Object data;

    public ResponseResult() {
    }

    public ResponseResult(ResponseCode code) {
        this  (code.getCode (),code.getMessage (),"");
    }

    public ResponseResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    //请求响应成功返回体
    public static ResponseResult success(Object data){
        return new ResponseResult (ResponseCode.SUCCESS.getCode (),ResponseCode.SUCCESS.getMessage (),data);
    }
    //请求响应成功分页体
    public static <T> ResponseResult setPages(int pageSize, int pageNum, List<T> resultList){
        return  ResponseResult.success (new ResponsePages<T> ());
    }

    //请求响应失败回应体
    public static ResponseResult errorRequest(String message){
        return new ResponseResult (REQUEST_FAIL.getCode (),REQUEST_FAIL.getMessage (),message);
    }
    //请求响应失败回应体
    public static ResponseResult failed(String message){
        return new ResponseResult (SERVER_FAIL.getCode (),SERVER_FAIL.getMessage (),message);
    }
    public static ResponseResult forbidden(){
        return new ResponseResult (ResponseCode.FORBIDDEN);
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr (this);
    }
}
