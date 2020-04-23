package club.banyuan.mall.mgt.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class GlobalCorsConfig {

    @Bean
    //跨域配置
    public CorsFilter corsFilter(){
        CorsConfiguration corsConfiguration=new CorsConfiguration ();
        //同源
        corsConfiguration.addAllowedOrigin ("*");
        corsConfiguration.addAllowedHeader ("*");
        corsConfiguration.addAllowedMethod ("*");
        //支持cookie跨域
        corsConfiguration.setAllowCredentials (true);

        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource ();
        source.registerCorsConfiguration ("/**",corsConfiguration);
        return new CorsFilter (source);

    }
}
