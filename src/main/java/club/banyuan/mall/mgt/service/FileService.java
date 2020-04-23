package club.banyuan.mall.mgt.service;

import java.io.IOException;
import java.io.InputStream;

public interface FileService {
    //保存文件到minio服务中
    String sava(String objectName, InputStream stream);

    //删除文件
    void delete(String name);

    //判断文件是否存在
    boolean fileIsExist(String objectName) throws IOException;

}
