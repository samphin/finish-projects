package com.ryit.commons.entity.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户菜单、按钮Vo类
 *
 * @author samphin
 * @since 2019-11-13 11:50:28
 */
@Data
public class SysUserMenuVo implements Serializable {

    /**
     * 菜单信息
     */
    List<SysMenuVo> menus = new ArrayList<>();

    /**
     * 按钮信息
     */
    JSONObject buttons = new JSONObject();

}