package com.ryit.commons.base.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.service.IBaseService;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.constants.JwtConstant;
import com.ryit.commons.constants.RedisConstants;
import com.ryit.commons.entity.pojo.SysUser;
import com.ryit.commons.exception.CustomException;
import com.ryit.commons.util.JwtUtil;
import com.ryit.commons.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 公共Service接口实现类
 *
 * @param <PK> 业务主键
 * @param <D>  业务Dto
 * @param <V>  业务Vo
 * @author samphin
 * @since 2019-10-24 11:34:58
 */
public class BaseServiceImpl<PK, D, V> implements IBaseService<PK, D, V> {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean insertBatch(List<D> dto) {

        return false;
    }

    @Override
    public boolean insert(D dto) {

        return false;
    }

    @Override
    public boolean insert(D dto, HttpServletRequest httpServletRequest) {
        return false;
    }

    @Override
    public boolean insertSelective(D dto) {
        return false;
    }

    @Override
    public boolean insertSelective(D dto, HttpServletRequest httpServletRequest) {
        return false;
    }

    @Override
    public boolean updateById(D dto) {
        return false;
    }

    @Override
    public boolean updateById(D dto, HttpServletRequest httpServletRequest) {
        return false;
    }

    @Override
    public boolean updateByIdSelective(D dto) {
        return false;
    }

    @Override
    public boolean updateByIdSelective(D dto, HttpServletRequest httpServletRequest) {
        return false;
    }

    @Override
    public boolean deleteById(PK id) {
        return false;
    }

    @Override
    public boolean deleteBatch(List<PK> ids) {
        return false;
    }

    /**
     * 无条件查询
     *
     * @return
     */
    @Override
    public List<V> queryList() {
        return null;
    }

    /**
     * 条件查询
     *
     * @param dto
     * @return
     */
    @Override
    public List<V> queryListByCondition(D dto) {

        return null;
    }

    /**
     * 条件查询
     *
     * @param dto
     * @return
     */
    @Override
    public List<V> queryListByCondition(D dto, HttpServletRequest request) {

        return null;
    }

    /**
     * 分页查询
     *
     * @param queryDto
     * @return
     */
    @Override
    public PageBean<V> queryPageList(BaseQueryDto<D> queryDto) {
        return null;
    }

    /**
     * 分页查询
     *
     * @param queryDto
     * @return
     */
    @Override
    public PageBean<V> queryPageList(BaseQueryDto<D> queryDto, HttpServletRequest request) {
        return null;
    }

    @Override
    public int queryCount(D dto) {
        return 0;
    }

    @Override
    public V queryById(PK id) {
        return null;
    }

    /**
     * 获取分页对象
     *
     * @return
     * @author samphin
     * @since 2019-10-23 20:17:31
     */
    protected PageBean getPageData(List voList, Page page) {
        PageBean pageData = new PageBean();
        pageData.setList(voList);
        pageData.setPageNum(page.getPageNum());
        pageData.setPageSize(page.getPageSize());
        pageData.setPages(page.getPages());
        pageData.setTotal(page.getTotal());
        return pageData;
    }

    /**
     * 将Mongodb分页对象转换成系统通用分页对象
     *
     * @param pageData
     * @return
     * @author samphin
     * @since 2019-11-13 09:04:12
     */
    protected PageBean getPageData(org.springframework.data.domain.Page pageData) {
        return getPageData(pageData, null);
    }

    /**
     * @param pageData
     * @param dataList
     * @return
     */
    protected PageBean getPageData(org.springframework.data.domain.Page pageData, List dataList) {
        PageBean pageBean = new PageBean();
        pageBean.setTotal(pageData.getTotalElements());
        pageBean.setPages(pageData.getTotalPages());
        pageBean.setPageNum(pageData.getPageable().getPageNumber());
        pageBean.setPageSize(pageData.getPageable().getPageSize());
        if (CollectionUtils.isEmpty(dataList)) {
            pageBean.setList(pageData.getContent());
        } else {
            pageBean.setList(dataList);
        }
        return pageBean;
    }

    /**
     * @param dataList
     * @return
     */
    protected PageBean getPageData(int pageNum, int pageSize, long total, List dataList) {
        PageBean pageBean = new PageBean();
        pageBean.setTotal(total);
        pageBean.setPages((int) Math.ceil((double) total / (double) pageSize));
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setList(dataList);
        return pageBean;
    }

    /**
     * 获取当前登录人ID
     *
     * @param request
     * @return
     * @throws CustomException
     */
    public Integer getCurrentUserId(HttpServletRequest request) throws CustomException {

        SysUser user = getCurrentUser(request);

        return user.getId();
    }

    /**
     * 获取当前登录人信息
     *
     * @param request
     * @return
     * @throws CustomException
     */
    public SysUser getCurrentUser(HttpServletRequest request) throws CustomException {

        String token = request.getHeader(JwtConstant.AUTHORIZATION);

        // 解密获得account，用于和数据库进行对比
        String account = JwtUtil.getClaim(token, JwtConstant.ACCOUNT);

        // 查询用户是否存在
        ObjectMapper objectMapper = new ObjectMapper();

        SysUser user = objectMapper.convertValue(redisUtil.getObject(String.format(RedisConstants.PREFIX_USER_INFO, account)), SysUser.class);

        return user;
    }

}
