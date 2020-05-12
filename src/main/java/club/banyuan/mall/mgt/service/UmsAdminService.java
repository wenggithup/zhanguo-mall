package club.banyuan.mall.mgt.service;

import club.banyuan.mall.mgt.bean.AdminCreateParam;
import club.banyuan.mall.mgt.bean.RoleResp;
import club.banyuan.mall.mgt.common.ResponsePages;
import club.banyuan.mall.mgt.dao.entity.UmsAdmin;

import java.util.List;

public interface UmsAdminService {
    ResponsePages list(Integer pageNum,Integer pageSize,String keyword);

    UmsAdmin registerUmsAdmin(AdminCreateParam adminCreateParam);

    Long update(AdminCreateParam adminCreateParam);

    int delete(Long id);

    List<RoleResp> adminRole(Long adminId);

    int updateRoleByAdminId(Long adminId, String roleIdStr);

    int updateStatus(Integer status, Integer adminId);
}
