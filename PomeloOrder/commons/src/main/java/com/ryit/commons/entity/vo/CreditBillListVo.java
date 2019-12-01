package com.ryit.commons.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ryit.commons.entity.pojo.CreditBill;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : samphin
 * @date : 2019-9-5 13:51:47
 */
@Getter
@Setter
@NoArgsConstructor
public class CreditBillListVo implements Serializable {

    /**
     * 用户名(用于显示用户名)
     */
    private String userName;
    /**
     *  金额
     */
    private Double billCoin;

    /**
     * 账单类型名称
     */
    private String billTypeName;
    /**
     *  账单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date billTime;
    /**
     *  优惠券id
     */
    private Long couponId;

    /**
     * 充值金额
     */
    private Double billMoney;

    public static CreditBillListVo buildVo(CreditBill po){
        CreditBillListVo vo = new CreditBillListVo();
        BeanUtils.copyProperties(po,vo);
        return vo;
    }

    public static List<CreditBillListVo> buildVoList(List<CreditBill> poList){
        List<CreditBillListVo> voList = new ArrayList<>();
        if(CollectionUtils.isEmpty(poList)){
            return voList;
        }

        poList.stream().forEach(po->{
            voList.add(buildVo(po));
        });
        return voList;
    }


}
