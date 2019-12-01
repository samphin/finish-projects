package com.ryit.commons.entity.enums;

import java.util.*;

/**
 * 用户账单流水枚举类
 *
 * @author samphin
 * @date 2019-9-2 16:25:29
 */
public enum  BillTypeEnum {
    /**
     * 充值
     */
    RECHARGE("充值",0),
    /**
     * 抢单
     */
    GRAB("抢单",1),
    /**
     * 退单
     */
    CHARGE_BACK("退单",2);

    /**
     * 获取所有流水类型
     * @return
     */
    public static List<Map<String,Object>> queryAllBillType(){
        List<Map<String,Object>> dataList = new ArrayList<>();
        Arrays.stream(BillTypeEnum.values()).forEach(em->{
            Map<String,Object> data = new HashMap<>();
            data.put("name",em.getName());
            data.put("value",em.getValue());
            dataList.add(data);
        });
        return dataList;
    }

    /**
     * 字段含义
     */
    private String name;

    /**
     * 字段编码值
     */
    private Integer value;


    BillTypeEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
