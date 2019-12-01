package com.ryit.commons.entity.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * credit_user_card
 *
 * @author
 */
@Setter
@Getter
@NoArgsConstructor
public class CreditUserCard implements Serializable {
    private static final long serialVersionUID = 8105252278468537445L;
    private Long userId;

    /**
     * 身份证号正面
     */
    private String frontImg;

    /**
     * 身份证号反面
     */
    private String afterImg;


}