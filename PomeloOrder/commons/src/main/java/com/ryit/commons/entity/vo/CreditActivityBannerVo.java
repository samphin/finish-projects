package com.ryit.commons.entity.vo;

import com.ryit.commons.entity.pojo.CreditActivity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户活动对象
 */
@Getter
@Setter
@NoArgsConstructor
public class CreditActivityBannerVo implements Serializable {

    private static final long serialVersionUID = 4893686115048466562L;

    /**
     * 活动图片地址
     */
    private String imageUrl;

    /**
     * 活动h5页面地址
     */
    private String pageUrl;

    /**
     * 绑定优惠券
     */
    private Long moduleId;

    public static CreditActivityBannerVo buildVo(CreditActivity po){
        CreditActivityBannerVo vo = new CreditActivityBannerVo();
        BeanUtils.copyProperties(po,vo);
        return vo;
    }

    /**
     * 将poList->voList
     * @return
     */
    public static List<CreditActivityBannerVo> buildVoList(List<CreditActivity> poList){
        List<CreditActivityBannerVo> voList = new ArrayList<>();
        if(CollectionUtils.isEmpty(poList)){
            return voList;
        }

        poList.stream().forEach(po->{
            voList.add(buildVo(po));
        });

        return voList;
    }
}
