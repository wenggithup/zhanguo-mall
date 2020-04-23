package club.banyuan.mall.mgt.service.impl;


import club.banyuan.mall.mgt.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CacheServiceImpl implements CacheService {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue ().set (key,value);

    }

    //声明类转换异常
    @SuppressWarnings ("unchecked")
    @Override
    public <T> T  get(String key) {
        return (T) redisTemplate.opsForValue ().get (key);

    }

    @Override
    public Boolean expire(String key,long sec) {
       return redisTemplate.expire (key,sec, TimeUnit.SECONDS);

    }

    @Override
    public Long getExpireSec(String key) {
        return redisTemplate.getExpire (key,TimeUnit.SECONDS);
    }
}
