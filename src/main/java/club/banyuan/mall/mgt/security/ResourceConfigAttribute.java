package club.banyuan.mall.mgt.security;

import club.banyuan.mall.mgt.dao.entity.UmsResource;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.GrantedAuthority;

public class ResourceConfigAttribute implements ConfigAttribute, GrantedAuthority {
    private UmsResource umsResource;
    @Override
    public String getAttribute() {
        return umsResource.getId ()+":"+umsResource.getName ();
    }

    public ResourceConfigAttribute(UmsResource umsResource) {
        this.umsResource = umsResource;
    }

    public UmsResource getUmsResource() {
        return umsResource;
    }

    public void setUmsResource(UmsResource umsResource) {
        this.umsResource = umsResource;
    }

    @Override
    public String getAuthority() {
        return getAttribute ();
    }
}
