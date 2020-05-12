package club.banyuan.mall.mgt.dao;

import club.banyuan.mall.mgt.dao.entity.UmsRoleResourceRelation;
import club.banyuan.mall.mgt.dao.entity.UmsRoleResourceRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UmsRoleResourceRelationDao {
    long countByExample(UmsRoleResourceRelationExample example);

    int deleteByExample(UmsRoleResourceRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UmsRoleResourceRelation record);

    int insertSelective(UmsRoleResourceRelation record);

    List<UmsRoleResourceRelation> selectByExample(UmsRoleResourceRelationExample example);

    UmsRoleResourceRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UmsRoleResourceRelation record, @Param("example") UmsRoleResourceRelationExample example);

    int updateByExample(@Param("record") UmsRoleResourceRelation record, @Param("example") UmsRoleResourceRelationExample example);

    int updateByPrimaryKeySelective(UmsRoleResourceRelation record);

    int updateByPrimaryKey(UmsRoleResourceRelation record);

    List<UmsRoleResourceRelation> selectByRoleId(Long roleId);

    void deleteByRoleId(Long roleId);
}