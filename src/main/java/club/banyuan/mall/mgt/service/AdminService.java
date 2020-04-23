package club.banyuan.mall.mgt.service;

import club.banyuan.mall.mgt.bean.AdminInfoResp;
import club.banyuan.mall.mgt.bean.AdminLoginParam;
import club.banyuan.mall.mgt.bean.AdminLoginResp;
import club.banyuan.mall.mgt.dao.entity.UmsAdmin;
import org.springframework.security.core.userdetails.UserDetails;

public interface AdminService {
    AdminLoginResp login(AdminLoginParam adminLoginParam);

    UmsAdmin getAdminById(long id);


    UserDetails getUserDetailsById(String token);

    AdminInfoResp getInfoByAdminId(long adminid);
}
