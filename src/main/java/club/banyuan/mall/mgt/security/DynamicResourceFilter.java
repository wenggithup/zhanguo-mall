package club.banyuan.mall.mgt.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;

import javax.servlet.*;
import java.io.IOException;


/*
    * 查询用户所有资源
    * 查询当前请求路径资源
    * 比较路径资源是否在用户资源列表中
    *
* */
public class DynamicResourceFilter extends AbstractSecurityInterceptor implements Filter {
    @Autowired
    private DynamicMetadataSource dynamicMetadataSource;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        FilterInvocation filterInvocation=new FilterInvocation (servletRequest,servletResponse,filterChain);

        //在调用方法之前，将参数传递到metadateSource 进行资源路径获取,并比较用户是否包含此权限
        InterceptorStatusToken token=super.beforeInvocation (filterInvocation);

        try {
            //鉴权后将参数传递给下一个过滤器，并放行
            filterInvocation.getChain ().doFilter (filterInvocation.getRequest (),filterInvocation.getResponse ());
        } finally {
            //将鉴权后的结果返回到下一个invocation
            super.afterInvocation (token,null);
        }
    }


    //判断是否合法
    @Autowired
    @Override
    public void setAccessDecisionManager(AccessDecisionManager accessDecisionManager) {
        super.setAccessDecisionManager (accessDecisionManager);
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return dynamicMetadataSource;
    }
}
