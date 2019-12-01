package com.ryit.commons.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ryit.commons.entity.pojo.CreditCouponList;
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
 * 优惠券使用信息列表
 *
 * @author : samphin
 * @Date : 2019-9-4 15:27:15
 */
@Getter
@Setter
@NoArgsConstructor
public class CreditCouponListVo implements Serializable {


    private static final long serialVersionUID = 8938276290374748473L;

    private Long moduleId;

    private String moduleName;

    private Long userId;

    private String userName;

    private String useStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date validStartTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date validEndTime;

    /**
     * 将poList->voList
     *
     * @author samphin
     * @date 2019-9-4 15:31:05
     */
    public static List<CreditCouponListVo> buildVoList(List<CreditCouponList> poList) {
        List<CreditCouponListVo> voList = new ArrayList<>();
        if (CollectionUtils.isEmpty(poList)) {
            return voList;
        }

        poList.stream().forEach(po -> {
            CreditCouponListVo vo = new CreditCouponListVo();
            BeanUtils.copyProperties(po, vo);
            voList.add(vo);
        });

        return voList;
    }
}
