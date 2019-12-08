package com.ryit.commons.entity.vo;

import com.ryit.commons.base.vo.BaseVo;
import com.ryit.commons.entity.pojo.SysPermission;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class SysPermissionVo extends BaseVo<Integer, SysPermission, SysPermissionVo> implements Serializable {

    private String name;

    private String code;

    private Boolean status;

    private String description;

    /**
     * 权限绑定的资源信息
     */
    private List<Integer> resourceIds = new ArrayList<>();
}