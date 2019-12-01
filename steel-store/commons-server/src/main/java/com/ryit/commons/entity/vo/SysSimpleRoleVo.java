package com.ryit.commons.entity.vo;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.ryit.commons.entity.pojo.SysRole;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户或用户组--->角色列表
 */
@Data
public class SysSimpleRoleVo implements Serializable {

    private static final long serialVersionUID = 1354478650781244690L;

    private Integer id;

    private String code;

    private String name;

    /**
     * poList->voList
     *
     * @param roleList
     * @return
     */
    public static List<SysSimpleRoleVo> buildSysGroupRoleVoList(List<SysRole> roleList) {
        if (CollectionUtils.isEmpty(roleList)) {
            return new ArrayList<SysSimpleRoleVo>();
        }
        List<SysSimpleRoleVo> sysGroupRoleVoList = Lists.transform(roleList, new Function<SysRole, SysSimpleRoleVo>() {
            @Override
            public SysSimpleRoleVo apply(SysRole sysRole) {
                SysSimpleRoleVo vo = new SysSimpleRoleVo();
                vo.setId(sysRole.getId());
                vo.setName(sysRole.getName());
                vo.setCode(sysRole.getCode());
                return vo;
            }
        });
        return sysGroupRoleVoList;
    }
}
