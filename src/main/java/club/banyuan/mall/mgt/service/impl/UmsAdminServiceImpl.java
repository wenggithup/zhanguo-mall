package club.banyuan.mall.mgt.service.impl;

import club.banyuan.mall.mgt.bean.AdminCreateParam;
import club.banyuan.mall.mgt.bean.AdminResp;
import club.banyuan.mall.mgt.bean.RoleResp;
import club.banyuan.mall.mgt.common.RequestFailException;
import club.banyuan.mall.mgt.common.ResponsePages;
import club.banyuan.mall.mgt.dao.UmsAdminDao;
import club.banyuan.mall.mgt.dao.entity.UmsAdmin;
import club.banyuan.mall.mgt.dao.entity.UmsAdminExample;
import club.banyuan.mall.mgt.dao.entity.UmsAdminRoleRelation;
import club.banyuan.mall.mgt.dao.entity.UmsRole;
import club.banyuan.mall.mgt.service.UmsAdminService;
import club.banyuan.mall.mgt.service.UmsRoleService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static club.banyuan.mall.mgt.common.FailReason.*;

/*
* 用户列表服务实现类
* */
@Service
public class UmsAdminServiceImpl implements UmsAdminService {
    @Autowired
    private UmsAdminDao umsAdminDao;

    @Autowired
    private UmsRoleService umsRoleService;
    //分页展示用户列表
    @Override
    public ResponsePages list(Integer pageNum, Integer pageSize, String keyword) {

        UmsAdminExample umsAdminExample=new UmsAdminExample ();
        //1、如果关键词不为空，则添加模糊查询
        if(keyword!=null) {
            UmsAdminExample.Criteria criteria = umsAdminExample.createCriteria ();
            //添加模糊查询
            criteria.andUsernameLike (StrUtil.concat (false, "%", keyword, "%"));
        }
        //2、开启分页
        PageHelper.startPage (pageNum,pageSize);
        //3、调用dao层方法，返回查询结果集
        List<UmsAdmin> umsAdmins = umsAdminDao.selectByExample (umsAdminExample);

        PageInfo<UmsAdmin> pageInfo=new PageInfo<> (umsAdmins);
        //4、将实体类字段转换成回复类
        List<AdminResp> list=new ArrayList<> ();
        for (UmsAdmin umsAdmin : umsAdmins) {
            AdminResp adminResp=new AdminResp ();
            BeanUtil.copyProperties (umsAdmin,adminResp);
            list.add (adminResp);

        }
        return  ResponsePages.listByPageInfo (pageInfo,list);
    }

    //添加用户列表功能
    @Override
    public UmsAdmin registerUmsAdmin(AdminCreateParam adminCreateParam) {
        //判断该用户是否存在，如果存在，则抛出用户已存在异常
        UmsAdminExample umsAdminExample=new UmsAdminExample ();
        UmsAdminExample.Criteria criteria = umsAdminExample.createCriteria ().
                andUsernameEqualTo (adminCreateParam.getUsername ());
        if(umsAdminDao.countByExample (umsAdminExample)>0){
            throw new RequestFailException(UMS_ADMIN_USERNAME_EXIST);
        }
        //将请求类转换成实现类并插入
        UmsAdmin umsAdmin=new UmsAdmin ();
        BeanUtil.copyProperties (adminCreateParam,umsAdmin);
        umsAdmin.setCreateTime (new Date ());
        umsAdminDao.insert (umsAdmin);
        return umsAdmin;

    }

    //编辑用户，修改用户信息功能
    @Override
    public Long update(AdminCreateParam adminCreateParam) {
        UmsAdminExample umsAdminExample=new UmsAdminExample ();
        UmsAdminExample.Criteria criteria = umsAdminExample.createCriteria ().andUsernameEqualTo (adminCreateParam.getUsername ())
                .andIdNotEqualTo(adminCreateParam.getId ());
        if(umsAdminDao.countByExample (umsAdminExample)>0){
            throw new RequestFailException(UMS_ADMIN_USERNAME_EXIST);
        }
        UmsAdmin umsAdmin=new UmsAdmin ();
        BeanUtil.copyProperties (adminCreateParam,umsAdmin);
        umsAdmin.setCreateTime (new Date ());
        umsAdminDao.updateByPrimaryKeySelective (umsAdmin);
        return umsAdmin.getId ();
    }
    //删除用户
    @Override
    public int delete(Long id) {
       if(umsAdminDao.deleteByPrimaryKey (id)<=0){
           throw new RequestFailException (UMS_ADMIN_ADMINID_NOT_EXIST);
       }
       return umsAdminDao.deleteByPrimaryKey (id);
    }
    //展示用户角色
    @Override
    public  List<RoleResp> adminRole(Long adminId) {
        //判断adminid是否合法,如果不合法则抛出异常
       if(umsAdminDao.selectByPrimaryKey (adminId)==null){
           throw new RequestFailException (UMS_ADMIN_ADMINID_NOT_EXIST);
       }
       //通过adminId展示role表中的数据
       List<UmsRole> roles= umsRoleService.selectRoleByAdminId(adminId);
       List<RoleResp> roleResps=new ArrayList<> ();
        for (UmsRole role : roles) {
            RoleResp roleResp=new RoleResp ();
            BeanUtil.copyProperties (role,roleResp);
            roleResps.add (roleResp);
        }

       return roleResps;
    }

    //更新用户角色功能
    @Override
    public int updateRoleByAdminId(Long adminId, String roleIdStr) {
        //1、判断adminId是否合法
        if(umsAdminDao.selectByPrimaryKey (adminId)==null){
            throw new RequestFailException (UMS_ADMIN_ADMINID_NOT_EXIST);
        }
        //2、判断roleIdStr是否合法
        String[] split = roleIdStr.split (",");
        //2、1判断split是否为空，如果为空，则前台传入的数据不合法
        if (ArrayUtil.isEmpty (split)){
            throw new RequestFailException (UMS_ADMIN_ROLEID_NOT_EXIST);
        }
        List<Long> roleIds=new ArrayList<> ();
        for (String s : split) {
            Long roleId = Long.valueOf (s);
            //如果roleId不合法，则抛出roleId不存在异常
            if(CollUtil.isEmpty (umsRoleService.selectByRoleId(roleId))) {
                throw new RequestFailException (UMS_ADMIN_ROLEID_NOT_EXIST);
            }
            roleIds.add (roleId);
        }
        //3、先删除adminId和roleId之间的关系，在重新添加
        umsAdminDao.deleteByAdminId(adminId);

        for (Long roleId : roleIds) {
            UmsAdminRoleRelation umsAdminRoleRelation=new UmsAdminRoleRelation ();
            umsAdminRoleRelation.setAdminId (adminId);
            umsAdminRoleRelation.setRoleId (roleId);
            umsAdminDao.insertUmsAdminRoleRelation(umsAdminRoleRelation);
        }
        return split.length;
    }

    @Override
    public int updateStatus(Integer status, Integer adminId) {
        return umsAdminDao.updateByStatus(status,adminId);
    }


}
