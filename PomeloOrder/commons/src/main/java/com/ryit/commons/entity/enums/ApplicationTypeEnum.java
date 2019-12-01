package com.ryit.commons.entity.enums;

import java.util.*;

/**
 * APP信息枚举类
 *
 * @author samphin
 * @date 2019-10-11 16:01:05
 */
public enum ApplicationTypeEnum {
    /**
     * 柚子抢单APP信息
     */
    CREDIT("柚子抢单","credit"),
    /**
     * 七一钱包APP信息
     */
    WALLET("七一钱包","wallet");

    /**
     * 获取所有APP信息
     * @return
     */
    public static List<Map<String,Object>> queryAllApplications(){
        List<Map<String,Object>> dataList = new ArrayList<>();
        Arrays.stream(ApplicationTypeEnum.values()).forEach(em->{
            Map<String,Object> data = new HashMap<>();
            data.put("name",em.getName());
            data.put("code",em.getCode());
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
    private String code;


    ApplicationTypeEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
