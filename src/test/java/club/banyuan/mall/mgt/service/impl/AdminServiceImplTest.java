package club.banyuan.mall.mgt.service.impl;

import club.banyuan.mall.mgt.bean.AdminInfoMenusBean;
import club.banyuan.mall.mgt.bean.AdminInfoResp;
import club.banyuan.mall.mgt.bean.AdminLoginParam;
import club.banyuan.mall.mgt.bean.AdminLoginResp;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith (value = SpringRunner.class)
@SpringBootTest
public class AdminServiceImplTest {
    @Autowired
    private AdminServiceImpl adminService;

    @Test
    public void loginTest(){
        AdminLoginParam adminLoginParam=new AdminLoginParam ();
        adminLoginParam.setUsername ("admin");
        adminLoginParam.setPassword ("banyuan");

        AdminLoginResp login = adminService.login (adminLoginParam);
        String token = login.getToken ();
        Assert.assertTrue (StrUtil.isNotBlank (token));
    }

    @Test
    public void infoTest(){
        AdminInfoResp info=adminService.getInfoByAdminId (3);
        Assert.assertTrue (CollUtil.isNotEmpty (info.getMenus ()));
        Assert.assertTrue (CollUtil.isNotEmpty (info.getRoles ()));
    }
}
