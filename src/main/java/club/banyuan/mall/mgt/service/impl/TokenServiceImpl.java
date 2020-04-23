package club.banyuan.mall.mgt.service.impl;


import club.banyuan.mall.mgt.service.TokenService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${token.secretKey}")
    private  String SECRECT_KEY;

    @Value ("${token.expireSec}")
    private long EXPIRE_SEC;
    //生成token
    @Override
    public String generateToken(String suject) {
       return Jwts.builder ()
               //设置主题
               .setSubject (suject)
               //设置编码方式和密钥
                .signWith (SignatureAlgorithm.HS512,SECRECT_KEY)
               //设置系统过期时间
               .setExpiration (new Date (System.currentTimeMillis () + (EXPIRE_SEC*1000)))
               //结束标志
               .compact ();
    }
    //重载，集合map
    @Override
    public String generateToken(Map<String, Object> claims) {
        return Jwts.builder ()
                //设置主题
                .setClaims (claims)
                //设置编码方式和密钥

                .signWith (SignatureAlgorithm.HS512,SECRECT_KEY)
                //设置系统过期时间
                .setExpiration (new Date (System.currentTimeMillis () + (EXPIRE_SEC*1000)))
                //结束标志
                .compact ();
    }

    //解析token
    @Override
    public String parseSubject(String token) {
        return valiBody (token);

    }
    //解析token
    @Override
    public Map<String,Object> parseMap(String token) {
        if(getExpireSec(token)>=0 && getBody (token).getExpiration ()!=null){
            //token没有过期,返回claims集合
           return getBody (token);
        }else {
            throw new IllegalArgumentException ("token过期");
        }

    }

    private String valiBody(String token) {
        //如果token没过期则解析，否则抛出token过期异常
        if(getExpireSec(token)>=0 && getBody (token).getExpiration ()!=null){
            return getBody (token).getSubject ();
        }else {
            throw new IllegalArgumentException ("token过期");
        }
    }

    //刷新token
    @Override
    public String refreshToken(String token){
        String subject = valiBody (token);
        return generateToken (subject);
    }

    //判断token是否过期
    @Override
    public boolean isExpired(String token){
        return getExpireSec (token)<=0;
    }

    //得到token有效时长
    @Override
    public long getExpireSec(String token){
      return   (getBody (token).getExpiration ().getTime ()-System.currentTimeMillis ())/1000;
    }

    //获取getBody
    private Claims getBody(String token) {
        try {
            return Jwts.parser ().setSigningKey (SECRECT_KEY)
                    .parseClaimsJws (token).getBody ();
        } catch (ExpiredJwtException e) {
            //过期也把body解析出来
           return e.getClaims ();
        }  catch (UnsupportedJwtException | MalformedJwtException |SignatureException |IllegalArgumentException e) {
            throw new IllegalArgumentException (e);
        }
    }
}
