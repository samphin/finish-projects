package com.ryit.commons.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 身份证号Dto
 *
 * @author : samphin
 * @Date : 2019-10-15 09:22:07
 */
@Data
public class CreditUserIdCardDto implements Serializable {

    private static final long serialVersionUID = -7368259578712106086L;

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 性别 (0女1男2未知)
     */
    private Integer sex;

    /**
     * 身份证号
     */
    private String realCode;

    /**
     * 有效期
     */
    private String activeLife;

    /**
     * 签发机关
     */
    private String issuingUnit;

}
