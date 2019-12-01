package com.ryit.commons.entity.vo;

import com.ryit.commons.entity.pojo.CreditUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author : 刘修
 * @Date : 2019/8/25 15:34
 */
@Getter
@Setter
@NoArgsConstructor
public class CreditUserVo extends CreditUser {

    private String cityName;

    /**
     * 身份证正面
     */
    private String frontImg;

    /**
     * 身份证反面
     */
    private String afterImg;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;


}
