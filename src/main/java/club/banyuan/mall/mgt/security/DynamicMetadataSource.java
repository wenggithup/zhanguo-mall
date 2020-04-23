package club.banyuan.mall.mgt.security;

import club.banyuan.mall.mgt.dao.entity.UmsResource;
import club.banyuan.mall.mgt.service.UserResourceService;
import cn.hutool.core.util.URLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
*  判断请求路径是否合法，如果合法则放入集合中，等待下一步和用户权限校验
* */

@Component
public class DynamicMetadataSource implements SecurityMetadataSource {
    @Autowired
    private UserResourceService userResourceService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //将参数取出来
        FilterInvocation filterInvocation=(FilterInvocation)o;
        //拿到当前请求地址
        String url=filterInvocation.getRequestUrl ();
        String path= URLUtil.getPath (url);
        //拿到数据库中的所有的资源路径
        List<UmsResource> allUmsResource = userResourceService.getAllUmsResource ();
        AntPathMatcher matcher=new AntPathMatcher ();
        List<ConfigAttribute> configAttribute=new ArrayList<> ();
        //判断当前的资源路径是否和数据库中的资源路径相同，如果相同则添加到集合中
        for (UmsResource resource : allUmsResource) {
            if(matcher.match (resource.getUrl (),path)){
                configAttribute.add (new ResourceConfigAttribute(resource));
            }
        }
        return configAttribute;

    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
