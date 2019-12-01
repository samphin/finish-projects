package com.ryit.commons.entity.vo;

import com.ryit.commons.entity.pojo.SysOrder;
import com.ryit.commons.entity.pojo.SysOrderAptitude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单列表VO类
 * @author : 刘修
 * @Date : 2019/8/20 11:24
 */
@Getter
@Setter
@NoArgsConstructor
public class SysOrderVo extends SysOrder {

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 所在城市名
     */
    private String cityName;

    /**
     * 信用卡额度
     */
    private String creditCard;

    /**
     * 是否有信用卡
     */
    private String creditCardFlag;

    /**
     * 公积金
     */
    private String accumulatioinFund;

    /**
     * 社保
     */
    private String socialSecurity;

    /**
     * 房产
     */
    private String houseProperty;

    /**
     * 车产
     */
    private String carProperty;

    /**
     * 寿险
     */
    private String lifeInsurance;

    /**
     * 芝麻信用
     */
    private String sesameCredit;

    /**
     * 微粒贷
     */
    private String microfinance;

    /**
     * 手机号
     */
    private String phone;

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
     * 订单用户资质信息
     */
    private List<SysOrderAptitude> aptitudes;

    //========================基本信息======================================
    /**
     * 最高学历
     
     */
    private String eduLevel;

    /**
     * 婚姻状况
     
     */
    private String maritalStatus;

    /**
     * 职业类型
     
     */
    private String careersType;

    /**
     * 单位性质
     
     */
    private String companyType;

    /**
     * 月收入
     
     */
    private String monthCash;

    /**
     * 工资发放形式
     
     */
    private String wagesWay;

    /**
     * 当前单位工龄
     
     */
    private String workAge;

    /**
     * 工作城市
     
     */
    private Integer city;

    /**
     * 单位名称
     
     */
    private String companyName;

    /**
     * 月经营流水
     
     */
    private String managerFlow;

    /**
     * 经营年限
     
     */
    private String managerYear;

    /**
     * 营业执照
     
     */
    private String businessLicense;

    /**
     *  发布时间 字符串
     */
    private String releaseTimeStr;

    /**
     * 抢单用户剩余退单次数
     */
    private Integer backOrderNum;

    public static List<SysOrderVo> buildVoList(List<SysOrder> poList) {
        List<SysOrderVo> voList = new ArrayList<>();
        if (CollectionUtils.isEmpty(poList)) {
            return voList;
        }

        poList.stream().forEach(po -> {
            SysOrderVo vo = new SysOrderVo();
            BeanUtils.copyProperties(po, vo);
            voList.add(vo);
        });
        return voList;
    }
}
