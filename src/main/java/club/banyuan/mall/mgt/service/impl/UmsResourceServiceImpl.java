package club.banyuan.mall.mgt.service.impl;

import club.banyuan.mall.mgt.common.RequestFailException;
import club.banyuan.mall.mgt.common.ResponsePages;
import club.banyuan.mall.mgt.dao.UmsResourceDao;
import club.banyuan.mall.mgt.dao.entity.UmsResource;
import club.banyuan.mall.mgt.dao.entity.UmsResourceCategory;
import club.banyuan.mall.mgt.dao.entity.UmsResourceExample;
import club.banyuan.mall.mgt.service.UmsResourceService;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static club.banyuan.mall.mgt.common.FailReason.*;

@Service
public class UmsResourceServiceImpl implements UmsResourceService {
    @Autowired
    private UmsResourceDao umsResourceDao;

    //权限 资源列表 展示数据
    @Override
    public ResponsePages resourceList(Integer pageNum, Integer pageSize,
                                                   String nameKeyword, String urlKeyword,
                                                   Integer categoryId) {
        //开启分页插件
        PageHelper.startPage (pageNum,pageSize);
        UmsResourceExample umsResourceExample=new UmsResourceExample ();
        if(nameKeyword!=null && urlKeyword !=null && categoryId!=null){
            //添加查询条件
            umsResourceExample.createCriteria ().andNameLike (StrUtil.concat (false,"%",nameKeyword,"%"))
                    .andUrlLike (StrUtil.concat (false,"%",urlKeyword,"%"))
                    .andCategoryIdEqualTo (categoryId.longValue ());


        }else if(nameKeyword!=null && urlKeyword ==null && categoryId!=null){
            umsResourceExample.createCriteria ().andNameLike (StrUtil.concat (false,"%",nameKeyword,"%"))
                    .andCategoryIdEqualTo (categoryId.longValue ());
        }else if(nameKeyword!=null && urlKeyword ==null && categoryId==null){
            umsResourceExample.createCriteria ().andNameLike (StrUtil.concat (false,"%",nameKeyword,"%"));
        }else if(nameKeyword!=null && urlKeyword !=null && categoryId==null){
            umsResourceExample.createCriteria ().andNameLike (StrUtil.concat (false,"%",nameKeyword,"%"))
                    .andUrlLike (StrUtil.concat (false,"%",urlKeyword,"%"));
        }else if(nameKeyword==null && urlKeyword !=null && categoryId!=null){
            umsResourceExample.createCriteria ()
                    .andUrlLike (StrUtil.concat (false,"%",urlKeyword,"%"))
                    .andCategoryIdEqualTo (categoryId.longValue ());

        }else if(nameKeyword==null && urlKeyword !=null && categoryId==null){
            umsResourceExample.createCriteria ()
                    .andUrlLike (StrUtil.concat (false,"%",urlKeyword,"%"));

        }else if(nameKeyword==null && urlKeyword ==null && categoryId!=null){
            umsResourceExample.createCriteria ()
                    .andCategoryIdEqualTo (categoryId.longValue ());
        }else {

        }

        List<UmsResource> umsResources=umsResourceDao.selectByExample (umsResourceExample);
        PageInfo<UmsResource> pageInfo=new PageInfo<> (umsResources);

       return  ResponsePages.listByPageInfo (pageInfo,umsResources);




    }

    @Override
    public int CreateResource(UmsResource umsResource) {
        //判断资源是否合法
        CategoryIdIsVaild (umsResource);
        //判断name和url是否存在，如果存在则抛出异常
        UmsResourceExample umsResourceExample=new UmsResourceExample ();
        umsResourceExample.createCriteria ().andNameEqualTo (umsResource.getName ())
                .andUrlEqualTo (umsResource.getUrl ());
        if(umsResourceDao.countByExample (umsResourceExample)>0){
            throw  new RequestFailException (UMS_RESOURCE_NAMEANDURL_EXIST);
        }
        //设置创建时间
        umsResource.setCreateTime (new Date ());
        return umsResourceDao.insert (umsResource);
    }

    @Override
    public int updateResource(UmsResource umsResource) {
        //判断数据是否合法
        CategoryIdIsVaild (umsResource);
        UmsResourceExample umsResourceExample=new UmsResourceExample ();
        umsResourceExample.createCriteria ().andNameEqualTo (umsResource.getName ())
                .andUrlEqualTo (umsResource.getUrl ())
                .andIdNotEqualTo (umsResource.getId ());
        if(umsResourceDao.countByExample (umsResourceExample)>0){
            throw  new RequestFailException (UMS_RESOURCE_NAMEANDURL_EXIST);
        }
        //设置创建时间
        umsResource.setCreateTime (new Date ());
       return umsResourceDao.updateByPrimaryKeySelective (umsResource);
    }

    @Override
    public int deleteResourceByResourceId(Long resourceId) {
        if(umsResourceDao.selectByPrimaryKey (resourceId)==null){
            throw  new RequestFailException (UMS_RESOURCE_ID_NOT_EXIST);
        }
        return umsResourceDao.deleteByPrimaryKey (resourceId);
    }

    @Override
    public List<UmsResource> listAll() {
       return umsResourceDao.selectAll ();
    }

    @Override
    public List<UmsResource> selectResourceByRoleId(Long roleId) {
        return umsResourceDao.selectResourceByRoleId(roleId);
    }

    private void CategoryIdIsVaild(UmsResource umsResource) {
        List<UmsResource> umsResources = umsResourceDao.selectAll ();
        List<Long> categoryIds = new ArrayList<> ();
        for (UmsResource resource : umsResources) {
            categoryIds.add (resource.getCategoryId ());
        }
        if (!categoryIds.contains (umsResource.getCategoryId ())) {
            throw new RequestFailException (UMS_RESOURCE_CATEGORYID_NOT_EXIST);
        }
    }
}
