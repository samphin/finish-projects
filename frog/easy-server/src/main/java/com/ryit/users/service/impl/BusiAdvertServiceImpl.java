package com.ryit.users.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.service.impl.BaseServiceImpl;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.entity.dto.BusiAdvertDto;
import com.ryit.commons.entity.pojo.BusiAdvert;
import com.ryit.commons.entity.pojo.BusiAdvertImg;
import com.ryit.commons.entity.vo.BusiAdvertVo;
import com.ryit.commons.entity.vo.BusiHomePageAdvertVo;
import com.ryit.commons.exception.CustomException;
import com.ryit.commons.util.SnowflakeIdWorker;
import com.ryit.users.mapper.BusiAdvertImgMapper;
import com.ryit.users.mapper.BusiAdvertMapper;
import com.ryit.users.service.IBusiAdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BusiAdvertServiceImpl extends BaseServiceImpl<Long, BusiAdvertDto, BusiAdvertVo> implements IBusiAdvertService {

    @Autowired
    private BusiAdvertMapper busiAdvertMapper;

    @Autowired
    private BusiAdvertImgMapper busiAdvertImgMapper;

    @Override
    public boolean insertSelective(BusiAdvertDto dto, HttpServletRequest request) {
        Integer userId = getCurrentUserId(request);
        try {
            BusiAdvert po = new BusiAdvert().buildPo(dto);
            long advertId = SnowflakeIdWorker.generateId();
            po.setId(advertId);
            po.setCreateUserId(userId);
            po.setCreateDate(new Date());
            po.setLastUpdateUserId(userId);
            po.setLastUpdateDate(new Date());
            busiAdvertMapper.insertSelective(po);

            //保存广告图片
            List<String> images = dto.getImages();
            if (!CollectionUtils.isEmpty(images)) {
                saveAdvertImage(advertId, userId, images);
            }
            return true;
        } catch (Exception e) {
            throw new CustomException("广告信息保存失败", e);
        }
    }

    @Override
    public boolean updateByIdSelective(BusiAdvertDto dto, HttpServletRequest request) {
        Integer userId = getCurrentUserId(request);
        try {
            BusiAdvert po = new BusiAdvert().buildPo(dto);
            po.setLastUpdateUserId(userId);
            po.setLastUpdateDate(new Date());
            busiAdvertMapper.updateByPrimaryKeySelective(po);

            //保存广告图片
            List<String> images = dto.getImages();
            if (!CollectionUtils.isEmpty(images)) {
                long advertId = po.getId();
                //先清空广告图片
                busiAdvertImgMapper.deleteByAdvertId(advertId);
                saveAdvertImage(advertId, userId, images);
            }
            return true;
        } catch (Exception e) {
            throw new CustomException("广告信息修改失败", e);
        }
    }

    /**
     * 保存广告图片
     *
     * @param images
     */
    private void saveAdvertImage(Long advertId, Integer userId, List<String> images) {
        List<BusiAdvertImg> imageList = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            BusiAdvertImg image = new BusiAdvertImg();
            image.setId(SnowflakeIdWorker.generateId());
            image.setAdvertId(advertId);
            image.setPath(images.get(i));
            image.setSort(i);
            image.setCreateUserId(userId);
            image.setCreateDate(new Date());
            image.setLastUpdateUserId(userId);
            image.setLastUpdateDate(new Date());
            imageList.add(image);
        }
        busiAdvertImgMapper.insertBatch(imageList);
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            busiAdvertMapper.deleteByPrimaryKey(id);
            //先清空广告图片
            busiAdvertImgMapper.deleteByAdvertId(id);
            return true;
        } catch (Exception e) {
            throw new CustomException("广告信息删除失败", e);
        }
    }

    @Override
    public List<BusiAdvertVo> queryListByCondition(BusiAdvertDto dto) {
        Example example = new Example(BusiAdvert.class);
        Example.Criteria criteria = example.createCriteria();
        //发布
        criteria.andEqualTo("releaseStatus", 1);
        //有效
        criteria.andEqualTo("validStatus", 1);
        //首页广告
        criteria.andEqualTo("homeStatus", 1);
        //正序
        Example.OrderBy orderBy = example.orderBy("sort").asc();
        List<BusiAdvert> poList = busiAdvertMapper.selectByExample(example);
        List<BusiAdvertVo> voList = new BusiAdvertVo().buildVoList(poList);

        return voList;
    }

    /**
     * APP首页广告信息
     *
     * @return
     */
    @Override
    public List<BusiHomePageAdvertVo> queryAdvertInfo() {
        return busiAdvertMapper.selectAdvertInfo();
    }

    @Override
    public PageBean<BusiAdvertVo> queryPageList(BaseQueryDto<BusiAdvertDto> queryDto) {
        Page<Object> page = PageHelper.startPage(queryDto.getPageNum(), queryDto.getPageSize(), true);

        BusiAdvertDto dto = queryDto.getParam();

        BusiAdvert po = new BusiAdvert().buildPo(dto);

        List<BusiAdvert> poList = busiAdvertMapper.selectList(po);

        List<BusiAdvertVo> voList = new BusiAdvertVo().buildVoList(poList);

        return getPageData(voList, page);
    }

    @Override
    public BusiAdvertVo queryById(Long id) {
        BusiAdvert po = busiAdvertMapper.selectByPrimaryKey(id);
        BusiAdvertVo vo = new BusiAdvertVo().buildVo(po);
        //查询广告图片
        List<String> images = busiAdvertImgMapper.selectImagesByAdvertId(id);
        vo.setImages(images);
        return vo;
    }
}