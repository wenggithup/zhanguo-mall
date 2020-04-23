package club.banyuan.mall.mgt.bean;

import java.util.List;

public class AdminInfoResp {

    /**
     * roles : ["TEST"]
     * icon : http://minio.banyuan.club/dev/preset/timg.jpg
     * menus : [{"id":1,"parentId":0,"createTime":"2020-02-02T06:50:36.000+0000","title":"商品","level":0,"sort":0,"name":"pms","icon":"product","hidden":0}]
     * username : admin
     */

    private String icon;
    private String username;
    private List<String> roles;
    private List<AdminInfoMenusBean> menus;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<AdminInfoMenusBean> getMenus() {
        return menus;
    }

    public void setMenus(List<AdminInfoMenusBean> menus) {
        this.menus = menus;
    }


}
