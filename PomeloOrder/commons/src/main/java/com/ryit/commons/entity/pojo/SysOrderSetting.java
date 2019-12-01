package com.ryit.commons.entity.pojo;

import com.ryit.commons.entity.dto.SysOrderSettingDto;
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
public class SysOrderSetting implements Serializable {

    private static final long serialVersionUID = 6672093967622232417L;

    private Long id;

    /**
     * 分数下限
     */
    private Integer scopeMin;

    /**
     * 分数上限
     */
    private Integer scopeMax;

    /**
     * 金额
     */
    private Integer score;

    public static List<SysOrderSetting> buildPoList(List<SysOrderSettingDto> dtoList){
        if(CollectionUtils.isEmpty(dtoList)){
            return null;
        }
        List<SysOrderSetting> poList = new ArrayList<>();
        dtoList.stream().forEach(dto->{
            SysOrderSetting po = new SysOrderSetting();
            BeanUtils.copyProperties(dto,po);
            poList.add(po);
        });

        return poList;
    }

}