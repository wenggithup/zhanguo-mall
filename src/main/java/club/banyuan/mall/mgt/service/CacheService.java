package club.banyuan.mall.mgt.service;

public interface CacheService {
    //存储
    void set(String key, Object value);

    //通过key获取value
    <T> T get(String key);

    //设置redis key过期时间
    Boolean expire(String key, long sec);

    //查询还有多长时间过期
    Long getExpireSec(String key);

}
