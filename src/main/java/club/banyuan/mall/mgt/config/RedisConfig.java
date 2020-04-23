package club.banyuan.mall.mgt.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
//开启缓存
@EnableCaching

public class RedisConfig  extends CachingConfigurerSupport {

    /*
    * 加载redis启动项，并指定序列化和反序列化方式
    * */
    @Bean
        //当创建RedisConnectionFactory对象时，spring会将bean注入
    public RedisTemplate<String,Object> setRedisTemplate(RedisConnectionFactory redisConnectionFactory){


        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<> ();
        redisTemplate.setConnectionFactory (redisConnectionFactory);

        //指定 key 和value的序列化和反序列化的方式
        RedisSerializer<Object> serializer=setRedisSerializer ();
        //指定字符串类型序列化和反序列化
        redisTemplate.setKeySerializer (new StringRedisSerializer ());
        redisTemplate.setValueSerializer (serializer);

        //指定hash类型序列化和反序列化
        redisTemplate.setHashKeySerializer (new StringRedisSerializer ());
        redisTemplate.setHashValueSerializer (serializer);

        //让配置生效
        redisTemplate.afterPropertiesSet ();

        return redisTemplate;
    }

    //设置序列化方式
    @Bean
    public RedisSerializer<Object> setRedisSerializer(){
        //使用jackson2 指定序列化
        Jackson2JsonRedisSerializer<Object> serializer=new Jackson2JsonRedisSerializer<Object> (Object.class);

        //将java对象转换成json对象
        ObjectMapper objectMapper=new ObjectMapper ();
        //保存key的时候会吧类的名称保存下来
        objectMapper.enableDefaultTyping (ObjectMapper.DefaultTyping.NON_FINAL);

        //指定所有的get set方法都可以被序列化出来，无论是共有的还是私有的方法
        objectMapper.setVisibility (PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        serializer.setObjectMapper (objectMapper);
        return serializer;
    }

    //设置redis过期时间
    @Bean
    public RedisCacheManager setRedisCacheManager(RedisConnectionFactory redisConnectionFactory){
        RedisCacheWriter redisCacheWriter = RedisCacheWriter
                .nonLockingRedisCacheWriter (redisConnectionFactory);

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig ()
                .serializeValuesWith (
                        RedisSerializationContext.SerializationPair.fromSerializer (setRedisSerializer ()))
                .entryTtl (Duration.ofDays (1));
        return new RedisCacheManager (redisCacheWriter,redisCacheConfiguration);
    }

}
