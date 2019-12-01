package com.ryit.commons.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色权限对象
 *
 * @author samphin
 * @since 2019-10-30 10:46:33
 */
@Data
public class RolesPermissionsVo {

    /**
     * 角色
     */
    private List<String> roles = new ArrayList<>();

    /**
     * 权限信息
     */
    private List<String> permissions = new ArrayList<>();
}
