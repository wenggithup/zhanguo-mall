package club.banyuan.mall.mgt.controller;

import club.banyuan.mall.mgt.bean.RoleResp;
import club.banyuan.mall.mgt.common.ResponsePages;
import club.banyuan.mall.mgt.common.ResponseResult;
import club.banyuan.mall.mgt.service.UmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


}
