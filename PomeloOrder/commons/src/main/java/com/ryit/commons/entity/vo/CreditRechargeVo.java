package com.ryit.commons.entity.vo;

import com.ryit.commons.entity.pojo.CreditRecharge;
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
 * @Date : 2019-9-3 11:57:40
 */
@Getter
@Setter
@NoArgsConstructor
public class CreditRechargeVo implements Serializable {

    private static final long serialVersionUID = 5164595257767175121L;

    private Long id;

    /**
     * 充值金额
     */
    private Double money;

    /**
     * 赠送金额
     */
    private Double gift;

    /**
     * 充值方案(0:首充1:重复充值)
     */
    private Integer rechargeType;

    public static CreditRechargeVo buildVo(CreditRecharge po) {
        CreditRechargeVo vo = new CreditRechargeVo();
        BeanUtils.copyProperties(po, vo);
        return vo;
    }

    public static List<CreditRechargeVo> buildVoList(List<CreditRecharge> poList) {

        List<CreditRechargeVo> voList = new ArrayList<>();

        if (CollectionUtils.isEmpty(poList)) {
            return voList;
        }

        poList.stream().forEach(po -> {
            voList.add(buildVo(po));
        });

        return voList;

    }
}
