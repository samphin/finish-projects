package com.ryit.commons.entity.pojo;

import com.ryit.commons.entity.dto.CreditCouponDto;
import com.ryit.commons.entity.dto.CreditGrantCouponDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.*;

/**
 * 用户优惠券关联对象
 */
@Getter
@Setter
@NoArgsConstructor
public class CreditCoupon implements Serializable {

    private static final long serialVersionUID = 2017033112063981781L;

    /**
     * 关联表主键ID
     */
    private Long id;

    /**
     * 所属用户ID
     */
    private Long userId;

    /**
     * 优惠券模版ID
     */
    private Long moduleId;

    /**
     * 使用状态(0:未使用 1:已使用)
     */
    private Integer useStatus;

    /**
     * 免单券领取时间
     */
    private Date validStartTime;

    /**
     * 免单券有效期结束时间
     */
    private Date validEndTime;

    /**
     * 将Dto转换成po
     * @author samphin
     * @date 2019-8-30 11:19:32
     * @return
     */
    public List<CreditCoupon> buildPoList(CreditGrantCouponDto dto, CreditCouponModule creditCouponModule){
        List<CreditCoupon> couponList = new ArrayList<>();
        //当前优惠券id
        Long moduleId = dto.getModuleId();
        //当前优惠券类型
        int moduleType = creditCouponModule.getType();
        //批量发放优惠券
        dto.getUserIds().forEach(id->{
            CreditCoupon coupon = new CreditCoupon();
            coupon.setUserId(id);
            coupon.setModuleId(moduleId);
            //免单券领取时间根据当前系统时间计算
            if(1 == moduleType){
                coupon.setValidStartTime(validDateMap().get("startTime"));
                coupon.setValidEndTime(validDateMap().get("endTime"));
            }
            //满减券领取时间根据满减券模版定义的有效期起止时间计算
            if(2 == moduleType){
                coupon.setValidStartTime(creditCouponModule.getValidStartTime());
                coupon.setValidEndTime(creditCouponModule.getValidEndTime());
            }
            //默认券状态是未使用
            coupon.setUseStatus(0);
            couponList.add(coupon);
        });

        return couponList;
    }

    /**
     * 将Dto转换成po
     * @author samphin
     * @date 2019-8-30 11:19:32
     * @return
     */
    public CreditCoupon buildPo(Long userId){
        this.setUserId(userId);
        this.setModuleId(0L);
        this.setValidStartTime(validDateMap().get("startTime"));
        this.setValidEndTime(validDateMap().get("endTime"));
        //默认券状态是未使用
        this.setUseStatus(0);
        return this;
    }

    /**
     * 计算免单券有效期起止时间
     * @author samphin
     * @date 2019-8-31 10:10:07
     * @return
     */
    private Map<String,Date> validDateMap(){
        Map<String,Date> map = new HashMap<>(2);
        Calendar calendar = Calendar.getInstance();
        map.put("startTime",calendar.getTime());
        //有效期结束时间=领取时间向后推7天
        calendar.add(Calendar.DAY_OF_MONTH,7);
        map.put("endTime",calendar.getTime());
        return map;
    }
}
