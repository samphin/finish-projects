package com.ryit.commons.entity.vo;

import com.ryit.commons.entity.pojo.SysOrder;
import com.ryit.commons.entity.pojo.SysOrderAptitude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 订单详情VO类
 *
 * @author : samphin
 * @date : 2019-9-18 15:28:17
 */
@Getter
@Setter
@NoArgsConstructor
public class SysOrderDetailVo extends SysOrder {

    /**
     * 真实姓名（通过性别转换成的X先生或X女士）
     */
    private String realName;

    /**
     * 所在城市名
     */
    private String cityName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 婚姻状况
     */
    private String maritalStatus;

    /**
     * 最高学历
     */
    private String eduLevel;

    /**
     * 职业类型
     */
    private String careersType;

    /**
     * 当前单位工龄
     */
    private String workAge;

    /**
     * 单位性质
     */
    private String companyType;

    /**
     * 单位名称
     */
    private String companyName;

    /**
     * 营业执照
     */
    private String businessLicense;

    /**
     * 月经营流水
     */
    private String managerFlow;

    /**
     * 经营年限
     */
    private String managerYear;

    /**
     * 个人信用
     */
    private String personalCredit;

    /**
     * 退单图片
     */
    private String backImg;

    /**
     * 退单图片数组
     */
    private String[] backImgs;

    /**
     * 抢单用户ID
     */
    private Long creditUserId;

    /**
     * 抢单用户剩余退单次数
     */
    private Integer backOrderNum;

    /**
     * 发布人名称
     */
    private String releaseUserName;

    /**
     * 订单信息来源（1：H5；2：PC管理后台）
     */
    private Integer source;

    /**
     * 订单用户资质信息
     */
    private List<SysOrderAptitude> aptitudes;
}
