package com.ryit.commons.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "买家列表Vo类")
public class SysBuyerListVo implements Serializable {

    private Integer id;

    private String username;

    private String realName;

    private String mobilePhone;

    /**
     * 访问类型 1-随时 2-上班时间 3-下班时间
     */
    private Integer accessDateType;

}
