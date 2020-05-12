package club.banyuan.mall.mgt.controller;


import club.banyuan.mall.mgt.bean.AdminCreateParam;
import club.banyuan.mall.mgt.common.ResponsePages;
import club.banyuan.mall.mgt.common.ResponseResult;
import club.banyuan.mall.mgt.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/*
*   权限/用户列表 功能
* */
@RestController
@RequestMapping("/admin")
public class UmsAdminController {
    @Autowired
    private UmsAdminService umsAdminService;


    //展示所有用户数据列表
   @ResponseBody
   @RequestMapping(value = "/list",method = RequestMethod.GET)
   public ResponseResult list(@RequestParam Integer pageNum,
                              @RequestParam Integer pageSize,
                              @RequestParam(required = false) String keyword){
       ResponsePages list = umsAdminService.list (pageNum, pageSize, keyword);

       return  ResponseResult.success (list);
   }
    // 添加用户功能
   @ResponseBody
   @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResponseResult register(@RequestBody @Valid AdminCreateParam adminCreateParam){
      return ResponseResult.success (umsAdminService.registerUmsAdmin(adminCreateParam));

   }

    // 编辑用户功能
    @ResponseBody
    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    public ResponseResult update(@RequestBody @Valid AdminCreateParam adminCreateParam){
        return ResponseResult.success (umsAdminService.update(adminCreateParam));

    }
    //删除用户功能
    @ResponseBody
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.POST)
    public ResponseResult delete(@PathVariable(value = "id") Long id){
        return ResponseResult.success (umsAdminService.delete(id));

    }
    //分配角色按钮功能
    @ResponseBody
    @RequestMapping(value = "/role/{id}",method = RequestMethod.GET)
    public ResponseResult adminRole(@PathVariable(value = "id")Long adminId){
        return ResponseResult.success (umsAdminService.adminRole(adminId));
    }

    //分配角色提交功能
    @ResponseBody
    @RequestMapping(value = "/role/update",method = RequestMethod.POST)
    public ResponseResult updateRoleByAdminId(@RequestParam(value = "adminId") Long adminId,
                                              @RequestParam(value = "roleIds") String roleIdStr){
       return ResponseResult.success (umsAdminService.updateRoleByAdminId(adminId,roleIdStr));
    }
    //角色状态修改功能
    @RequestMapping(value = "/updateStatus/{id}",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult updateStatus(@RequestParam("status") Integer status,
                                       @PathVariable(value = "id") Integer adminId){
        return ResponseResult.success (umsAdminService.updateStatus(status,adminId));

    }

}
