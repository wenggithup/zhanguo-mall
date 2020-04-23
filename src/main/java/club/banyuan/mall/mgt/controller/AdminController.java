package club.banyuan.mall.mgt.controller;

import club.banyuan.mall.mgt.bean.AdminInfoResp;
import club.banyuan.mall.mgt.bean.AdminLoginParam;
import club.banyuan.mall.mgt.bean.AdminLoginResp;
import club.banyuan.mall.mgt.common.ResponseResult;
import club.banyuan.mall.mgt.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    ResponseResult login(@RequestBody @Valid AdminLoginParam adminLoginParam){
        AdminLoginResp adminLoginResp= adminService.login(adminLoginParam);

        return ResponseResult.success (adminLoginResp);
    }

    @RequestMapping(value = "/auth",method = RequestMethod.GET)
    @ResponseBody
    ResponseResult auth(){
        return ResponseResult.success ("success");
    }

    @RequestMapping(value = "/info",method = RequestMethod.GET)
    @ResponseBody
    ResponseResult info(Principal principal){
        long adminid =Long.parseLong ( principal.getName ());

        AdminInfoResp adminInfoResp= adminService.getInfoByAdminId(adminid);
        return ResponseResult.success (adminInfoResp);
    }
}
