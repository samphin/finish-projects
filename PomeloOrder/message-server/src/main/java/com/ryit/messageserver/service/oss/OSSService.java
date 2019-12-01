package com.ryit.messageserver.service.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author : 刘修
 * @Date : 2019/8/26 17:17
 * OSS对象存储
 */
@RestController
@RequestMapping("/oss")
public class OSSService {

    private Logger log = LoggerFactory.getLogger(OSSService.class);

    @Value("${oss.endpoint}")
    private String endpoint;
    @Value("${aliyun.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.accessKeySecret}")
    private String accessKeySecret;
    @Value("${oss.bucketName}")
    private String bucketName;
    @Value("${oss.fileLink}")
    private String fileLink;

    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/uploadFile")
    public AjaxResult uploadFile(MultipartFile file) {
        if(null == file){
            return AjaxResult.error("请选择要上传的文件");
        }
        String filename = null;
        OSS ossClient = null;
        try {
            // 创建上传文件的元信息，可以通过文件元信息设置HTTP header。
            ObjectMetadata meta = new ObjectMetadata();
            // 指定上传的内容类型。内容类型决定浏览器将以什么形式、什么编码读取文件。如果没有指定则根据文件的扩展名生成，如果没有扩展名则为默认值application/octet-stream。
            meta.setContentType("image/jpeg");
            // 设置内容被下载时网页的缓存行为。
            meta.setCacheControl("no-cache");
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            filename = RandomUtil.getUUID();//+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            ossClient.putObject(bucketName, filename, file.getInputStream(), meta);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传文件失败", e);
            return AjaxResult.error(0,"上传文件失败");
        } finally {
            if (null != ossClient) {
                ossClient.shutdown();
            }
        }
        return AjaxResult.success(fileLink + filename);
    }

}
