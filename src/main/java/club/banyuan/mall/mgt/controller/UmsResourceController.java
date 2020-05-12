package club.banyuan.mall.mgt.controller;

import club.banyuan.mall.mgt.common.ResponseResult;
import club.banyuan.mall.mgt.dao.entity.UmsResource;
import club.banyuan.mall.mgt.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/*
*   权限 资源列表功能
* */
@RestController
@RequestMapping("/resource")
public class UmsResourceController {
    @Autowired
    private UmsResourceService umsResourceService;

    //权限 资源列表 展示功能（包含关键词检索）
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult resourceList(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam(value = "nameKeyword", required = false) String nameKeyword,
                                       @RequestParam(value = "urlKeyword", required = false) String urlKeyword,
                                       @RequestParam(value = "categoryId", required = false) Integer categoryId
    ) {

        return ResponseResult.success (umsResourceService.resourceList (pageNum, pageSize, nameKeyword, urlKeyword, categoryId));
    }

    //权限 资源列表 添加资源
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult CreateResource(@RequestBody @Valid UmsResource umsResource) {
        return ResponseResult.success (umsResourceService.CreateResource (umsResource));
    }

    //权限 资源列表 编辑资源
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult updateResource(@RequestBody @Valid UmsResource umsResource) {
        return ResponseResult.success (umsResourceService.updateResource (umsResource));
    }

    //权限 资源列表 删除资源
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult deleteResource(@PathVariable(value = "id")Long resourceId){
        return ResponseResult.success (umsResourceService.deleteResourceByResourceId(resourceId));
    }
    //权限 角色列表 操作 分配资源
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult listAll(){
        return ResponseResult.success (umsResourceService.listAll());
    }
}