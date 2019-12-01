package com.ryit.commons.entity.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 更新贷款人身份证号
 *
 * @author : samphin
 * @Date : 2019-10-14 13:36:19
 */
@Data
public class UpdateOrderWalletorDto implements Serializable {


    private static final long serialVersionUID = -4014861409107497829L;

    /**
     * 贷款人真实姓名
     */
    private String realname;

    /**
     * 贷款人身份证号
     */
    private String creditorIdcard;

    /**
     * 贷款人手机号
     */
    private String phone;
}
