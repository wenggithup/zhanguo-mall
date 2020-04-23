package club.banyuan.mall.mgt.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisCacheServiceImplTest {
    @Autowired
    private CacheServiceImpl cacheService;

    //redis 存取测试
    @Test
    public void getSetTest(){
        cacheService.set ("test","admin");

        Assert.assertEquals ("admin",cacheService.get ("test"));
    }
    //测试redis存储时间
    @Test
    public  void expireTest(){
        String key="admin";
        String value="banyuan";
        cacheService.set (key,value);
        cacheService.expire (key,3);
        Assert.assertTrue (cacheService.getExpireSec (key)<3);
        try {
            Thread.sleep (4000);
            Assert.assertNull (cacheService.get (key));
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }
    }


}
