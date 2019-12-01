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
 * 优惠券模版对象
 */
@Getter
@Setter
@NoArgsConstructor
public class CreditCouponModuleVo implements Serializable {


    private static final long serialVersionUID = -3526768156763402531L;

    /**
     * 优惠券模版ID
     */
    private Long id;

    /**
     * 优惠券名称
     */
    private String name;

    /**
     * 优惠券面值（如果满减券则为满金额，条件值）
     */
    private Double coin;

    /**
     * 优惠金额面值（针对满减券，折扣值）
     */
    private Double discountCoin;

    /**
     * 优惠券类型:1：免单券，2：满减券
     */
    private Integer type;

    /**
     * 优惠券类型名称
     */
    private String typeName;

    /**
     * 有效期起始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date validStartTime;

    /**
     * 有效期结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date validEndTime;

    public static CreditCouponModuleVo buildVo(CreditCouponModule po) {
        CreditCouponModuleVo vo = new CreditCouponModuleVo();
        BeanUtils.copyProperties(po, vo);
        vo.setTypeName(convertTypeName(po.getType()));
        return vo;
    }

    /**
     * 将poList->voList
     *
     * @param poList
     * @return
     */
    public static List<CreditCouponModuleVo> buildVoList(List<CreditCouponModule> poList) {

        List<CreditCouponModuleVo> voList = new ArrayList<>();

        if (CollectionUtils.isEmpty(poList)) {
            return voList;
        }

        poList.stream().forEach(po -> {
            voList.add(buildVo(po));
        });
        return voList;
    }

    /**
     * 根据券类型编码，转换券类型名称
     *
     * @param type
     * @return
     */
    private static String convertTypeName(int type) {
        String typeName = "";
        switch (type) {
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
