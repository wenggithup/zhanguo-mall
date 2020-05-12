package club.banyuan.mall.mgt.controller;

import club.banyuan.mall.mgt.common.ResponseResult;
import club.banyuan.mall.mgt.dao.entity.UmsResourceCategory;
import club.banyuan.mall.mgt.service.UmsResourceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/* 资源分类模块*/
@RestController
@RequestMapping("/resourceCategory")
public class UmsResourceCategoryController {
    @Autowired
    private UmsResourceCategoryService umsResourceCategoryService;

    @RequestMapping(value = "/listAll",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult ResourceListAll(){
        return ResponseResult.success (umsResourceCategoryService.ResourceListAll());
    }


    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult createCategory(@RequestBody @Valid UmsResourceCategory umsResourceCategory){
        return ResponseResult.success (umsResourceCategoryService.createCategory(umsResourceCategory));
    }
    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult updateCategory(@RequestBody @Valid UmsResourceCategory umsResourceCategory){
        return ResponseResult.success (umsResourceCategoryService.updateCategory(umsResourceCategory));
    }
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult deleteCategory(@PathVariable(value = "id")Long categoryId){
        return ResponseResult.success (umsResourceCategoryService.deleteCategory(categoryId));
    }
}
