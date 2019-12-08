package com.ryit.order.service.impl;

import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.service.impl.BaseServiceImpl;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.entity.bson.BusiGoodsComplaint;
import com.ryit.commons.entity.dto.BusiGoodsComplaintDto;
import com.ryit.commons.entity.dto.BusiGoodsComplaintQueryDto;
import com.ryit.commons.entity.dto.BusiGoodsHandlerComplaintDto;
import com.ryit.commons.entity.pojo.SysUser;
import com.ryit.commons.entity.vo.BusiComplaintVo;
import com.ryit.commons.entity.vo.BusiGoodsComplaintListVo;
import com.ryit.commons.exception.CustomException;
import com.ryit.commons.util.SnowflakeIdWorker;
import com.ryit.order.mongo.BusiGoodsComplaintDao;
import com.ryit.order.service.IBusiComplaintService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class BusiComplaintServiceImpl extends BaseServiceImpl<Long, BusiGoodsComplaintDto, BusiComplaintVo> implements IBusiComplaintService {

    @Autowired
    private BusiGoodsComplaintDao busiGoodsComplaintDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public boolean saveComplaint(BusiGoodsComplaintDto dto, HttpServletRequest request) {
        SysUser currentUser = getCurrentUser(request);
        BusiGoodsComplaint po = new BusiGoodsComplaint().buildPo(dto);
        po.setId(SnowflakeIdWorker.generateId());
        //处理中
        po.setStatus(0);
        po.setComplaintUserId(currentUser.getId());
        po.setComplaintUserName(currentUser.getRealName());
        po.setComplaintDate(new Date());
        return busiGoodsComplaintDao.save(po) != null;
    }

    @Override
    public PageBean queryComplaintList(BaseQueryDto<BusiGoodsComplaintQueryDto> queryDto) {

        //查询参数
        BusiGoodsComplaintQueryDto param = queryDto.getParam();

        Query query = new Query();
        query.skip((queryDto.getPageNum() - 1) * queryDto.getPageSize());
        query.limit(queryDto.getPageSize());
        //根据投诉时间倒序
        query.with(new Sort(Sort.Direction.DESC, "complaintDate"));

        //订单号
        if (StringUtils.isNotBlank(param.getOrderNo())) {
            query.addCriteria(Criteria.where("orderNo").is(param.getOrderNo()));
        }

        //商品名称
        if (StringUtils.isNotBlank(param.getGoodsName())) {
            Pattern pattern = Pattern.compile("^.*" + param.getGoodsName() + ".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("goodsName").regex(pattern));
        }

        if (null != param.getStatus()) {
            query.addCriteria(Criteria.where("status").is(param.getStatus()));
        }

        //投诉时间查询
        if (null != param.getComplaintStartDate() && null != param.getComplaintEndDate()) {
            query.addCriteria(Criteria.where("complaintDate").gt(param.getComplaintStartDate()).lte(param.getComplaintEndDate()));
        }

        //投诉人名称
        if (StringUtils.isNotBlank(param.getComplaintUserName())) {
            Pattern pattern = Pattern.compile("^.*" + param.getComplaintUserName() + ".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("complaintUserName").regex(pattern));
        }

        //计算总数
        long total = mongoTemplate.count(query, "Busi_Goods_Complaint");

        //查询结果集
        List<BusiGoodsComplaint> poList = mongoTemplate.find(query, BusiGoodsComplaint.class, "Busi_Goods_Complaint");
        List<BusiGoodsComplaintListVo> voList = new BusiGoodsComplaintListVo().buildVoList(poList);

        return getPageData(queryDto.getPageNum(), queryDto.getPageSize(), total, voList);
    }

    @Override
    public boolean handlerComplaint(BusiGoodsHandlerComplaintDto dto, HttpServletRequest request) {
        SysUser currentUser = getCurrentUser(request);
        Optional<BusiGoodsComplaint> tempEntityOptional = busiGoodsComplaintDao.findById(dto.getId());
        if (!tempEntityOptional.isPresent()) {
            throw new CustomException("投诉信息不存在");
        }
        BusiGoodsComplaint tempEntity = tempEntityOptional.get();
        tempEntity.setHandlerUserId(currentUser.getId());
        tempEntity.setHandlerUserName(currentUser.getRealName());
        tempEntity.setHandlerExplain(dto.getHandlerExplain());
        tempEntity.setHandlerDate(new Date());
        //处理完成
        tempEntity.setStatus(1);
        return busiGoodsComplaintDao.save(tempEntity) != null;
    }

    @Override
    public PageBean<BusiComplaintVo> queryMyComplaints(BaseQueryDto queryDto, HttpServletRequest request) {
        Integer userId = getCurrentUserId(request);
        //根据评价创建时间倒序
        Sort sort = Sort.by(Sort.Direction.DESC, "complaintDate");
        //组装查询条件
        BusiGoodsComplaint queryParam = new BusiGoodsComplaint();
        queryParam.setComplaintUserId(userId);
        //创建分页对象
        PageRequest pageRequest = PageRequest.of(queryDto.getPageNum() - 1, queryDto.getPageSize(), sort);
        ExampleMatcher matcher = ExampleMatcher.matching()
                //改变默认字符串匹配方式：模糊查询
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                //改变默认大小写忽略方式：忽略大小写
                .withIgnoreCase(true)
                //评对投诉内容模糊匹配
                .withMatcher("complaintContent", ExampleMatcher.GenericPropertyMatchers.contains())
                //忽略属性，不参与查询;
                .withIgnorePaths("pageNum", "pageSize");
        Example<BusiGoodsComplaint> example = Example.of(queryParam, matcher);
        org.springframework.data.domain.Page<BusiGoodsComplaint> pageData = busiGoodsComplaintDao.findAll(example, pageRequest);

        return getPageData(pageData);
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            busiGoodsComplaintDao.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new CustomException("投诉删除失败", e);
        }
    }

    @Override
    public BusiComplaintVo queryById(Long id) {
        Optional<BusiGoodsComplaint> optional = busiGoodsComplaintDao.findById(id);
        if (!optional.isPresent()) {
            return null;
        }

        BusiGoodsComplaint po = optional.get();
        return new BusiComplaintVo().buildVo(po);
    }
}