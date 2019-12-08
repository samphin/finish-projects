package com.ryit.message.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.constants.BaseUrlConstants;
import com.ryit.commons.enums.SystemErrorEnum;
import com.ryit.commons.exception.CustomException;
import com.ryit.commons.util.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * OSS服务
 *
 * @author samphin
 * @since 2019-11-4 09:21:31
 */
@Api(value = "OssController",tags = "OSS文件存储服务")
@RestController
@RequestMapping
public class OssController {

    private Logger log = LoggerFactory.getLogger(OssController.class);

    @Value("${aliyun.oss.AccessKey-ID}")
    private String accessKeyId;
    @Value("${aliyun.oss.AccessKey-Secret}")
    private String accessKeySecret;
    @Value("${aliyun.oss.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.bucket-name}")
    private String bucketName;
    @Value("${aliyun.oss.file-url}")
    private String fileLink;

    /**
     * 通用文件上传
     *
     * @param file
     * @return
     */
    @ApiOperation(value = "文件上传", httpMethod = "POST")
    @PostMapping(value = {
            BaseUrlConstants.BASE_API_PREFIX+"/upload_file",
            BaseUrlConstants.BASE_ADMIN_PREFIX+"/upload_file"
    })
    public ResponseData uploadFile(MultipartFile file) {
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
            filename = RandomUtil.getUUID();
            PutObjectResult objectResult = ossClient.putObject(bucketName, filename, file.getInputStream(), meta);

            System.out.println("objectResult = " + JSONObject.toJSONString(objectResult));
        } catch (Exception e) {
            log.error("上传文件失败", e);
            throw new CustomException(SystemErrorEnum.FILE_UPLOAD_ERROR);
        } finally {
            if (null != ossClient) {
                ossClient.shutdown();
            }
        }
        return ResponseData.success().setData(fileLink + filename);
    }
}
