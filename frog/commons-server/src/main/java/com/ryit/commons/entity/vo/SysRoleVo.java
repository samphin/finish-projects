package com.ryit.commons.entity.vo;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.ryit.commons.entity.pojo.SysRole;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class SysRoleVo implements Serializable {

    private static final long serialVersionUID = 591289043972089330L;

    private Integer id;

    private String name;

    private Boolean status;

    private String description;

    private Integer version;

    //转换Po-->Vo
    public static SysRoleVo buildSysRole(SysRole sysRole) {
        SysRoleVo vo = new SysRoleVo();
        BeanUtils.copyProperties(sysRole, vo);
        vo.setId(sysRole.getId());
        return vo;
    }

    //转换List<Po>-->List<Vo>
    public static List<SysRoleVo> buildSysRoleList(List<SysRole> sysRoles) {
        if (CollectionUtils.isEmpty(sysRoles)) {
            return new ArrayList<SysRoleVo>();
        }
        List<SysRoleVo> voList = Lists.transform(sysRoles, new Function<SysRole, SysRoleVo>() {
            @Override
            public SysRoleVo apply(SysRole sysRole) {
                return buildSysRole(sysRole);
            }
        });
        return voList;
    }
}