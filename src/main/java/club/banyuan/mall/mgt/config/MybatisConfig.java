package club.banyuan.mall.mgt.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("club/banyuan/mall/mgt/dao")
public class MybatisConfig {

}
