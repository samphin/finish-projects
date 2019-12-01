package com.ryit.commons.entity.dto;

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
public class CreditNotAdminUserDto extends BaseQueryDto implements Serializable {

    private static final long serialVersionUID = 8174783855647341369L;

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
}
