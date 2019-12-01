package com.ryit.credithelpserver.controller;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.base.controller.BaseController;
import com.ryit.commons.entity.dto.CreditNotesDto;
import com.ryit.commons.entity.vo.CreditNotesVo;
import com.ryit.credithelpserver.checktor.CreditNotesDtoChecktor;
import com.ryit.credithelpserver.service.ICreditNotesService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;


/**
 * 留言模块Controller
 *
 * @author samphin
 * @date 2019-8-29 16:55:10
 */
@RestController
@RequestMapping("/creditNotes")
public class CreditNotesController extends BaseController {

    @Autowired
    private ICreditNotesService creditNotesService;

    /**
     * 提交留言信息
     *
     * @param creditNotesDto
     * @author samphin
     * @date 2019-9-1 14:02:11
     */
    @PostMapping("/notes")
    public AjaxResult saveNotes(@RequestBody CreditNotesDto creditNotesDto) {
        CreditNotesDtoChecktor.check(creditNotesDto);
        boolean bl = this.creditNotesService.save(creditNotesDto);
        if (bl) {
            return AjaxResult.success("留言提交成功");
        } else {
            return AjaxResult.error();
        }
    }

    /**************************PC端接口***********************************************/
    /**
     * 查看留言列表
     *
     * @param creditNotesDto
     * @return
     */
    @GetMapping("/admin/notes")
    public AjaxResult queryNotesList(@ModelAttribute CreditNotesDto creditNotesDto) {
        startPage(creditNotesDto.getPageNum(), creditNotesDto.getPageSize(), true);
        List<CreditNotesVo> voList = creditNotesService.queryAllByCondition(creditNotesDto);
        return AjaxResult.success(getNewPageData(voList));
    }

    /**
     * 查看留言
     *
     * @param id
     * @return
     */
    @GetMapping("/admin/notes/{id}")
    public AjaxResult queryNotesDetail(@PathVariable("id") Long id) {
        CreditNotesVo vo = creditNotesService.queryOne(id);
        return AjaxResult.success(vo);
    }

    /**
     * 回复留言
     *
     * @param creditNotesDto
     * @return
     */
    @PutMapping("/admin/notes")
    public AjaxResult revertNotes(@RequestBody CreditNotesDto creditNotesDto, HttpServletRequest request) {
        if (StringUtils.isBlank(creditNotesDto.getAnswerContent())) {
            throw new RuntimeException("留言回复信息不能为空");
        }
        Long account = getUserId(request);
        boolean bl = creditNotesService.answer(account,creditNotesDto);
        if (bl) {
            return AjaxResult.success("留言回复成功");
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 删除留言
     *
     * @param id
     * @return
     */
    @DeleteMapping("/admin/notes/{id}")
    public AjaxResult deleteNotes(@PathVariable("id") Long id) {
        boolean bl = creditNotesService.delete(id);
        if (bl) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error("留言已删除");
        }
    }

    /**
     * 批量删除留言信息
     *
     * @param
     * @return
     */
    @DeleteMapping("/admin/notes")
    public AjaxResult deleteHelp(@RequestParam("ids") String ids) {
        if (StringUtils.isBlank(ids)) {
            throw new RuntimeException("请选择要删除的留言");
        }
        List idList = Arrays.asList(ids.split(","));
        boolean bl = creditNotesService.deleteBatch(idList);
        if (bl) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

}
