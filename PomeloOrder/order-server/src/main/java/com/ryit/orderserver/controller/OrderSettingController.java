package com.ryit.orderserver.controller;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.base.controller.BaseController;
import com.ryit.commons.entity.dto.BaseQueryDto;
import com.ryit.commons.entity.dto.SysDictDto;
import com.ryit.commons.entity.dto.SysOrderSettingDto;
import com.ryit.commons.entity.vo.SysDictListVo;
import com.ryit.commons.entity.vo.SysOrderSettingVo;
import com.ryit.orderserver.service.ISysDictService;
import com.ryit.orderserver.service.ISysOrderSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单设置Controller
 *
 * @author : samphin
 * @Date : 2019-9-2 14:47:04
 */
@RestController
@RequestMapping("/orderSetting")
public class OrderSettingController extends BaseController {

    private Logger log = LoggerFactory.getLogger(OrderSettingController.class);

    @Autowired
    private ISysOrderSettingService sysOrderSettingService;

    /**
     * 字典-->资质分值设置
     */
    @Autowired
    private ISysDictService sysDictService;

    /**
     * 保存订单金额标准信息
     *
     * @author samphin
     * @date 2019-9-2 08:59:42
     */
    @PostMapping("/admin/settings")
    public AjaxResult saveOrderSetting(@RequestBody List<SysOrderSettingDto> sysOrderSettingDtoList) {
        boolean bl = sysOrderSettingService.saveOrderSetting(sysOrderSettingDtoList);
        if (bl) {
            return AjaxResult.success("订单金额标准保存成功");
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 查询订单金额标准备信息
     *
     * @author samphin
     * @date 2019-9-2 08:59:42
     */
    @GetMapping("/admin/settings")
    public AjaxResult queryOrderSetting() {
        List<SysOrderSettingVo> voList = sysOrderSettingService.queryAllCondition();
        return AjaxResult.success(voList);
    }

    /**
     * 查询资质分值信息
     *
     * @author samphin
     * @date 2019-9-9 12:48:37
     */
    @GetMapping("/admin/aptitudes")
    public AjaxResult queryAllAptitude(@ModelAttribute BaseQueryDto queryDto) {
        startPage(queryDto.getPageNum(),queryDto.getPageSize(),true);
        List<SysDictListVo> voList = sysDictService.queryAllAptitudeByOrderSetting();
        return AjaxResult.success(getNewPageData(voList));
    }

    /**
     * 保存资质分值信息
     *
     * @author samphin
     * @date 2019-9-9 12:48:37
     */
    @PatchMapping("/admin/aptitudes")
    public AjaxResult saveAptitude(@RequestBody SysDictDto sysDictDto) {
        boolean bl = sysDictService.updateAptitude(sysDictDto);
        if (bl) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

}
