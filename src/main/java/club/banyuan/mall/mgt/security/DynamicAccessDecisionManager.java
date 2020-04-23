package club.banyuan.mall.mgt.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/*
* 判断用户是否有请求url的权限
* */
@Component
public class DynamicAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        //将authentication遍历拿到用户的请求uri资源
        Set<String> adminResource = authentication.getAuthorities ().stream ()
                .map (GrantedAuthority::getAuthority).collect(Collectors.toSet());
        //标记是否合法
        boolean isAuthorized=false;
        //判断用户权限是否包含请求uri路径，如果没有则跑出异常
        for (ConfigAttribute configAttribute : collection) {
            if(adminResource.contains (configAttribute.getAttribute ())){
               isAuthorized=true;
               break;
            }
        }
        if(!isAuthorized){
            throw new AccessDeniedException ("没有访问权限");
        }
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
