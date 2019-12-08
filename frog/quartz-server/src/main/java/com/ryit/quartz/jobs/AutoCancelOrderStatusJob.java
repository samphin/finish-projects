package com.ryit.quartz.jobs;

import com.alibaba.fastjson.JSONArray;
import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.entity.dto.BusiOrderDto;
import com.ryit.commons.entity.vo.BusiOrderVo;
import com.ryit.commons.enums.SystemErrorEnum;
import com.ryit.commons.exception.CustomException;
import com.ryit.quartz.feign.BusiOrderFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * 每天定时同步钢铁价格
 *
 * @author samphin
 * @since 2019-10-22 11:36:54
 */
@Component
@Async
@Slf4j
public class AutoCancelOrderStatusJob {

    /**
     * 定时周期，每隔15秒
     */
    private static final String JOB_CRON = "0/15 * * * * ?";

    @Autowired
    private BusiOrderFeignClient busiOrderFeignClient;

    /**
     * 执行方法
     *
     * @author samphin
     * @since 2019-10-29 10:07:45
     */
    @Scheduled(cron = JOB_CRON)
    public void excute() {

        LocalDateTime startTime = LocalDateTime.now();

        log.info("执行时间 = " + startTime);
        try {
            //调用业务接口实现同步操作


            //查询出所有未支付订单
            BusiOrderDto orderDto = new BusiOrderDto();
            //未支付
            orderDto.setPayStatus(0);
            //未删除
            orderDto.setDelStatus(0);
            //订单状态是待付款
            orderDto.setStatus(0);
            ResponseData<String> responseData = busiOrderFeignClient.queryAllNoPayOrder(orderDto);
            if (responseData.getCode() != HttpStatus.SC_OK) {
                throw new CustomException(SystemErrorEnum.ORDER_NO_PAY_LIST_ERROR);
            }
            //转换订单列表
            List<BusiOrderVo> orderVoList = JSONArray.parseArray(responseData.getData(), BusiOrderVo.class);
            //批量修改订单状态
            orderVoList.forEach(order -> {
                //订单未支付，且已超过30分钟未支付
                if (order.getPayStatus() == 0 && isExpire(order)) {
                    //订单号
                    long orderId = order.getId();
                    //订单编号
                    String orderNo = order.getOrderNo();
                    //取出订单信息后，将订单信息状态改为取消
                    busiOrderFeignClient.autoCancel(orderId);
                    //打印消息
                    log.info("订单号：" + orderNo + "已自动取消");
                }
            });

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        LocalDateTime endTime = LocalDateTime.now();
        StringBuilder sbMsg = new StringBuilder(100);
        sbMsg.append("，开始时间：").append(startTime);
        sbMsg.append("，结束时间:").append(endTime);
        sbMsg.append("，耗时：").append(startTime.until(endTime, ChronoUnit.SECONDS)).append("秒");
        log.info(sbMsg.toString());
    }

    /**
     * 是否已超时
     *
     * @param orderVo
     * @return
     */
    private boolean isExpire(BusiOrderVo orderVo) {
        long time = System.currentTimeMillis() - orderVo.getCreateDate().getTime();
        if (time > 60 * 60 * 1000) {
            return true;
        } else {
            return false;
        }
    }
}
