package com.ryit.commons.entity.vo;

import com.ryit.commons.entity.pojo.SysOrderSetting;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单金额标准
 *
 * @author samphin
 * @date 2019-9-2 14:50:42
 */
@Setter
@Getter
@NoArgsConstructor
public class SysOrderSettingVo implements Serializable {

    private static final long serialVersionUID = 4215278842955656930L;

    private Integer scopeMin;

    private Integer scopeMax;

    private Integer score;

    public static List<SysOrderSettingVo> buildVoList(List<SysOrderSetting> poList) {
        List<SysOrderSettingVo> voList = new ArrayList<>();
        if (CollectionUtils.isEmpty(poList)) {
            return voList;
        }

        poList.stream().forEach(po -> {
            SysOrderSettingVo vo = new SysOrderSettingVo();
            BeanUtils.copyProperties(po, vo);
            voList.add(vo);
        });

        return voList;
    }

}