package com.ryit.commons.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ryit.commons.entity.pojo.CreditCouponModule;
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
 * @author : 陈永丰
 * @Date : 2019/8/23 13:56
 */
@Getter
@Setter
@NoArgsConstructor
public class CreditCouponVo implements Serializable {

    private static final long serialVersionUID = 482247959892606346L;

    private Long id;

    private String name;

    /**
     * 优惠券类型名称
     */
    private String typeName;

    /**
     * 优惠券类型:1：免单券，2：满减券
     */
    private Integer type;

    /**
     * 优惠券面值（如果满减券则为满金额）
     */
    private Double coin;

    /**
     * 优惠金额面值（针对满减券）
     */
    private Double discountCoin;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date validStartTime;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date validEndTime;

    /**
     * 将poList->voList
     * @return
     */
    public static List<CreditCouponVo> buildVoList(List<CreditCouponModule> poList){
        List<CreditCouponVo> voList = new ArrayList<>();
        if(CollectionUtils.isEmpty(poList)){
            return voList;
        }

        poList.stream().forEach(po->{
            CreditCouponVo vo = new CreditCouponVo();
            BeanUtils.copyProperties(po,vo);
            vo.setTypeName(convertTypeName(po.getType()));
            voList.add(vo);
        });

        return voList;
    }

    /**
     * 根据券类型编码，转换券类型名称
     * @param type
     * @return
     */
    private static String convertTypeName(int type){
        String typeName = "";
        switch (type){
            case 1:
                typeName = "免单券";
                break;
            case 2:
                typeName = "满减券";
                break;
        }
        return typeName;
    }

}
