package club.banyuan.mall.mgt.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan("club/banyuan/mall/mgt/dao")
@EnableTransactionManagement
public class MybatisConfig {

}
