package com.ryit.commons.entity.vo;

import com.ryit.commons.entity.pojo.CreditInvite;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : samphin
 * @Date : 2019-9-3 11:57:16
 */
@Getter
@Setter
@NoArgsConstructor
public class CreditUserInviteInfoVo implements Serializable {

    private static final long serialVersionUID = -8655985929063721923L;

    /**
     * 成功邀请的奖励金额
     */
    private Double prize;

    /**
     * 成功邀请的人数
     */
    private Integer persons;





}
