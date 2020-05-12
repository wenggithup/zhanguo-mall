package club.banyuan.mall.mgt.service;

import club.banyuan.mall.mgt.common.ResponsePages;
import club.banyuan.mall.mgt.dao.entity.UmsResource;
import club.banyuan.mall.mgt.dao.entity.UmsResourceCategory;

import java.util.List;


public interface UmsResourceService {

    ResponsePages resourceList(Integer pageNum, Integer pageSize, String nameKeyword, String urlKeyword, Integer categoryId);

    int CreateResource(UmsResource umsResource);

    int updateResource(UmsResource umsResource);

    int deleteResourceByResourceId(Long resourceId);

    List<UmsResource> listAll();

    List<UmsResource> selectResourceByRoleId(Long roleId);
}
