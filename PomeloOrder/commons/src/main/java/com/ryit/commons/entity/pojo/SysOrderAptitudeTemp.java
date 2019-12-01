package com.ryit.commons.entity.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Transient;
import org.springframework.util.CollectionUtils;

/**
 * 资质草稿表
 * @author samphin
 * @date 2019-10-9 18:44:02
 */
@Getter
@Setter
@ToString
public class SysOrderAptitudeTemp implements Serializable {


    private static final long serialVersionUID = 8009306390180039773L;

    /**
    * 主键
    */
    private Long id;

    /**
    * 资质名称
    */
    private String name;

    /**
    * 资质编码
    */
    private String code;

    /**
     * 资质编码驼峰写法
     */
    @Transient
    private String codeCamelCaseName;

    /**
    * 资质值描述
    */
    private String label;

    /**
    * 资质值
    */
    private Integer value;

    /**
    * 分数
    */
    private Integer score;

    /**
    * 优先级0：不显示；1：显示
    */
    private Integer priority;

    /**
    * 订单编号
    */
    private Long orderId;

    public static List<SysOrderAptitudeTemp> buildPoList(List<SysOrderAptitude> sysOrderAptitudeList){
        if(CollectionUtils.isEmpty(sysOrderAptitudeList)){
            return null;
        }

        List<SysOrderAptitudeTemp> poList = new ArrayList<>();
        sysOrderAptitudeList.forEach(ap->{
            SysOrderAptitudeTemp po = new SysOrderAptitudeTemp();
            BeanUtils.copyProperties(ap,po);
            poList.add(po);
        });

        return poList;
    }
}