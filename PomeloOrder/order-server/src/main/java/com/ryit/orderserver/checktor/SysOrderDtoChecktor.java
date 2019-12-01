package com.ryit.orderserver.checktor;

import com.ryit.commons.entity.dto.SysOrderDto;
import com.ryit.commons.entity.pojo.SysOrderAptitude;
import com.ryit.commons.web.exception.user.CustomException;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/9/20 0020下午 4:27
 */
public class SysOrderDtoChecktor {

    public static void check(SysOrderDto dto) {

        if (null == dto) {
            throw new CustomException("信息不能为空");
        }

        if (StringUtils.isEmpty(dto.getEduLevel())) {
            throw new CustomException("用户最高学历不能为空");
        }
        if (StringUtils.isEmpty(dto.getMaritalStatus())) {
            throw new CustomException("用户婚姻状态不能为空");
        }
        if (StringUtils.isEmpty(dto.getCareersType())) {
            throw new CustomException("用户职业类型不能为空");
        }
        if (dto.getCity() == null) {
            throw new CustomException("用户工作城市不能为空");
        }
        
        if ("上班族/公务员".equals(dto.getCareersType())) {
            if (StringUtils.isEmpty(dto.getCompanyType())) {
                throw new CustomException("单位性质不能为空");
            }
            if (StringUtils.isEmpty(dto.getWagesWay())) {
                throw new CustomException("工资发放形式不能为空");
            }
            if (StringUtils.isEmpty(dto.getWorkAge())) {
                throw new CustomException("当前单位工龄不能为空");
            }
        } else if ("企业法人/个体户".equals(dto.getCareersType())) {
            if (StringUtils.isEmpty(dto.getCompanyName())) {
                throw new CustomException("单位名称不能为空");
            }
            if (dto.getManagerFlow() == null) {
                throw new CustomException("月经营流水不能为空");
            }
            if (StringUtils.isEmpty(dto.getManagerYear())) {
                throw new CustomException("经营年限不能为空");
            }
            if (StringUtils.isEmpty(dto.getBusinessLicense())) {
                throw new CustomException("营业执照不能为空");
            }
        } else {
            if (StringUtils.isEmpty(dto.getMonthCash())) {
                throw new CustomException("月收入不能为空");
            }
        }

        //校验资质信息
        List<SysOrderAptitude> aptitudes = dto.getAptitudes();
        if (CollectionUtils.isEmpty(aptitudes)) {
            throw new CustomException("资质信息不能为空");
        }
        for (SysOrderAptitude aptitude : aptitudes) {
            if (StringUtils.isEmpty(aptitude.getLabel()) || aptitude.getValue() == null) {
                throw new CustomException(aptitude.getName() + "信息不能为空");
            }
        }
        if (null == dto.getWalletUserId() && null == dto.getUserInfo()) {
            throw new CustomException("用户基本信息不能为空");
        }
    }
}
