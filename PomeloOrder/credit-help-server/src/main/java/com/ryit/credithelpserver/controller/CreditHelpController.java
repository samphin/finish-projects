package com.ryit.credithelpserver.controller;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.base.controller.BaseController;
import com.ryit.commons.entity.dto.CreditHelpDto;
import com.ryit.commons.entity.vo.CreditHelpVo;
import com.ryit.credithelpserver.service.ICreditHelpService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 帮助模块Controller
 *
 * @author samphin
 * @date 2019-8-29 16:55:10
 */
@RestController
@RequestMapping("/creditHelp")
public class CreditHelpController extends BaseController {

    private Logger log = LoggerFactory.getLogger(CreditHelpController.class);

    @Autowired
    private ICreditHelpService creditHelpService;

    /**
     * 查询问答列表
     *
     * @author samphin
     * @date 2019-9-1 12:47:59
     */
    @GetMapping("/anon/list")
    public AjaxResult queryHelpList() {
        try {
            List<CreditHelpVo> voList = creditHelpService.queryAllByCondition(null);
            return AjaxResult.success(voList);
        } catch (Exception e) {
            log.error("请求帮助问答列表错误", e);
            return AjaxResult.error();
        }
    }

    /**************************PC端接口***********************************************/
    /**
     * 帮助列表
     *
     * @param creditHelpDto
     * @return
     */
    @GetMapping("/admin/helps")
    public AjaxResult queryList(@ModelAttribute CreditHelpDto creditHelpDto) {
        startPage(creditHelpDto.getPageNum(), creditHelpDto.getPageSize(),true);
        List<CreditHelpVo> voList = creditHelpService.queryAllByCondition(creditHelpDto);
        return AjaxResult.success(getNewPageData(voList));
    }

    /**
     * 查看帮助详情
     *
     * @param id
     * @return
     */
    @GetMapping("/admin/helps/{id}")
    public AjaxResult queryHelpDetail(@PathVariable("id") Long id) {
        CreditHelpVo vo = creditHelpService.queryOne(id);
        return AjaxResult.success(vo);
    }

    /**
     * 新增帮助信息
     *
     * @param creditHelpDto
     * @return
     */
    @PostMapping("/admin/helps")
    public AjaxResult saveHelp(@RequestBody CreditHelpDto creditHelpDto) {
        boolean bl = creditHelpService.save(creditHelpDto);
        if (bl) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 更新帮助信息
     *
     * @param creditHelpDto
     * @return
     */
    @PutMapping("/admin/helps")
    public AjaxResult updateHelp(@RequestBody CreditHelpDto creditHelpDto) {
        return saveHelp(creditHelpDto);
    }

    /**
     * 删除帮助信息
     *
     * @param id
     * @return
     */
    @DeleteMapping("/admin/helps/{id}")
    public AjaxResult deleteHelp(@PathVariable("id") Long id) {
        boolean bl = creditHelpService.delete(id);
        if (bl) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error("帮助信息已删除");
        }
    }

    /**
     * 批量删除帮助信息
     * @param
     * @return
     */
    @DeleteMapping("/admin/helps")
    public AjaxResult deleteHelp(@RequestParam("ids")String ids) {
        if(StringUtils.isBlank(ids)){
            throw new RuntimeException("请选择要删除的内容。");
        }
        List idList = Arrays.asList(ids.split(","));
        boolean bl = creditHelpService.deleteBatch(idList);
        if (bl) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }
}
