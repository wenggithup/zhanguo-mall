package club.banyuan.mall.mgt.service;

import club.banyuan.mall.mgt.dao.entity.UmsRoleResourceRelation;

import java.util.List;

public interface UmsRoleResourceRelationService {
    List<UmsRoleResourceRelation> selectByRoleId(Long roleId);

    void deleteByRoleId(Long roleId);

    int insertResource(UmsRoleResourceRelation umsRoleResourceRelation);
}
