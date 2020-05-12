package club.banyuan.mall.mgt.controller;

import club.banyuan.mall.mgt.bean.RoleCreateParam;
import club.banyuan.mall.mgt.common.ResponsePages;
import club.banyuan.mall.mgt.common.ResponseResult;
import club.banyuan.mall.mgt.dao.entity.UmsMenu;
import club.banyuan.mall.mgt.service.UmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
@RequestMapping("/role")
public class UmsRoleController {
    @Autowired
    private UmsRoleService umsRoleService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult list(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam(required = false) String keyword){
        ResponsePages list = umsRoleService.list (pageNum, pageSize, keyword);

        return  ResponseResult.success (list);

    }
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult list(@RequestBody @Valid RoleCreateParam roleCreateParam){
        Long adminId=umsRoleService.createRole(roleCreateParam);

        return  ResponseResult.success (adminId);

    }
    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult update(@RequestBody @Valid RoleCreateParam roleCreateParam){

        Long adminId=umsRoleService.updateRole(roleCreateParam);

        return  ResponseResult.success (adminId);

    }
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult delete(@RequestParam @NotNull Long ids){
        Long adminId=umsRoleService.delete(ids);

        return  ResponseResult.success (adminId);

    }


    @RequestMapping(value = "/listMenu/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult listMenu(@PathVariable("id") @NotNull Long id){
        List<UmsMenu> umsMenus = umsRoleService.listMenu (id);

        return  ResponseResult.success (umsMenus);

    }

    @RequestMapping(value = "/allocMenu",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult allocMenu(@RequestParam @NotNull Long roleId,
                                    @RequestParam(value = "menuIds") @NotEmpty String menuIdStr){
        //1、拿到meunsId数组
        String[] split = menuIdStr.split (",");
        umsRoleService.allocMenu(roleId,split);


        return ResponseResult.success (split.length);

    }
    @RequestMapping(value = "/listAll",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult listAll(){
        return ResponseResult.success (umsRoleService.listAll());
    }
    //更新权限状态
    @RequestMapping(value = "/updateStatus/{id}",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult updateStatus(@RequestParam("status") Integer status,
                                       @PathVariable(value = "id") Integer roleId){
        return ResponseResult.success (umsRoleService.updateStatus(status,roleId));

    }
    //展示用户资源状态
    @RequestMapping(value = "/listResource/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult showRoleResource(@PathVariable(value = "id") Long roleId){
        return ResponseResult.success (umsRoleService.showRoleResource(roleId));
    }
    @RequestMapping(value = "/allocResource",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult allocResource(@RequestParam(value = "roleId") Long roleId,
                                        @RequestParam(value = "resourceIds") String resourceIdStr){

        return ResponseResult.success (umsRoleService.allocResource(roleId,resourceIdStr));
    }


}
