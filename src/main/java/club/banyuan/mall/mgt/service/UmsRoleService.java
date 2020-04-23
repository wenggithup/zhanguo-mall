package club.banyuan.mall.mgt.service;

import club.banyuan.mall.mgt.bean.RoleCreateParam;
import club.banyuan.mall.mgt.common.ResponsePages;
import club.banyuan.mall.mgt.dao.entity.UmsMenu;

import java.util.List;

public interface UmsRoleService {
    ResponsePages list(Integer pageNum, Integer pageSize, String keyword);

    Long createRole(RoleCreateParam roleCreateParam);

    Long updateRole(RoleCreateParam roleCreateParam);

    Long delete(Long ids);

    List<UmsMenu> listMenu(Long id);
}
