package club.banyuan.mall.mgt.service.impl;

import club.banyuan.mall.mgt.bean.UmsMenuTreeNode;
import club.banyuan.mall.mgt.common.RequestFailException;
import club.banyuan.mall.mgt.common.ResponsePages;
import club.banyuan.mall.mgt.dao.UmsMenuDao;
import club.banyuan.mall.mgt.dao.entity.UmsMenu;
import club.banyuan.mall.mgt.service.UmsMenuService;
import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static club.banyuan.mall.mgt.common.FailReason.UMS_MENU__MENUID_NOT_EXIST;

@Service
public class UmsMenuServiceImpl implements UmsMenuService {
    @Autowired
    private UmsMenuDao umsMenuDao;


    @Override
    public List<UmsMenuTreeNode> treeList() {
        List<UmsMenu> umsMenus = umsMenuDao.selectAll ();
        List<UmsMenuTreeNode> parentlist=new ArrayList<> ();
        for (UmsMenu umsMenu : umsMenus) {
           if(umsMenu.getParentId ()==0){
               UmsMenuTreeNode umsMenuTreeNode=new UmsMenuTreeNode ();
               BeanUtil.copyProperties (umsMenu,umsMenuTreeNode);
               umsMenuTreeNode.setChildren (new ArrayList<> ());
               parentlist.add (umsMenuTreeNode);
           }
        }

        for (UmsMenuTreeNode parent : parentlist) {
            for (UmsMenu children : umsMenus) {
                if(parent.getId ()==children.getParentId ()){
                    UmsMenuTreeNode umsMenuTreeNode=new UmsMenuTreeNode ();
                    BeanUtil.copyProperties (children,umsMenuTreeNode);
                    parent.getChildren ().add (umsMenuTreeNode);
                }
            }
        }

        return parentlist;
    }
    //展示权限 菜单列表
    @Override
    public ResponsePages MenuList(Integer pageNum, Integer pageSize,Long menuParentId) {
            UmsMenuIdisValid(menuParentId);

            //1、开启分页插件
            PageHelper.startPage (pageNum,pageSize);
            //2、获取所有父级目录
            List<UmsMenu> umsMenus = umsMenuDao.selectByParentId (menuParentId);
            //3、将查询出来的结果分页
            PageInfo<UmsMenu> pageInfo=new PageInfo<> (umsMenus);
            ResponsePages responsePages = ResponsePages.listByPageInfo (pageInfo, umsMenus);
            return responsePages;





    }
    // 新增菜单
    @Override
    public int CreateMenu(UmsMenuTreeNode umsMenuTreeNode) {
        //判断传参是否合法，获取所有的父级id
        UmsMenuIdisValid (umsMenuTreeNode.getId ());
        //插入menu
        UmsMenu umsMenu=new UmsMenu ();
        BeanUtil.copyProperties (umsMenuTreeNode,umsMenu);
        return umsMenuDao.insert (umsMenu);

    }
    //编辑菜单显示
    @Override
    public UmsMenuTreeNode MenuClick(Long MenuId) {
        UmsMenuIdisValid(MenuId);

        UmsMenu umsMenu = umsMenuDao.selectByPrimaryKey (MenuId);
        UmsMenuTreeNode umsMenuTreeNode=new UmsMenuTreeNode ();
        BeanUtil.copyProperties (umsMenu,umsMenuTreeNode);
        return umsMenuTreeNode;
    }
    //编辑菜单功能
    @Override
    public int UpdateMenu(UmsMenuTreeNode umsMenuTreeNode) {
        //判断传参是否合法
        UmsMenuIdisValid (umsMenuTreeNode.getId ());
        UmsMenu umsMenu=new UmsMenu ();
        BeanUtil.copyProperties (umsMenuTreeNode,umsMenu);
        return   umsMenuDao.updateByPrimaryKeySelective (umsMenu);

    }
    //删除菜单功能
    @Override
    public int deleteMenuByMenuId(Long menuId) {

        return umsMenuDao.deleteByPrimaryKey (menuId);
    }

    @Override
    public int updateHidden(Integer hidden, Integer menuId) {
        return umsMenuDao.updateHidden(hidden,menuId);
    }

    private void UmsMenuIdisValid(Long MenuId) {
        List<UmsMenu> umsMenus = umsMenuDao.selectAll();
        List<Long> allMenuIds = new ArrayList<> ();
        for (UmsMenu umsMenu : umsMenus) {
            allMenuIds.add (umsMenu.getId ());
        }
        if (!allMenuIds.contains (MenuId) && MenuId!=0) {
            throw new RequestFailException (UMS_MENU__MENUID_NOT_EXIST);
        }
    }
}
