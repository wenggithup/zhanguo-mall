package club.banyuan.mall.mgt.service.impl;

import club.banyuan.mall.mgt.bean.AdminInfoMenusBean;
import club.banyuan.mall.mgt.bean.AdminInfoResp;
import club.banyuan.mall.mgt.bean.AdminLoginParam;
import club.banyuan.mall.mgt.bean.AdminLoginResp;
import club.banyuan.mall.mgt.dao.UmsAdminDao;
import club.banyuan.mall.mgt.dao.UmsMenuDao;
import club.banyuan.mall.mgt.dao.UmsRoleDao;
import club.banyuan.mall.mgt.dao.entity.UmsAdmin;
import club.banyuan.mall.mgt.dao.entity.UmsMenu;
import club.banyuan.mall.mgt.dao.entity.UmsResource;
import club.banyuan.mall.mgt.dao.entity.UmsRole;
import club.banyuan.mall.mgt.security.ResourceConfigAttribute;
import club.banyuan.mall.mgt.service.AdminService;
import club.banyuan.mall.mgt.service.TokenService;
import club.banyuan.mall.mgt.service.UserResourceService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
    @Value ("${request.schema}")
    private String SCHEMA;
    @Value ("${request.header}")
    private String TOKEN_HEAD_KEY;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UmsAdminDao umsAdminDao;

    @Autowired
    private UmsMenuDao umsMenuDao;

    @Autowired
    private UmsRoleDao umsRoleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserResourceService userResourceService;

    @Override
    public AdminLoginResp login(AdminLoginParam adminLoginParam) {
       AdminLoginResp adminLoginResp=new AdminLoginResp ();
        String username = adminLoginParam.getUsername ();
        //通过username查询umsadmin对象
        UmsAdmin umsAdmin = umsAdminDao.selectByUsername (username);
        if(umsAdmin==null || !passwordEncoder.
                matches (adminLoginParam.getPassword (),umsAdmin.getPassword ())){
            throw new RuntimeException ("用户名或密码错误");
        }

        adminLoginResp.setToken (tokenService.generateToken (umsAdmin.getId ().toString ()));
        adminLoginResp.setTokenHead (SCHEMA);

        return adminLoginResp;

    }
    @Override
    public UmsAdmin getAdminById(long id){
        return umsAdminDao.selectByPrimaryKey (id);
    }

    @Override
    public UserDetails getUserDetailsById(String token){
        Long adminId =Long.parseLong (tokenService.parseSubject(token)) ;
        //通过id找到umsAdmin对象
        UmsAdmin umsAdmin = adminService.getAdminById (adminId);
        //通过id找到resource匹配的路径
        List<UmsResource> adminResources= userResourceService.getResouceByAdminId (adminId);
        List<ResourceConfigAttribute> grantedAuthorities=new ArrayList<> ();
        //将用户的权限塞入集合中，并传递给下一个过滤器
        if(adminResources!=null){

            for (UmsResource adminResource : adminResources) {
                grantedAuthorities.add (new ResourceConfigAttribute (adminResource));
            }
        }
        return new UserDetailsImpl (umsAdmin,grantedAuthorities);

    }

    @Override
    public AdminInfoResp getInfoByAdminId(long adminid) {
        UmsAdmin umsAdmin=umsAdminDao.selectByPrimaryKey (adminid);

        AdminInfoResp adminInfoResp=new AdminInfoResp ();
        adminInfoResp.setIcon (umsAdmin.getIcon ());
        adminInfoResp.setUsername (umsAdmin.getUsername ());
        List<UmsRole> umsRoles = umsRoleDao.selectRoleByAdminId (adminid);
        if(CollUtil.isEmpty (umsRoles)){
            throw new RuntimeException ("角色权限不合法");
        }
        List<Long> roleIds=new ArrayList<> ();
        for (UmsRole umsRole : umsRoles) {
            roleIds.add (umsRole.getId ());
        }
        List<UmsMenu> umsMenus = umsMenuDao.selectByRoleByIds (roleIds);
        adminInfoResp.setMenus (umsMenus.stream ().map (t->{
            AdminInfoMenusBean adminInfoMenusBean=new AdminInfoMenusBean ();
            BeanUtil.copyProperties (t,adminInfoMenusBean);
            return adminInfoMenusBean;
        }).collect (Collectors.toList ()));

        adminInfoResp.setRoles (umsRoles.stream ().map (UmsRole::getName).collect(Collectors.toList()));

        return adminInfoResp;
    }
}
