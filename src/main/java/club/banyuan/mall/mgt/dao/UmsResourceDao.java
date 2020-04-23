package club.banyuan.mall.mgt.dao;

import club.banyuan.mall.mgt.dao.entity.UmsResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UmsResourceDao {
    int deleteByPrimaryKey(Long id);

    int insert(UmsResource record);

    int insertSelective(UmsResource record);

    UmsResource selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UmsResource record);

    int updateByPrimaryKey(UmsResource record);

    List <UmsResource> selectAll();

    List<UmsResource> selectResourceByAdminId(Long adminId);
}