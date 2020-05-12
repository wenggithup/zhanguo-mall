package club.banyuan.mall.mgt.service.impl;

import club.banyuan.mall.mgt.bean.RoleCreateParam;
import club.banyuan.mall.mgt.bean.RoleResp;
import club.banyuan.mall.mgt.bean.UmsMenuTreeNode;
import club.banyuan.mall.mgt.common.RequestFailException;
import club.banyuan.mall.mgt.common.ResponsePages;
import club.banyuan.mall.mgt.dao.UmsMenuDao;
import club.banyuan.mall.mgt.dao.UmsRoleDao;
import club.banyuan.mall.mgt.dao.entity.*;
import club.banyuan.mall.mgt.service.UmsMenuService;
import club.banyuan.mall.mgt.service.UmsResourceService;
import club.banyuan.mall.mgt.service.UmsRoleResourceRelationService;
import club.banyuan.mall.mgt.service.UmsRoleService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static club.banyuan.mall.mgt.common.FailReason.*;

@Service
public class UmsRoleServiceImpl implements UmsRoleService {

    @Autowired
    private UmsRoleDao umsRoleDao;

    @Autowired
    private UmsMenuDao umsMenuDao;

    @Autowired
    private UmsMenuService umsMenuService;

    @Autowired
    private UmsResourceService umsResourceService;
    @Autowired
    private UmsRoleResourceRelationService umsRoleResourceRelationService;

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
                .andIdNotEqualTo (roleCreateParam.getId ());
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

    @Transactional
    @Override
    public void allocMenu(Long roleId, String[] split) {

        List<Long> menuIds=new ArrayList<> ();
        //1、将String数组转换成Long类型数组
        if(split!=null){
            for (String s : split) {
                menuIds.add (Long.valueOf (s));
            }
        }else {
            throw new RequestFailException (UMS_ADMIN_ROLE_MENU_NOT_EXIST);
        }
        //2、判断roleId是否合法
        UmsRoleExample umsRoleExample=new UmsRoleExample ();
        UmsRoleExample.Criteria criteria = umsRoleExample.createCriteria ().andIdEqualTo (roleId);
        if(umsRoleDao.countByExample (umsRoleExample)<=0){
            throw new RequestFailException (UMS_ADMIN_ROLEID_NOT_EXIST);
        }
        List<UmsMenuTreeNode> list = umsMenuService.treeList ();
        //利用SET集合来判断参数是否合法
        //4、1将menuIds集合转换成Set集合
        Set<Long> menuIdsSet=new HashSet<> (menuIds);
        for (UmsMenuTreeNode umsMenuTreeNode : list) {
            if(menuIdsSet.contains (umsMenuTreeNode.getId ())){
                //如果父级id存在，则删除所有的子集和本身
                //4、2将子集id集合遍历出来
                List<UmsMenuTreeNode> children = umsMenuTreeNode.getChildren ();
                List<Long> childIds=new ArrayList<> ();
                for (UmsMenuTreeNode child : children) {
                    childIds.add (child.getId ());
                }
                menuIdsSet.removeAll (childIds);
                menuIdsSet.remove (umsMenuTreeNode.getId ());
            }
        }
        if(CollUtil.isNotEmpty (menuIdsSet)){
            throw new RequestFailException (UMS_ROLE_MENU_REL_ILLEGAL);
        }
        //先删除角色权限，在重新给权限
        umsRoleDao.deleteRoleMenuRelationByRoleId (roleId);
        for (Long menuId : menuIds) {
            UmsRoleMenuRelation umsRoleMenuRelation=new UmsRoleMenuRelation ();
            umsRoleMenuRelation.setRoleId (roleId);
            umsRoleMenuRelation.setMenuId (menuId);
            umsRoleDao.insertRoleMenuRelationByRoleId (umsRoleMenuRelation);
        }



/*
        //3、判断前台数组是否合法
        //  3、1 拿到treelist集合

        for (UmsMenuTreeNode umsMenuTreeNode : list) {
            for (Long menuId : menuIds) {
                //表示menuid是父亲节点
                if(umsMenuTreeNode.getId ()==Long.valueOf (menuId)){
                    break;
                }else {
                    //如果menuId是子节点，还需判断父节点是否合法
                    List<UmsMenuTreeNode> children = umsMenuTreeNode.getChildren ();
                    for (UmsMenuTreeNode child : children) {
                        if(child.getId ().longValue ()==menuId){
                            if(!menuIds.contains (umsMenuTreeNode.getId ())){
                                throw new RequestFailException (UMS_ROLE_MENU_REL_ILLEGAL);
                            }else {
                                break;
                            }
                        }
                    }
                    throw new RequestFailException (UMS_ROLE_MENU_REL_ILLEGAL);
                }
            }
        }*/
    }

    @Override
    public void testRollback(Long roleId, String[] split){
        allocMenu (roleId,split);

    }

    @Override
    public List<UmsRole> listAll() {
        return umsRoleDao.selectListAll();
    }

    @Override
    public  List<UmsRole>  selectRoleByAdminId(Long adminId) {
        return umsRoleDao.selectRoleByAdminId (adminId);
    }

    @Override
    public List<UmsRole> selectByRoleId(Long roleId) {
        UmsRoleExample umsRoleExample=new UmsRoleExample ();
        UmsRoleExample.Criteria criteria = umsRoleExample.createCriteria ().andIdEqualTo (roleId);

        return umsRoleDao.selectByExample (umsRoleExample);
    }

    @Override
    public int updateStatus(Integer status,Integer roleId) {

        return umsRoleDao.updateByStatus(status,roleId);
    }

    @Override
    public List<UmsResource> showRoleResource(Long roleId) {
        //判断roleId是否合法
        if(umsRoleDao.selectByPrimaryKey (roleId)==null){
            throw new RequestFailException (UMS_ADMIN_ROLE_EMPTY);

        }
       return umsResourceService.selectResourceByRoleId (roleId);
    }

    @Override
    public int allocResource(Long roleId, String resourceIdStr) {
        //将resourceIdStr转换成resoucesId数组
        String[] split = resourceIdStr.split (",");
        List<Long> resourceIds=new ArrayList<> ();
        for (String s : split) {
            resourceIds.add (Long.valueOf (s));
        }
        //判断roleId是否合法
        UmsRoleExample umsRoleExample=new UmsRoleExample ();
        UmsRoleExample.Criteria criteria = umsRoleExample.createCriteria ().andIdEqualTo (roleId);
        if(umsRoleDao.countByExample (umsRoleExample)<=0){
            throw new RequestFailException (UMS_ADMIN_ROLEID_NOT_EXIST);
        }
        //判断resourceIds是否合法
        List<UmsResource> umsResources = umsResourceService.listAll ();
        List<Long> umsResourceIds=new ArrayList<> ();
        for (UmsResource umsResource : umsResources) {
            umsResourceIds.add (umsResource.getId ());
        }
        for (Long resourceId : resourceIds) {
            if(!umsResourceIds.contains (resourceId) && resourceIds!=null){
                throw new RequestFailException (UMS_ROLE_RESOURCE_ID_NOT_EXIST);
            }
        }

        //先删除关系，再添加
        umsRoleResourceRelationService.deleteByRoleId(roleId);
        for (Long resourceId : resourceIds) {
            UmsRoleResourceRelation umsRoleResourceRelation=new UmsRoleResourceRelation ();
            umsRoleResourceRelation.setRoleId (roleId);
            umsRoleResourceRelation.setResourceId (resourceId);
            umsRoleResourceRelationService.insertResource(umsRoleResourceRelation);
        }
        return resourceIds.size ();
    }


}
