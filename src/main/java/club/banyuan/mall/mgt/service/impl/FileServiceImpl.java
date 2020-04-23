package club.banyuan.mall.mgt.service.impl;


import club.banyuan.mall.mgt.service.FileService;
import io.minio.ErrorCode;
import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.errors.ErrorResponseException;
import io.minio.policy.PolicyType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class FileServiceImpl implements FileService {
    @Value("${minio.endpoint}")
    private String ENDPOINT;


    @Value("${minio.bucketName}")
    private String BUCKET_NAME;

    @Value("${minio.accessKey}")
    private String ACCESS_KEY;

    @Value("${minio.secretKey}")
    private String SECRET_KEY;


    @Override

    //保存文件
    public String sava(String objectName, InputStream stream) {
        try {
            MinioClient minioClient = new MinioClient (ENDPOINT, ACCESS_KEY, SECRET_KEY);
            //如果桶不存在，则创建出一个新的桶，并指定读写权限
            if (!minioClient.bucketExists (BUCKET_NAME)) {
                minioClient.makeBucket (BUCKET_NAME);
                minioClient.setBucketPolicy (BUCKET_NAME, "*.*", PolicyType.READ_WRITE);
            }
            minioClient.putObject (BUCKET_NAME, objectName, stream, null);


        } catch (Exception e) {
            e.printStackTrace ();
        }
        //返回文件路径
        return ENDPOINT + "/" + BUCKET_NAME + "/" + objectName;
    }

    @Override

    //删除文件
    public void delete(String objectName) {
        try {
            MinioClient minioClient = new MinioClient (ENDPOINT, ACCESS_KEY, SECRET_KEY);
            minioClient.removeObject (BUCKET_NAME, objectName);
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    @Override
    //查询文件是否存在
    public boolean fileIsExist(String objectName) throws IOException {
        try {
            MinioClient minioClient = new MinioClient (ENDPOINT, ACCESS_KEY, SECRET_KEY);
            ObjectStat objectStat = minioClient.statObject (BUCKET_NAME, objectName);
            return true;
        } catch (ErrorResponseException e){
            if(ErrorCode.NO_SUCH_KEY.code ()==e.errorResponse ().code ()){
                return false;
            }else {
                throw new IOException (e);
            }

        } catch (Exception e) {
            e.printStackTrace ();
        }
        return false;
    }
}
