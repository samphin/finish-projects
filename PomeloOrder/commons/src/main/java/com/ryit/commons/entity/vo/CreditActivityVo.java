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
 *
 * @author samphin
 * @date 2019-9-5 09:50:08
 */
@Getter
@Setter
@NoArgsConstructor
public class CreditActivityVo extends CreditActivity implements Serializable {


    private static final long serialVersionUID = 1393518673924543898L;

    /**
     * po->vo
     * @param po
     */
    public static CreditActivityVo buildVo(CreditActivity po) {
        CreditActivityVo vo = new CreditActivityVo();
        BeanUtils.copyProperties(po, vo);
        return vo;
    }

    /**
     * 将poList->voList
     *
     * @return
     */
    public static List<CreditActivityVo> buildVoList(List<CreditActivity> poList) {
        List<CreditActivityVo> voList = new ArrayList<>();
        if (CollectionUtils.isEmpty(poList)) {
            return voList;
        }

        poList.stream().forEach(po -> {
            voList.add(buildVo(po));
        });

        return voList;
    }
}
