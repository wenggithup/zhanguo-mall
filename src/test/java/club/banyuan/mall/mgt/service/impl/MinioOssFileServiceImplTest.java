package club.banyuan.mall.mgt.service.impl;


import club.banyuan.mall.mgt.service.FileService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;


@RunWith (SpringRunner.class)
@SpringBootTest
public class MinioOssFileServiceImplTest {

    @Autowired
    private FileService fileService;

    private final String OBJECT_NAME="test/test.jpeg";

    @Test
    public void uploadTest() throws IOException {
        //获取文件输入流
        InputStream is = this.getClass ().getClassLoader ().getResourceAsStream ("http-client/test.jpeg");
        //获取上传后的url
        String url= fileService.sava (OBJECT_NAME,is);
        Assert.assertTrue (url.endsWith (OBJECT_NAME));

        Assert.assertTrue (fileService.fileIsExist (OBJECT_NAME));
    }

    @Test
    public void deleteTest() throws IOException {
       fileService.delete (OBJECT_NAME);
       Assert.assertFalse (fileService.fileIsExist (OBJECT_NAME));
    }


}
