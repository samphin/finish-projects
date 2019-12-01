package com.ryit.commons.base.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.base.PageData;
import com.ryit.commons.constant.JwtConstant;
import com.ryit.commons.web.exception.user.CustomException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author : 刘修
 * @Date : 2019/8/22 10:09
 */
@Component
public class BaseController {

    @Value("${default-page-size}")
    private Integer defaultPageSize;

    /**
     * 分页对象，分页查询时，用来获取总记录数
     * @author samphin
     * @date 2019-9-6 15:11:26
     */
    private static Page PAGE = new Page();

    /**
     * APP分页
     * @param page
     */
    public void startPage(Integer page) {
        PAGE = PageHelper.startPage(page, defaultPageSize,true);
    }

    public void startPage(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize,true);
    }

    /**
     * @param pageNum  页码值
     * @param pageSize 页大小
     * @param count    是否进行count查询
     * @author samphin
     * @date 2019-9-6 14:57:08
     */
    public void startPage(Integer pageNum, Integer pageSize, boolean count) {
        PAGE = PageHelper.startPage(pageNum, pageSize, count);
    }

    /**
     * @author samphin
     * @date 2019-9-6 14:56:42
     */
    public PageData getNewPageData(List<?> list) {
        PageData pageData = new PageData();
        pageData.setRows(list);
        pageData.setTotal(PAGE.getTotal());
        return pageData;
    }

    /**
     * @param list
     * @return
     */
    public PageData getPageData(List<?> list) {
        PageData pageData = new PageData();
        pageData.setRows(list);
        //总记录条数
        long total = PAGE.getTotal();
        pageData.setTotal(total);
        pageData.setTotalPages(PAGE.getPages());
        return pageData;
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows) {
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    protected Long getUserId(HttpServletRequest request) throws CustomException {
        String account = request.getHeader(JwtConstant.ACCOUNT);
        if (StringUtils.isEmpty(account)) {
            throw new CustomException("请先登录");
        }
        return Long.valueOf(account);
    }
}
