package com.ryit.credituserserver.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.base.controller.BaseController;
import com.ryit.commons.entity.dto.CreditMessageDto;
import com.ryit.commons.entity.dto.CreditMessageStandardDto;
import com.ryit.commons.entity.dto.CreditUserDto;
import com.ryit.commons.entity.vo.CreditMessageStandardVo;
import com.ryit.commons.entity.vo.CreditMessageVo;
import com.ryit.credituserserver.service.CreditUserService;
import com.ryit.credituserserver.service.ICreditMessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 柚子抢单用户消息管理
 *
 * @author samphin
 * @date 2019-8-31 20:11:41
 */
@RestController
@RequestMapping("/creditMessage")
public class CreditMessageController extends BaseController {

    @Autowired
    private CreditUserService creditUserService;

    @Autowired
    private ICreditMessageService creditMessageService;

    /**
     * 设置是否推送消息
     *
     * @author samphin
     * @date 2019-9-1 14:27:09
     */
    @PostMapping("/changeMessageFlag")
    public AjaxResult changeMessageFlag(@RequestBody CreditUserDto creditUserDto, HttpServletRequest request) {
        if (null == creditUserDto.getMessageFlag()) {
            return AjaxResult.error();
        }
        Long account = getUserId(request);
        creditUserDto.setId(account);
        Boolean bl = creditUserService.changeMessageFlag(creditUserDto);
        if (bl) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 保存订单偏好
     *
     * @param creditMessageStandardDto 订单偏好对象
     * @return
     */
    @PostMapping("/billBehavior")
    public AjaxResult saveBillBehavior(@RequestBody CreditMessageStandardDto creditMessageStandardDto, HttpServletRequest request) {
        Long account = getUserId(request);
        Boolean bl = creditMessageService.saveBillBehavior(account, creditMessageStandardDto);
        if (bl) {
            return AjaxResult.success("订单偏好保存成功");
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 查询订单偏好
     *
     * @author samphin
     * @date 2019-9-1 14:26:20
     */
    @GetMapping("/billBehavior")
    public AjaxResult queryBillBehavior(HttpServletRequest request) {
        Long account = getUserId(request);
        CreditMessageStandardVo vo = creditMessageService.queryBillBehavior(account);
        return AjaxResult.success(vo);
    }

    /**
     * 保存消息信息
     *
     * @param messageDtoList
     * @return
     * @author samphin
     * @date 2019-9-30 10:16:32
     */
    @PostMapping("/save")
    public AjaxResult saveMessage(@RequestBody List<CreditMessageDto> messageDtoList) {
        Boolean bl = creditMessageService.saveMessage(messageDtoList);
        if (bl) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 阅读消息
     *
     * @param requestData 请求对象
     * @author samphin
     * @date 2019-9-30 10:19:42
     */
    @PostMapping("/read")
    public AjaxResult readMessage(@RequestBody JSONObject requestData, HttpServletRequest request) {
        JSONArray ids = requestData.getJSONArray("ids");
        if(CollectionUtils.isEmpty(ids)){
            return AjaxResult.error("消息ID不能为空");
        }
        Long account = getUserId(request);
        List<Long> idList = ids.toJavaList(Long.class);
        Boolean bl = creditMessageService.updateMessageReadStatus(account, idList);
        if (bl) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 查询我的历史消息
     *
     * @author samphin
     * @date 2019-9-1 09:53:19
     */
    @GetMapping("/historyMessage")
    public AjaxResult historyMessage(@RequestParam(value = "page", defaultValue = "1") Integer page, HttpServletRequest request) {
        Long account = getUserId(request);
        startPage(page);
        List<CreditMessageVo> voList = creditMessageService.queryHistoryMessage(account);
        return AjaxResult.success(getPageData(voList));
    }


}
