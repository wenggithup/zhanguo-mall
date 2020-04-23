package club.banyuan.mall.mgt.security;

import club.banyuan.mall.mgt.common.ResponseResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
    * 未授权请求回应实现类
* */
public class AuthenticationImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setCharacterEncoding ("utf-8");
        httpServletResponse.setContentType ("application/json");
        httpServletResponse.getWriter ().println (ResponseResult.forbidden ());
    }
}
