package club.banyuan.mall.mgt.controller;
import club.banyuan.mall.mgt.bean.UmsMenuTreeNode;
import club.banyuan.mall.mgt.common.ResponseResult;
import club.banyuan.mall.mgt.service.UmsMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/menu")
public class UmsMenuController {
    @Autowired
    private UmsMenuService umsMenuService;

    @RequestMapping(value = "/treeList",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult treeList(){


        return ResponseResult.success (umsMenuService.treeList());
    }

    //权限/菜单列表展示功能
    @RequestMapping(value = "/list/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult menuList(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize,
                                   @PathVariable("id") Long menuParentId){
        return ResponseResult.success (umsMenuService.MenuList(pageNum,pageSize,menuParentId));

    }
    //权限 菜单列表添加数据
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult createMenu(@RequestBody @Valid UmsMenuTreeNode umsMenuTreeNode){
        return ResponseResult.success (umsMenuService.CreateMenu(umsMenuTreeNode));
    }

    //权限 菜单列表 操作/编辑 数据展示
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult clickMenu(@PathVariable(value = "id") Long MenuId){
        return ResponseResult.success (umsMenuService.MenuClick(MenuId));
    }
    //权限 菜单列表 操作/编辑 表单提交
    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult updateMenu(@RequestBody @Valid UmsMenuTreeNode umsMenuTreeNode){
        return ResponseResult.success (umsMenuService.UpdateMenu(umsMenuTreeNode));
    }

    //权限 菜单列表 操作 /删除
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult deleteMenu(@PathVariable(value = "id") Long menuId){
        return ResponseResult.success (umsMenuService.deleteMenuByMenuId(menuId));
    }

    //权限 菜单列表 修改权限状态
    @RequestMapping(value = "/updateHidden/{id}",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult updateStatus(@RequestParam("hidden") Integer hidden,
                                       @PathVariable(value = "id") Integer menuId){
        return ResponseResult.success (umsMenuService.updateHidden(hidden,menuId));

    }
}
