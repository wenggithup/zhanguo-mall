package club.banyuan.mall.mgt.dao;

import club.banyuan.mall.mgt.dao.entity.UmsResourceCategory;
import club.banyuan.mall.mgt.dao.entity.UmsResourceCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UmsResourceCategoryDao {
    long countByExample(UmsResourceCategoryExample example);

    int deleteByExample(UmsResourceCategoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UmsResourceCategory record);

    int insertSelective(UmsResourceCategory record);

    List<UmsResourceCategory> selectByExample(UmsResourceCategoryExample example);

    UmsResourceCategory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UmsResourceCategory record, @Param("example") UmsResourceCategoryExample example);

    int updateByExample(@Param("record") UmsResourceCategory record, @Param("example") UmsResourceCategoryExample example);

    int updateByPrimaryKeySelective(UmsResourceCategory record);

    int updateByPrimaryKey(UmsResourceCategory record);

    List <UmsResourceCategory> ResourceListAll();
}