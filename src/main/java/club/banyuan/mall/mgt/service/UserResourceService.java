package club.banyuan.mall.mgt.service;

import club.banyuan.mall.mgt.dao.entity.UmsResource;

import java.util.List;

public interface UserResourceService {
    List<UmsResource> getAllUmsResource();

    List<UmsResource> getResouceByAdminId(Long adminId);
}
