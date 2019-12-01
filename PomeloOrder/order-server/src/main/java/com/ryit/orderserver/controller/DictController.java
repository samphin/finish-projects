package com.ryit.orderserver.controller;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.entity.vo.SysDictVo;
import com.ryit.orderserver.service.ISysDictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 字典管理Controller
 *
 * @author : samphin
 * @Date : 2019-8-31 15:47:10
 */
@RestController
@RequestMapping("/dict")
public class DictController {

    private Logger log = LoggerFactory.getLogger(DictController.class);

    @Autowired
    private ISysDictService sysDictService;

    /**
     * 柚子抢单字典查询
     *
     * @author samphin
     * @date 2019-9-3 10:09:31
     */
    @GetMapping("/anon/grapefruit/list")
    public AjaxResult queryGrapefruitAllDict() {
        try {
            List<SysDictVo> voList = sysDictService.queryAllDict(true, false,null);
            return AjaxResult.success(voList);
        } catch (Exception e) {
            log.error("请求字典信息错误", e);
            return AjaxResult.error();
        }
    }

    /**
     * 查询所有资质信息
     *
     * @return
     */
    @GetMapping("/admin/aptitudes")
    public AjaxResult queryAllAptitudeByAdmin() {
        try {
            List<SysDictVo> aptitudes = this.sysDictService.queryAllAptitude();
            return AjaxResult.success(aptitudes);
        } catch (Exception e) {
            log.error("请求字典信息错误", e);
            return AjaxResult.error();
        }
    }

    /**
     * 用户查询资质信息
     *
     * @return
     */
    @GetMapping("/anon/aptitudes")
    public AjaxResult queryAllAptitude() {
        return this.queryAllAptitudeByAdmin();
    }


    /**
     * 钱包字典查询
     *
     * @author samphin
     * @date 2019-9-3 10:09:31
     */
    @GetMapping("/anon/wallet/list")
    public AjaxResult queryWalletAllDict() {
        try {
            List<SysDictVo> voList = sysDictService.queryAllDict(false, false,null);
            return AjaxResult.success(voList);
        } catch (Exception e) {
            log.error("请求字典信息错误", e);
            return AjaxResult.error();
        }
    }

    /**
     * 柚子抢单APP审核状态
     *
     * @author samphin
     * @date 2019-9-3 10:09:31
     */
    @GetMapping("/pomeloFlag")
    public String getPomeloFlag() {
        try {
            return sysDictService.getPomeloFlag();
        } catch (Exception e) {
            log.error("请求字典信息错误", e);
            return "0";
        }
    }

    /**
     * 查询七一钱包首页贷款最大额度
     *
     * @return
     */
    @GetMapping("/anon/maxQuota")
    public AjaxResult getMaxQuota() {
        try {
            return AjaxResult.success(sysDictService.getMaxQuota());
        } catch (Exception e) {
            log.error("请求字典信息错误", e);
            return AjaxResult.error("请求字典信息错误");
        }
    }


}
