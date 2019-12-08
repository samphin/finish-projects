package com.ryit.users.mapper;

import com.ryit.commons.entity.pojo.SysUserRolePk;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysUserRolePkMapper extends Mapper<SysUserRolePk> {

    /**
     * 给用户设置角色
     *
     * @param userRolePkList
     * @return
     */
    int saveBatch(List<SysUserRolePk> userRolePkList);

}