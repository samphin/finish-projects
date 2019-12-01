package com.ryit.orderserver.controller;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.base.controller.BaseController;
import com.ryit.commons.entity.dto.SysApplicationDto;
import com.ryit.commons.entity.enums.ApplicationTypeEnum;
import com.ryit.commons.entity.vo.SysApplicationVersionVo;
import com.ryit.commons.entity.vo.SysApplicationVo;
import com.ryit.orderserver.checktor.SysApplicationDtoChecktor;
import com.ryit.orderserver.service.ISysApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 应用管理Controller
 *
 * @author : samphin
 * @Date : 2019-10-11 14:19:47
 */
@RestController
@RequestMapping("/api")
public class SysApplicationController extends BaseController {

    @Autowired
    private ISysApplicationService sysApplicationService;

    /**
     * 查询应用最新版本号
     *
     * @param code APP编码
     * @return
     */
    @GetMapping("/anon/applications")
    public AjaxResult queryApplicationUpdateInfo(@RequestParam("code") String code) {
        SysApplicationVersionVo sysApplicationVo = sysApplicationService.queryByCode(code);
        return AjaxResult.success(sysApplicationVo);
    }

    //============================================================PC端接口======================================================

    /**
     * 新增应用版本信息
     *
     * @author samphin
     * @date 2019-10-11 14:34:42
     */
    @PostMapping("/admin/applications")
    public AjaxResult saveApplications(@RequestBody SysApplicationDto sysApplicationDto, HttpServletRequest request) {
        SysApplicationDtoChecktor.check(sysApplicationDto);
        long account = getUserId(request);
        Boolean bl = sysApplicationService.save(account, sysApplicationDto);
        if (bl) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 修改应用版信息
     *
     * @author samphin
     * @date 2019-10-11 15:42:32
     */
    @PatchMapping("/admin/applications")
    public AjaxResult updateApplications(@RequestBody SysApplicationDto sysApplicationDto, HttpServletRequest request) {
        return saveApplications(sysApplicationDto, request);
    }

    /**
     * APP信息下拉框值
     *
     * @author samphin
     * @date 2019-10-11 16:02:52
     */
    @GetMapping("/admin/applications/name")
    public AjaxResult queryApplicationNames() {
        List<Map<String, Object>> maps = ApplicationTypeEnum.queryAllApplications();
        return AjaxResult.success(maps);
    }

    /**
     * 查询应用版本列表
     *
     * @author samphin
     * @date 2019-10-11 15:42:14
     */
    @GetMapping("/admin/applications")
    public AjaxResult queryApplications(@ModelAttribute SysApplicationDto sysApplicationDto) {
        startPage(sysApplicationDto.getPageNum(), sysApplicationDto.getPageSize(), true);
        List<SysApplicationVo> voList = sysApplicationService.queryAllByCondition(sysApplicationDto);
        return AjaxResult.success(getNewPageData(voList));
    }

    /**
     * 查询应用版本详情
     *
     * @return
     * @author samphin
     * @date 2019-10-11 15:42:03
     */
    @GetMapping("/admin/applications/{id}")
    public AjaxResult queryApplications(@PathVariable("id") Long id) {
        SysApplicationVo vo = sysApplicationService.queryOne(id);
        return AjaxResult.success(vo);
    }

    /**
     * 查询应用版本详情
     *
     * @return
     * @author samphin
     * @date 2019-10-11 15:42:03
     */
    @DeleteMapping("/admin/applications/{id}")
    public AjaxResult deleteApplications(@PathVariable("id") Long id) {
        boolean bl = sysApplicationService.delete(id);
        if(bl){
            return AjaxResult.success();
        }else{
            return AjaxResult.error();
        }

    }
}
