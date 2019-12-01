package com.ryit.commons.entity.vo;

import com.ryit.commons.entity.pojo.CreditUser;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 非管理员用户
 *
 * @author : samphin
 * @date : 2019-10-18 15:08:40
 */
@Data
public class CreditNotAdminUserVo implements Serializable {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 真实姓名
     */
    private String realName;

    public static List<CreditNotAdminUserVo> buildVoList(List<CreditUser> poList) {
        List<CreditNotAdminUserVo> voList = new ArrayList<>();
        if (CollectionUtils.isEmpty(poList)) {
            return voList;
        }

        poList.forEach(po -> {
            CreditNotAdminUserVo vo = new CreditNotAdminUserVo();
            BeanUtils.copyProperties(po, vo);
            voList.add(vo);
        });
        return voList;
    }
}
