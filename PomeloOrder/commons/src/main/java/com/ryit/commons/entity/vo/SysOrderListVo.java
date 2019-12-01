package com.ryit.commons.entity.vo;

import com.ryit.commons.entity.pojo.SysOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * PC后台订单列表对象
 *
 * @author : samphin
 * @Date : 2019/8/20 11:24
 */
@Getter
@Setter
@NoArgsConstructor
public class SysOrderListVo implements Serializable {

    private static final long serialVersionUID = -851872380868153743L;

    private Long id;

    /**
     * 贷款金额
     */
    private Double orderMoney;

    /**
     * 贷款期限
     */
    private String orderTerm;

    /**
     * 贷款人真实姓名
     */
    private String realName;

    /**
     * 抢单价格
     */
    private Double orderCoin;

    /**
     * 贷款目的
     */
    private String orderReason;

    /**
     * 订单备注
     */
    private String orderRemark;

    /**
     * 是否被抢(0:否1:是)
     */
    private String orderStatus;

    /**
     * 是否通话(0:否1:是)
     */
    private String callFlag;

    /**
     * 是否删除(0:否1:是)
     */
    private String delFlag;

    /**
     * 是否完结(0:否1:是)
     */
    private String finishFlag;

    /**
     * 发布人名称
     */
    private String releaseUserName;

    /**
     * 发布时间
     */
    private Date releaseTime;

    /**
     * 贷款用户
     */
    private Integer walletUserId;

    /**
     * 抢单账单id
     */
    private Long payId;

    /**
     * 退单原因
     */
    private String backReason;

    /**
     * 抢单时间
     */
    private Date payTime;

    /**
     * 退单账单id
     */
    private Integer backId;

    /**
     * 退单时间
     */
    private Date backTime;

    /**
     * 退单失败反馈
     */
    private String errorReason;

    /**
     * 退单状态(0:未退单1:退单审核2:退单成功3:退单失败)
     */
    private Integer backStatus;

    /**
     * 所属产品id
     */
    private Integer productId;

    /**
     * 订单信息来源（1：H5；2：PC管理后台）
     */
    private Integer source;

    /**
     * 来源网站编码
     */
    private String sourceCode;

    /**
     * 来源网站简称
     */
    private String sourceName;

    /**
     * 来源网站地址
     */
    private String sourceUrl;

    //===========================基本信息=================================
    /**
     * 最高学历
     * isNullAble:1
     */
    private String eduLevel;

    /**
     * 婚姻状况
     * isNullAble:1
     */
    private String maritalStatus;

    /**
     * 职业类型
     * isNullAble:1
     */
    private String careersType;

    /**
     * 单位性质
     * isNullAble:1
     */
    private String companyType;

    /**
     * 月收入
     * isNullAble:1
     */
    private String monthCash;

    /**
     * 工资发放形式
     * isNullAble:1
     */
    private String wagesWay;

    /**
     * 当前单位工龄
     * isNullAble:1
     */
    private String workAge;

    /**
     * 工作城市
     * isNullAble:1
     */
    private Integer city;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 单位名称
     * isNullAble:1
     */
    private String companyName;

    /**
     * 月经营流水
     * isNullAble:1
     */
    private String managerFlow;

    /**
     * 经营年限
     * isNullAble:1
     */
    private String managerYear;

    /**
     * 营业执照
     * isNullAble:1
     */
    private String businessLicense;

    /**
     * poList->voList
     *
     * @param poList
     * @author samphin
     * @date 2019-9-4 11:13:43
     */
    public static List<SysOrderListVo> buildVoList(List<SysOrder> poList) {
        List<SysOrderListVo> voList = new ArrayList<>();

        if (CollectionUtils.isEmpty(poList)) {
            return voList;
        }

        poList.stream().forEach(po -> {
            SysOrderListVo vo = new SysOrderListVo();
            BeanUtils.copyProperties(po, vo);
            vo.setOrderStatus(po.getOrderStatus() == 0 ? "否" : "是");
            vo.setCallFlag(po.getCallFlag() == 0 ? "否" : "是");
            vo.setDelFlag(po.getDelFlag() == 0 ? "否" : "是");
            vo.setFinishFlag(po.getFinishFlag() == 0 ? "否" : "是");
            voList.add(vo);
        });

        return voList;
    }

}
