package com.ryit.users.mapper;

import com.ryit.commons.entity.dto.SysBuyerQueryDto;
import com.ryit.commons.entity.pojo.SysUser;
import com.ryit.commons.entity.vo.SysBuyerListVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysUserMapper extends Mapper<SysUser> {

    /**
     * 分页查询
     *
     * @param po
     * @return
     */
    List<SysUser> selectList(SysUser po);

    /**
     * 根据手机号查询用户信息
     *
     * @param mobilePhone
     * @return SysUser
     * @author samphin
     * @since 2019-10-16 17:43:37
     */
    SysUser selectLoginInfoByPhone(@Param("mobilePhone") String mobilePhone);

    /**
     * 校验手机号是否存在
     *
     * @param mobilePhone
     * @return Integer
     * @author samphin
     * @since 2019-10-16 17:46:28
     */
    Integer selectUserByPhone(@Param("mobilePhone") String mobilePhone);

    /**
     * 查询买家列表
     *
     * @param queryDto
     * @return
     */
    List<SysBuyerListVo> selectBuyers(SysBuyerQueryDto queryDto);
}
