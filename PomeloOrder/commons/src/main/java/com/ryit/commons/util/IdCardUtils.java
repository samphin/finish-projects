package com.ryit.commons.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author samphin
 * @date 2019-9-23 21:45:21
 */
@Component
public class IdCardUtils {

    @Value("${idCard.appCode}")
    private String appcode;

    @Value("${idCard.host}")
    private String host;

    @Value("${idCard.path}")
    private String path;

    /**
     * 根据身份证号求性别 整除2代表女；否则为男
     *
     * @param idCard
     * @author samphin
     * @date 2019-9-23 18:17:40
     */
    public static Integer getSexByIdCard(String idCard) {
        int sex = Integer.parseInt(idCard.substring(16, idCard.length() - 1));
        if (sex % 2 == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * 根据性别识别-->先生、女士
     *
     * @author samphin
     * @date 2019-10-15 16:22:21
     */
    public static String getSexDescription(Integer sex) {
        if (0 == sex) {
            return "女士";
        } else if (1 == sex) {
            return "先生";
        } else {
            return "未知";
        }
    }

    /**
     * 身份证号获取用户年龄
     *
     * @param idCard
     * @return
     */
    public static Integer getAgeByIdCard(String idCard) {
        // 得到年份
        String year = idCard.substring(6).substring(0, 4);
        // 得到月份
        String month = idCard.substring(10).substring(0, 2);
        // 得到当前的系统时间
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // 当前年份
        String nowYear = format.format(date).substring(0, 4);
        // 月份
        String nowMonth = format.format(date).substring(5, 7);
        int age = 0;
        // 当前月份大于用户出身的月份表示已过生
        if (Integer.parseInt(month) <= Integer.parseInt(nowMonth)) {
            age = Integer.parseInt(nowYear) - Integer.parseInt(year) + 1;
            // 当前用户还没过生
        } else {
            age = Integer.parseInt(nowYear) - Integer.parseInt(year);
        }
        return age;
    }

    /**
     * 校验身份证号和用户名是否匹配
     *
     * @param idCard
     * @param realName
     * @return
     */
    public Boolean checkIdCardAndRealName(String idCard, String realName) {
        Map<String, String> headers = new HashMap<>(1);
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        String method = "GET";
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<>(2);
        querys.put("cardNo", idCard);
        querys.put("realName", realName);
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            String result = EntityUtils.toString(response.getEntity());
            if (StringUtils.isBlank(result)) {
                return false;
            }
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.parseObject(result).getString("result"));
            if (null != jsonObject.getBoolean("isok") && jsonObject.getBoolean("isok")) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
