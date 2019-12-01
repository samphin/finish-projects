package com.ryit.commons.entity.enums;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 订单状态枚举类
 *
 * @author samphin
 * @since 2019-11-4 16:30:42
 */
public enum OrderStatusEnum {

    NO_PAY("0", "待付款"),
    PAID("1", "已付款"),
    DELIVERED("2", "已发货"),
    RECEIVED("3", "已收货"),
    CANCELED("4", "已取消"),
    FINISHED("5", "已完成");

    OrderStatusEnum(String code, String name) {
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
        Arrays.stream(OrderStatusEnum.values()).forEach(en -> {
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
