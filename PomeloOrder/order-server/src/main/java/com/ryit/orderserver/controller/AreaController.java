package com.ryit.orderserver.controller;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.entity.pojo.SysArea;
import com.ryit.orderserver.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : 刘修
 * @Date : 2019/8/29 9:53
 */
@RestController
@RequestMapping("/area")
public class AreaController {

    private Logger log = LoggerFactory.getLogger(AreaController.class);

    @Autowired
    private AreaService areaService;

    /**
     * 查询地区列表
     *
     * @return
     */
    @GetMapping("/admin/getAreaList")
    public AjaxResult getAreaList() {
        try {
            List<SysArea> areaList = areaService.getAreaList();
            return AjaxResult.success(areaList);
        } catch (Exception e) {
            log.error("请求地区列表错误", e);
            return AjaxResult.error(0, "请求地区列表错误");
        }
    }

    /**
     * 无需权限查询地区列表
     *
     * @return
     */
    @GetMapping("/anon/getAreaList")
    public AjaxResult queryAreaList() {
        try {
            return this.getAreaList();
        } catch (Exception e) {
            log.error("请求地区列表错误", e);
            return AjaxResult.error(0, "请求地区列表错误");
        }
    }
}
