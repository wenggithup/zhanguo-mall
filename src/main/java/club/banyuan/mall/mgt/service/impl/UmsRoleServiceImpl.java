package club.banyuan.mall.mgt.service.impl;

import club.banyuan.mall.mgt.bean.RoleResp;
import club.banyuan.mall.mgt.common.ResponsePages;
import club.banyuan.mall.mgt.dao.UmsRoleDao;
import club.banyuan.mall.mgt.dao.entity.UmsRole;
import club.banyuan.mall.mgt.dao.entity.UmsRoleExample;
import club.banyuan.mall.mgt.service.UmsRoleService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UmsRoleServiceImpl implements UmsRoleService {
    @Autowired
    private UmsRoleDao umsRoleDao;
    @Override
    public ResponsePages list(Integer pageNum, Integer pageSize, String keyword) {
        //用example生成动态sql
        UmsRoleExample umsRoleExample=new UmsRoleExample ();
        if(keyword!=null) {
            UmsRoleExample.Criteria criteria = umsRoleExample.createCriteria ();
            //添加模糊查询
            criteria.andNameLike (StrUtil.concat (false, "%", keyword, "%"));
        }
        //分页插件
        PageHelper.startPage (pageNum,pageSize);
        List<UmsRole> umsRoles = umsRoleDao.selectByExample (umsRoleExample);
        //实体类返回的pageinfo结果
        PageInfo<UmsRole> pageInfo=new PageInfo<> (umsRoles);

        //将实体类的list 转换成回复请求类
        List<RoleResp> list = umsRoles.stream ().map (t -> {
            RoleResp roleResp = new RoleResp ();
            BeanUtil.copyProperties (t, roleResp);
            return roleResp;
        }).collect (Collectors.toList ());

        //调用方法返回ResponsePages类
        return ResponsePages.listByPageInfo (pageInfo,list);
    }


}
