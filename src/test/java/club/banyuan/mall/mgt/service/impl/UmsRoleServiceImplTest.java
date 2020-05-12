package club.banyuan.mall.mgt.service.impl;


import club.banyuan.mall.mgt.bean.RoleCreateParam;
import club.banyuan.mall.mgt.service.UmsRoleService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UmsRoleServiceImplTest {

    @Autowired
    private UmsRoleService umsRoleService;

    @Test
    public void create(){
        RoleCreateParam roleCreateParam=new RoleCreateParam ();
        roleCreateParam.setName ("test");
        roleCreateParam.setDescription ("test test");
        roleCreateParam.setStatus (1);
        Long role = umsRoleService.createRole (roleCreateParam);
        Assert.assertNotNull (role);
    }

    @Test
    public void allocMenu(){
        String [] arr=new String[]{"8","7","22","21","25"};
        umsRoleService.allocMenu (1L,arr);
    }

    @Test
    public void testRollback(){
        String [] arr=new String[]{"8","7","22","21","25"};
        umsRoleService.testRollback (1L,arr);
    }


}
