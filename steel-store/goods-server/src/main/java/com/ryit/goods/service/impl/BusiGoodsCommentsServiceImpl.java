package com.ryit.goods.service.impl;

import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.service.impl.BaseServiceImpl;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.entity.bson.BusiGoodsComments;
import com.ryit.commons.entity.bson.BusiGoodsCommentsImgs;
import com.ryit.commons.entity.dto.BusiGoodsCommentsDto;
import com.ryit.commons.entity.dto.BusiGoodsCommentsQueryDto;
import com.ryit.commons.entity.dto.BusiGoodsCommentsReplyDto;
import com.ryit.commons.entity.pojo.SysUser;
import com.ryit.commons.entity.vo.BusiGoodsCommentsListVo;
import com.ryit.commons.exception.CustomException;
import com.ryit.commons.util.SnowflakeIdWorker;
import com.ryit.goods.mongo.BusiGoodsCommentsDao;
import com.ryit.goods.mongo.BusiGoodsCommentsImgsDao;
import com.ryit.goods.service.IBusiGoodsCommentsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * 商品评价
 *
 * @author samphin
 * @since 2019-10-25 15:36:56
 */
@Service
public class BusiGoodsCommentsServiceImpl extends BaseServiceImpl implements IBusiGoodsCommentsService {

    @Autowired
    private BusiGoodsCommentsDao busiGoodsCommentsDao;

    @Autowired
    private BusiGoodsCommentsImgsDao busiGoodsCommentsImgsDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public boolean insertGoodsComments(BusiGoodsCommentsDto dto, HttpServletRequest request) {
        try {
            SysUser currentUser = getCurrentUser(request);
            BusiGoodsComments po = new BusiGoodsComments().buildPo(dto);
            Long commentId = SnowflakeIdWorker.generateId();
            po.setId(commentId);
            //默认不删除
            po.setDelStatus(0);
            //新增评价对商品评价
            po.setType(1);
            //1对动态的评价 2对评价的回复
            po.setType(1);
            //默认是0
            po.setParentId(0L);
            //首次评价
            po.setReviewStatus(0);
            po.setCreateUserId(currentUser.getId());
            po.setCreateUserName(currentUser.getUsername());
            po.setCreateDate(new Date());
            busiGoodsCommentsDao.save(po);
            //新增评价图片
            List<String> imageUrls = dto.getImages();
            if (!CollectionUtils.isEmpty(imageUrls)) {
                List<BusiGoodsCommentsImgs> imgPoList = new ArrayList<>();
                for (int i = 0; i < imageUrls.size(); i++) {
                    String imageUrl = imageUrls.get(i);
                    BusiGoodsCommentsImgs imgPo = new BusiGoodsCommentsImgs();
                    imgPo.setId(SnowflakeIdWorker.generateId());
                    imgPo.setCommentId(commentId);
                    imgPo.setFilePath(imageUrl);
                    imgPo.setSort(i);
                    imgPo.setType(1);
                    imgPoList.add(imgPo);
                }
                busiGoodsCommentsImgsDao.saveAll(imgPoList);
            }
            return true;
        } catch (CustomException e) {
            throw new CustomException("保存评价信息失败", e);
        }
    }

    /**
     * 回复评价
     *
     * @param dto 评价信息
     * @return
     */
    @Override
    public boolean replayGoodsComments(BusiGoodsCommentsReplyDto dto, HttpServletRequest request) {
        try {
            //当前回复人信息
            SysUser currentUser = getCurrentUser(request);
            //父级评价信息
            BusiGoodsCommentsDto parentComments = dto.getParentComments();
            //我的评价信息
            BusiGoodsCommentsDto myComments = dto.getMyComments();
            /*myComments.setReplyId(parentComments.getId());
            //如果父ID为0，代表动态评价
            if (0 == parentComments.getParentId()) {
                myComments.setParentId(parentComments.getId());
            } else {
                myComments.setParentId(parentComments.getParentId());
            }*/
            BusiGoodsComments myCommentsPo = new BusiGoodsComments().buildPo(myComments);
            //回复评价类型
            myCommentsPo.setId(SnowflakeIdWorker.generateId());
            myCommentsPo.setType(2);
            myCommentsPo.setDelStatus(0);
            myCommentsPo.setCreateUserId(currentUser.getId());
            myCommentsPo.setCreateUserName(currentUser.getUsername());
            myCommentsPo.setCreateDate(new Date());
            busiGoodsCommentsDao.save(myCommentsPo);
            return true;
        } catch (Exception e) {
            throw new CustomException("回复评价失败", e);
        }
    }

    @Override
    public boolean deleteGoodsComments(Long id) {
        try {
            Optional<BusiGoodsComments> optional = busiGoodsCommentsDao.findById(id);
            optional.ifPresent(comment -> {
                comment.setDelStatus(1);
                busiGoodsCommentsDao.save(comment);
            });
            return true;
        } catch (Exception e) {
            throw new CustomException("评价删除失败", e);
        }
    }

