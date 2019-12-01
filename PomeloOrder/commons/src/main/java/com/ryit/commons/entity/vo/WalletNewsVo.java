package com.ryit.commons.entity.vo;

import com.ryit.commons.entity.pojo.WalletNews;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/9/17 0017下午 3:34
 */
@Getter
@Setter
@NoArgsConstructor
public class WalletNewsVo extends WalletNews {

    /**
     * 对查询的时间与当前时间进行比对 得出的字符串 如 1天前
     */
    private String date;

    public static List<WalletNewsVo> buildVoList(List<WalletNews> poList) {
        List<WalletNewsVo> voList = new ArrayList<>();
        if (CollectionUtils.isEmpty(poList)) {
            return voList;
        }

        poList.stream().forEach(po -> {
            WalletNewsVo vo = new WalletNewsVo();
            BeanUtils.copyProperties(po, vo);
            voList.add(vo);
        });
        return voList;
    }
}
