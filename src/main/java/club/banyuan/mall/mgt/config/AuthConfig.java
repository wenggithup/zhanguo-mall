package club.banyuan.mall.mgt.config;

import club.banyuan.mall.mgt.security.AuthenticationImpl;
import club.banyuan.mall.mgt.security.DynamicResourceFilter;
import club.banyuan.mall.mgt.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AuthConfig extends WebSecurityConfigurerAdapter {
    //security配置

    //注入对象，但是不交给spring管理
    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    //用于做白名单
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring ().antMatchers ("/admin/login").antMatchers (HttpMethod.OPTIONS,"/**");
    }

    //用来做过滤器
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭跨域防护攻击
        http.csrf ().disable ()
                //关闭session
                .sessionManagement ().sessionCreationPolicy (SessionCreationPolicy.STATELESS);
        //所有请求都需要认证
        http.authorizeRequests ().anyRequest ().authenticated ();
        //处理自定义异常类
        http.exceptionHandling ().authenticationEntryPoint (new AuthenticationImpl ());

        //注入对象
        JwtAuthenticationFilter jwtAuthenticationFilter=new JwtAuthenticationFilter ();
        beanFactory.autowireBean (jwtAuthenticationFilter);
        http.addFilterBefore (jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        DynamicResourceFilter dynamicResourceFilter=new DynamicResourceFilter ();
        beanFactory.autowireBean (dynamicResourceFilter);
        http.addFilterBefore (dynamicResourceFilter, FilterSecurityInterceptor.class);

    }

    /*@Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter ();
    }*/
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder ();
    }
}
