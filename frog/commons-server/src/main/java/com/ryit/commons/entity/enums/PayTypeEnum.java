package com.ryit.commons.entity.enums;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 支付类型
 *
 * @author samphin
 * @since 2019年10月29日16:54:01
 */
public enum PayTypeEnum {

    ALI_PAY("alipay", "支付宝"),
    WE_CHAT("wechat", "微信"),
    COD("cod", "货到付款"),
    CASH("cash", "现金支付");

    PayTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 查询所有支付类型列表
     *
     * @return
     */
    public List<JSONObject> queryAllPayTypeList() {
        List<JSONObject> data = new ArrayList<>();
        Arrays.stream(PayTypeEnum.values()).forEach(en -> {
            JSONObject json = new JSONObject();
            json.put("code", en.getCode());
            json.put("name", en.getName());
            data.add(json);
        });
        return data;
    }

    private String code;

    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
