package club.banyuan.mall.mgt.service.impl;

import club.banyuan.mall.mgt.dao.UmsResourceDao;
import club.banyuan.mall.mgt.dao.entity.UmsResource;
import club.banyuan.mall.mgt.service.UserResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserResourceServiceImpl implements UserResourceService {
    @Autowired
    private UmsResourceDao umsResourceDao;
    @Override
    public List<UmsResource> getAllUmsResource() {
        return umsResourceDao.selectAll ();
    }
    @Override
    public List<UmsResource> getResouceByAdminId(Long adminId) {
        return umsResourceDao.selectResourceByAdminId(adminId);
    }

}
