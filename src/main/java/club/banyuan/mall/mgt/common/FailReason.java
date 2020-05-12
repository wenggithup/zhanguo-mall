package club.banyuan.mall.mgt.common;

//失败的枚举
public enum FailReason {
    UMS_ADMIN_USER_NOT_VALID("用户名或密码输入错误"),
    UMS_ADMIN_ROLE_EMPTY("角色列表为空"),
    UMS_ADMIN_USER_NOT_EXIST("用户不存在"),
    UMS_ADMIN_ROLE_EXIST("分配角色姓名已存在"),
    UMS_ADMIN_ROLEID_NOT_EXIST("分配角色姓名不存在"),
    UMS_ADMIN_ROLE_MENU_NOT_EXIST("角色菜单数组不存在"),
    UMS_ROLE_MENU_REL_ILLEGAL("角色菜单管理关系不合法"),
    UMS_ADMIN_USERNAME_EXIST("用户账号已存在"),
    UMS_ADMIN_ADMINID_NOT_EXIST("用户id不存在"),
    UMS_MENU__MENUID_NOT_EXIST("菜单id不存在"),
    UMS_RESOURCE_CATEGORYID_NOT_EXIST("资源分类id不存在"),
    UMS_RESOURCE_NAMEANDURL_EXIST("资源名称和url已经存在"),
    UMS_RESOURCE_ID_NOT_EXIST("资源id不存在"),
    UMS_RESOURCE_CATEGORYNAME_EXIST("分类资源名称已存在"),
    UMS_ROLE_RESOURCE_ID_NOT_EXIST("资源路径id不存在")
    ;


    private final String message;

    FailReason(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


}
