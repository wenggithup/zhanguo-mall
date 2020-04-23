package club.banyuan.mall.mgt.service.impl;

import club.banyuan.mall.mgt.bean.RoleCreateParam;
import club.banyuan.mall.mgt.bean.RoleResp;
import club.banyuan.mall.mgt.common.RequestFailException;
import club.banyuan.mall.mgt.common.ResponsePages;
import club.banyuan.mall.mgt.dao.UmsMenuDao;
import club.banyuan.mall.mgt.dao.UmsRoleDao;
import club.banyuan.mall.mgt.dao.entity.UmsMenu;
import club.banyuan.mall.mgt.dao.entity.UmsRole;
import club.banyuan.mall.mgt.dao.entity.UmsRoleExample;
import club.banyuan.mall.mgt.service.UmsRoleService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static club.banyuan.mall.mgt.common.FailReason.UMS_ADMIN_ROLEID_NOT_EXIST;
import static club.banyuan.mall.mgt.common.FailReason.UMS_ADMIN_ROLE_EXIST;

@Service
public class UmsRoleServiceImpl implements UmsRoleService {

    @Autowired
    private UmsRoleDao umsRoleDao;

    @Autowired
    private UmsMenuDao umsMenuDao;

    @Override
    public ResponsePages list(Integer pageNum, Integer pageSize, String keyword) {
        //用example生成动态sql
        UmsRoleExample umsRoleExample=new UmsRoleExample ();
        if(keyword!=null) {
            UmsRoleExample.Criteria criteria = umsRoleExample.createCriteria ();
            //添加模糊查询
            criteria.andNameLike (StrUtil.concat (false, "%", keyword, "%"));
        }
        //分页插件
        PageHelper.startPage (pageNum,pageSize);
        List<UmsRole> umsRoles = umsRoleDao.selectByExample (umsRoleExample);
        //实体类返回的pageinfo结果
        PageInfo<UmsRole> pageInfo=new PageInfo<> (umsRoles);

        //将实体类的list 转换成回复请求类
        List<RoleResp> list = umsRoles.stream ().map (t -> {
            RoleResp roleResp = new RoleResp ();
            BeanUtil.copyProperties (t, roleResp);
            return roleResp;
        }).collect (Collectors.toList ());

        //调用方法返回ResponsePages类
        return ResponsePages.listByPageInfo (pageInfo,list);
    }

    @Override
    public Long createRole(RoleCreateParam roleCreateParam) {
        UmsRoleExample umsRoleExample=new UmsRoleExample ();
        UmsRoleExample.Criteria criteria = umsRoleExample.createCriteria ().andNameEqualTo (roleCreateParam.getName ());
        if(umsRoleDao.countByExample (umsRoleExample)>0){
            throw new RequestFailException (UMS_ADMIN_ROLE_EXIST);
        }

        UmsRole umsRole=new UmsRole ();
        BeanUtil.copyProperties (roleCreateParam,umsRole);
        umsRole.setCreateTime (new Date ());
        umsRole.setSort (0);
        umsRoleDao.insert (umsRole);

        return umsRole.getId ();
    }

    @Override
    public Long updateRole(RoleCreateParam roleCreateParam) {
        UmsRoleExample umsRoleExample=new UmsRoleExample ();
        UmsRoleExample.Criteria criteria = umsRoleExample.createCriteria ().andNameEqualTo (roleCreateParam.getName ())
                .andIdEqualTo (roleCreateParam.getId ());
        if(umsRoleDao.countByExample (umsRoleExample)>0){
            throw new RequestFailException (UMS_ADMIN_ROLE_EXIST);
        }
        UmsRole umsRole=new UmsRole ();
        umsRole.setName (roleCreateParam.getName ());
        umsRole.setId (roleCreateParam.getId ());
        umsRole.setDescription (roleCreateParam.getDescription ());
        umsRole.setStatus (roleCreateParam.getStatus ());

        umsRoleDao.updateByPrimaryKeySelective (umsRole);

        return umsRole.getId ();
    }

    @Override
    public Long delete(Long ids) {
        if(umsRoleDao.deleteByPrimaryKey (ids)<=0){
            throw new RequestFailException (UMS_ADMIN_ROLEID_NOT_EXIST);
        }
        return ids;
    }

    @Override
    public List<UmsMenu> listMenu(Long id) {
        return umsMenuDao.selectByRoleByIds (Collections.singletonList (id));
    }


}
