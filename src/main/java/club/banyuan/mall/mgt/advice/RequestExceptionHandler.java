package club.banyuan.mall.mgt.advice;


import club.banyuan.mall.mgt.common.RequestFailException;
import club.banyuan.mall.mgt.common.ResponseResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//处理异常类
@RestControllerAdvice
public class RequestExceptionHandler {
    //拦截处理自定义类的异常
    @ExceptionHandler(RequestFailException.class)
    public ResponseResult requestFileException(RequestFailException request){
        return ResponseResult.errorRequest (request.getMessage ());
    }

    //拦截处理参数非法异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult methodArgumentNotValidException(MethodArgumentNotValidException e){
        return ResponseResult.errorRequest (e.getBindingResult ().getFieldError ().getDefaultMessage ());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseResult runtimeException(RuntimeException e){
        return ResponseResult.failed (e.getMessage ());
    }
}
