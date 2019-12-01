package com.ryit.commons.entity.vo;

import com.ryit.commons.entity.pojo.CreditBill;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 刘修
 * @Date : 2019/8/23 13:56
 */
@Getter
@Setter
@NoArgsConstructor
public class CreditBillVo extends CreditBill {

    /**
     * 贷款用户名
     */
    private String walletUserName;

    /**
     * 贷款金额
     */
    private Double creditMoney;

    /**
     * 还款周期
     */
    private String orderTerm;

    public CreditBillVo buildVo(CreditBill po){
        BeanUtils.copyProperties(po,this);
        return this;
    }

    public static List<CreditBillVo> buildVoList(List<CreditBill> poList){
        List<CreditBillVo> voList = new ArrayList<>();
        if(CollectionUtils.isEmpty(poList)){
            return voList;
        }

        poList.stream().forEach(po->{
            CreditBillVo vo = new CreditBillVo();
            BeanUtils.copyProperties(po,vo);
            voList.add(vo);
        });
        return voList;
    }


}
