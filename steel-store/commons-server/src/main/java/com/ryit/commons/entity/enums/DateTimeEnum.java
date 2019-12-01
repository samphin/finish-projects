package com.ryit.commons.entity.enums;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 年月日枚举类
 *
 * @author samphin
 * @since 2019年10月29日16:54:01
 */
public enum DateTimeEnum {

    YEAR("year", "今年"),
    MONTH("month", "今月"),
    DAY("day", "今日");

    DateTimeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 查询年月日列表
     *
     * @return
     */
    public List<JSONObject> queryYMDList() {
        List<JSONObject> data = new ArrayList<>();
        Arrays.stream(DateTimeEnum.values()).forEach(en -> {
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
