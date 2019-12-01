package com.ryit.commons.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 返回用户登录成功后的信息
 */
@Data
public class SysUserLoginSuccessVo implements Serializable {

    private static final long serialVersionUID = 617337637304406648L;

    private Integer id;

    private String username;

    private String realName;

    private List<SysMenuVo> menus;
}
