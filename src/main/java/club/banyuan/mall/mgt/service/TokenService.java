package club.banyuan.mall.mgt.service;

import java.util.Map;

public interface TokenService {

    //根据头牌生成token
    String generateToken(String suject);

    //重载，集合map
    String generateToken(Map<String, Object> claims);

    //根据token解析头牌
    String parseSubject(String token);

    //解析token
    Map<String,Object> parseMap(String token);

    //刷新token
     String refreshToken(String token);

    //判断token是否过期
     boolean isExpired(String token);

    //得到token有效时长
     long getExpireSec(String token);
}
