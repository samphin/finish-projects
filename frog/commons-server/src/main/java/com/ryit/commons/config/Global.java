package com.ryit.commons.config;

import com.ryit.commons.util.YamlUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局配置类
 *
 * @author ryit
 */
public class Global {
    private static final Logger log = LoggerFactory.getLogger(Global.class);

    private static String NAME = "application.yml";

    /**
     * 当前对象实例
     */
    private static Global global = null;

    /**
     * 保存全局属性值
     */
    private static Map<String, String> map = new HashMap<String, String>();

    private Global() {
    }

    /**
     * 静态工厂方法 获取当前对象实例 多线程安全单例模式(使用双重同步锁)
     */

    public static synchronized Global getInstance() {
        if (global == null) {
            synchronized (Global.class) {
                if (global == null) {
                    global = new Global();
                }
            }
        }
        return global;
    }

    /**
     * 获取配置
     */
    public static String getConfig(String key) {
        String value = map.get(key);
        if (value == null) {
            Map<?, ?> yamlMap = null;
            try {
                yamlMap = YamlUtil.loadYaml(NAME);
                value = String.valueOf(YamlUtil.getProperty(yamlMap, key));
                map.put(key, value != null ? value : StringUtils.EMPTY);
            } catch (FileNotFoundException e) {
                log.error("获取全局配置异常 {}", key);
            }
        }
        return value;
    }


    /**
     * 获取ip地址开关
     */
    public static Boolean isAddressEnabled() {
        String value = getConfig("ryit.addressEnabled");
        return Boolean.TRUE.equals(value);
    }

    /**
     * 获取文件上传路径
     */
    public static String getProfile() {
        return getConfig("ryit.profile");
    }

    public static String getStaticLocations() {
        return getConfig("ryit.static-locations");
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath() {
        return getConfig("ryit.profile") + "avatar/";
    }

    /**
     * 获取windows上图片的下载路径
     *
     * @return
     */
    public static String getWindowsImagesDownloadPath() {
        return getConfig("ryit.profile") + "images/";
    }

    /**
     * 获取windows上文件的下载路径
     *
     * @return
     */
    public static String getWindowsFilesDownloadPath() {
        return getConfig("ryit.profile") + "files/";
    }

}
