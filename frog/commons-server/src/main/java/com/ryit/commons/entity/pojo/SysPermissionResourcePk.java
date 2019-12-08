package com.ryit.commons.entity.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SysPermissionResourcePk implements Serializable {

    private static final long serialVersionUID = 5415114623238251652L;

    private Integer permissionId;

    private Integer resourceId;

    /**
     * 是否叶子节点0-否 1-是
     */
    private Integer leaf;

    public SysPermissionResourcePk(Integer permissionId, Integer resourceId, Integer leaf) {
        this.permissionId = permissionId;
        this.resourceId = resourceId;
        this.leaf = leaf;
    }

    public List<SysPermissionResourcePk> buildPoList(Integer permissionId, List<Integer> resourceIds, Integer leaf) {

        List<SysPermissionResourcePk> poList = new ArrayList<>();

        resourceIds.forEach(resourceId -> {
            SysPermissionResourcePk po = new SysPermissionResourcePk(permissionId, resourceId, leaf);
            poList.add(po);
        });

        return poList;
    }
}