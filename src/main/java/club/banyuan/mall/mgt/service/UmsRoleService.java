package club.banyuan.mall.mgt.service;

import club.banyuan.mall.mgt.bean.RoleCreateParam;
import club.banyuan.mall.mgt.common.ResponsePages;
import club.banyuan.mall.mgt.dao.entity.UmsMenu;
import club.banyuan.mall.mgt.dao.entity.UmsResource;
import club.banyuan.mall.mgt.dao.entity.UmsRole;
import club.banyuan.mall.mgt.dao.entity.UmsRoleResourceRelation;

import java.util.List;

public interface UmsRoleService {
    ResponsePages list(Integer pageNum, Integer pageSize, String keyword);

    Long createRole(RoleCreateParam roleCreateParam);

    Long updateRole(RoleCreateParam roleCreateParam);

    Long delete(Long ids);

    List<UmsMenu> listMenu(Long id);

    void allocMenu(Long roleId, String[] split);

    void testRollback(Long roleId, String[] split);

    List<UmsRole> listAll();

    List<UmsRole> selectRoleByAdminId(Long adminId);

    List<UmsRole> selectByRoleId(Long roleId);


    int updateStatus(Integer status,Integer roleId);

    List<UmsResource> showRoleResource(Long roleId);

    int allocResource(Long roleId, String resourceIdStr);
}
