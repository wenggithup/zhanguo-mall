package club.banyuan.mall.mgt.security;


import club.banyuan.mall.mgt.service.AdminService;
import club.banyuan.mall.mgt.service.TokenService;
import club.banyuan.mall.mgt.service.UserResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
    * token校验filter类，校验请求体中是否含有token，如果有
    *   则将token解析存入上下文，然后放行，否则直接放行；
    *
* */

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Value ("${request.schema}")
    private String SCHEMA;
    @Value ("${request.header}")
    private String TOKEN_HEAD_KEY;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserResourceService userResourceService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHead = request.getHeader(TOKEN_HEAD_KEY);
        if (authHead!=null && authHead.startsWith(SCHEMA)){
            //拿到token并解析，获取到用户的id
            String token = authHead.substring(SCHEMA.length());

            //拿到userDetails
            UserDetails userDetails = adminService.getUserDetailsById (token);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                    new UsernamePasswordAuthenticationToken (userDetails.getUsername (),userDetails.getPassword (),userDetails.getAuthorities ());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(request, response);
    }

}
