package club.banyuan.mall.mgt.service.impl;

import club.banyuan.mall.mgt.dao.UmsRoleResourceRelationDao;
import club.banyuan.mall.mgt.dao.entity.UmsRoleResourceRelation;
import club.banyuan.mall.mgt.service.UmsRoleResourceRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UmsRoleResourceRelationServiceImpl implements UmsRoleResourceRelationService {
    @Autowired
    private UmsRoleResourceRelationDao umsRoleResourceRelationDao;
    @Override
    public List<UmsRoleResourceRelation> selectByRoleId(Long roleId) {
        return umsRoleResourceRelationDao.selectByRoleId(roleId);
    }

    @Override
    public void deleteByRoleId(Long roleId) {
        umsRoleResourceRelationDao.deleteByRoleId(roleId);
    }

    @Override
    public int insertResource(UmsRoleResourceRelation umsRoleResourceRelation) {
        return umsRoleResourceRelationDao.insertSelective (umsRoleResourceRelation);
    }
}
