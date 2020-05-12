package club.banyuan.mall.mgt.dao;

import club.banyuan.mall.mgt.dao.entity.UmsResource;
import club.banyuan.mall.mgt.dao.entity.UmsResourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UmsResourceDao {
    long countByExample(UmsResourceExample example);

    int deleteByExample(UmsResourceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UmsResource record);

    int insertSelective(UmsResource record);

    List<UmsResource> selectByExample(UmsResourceExample example);

    UmsResource selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UmsResource record, @Param("example") UmsResourceExample example);

    int updateByExample(@Param("record") UmsResource record, @Param("example") UmsResourceExample example);

    int updateByPrimaryKeySelective(UmsResource record);

    int updateByPrimaryKey(UmsResource record);

    List <UmsResource> selectAll();

    List<UmsResource> selectResourceByAdminId(Long adminId);

    List<UmsResource> selectResourceByRoleId(Long roleId);
}