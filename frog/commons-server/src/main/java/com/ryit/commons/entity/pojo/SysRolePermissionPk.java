package com.ryit.commons.entity.pojo;

import com.ryit.commons.entity.dto.SysRolePermissionPkDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SysRolePermissionPk implements Serializable {

    private static final long serialVersionUID = 3807856588487367198L;

    private Integer roleId;

    private Integer permissionId;

    public SysRolePermissionPk(Integer roleId, Integer permissionId) {
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    public List<SysRolePermissionPk> buildPoList(SysRolePermissionPkDto dto) {

        List<SysRolePermissionPk> poList = new ArrayList<>();

        String[] permissionIds = dto.getPermissionIds().split(",");

        for (String permissionId : permissionIds) {
            SysRolePermissionPk po = new SysRolePermissionPk(dto.getRoleId(), Integer.valueOf(permissionId));
            poList.add(po);
        }
        return poList;
    }
}