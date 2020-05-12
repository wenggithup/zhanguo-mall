package club.banyuan.mall.mgt.service.impl;

import club.banyuan.mall.mgt.common.RequestFailException;
import club.banyuan.mall.mgt.dao.UmsResourceCategoryDao;
import club.banyuan.mall.mgt.dao.entity.UmsResourceCategory;
import club.banyuan.mall.mgt.dao.entity.UmsResourceCategoryExample;
import club.banyuan.mall.mgt.service.UmsResourceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static club.banyuan.mall.mgt.common.FailReason.UMS_RESOURCE_CATEGORYID_NOT_EXIST;
import static club.banyuan.mall.mgt.common.FailReason.UMS_RESOURCE_CATEGORYNAME_EXIST;

@Service
public class UmsResourceCategoryServiceImpl implements UmsResourceCategoryService {
    @Autowired
    private UmsResourceCategoryDao umsResourceCategoryDao;

    @Override
    public List<UmsResourceCategory> ResourceListAll() {
        return umsResourceCategoryDao.ResourceListAll();
    }

    @Override
    public int createCategory(UmsResourceCategory umsResourceCategory) {
        //判断参数是否合法
        UmsResourceCategoryExample umsResourceCategoryExample=new UmsResourceCategoryExample ();
        umsResourceCategoryExample.createCriteria ().andNameEqualTo (umsResourceCategory.getName ())
                ;
        if(umsResourceCategoryDao.countByExample (umsResourceCategoryExample)>0){
            throw new RequestFailException (UMS_RESOURCE_CATEGORYNAME_EXIST);
        }
        //设置创建时间
        umsResourceCategory.setCreateTime (new Date ());
        return umsResourceCategoryDao.insertSelective (umsResourceCategory);
    }

    @Override
    public int updateCategory(UmsResourceCategory umsResourceCategory) {
        //判断参数是否合法
        UmsResourceCategoryExample umsResourceCategoryExample=new UmsResourceCategoryExample ();
        umsResourceCategoryExample.createCriteria ().andNameEqualTo (umsResourceCategory.getName ())
                .andIdNotEqualTo (umsResourceCategory.getId ());
        if(umsResourceCategoryDao.countByExample (umsResourceCategoryExample)>0){
            throw new RequestFailException (UMS_RESOURCE_CATEGORYNAME_EXIST);
        }
        umsResourceCategory.setCreateTime (new Date ());
        return umsResourceCategoryDao.updateByPrimaryKey (umsResourceCategory);
    }

    @Override
    public int deleteCategory(Long categoryId) {
        if(umsResourceCategoryDao.selectByPrimaryKey (categoryId)==null){
            throw new RequestFailException (UMS_RESOURCE_CATEGORYID_NOT_EXIST);
        }
        return umsResourceCategoryDao.deleteByPrimaryKey (categoryId);
    }
}
