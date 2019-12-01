package com.ryit.commons.entity.vo;

import com.ryit.commons.entity.pojo.CreditInvite;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : samphin
 * @Date : 2019-9-3 11:57:16
 */
@Getter
@Setter
@NoArgsConstructor
public class CreditInviteVo implements Serializable {


    private static final long serialVersionUID = 143171298996128423L;

    private Long id;
    /**
     * 奖励金额
     */
    private Double coin;
    /**
     * 人数
     */
    private Integer num;

    public static CreditInviteVo buildVo(CreditInvite po){
        if(null == po){
            return null;
        }
        CreditInviteVo vo = new CreditInviteVo();
        BeanUtils.copyProperties(po,vo);
        return vo;
    }

    public static List<CreditInviteVo> buildVoList(List<CreditInvite> poList){

        List<CreditInviteVo> voList = new ArrayList<>();

        if(CollectionUtils.isEmpty(poList)){
            return voList;
        }

        poList.stream().forEach(po->{
            voList.add(buildVo(po));
        });

        return voList;

    }

}