    /**
     * 按创建时间倒序查询商品所有评价信息
     *
     * @return
     * @author samphin
     * @since 2019-10-25 15:35:52
     */
    @Override
    public PageBean<BusiGoodsCommentsListVo> queryPageGoodsComments(BaseQueryDto<BusiGoodsCommentsQueryDto> queryDto) {
        //商品编号
        BusiGoodsCommentsQueryDto queryDtoParam = queryDto.getParam();
        //查询参数
        Query query = new Query();
        query.skip((queryDto.getPageNum() - 1) * queryDto.getPageSize());
        query.limit(queryDto.getPageSize());
        //根据投诉时间倒序
        query.with(new Sort(Sort.Direction.DESC, "createDate"));
        if (StringUtils.isNotBlank(queryDtoParam.getGoodsName())) {
            Pattern pattern = Pattern.compile("^.*" + queryDtoParam.getGoodsName() + ".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("goodsName").regex(pattern));
        }

        if (StringUtils.isNotBlank(queryDtoParam.getContent())) {
            Pattern pattern = Pattern.compile("^.*" + queryDtoParam.getContent() + ".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("content").regex(pattern));
        }

        if (null != queryDtoParam.getCreateStartDate() && null != queryDtoParam.getCreateEndDate()) {
            query.addCriteria(Criteria.where("createDate").gte(queryDtoParam.getCreateStartDate()).lt(queryDtoParam.getCreateEndDate()));
        }

        //不选状态查询，则默认显示未删除的评价
        if (null != queryDtoParam.getDelStatus()) {
            query.addCriteria(Criteria.where("delStatus").is(queryDtoParam.getDelStatus()));
        } else {
            query.addCriteria(Criteria.where("delStatus").is(0));
        }

        //星级评价
        if (null != queryDtoParam.getGrade()) {
            query.addCriteria(Criteria.where("grade").is(queryDtoParam.getGrade()));
        }

        //计算总数
        long total = mongoTemplate.count(query, "Busi_Goods_Comments");

        //查询结果集
        List<BusiGoodsComments> poList = mongoTemplate.find(query, BusiGoodsComments.class, "Busi_Goods_Comments");

        List<BusiGoodsCommentsListVo> voList = new BusiGoodsCommentsListVo().buildVoList(poList);

        return getPageData(queryDto.getPageNum(), queryDto.getPageSize(), total, voList);
    }

    /**
     * 查询APP商品评价
     *
     * @return
     */
    @Override
    public PageBean<BusiGoodsComments> queryAppCommentsList(BaseQueryDto<Long> queryDto) {
        //商品编号
        Long goodsId = queryDto.getParam();
        //查询参数
        Query query = new Query();
        query.skip((queryDto.getPageNum() - 1) * queryDto.getPageSize());
        query.limit(queryDto.getPageSize());
        //根据投诉时间倒序
        query.with(new Sort(Sort.Direction.DESC, "createDate"));
        query.addCriteria(Criteria.where("delStatus").is(0));
        query.addCriteria(Criteria.where("goodsId").is(goodsId));

        //计算总数
        long total = mongoTemplate.count(query, "Busi_Goods_Comments");

        //查询结果集
        List<BusiGoodsComments> poList = mongoTemplate.find(query, BusiGoodsComments.class, "Busi_Goods_Comments");

        return getPageData(queryDto.getPageNum(), queryDto.getPageSize(), total, poList);
    }

    /**
     * 查询我的评价
     *
     * @param queryDto
     * @param request
     * @return
     */
    @Override
    public PageBean<BusiGoodsComments> queryMyComments(BaseQueryDto queryDto, HttpServletRequest request) {
        Integer userId = getCurrentUserId(request);
        //查询参数
        Query query = new Query();
        query.skip((queryDto.getPageNum() - 1) * queryDto.getPageSize());
        query.limit(queryDto.getPageSize());
        //根据投诉时间倒序
        query.with(new Sort(Sort.Direction.DESC, "createDate"));
        query.addCriteria(Criteria.where("delStatus").is(0));
        query.addCriteria(Criteria.where("createUserId").is(userId));

        //计算总数
        long total = mongoTemplate.count(query, "Busi_Goods_Comments");

        //查询结果集
        List<BusiGoodsComments> poList = mongoTemplate.find(query, BusiGoodsComments.class, "Busi_Goods_Comments");

        return getPageData(queryDto.getPageNum(), queryDto.getPageSize(), total, poList);
    }

    @Override
    public List<BusiGoodsComments> queryGoodsComments(Long goodsId) {
        //根据评价创建时间倒序
        Sort sort = Sort.by(Sort.Direction.DESC, "createDate");
        //组装查询条件
        BusiGoodsComments queryParam = new BusiGoodsComments();
        queryParam.setGoodsId(goodsId);
        queryParam.setDelStatus(0);
        ExampleMatcher matcher = ExampleMatcher.matching()
                //改变默认字符串匹配方式：模糊查询
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                //改变默认大小写忽略方式：忽略大小写
                .withIgnoreCase(true)
                //评对评价内容模糊匹配
                .withMatcher("content", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<BusiGoodsComments> example = Example.of(queryParam, matcher);
        List<BusiGoodsComments> poList = busiGoodsCommentsDao.findAll(example, sort);
        if (CollectionUtils.isEmpty(poList)) {
            return poList;
        }
        //只显示最新的两条评价
        return poList.subList(0, 2);
    }
}
