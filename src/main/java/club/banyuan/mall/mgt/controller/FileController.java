package club.banyuan.mall.mgt.controller;

import club.banyuan.mall.mgt.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    //上传文件
    @RequestMapping(value = "/image/upload",method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam(value = "file")MultipartFile file){
        String filename = file.getOriginalFilename ();
        //设置日期路径
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat ("yyyyMMdd");
        //设置文件名称，避免文件名重复
        String objectName=simpleDateFormat.format (new Date ())+"/"+new Date ().getTime ()+filename;
        try {
            return fileService.sava (objectName,file.getInputStream ());
        } catch (IOException e) {
            e.printStackTrace ();
        }
        return "file";
    }

    @RequestMapping(value = "/image/delete",method = RequestMethod.POST)
    @ResponseBody
    public String delete(@RequestParam(value = "objectName") String name){

        try {
            fileService.delete(name);
            return "success";
        } catch (Exception e) {
            e.printStackTrace ();
            return "fail";
        }


    }


}
