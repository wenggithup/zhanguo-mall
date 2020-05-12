package club.banyuan.mall.mgt.service;

import club.banyuan.mall.mgt.bean.UmsMenuTreeNode;
import club.banyuan.mall.mgt.common.ResponsePages;


import java.util.List;

public interface UmsMenuService {
    List<UmsMenuTreeNode> treeList();

    ResponsePages MenuList(Integer pageNum, Integer pageSize,Long menuParentId);

    int CreateMenu(UmsMenuTreeNode umsMenuTreeNode);

    UmsMenuTreeNode MenuClick(Long menuId);

    int UpdateMenu(UmsMenuTreeNode umsMenuTreeNode);

    int deleteMenuByMenuId(Long menuId);

    int updateHidden(Integer hidden, Integer menuId);
}
