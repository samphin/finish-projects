package com.ryit.commons.entity.vo;

import com.alibaba.fastjson.JSONObject;
import com.ryit.commons.entity.pojo.CreditBill;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 流水统计Vo类
 * @author : samphin
 * @date : 2019-9-2 17:42:16
 */
@Getter
@Setter
@NoArgsConstructor
public class CreditBillStatisticsVo implements Serializable {

    /**
     * 抢单信息
     */
    private JSONObject consumeInfo = new JSONObject();

    /**
     * 退单信息
     */
    private JSONObject backOrderInfo = new JSONObject();

    /**
     * 充值信息
     */
    private JSONObject rechargeInfo = new JSONObject();

}
