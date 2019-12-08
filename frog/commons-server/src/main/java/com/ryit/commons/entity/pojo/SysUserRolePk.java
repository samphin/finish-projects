package com.ryit.commons.entity.pojo;

import com.ryit.commons.entity.dto.SysUserRolePkDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SysUserRolePk implements Serializable {

    private static final long serialVersionUID = -7234222816545725210L;

    private Integer userId;

    private Integer roleId;

    public SysUserRolePk(Integer userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    /**
     * 将组与角色信息转换成集合
     *
     * @param dto
     * @return
     */
    public List<SysUserRolePk> buildSysGroupRole(SysUserRolePkDto dto) {
        String[] roleIdArray = dto.getRoleIds().split(",");
        List<SysUserRolePk> sysUserRolePkList = new ArrayList<>();
        for (String roleId : roleIdArray) {
            SysUserRolePk sysUserRolePk = new SysUserRolePk(dto.getUserId(), Integer.valueOf(roleId));
            sysUserRolePkList.add(sysUserRolePk);
        }
        return sysUserRolePkList;
    }
}