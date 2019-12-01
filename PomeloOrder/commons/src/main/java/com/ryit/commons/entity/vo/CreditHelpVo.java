package com.ryit.commons.entity.vo;

import com.ryit.commons.entity.pojo.CreditHelp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author samphin
 * @date 2019-8-29 17:18:24
 */
@Setter
@Getter
@NoArgsConstructor
public class CreditHelpVo implements Serializable {

    private static final long serialVersionUID = 7460906412912350101L;

    private Long id;

    private String ask;

    private Integer sort;

    private String answer;

    public static CreditHelpVo buildVo(CreditHelp po) {
        CreditHelpVo vo = new CreditHelpVo();
        BeanUtils.copyProperties(po, vo);
        return vo;
    }

    /**
     * 将poList->voList
     *
     * @param poList
     * @return
     */
    public static List<CreditHelpVo> buildVoList(List<CreditHelp> poList) {
        List<CreditHelpVo> voList = new ArrayList<>();
        if (CollectionUtils.isEmpty(poList)) {
            return voList;
        }

        poList.stream().forEach(po -> {
            voList.add(buildVo(po));
        });
        return voList;
    }
}