package club.banyuan.mall.mgt.service;

import club.banyuan.mall.mgt.dao.entity.UmsResourceCategory;

import java.util.List;

public interface UmsResourceCategoryService {
    List<UmsResourceCategory> ResourceListAll();

    int createCategory(UmsResourceCategory umsResourceCategory);

    int updateCategory(UmsResourceCategory umsResourceCategory);

    int deleteCategory(Long categoryId);
}
