package club.banyuan.mall.mgt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "club.banyuan.mall.mgt")
public class MallMgtApplication {

    public static void main(String[] args) {

        SpringApplication.run (MallMgtApplication.class,args);
    }
}
