package com.ryit.commons.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 登出Dto
 *
 * @author samphin
 * @since 2019-10-23 16:43:23
 */
@Data
public class LoginOutDto implements Serializable {

    private static final long serialVersionUID = 4384573660214549071L;

    private String token;
}
