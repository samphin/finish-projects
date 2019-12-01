package com.ryit.goods.service.impl;

import com.codingapi.txlcn.tc.annotation.TxTransaction;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.service.impl.BaseServiceImpl;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.entity.dto.BusiGoodsDto;
import com.ryit.commons.entity.dto.BusiGoodsPcQueryDto;
import com.ryit.commons.entity.dto.BusiGoodsQueryDto;
import com.ryit.commons.entity.dto.BusiGoodsShelveDto;
import com.ryit.commons.entity.pojo.BusiGoods;
import com.ryit.commons.entity.pojo.BusiGoodsBrand;
import com.ryit.commons.entity.pojo.BusiGoodsImgs;
import com.ryit.commons.entity.pojo.BusiGoodsShape;
import com.ryit.commons.entity.vo.*;
import com.ryit.commons.enums.SystemErrorEnum;
import com.ryit.commons.exception.CustomException;
import com.ryit.commons.util.SnowflakeIdWorker;
import com.ryit.goods.mapper.*;
import com.ryit.goods.service.IBusiGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品管理业务层
 *
 * @author samphin
 * @since 2019-10-24 15:41:30
 */
@Service
public class BusiGoodsServiceImpl extends BaseServiceImpl<Long, BusiGoodsDto, BusiGoodsVo> implements IBusiGoodsService {

    @Autowired
    private BusiGoodsMapper busiGoodsMapper;

    @Autowired
    private BusiGoodsBrandMapper busiGoodsBrandMapper;

    @Autowired
    private BusiGoodsShapeMapper busiGoodsShapeMapper;

    @Autowired
    private BusiGoodsImgsMapper busiGoodsImgsMapper;

    @Autowired
    private BusiGoodsRolledPriceMapper busiGoodsRolledPriceMapper;

    /**
     * 添加商品
     *
     * @param dto
     * @param request
     * @return
     */
    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSelective(BusiGoodsDto dto, HttpServletRequest request) {
        //获取当前用户ID
        int userId = getCurrentUserId(request);
        try {
            BusiGoods po = new BusiGoods().buildPo(dto);
            //创建商品ID
            Long goodsId = SnowflakeIdWorker.generateId();
            po.setId(goodsId);
            po.setCreateUserId(userId);
            po.setCreateDate(new Date());

            //新增商品图片
            List<String> images = dto.getImages();
            if (!CollectionUtils.isEmpty(images)) {
                po.setImgPath(images.get(0));
            }
            //新增商品
            busiGoodsMapper.insertSelective(po);
            //上传图片
            uploadGoodsImages(userId, po.getId(), images);
            return true;
        } catch (Exception e) {
            throw new CustomException("商品保存失败", e);
        }
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean updateByIdSelective(BusiGoodsDto dto, HttpServletRequest request) {
        //获取当前用户ID
        int userId = getCurrentUserId(request);
        try {
            BusiGoods po = new BusiGoods().buildPo(dto);
            po.setId(dto.getId());
            po.setLastUpdateUserId(userId);
            po.setLastUpdateDate(new Date());

            //新增商品图片
            List<String> images = dto.getImages();
            if (!CollectionUtils.isEmpty(images)) {
                po.setImgPath(images.get(0));
            }

            busiGoodsMapper.updateByPrimaryKeySelective(po);
            //上传图片
            uploadGoodsImages(userId, po.getId(), images);

            return true;
        } catch (Exception e) {
            throw new CustomException("商品信息更新失败", e);
        }
    }

    /**
     * 上传商品图片
     *
     * @param images
     */
    private void uploadGoodsImages(Integer userId, Long goodsId, List<String> images) {
        if (!CollectionUtils.isEmpty(images)) {
            //先清空商品对应的老图片
            busiGoodsImgsMapper.deleteGoodsImages(goodsId);
            List<BusiGoodsImgs> imageList = new ArrayList<>();
            images.forEach(imagPath -> {
                BusiGoodsImgs img = new BusiGoodsImgs();
                //绑定商品编号
                img.setId(SnowflakeIdWorker.generateId());
                img.setGoodsId(goodsId);
                //默认图片
                img.setType(0);
                //非图文详情
                img.setDetailsStatus(0);
                img.setFilePath(imagPath);
                img.setCreateUserId(userId);
                img.setCreateDate(new Date());
                img.setLastUpdateUserId(userId);
                img.setLastUpdateDate(new Date());
                imageList.add(img);
            });
            busiGoodsImgsMapper.insertBatch(imageList);
        }
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        return busiGoodsMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public List<BusiGoodsVo> queryListByCondition(BusiGoodsDto dto) {
        BusiGoods po = new BusiGoods().buildPo(dto);
        List<BusiGoods> poList = busiGoodsMapper.select(po);
        return new BusiGoodsVo().buildVoList(poList);
    }

    @Override
    public PageBean<BusiGoodsListVo> queryGoodsList(BaseQueryDto<BusiGoodsPcQueryDto> queryDto) {
        Page<Object> page = PageHelper.startPage(queryDto.getPageNum(), queryDto.getPageSize(), true);
        BusiGoodsPcQueryDto param = queryDto.getParam();
        List<BusiGoods> poList = busiGoodsMapper.selectGoodsList(param);
        List<BusiGoodsListVo> voList = new BusiGoodsListVo().buildVoList(poList);
        return getPageData(voList, page);
    }

    @Override
    public PageBean<BusiGoodsAppListVo> queryAppGoodsList(BaseQueryDto<BusiGoodsQueryDto> queryDto) {
        Page<Object> page = PageHelper.startPage(queryDto.getPageNum(), queryDto.getPageSize(), true);
        BusiGoodsQueryDto param = queryDto.getParam();
        List<BusiGoodsAppListVo> voList = busiGoodsMapper.selectAppGoodsList(param);
        return getPageData(voList, page);
    }

    /**
     * 上架|下架商品
     *
     * @param request
     * @return
     */
    @Override
    public boolean shelve(BusiGoodsShelveDto dto, HttpServletRequest request) {
        //获取当前用户ID
        int userId = getCurrentUserId(request);
        BusiGoods po = new BusiGoods();
        po.setShelfStatus(dto.getShelfStatus());
        po.setLastUpdateUserId(userId);
        po.setLastUpdateDate(new Date());
        Example example = new Example(BusiGoods.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", dto.getIds());
        return busiGoodsMapper.updateByExampleSelective(po, example) > 0;
    }

    /**
     * 查看商品详情
     *
     * @author samphin
     * @since 2019-11-25 16:43:17
     */
    @Override
    public BusiGoodsDetailVo queryGoodsDetail(Long id) {
        //查询商品信息
        BusiGoods po = busiGoodsMapper.selectByPrimaryKey(id);

        BusiGoodsDetailVo vo = new BusiGoodsDetailVo().buildVo(po);

        List<String> images = busiGoodsImgsMapper.selectGoodsImages(id);
        vo.setImages(images);
        //如果是异型板，必须查询出异型板今日废铁价格
        if (po.getShapeCode() == 2) {
            Integer newestPrice = busiGoodsRolledPriceMapper.selectNewestScrapIronPrice();
            vo.setScrapIronPrice(newestPrice);
        }
        return vo;
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteImages(long imageId, String imagePath) {
        try {
            //删除图片记录
            busiGoodsImgsMapper.deleteByPrimaryKey(imageId);
            //删除文件服务器对应图片
            //weedfsClient.delete(imagePath);
            return true;
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), e);
        }
    }

    /**
     * 查询商品分类列表
     *
     * @return
     */
    @Override
    public List<BusiGoodsBrandListVo> queryGoodsBrand() {
        Example example = new Example(BusiGoodsBrand.class);
        example.selectProperties("id", "brandName").orderBy("sort").asc();
        List<BusiGoodsBrand> poList = busiGoodsBrandMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(poList)) {
            return new ArrayList<>();
        }
        return Lists.transform(poList, po -> {
            BusiGoodsBrandListVo vo = new BusiGoodsBrandListVo();
            vo.setBrandId(po.getId());
            vo.setBrandName(po.getBrandName());
            return vo;
        });
    }

    /**
     * 查询所有商品形状列表
     *
     * @return
     */
    @Override
    public List<BusiGoodsShapeListVo> queryGoodsShape() {
        Example example = new Example(BusiGoodsShape.class);
        example.selectProperties("id", "name", "code").orderBy("code").asc();
        List<BusiGoodsShape> poList = busiGoodsShapeMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(poList)) {
            return new ArrayList<>();
        }
        return Lists.transform(poList, po -> {
            BusiGoodsShapeListVo vo = new BusiGoodsShapeListVo();
            vo.setId(po.getId());
            vo.setName(po.getName());
            vo.setCode(po.getCode());
            return vo;
        });
    }


    /**
     * 查询商品横拉版宽度
     *
     * @return
     * @author samphin
     */
    @Override
    public List<Double> queryGoodsShapeWidth(Long brandId) {
        try {
            Example example = new Example(BusiGoods.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("brandId", brandId).andEqualTo("shapeCode", 3);
            example.selectProperties("defaultWidth").setDistinct(true);
            List<BusiGoods> poList = busiGoodsMapper.selectByExample(example);
            List<Double> width = poList.stream().map(BusiGoods::getDefaultWidth).
                    sorted(Comparator.comparing(Double::valueOf)).collect(Collectors.toList());
            return width;
        } catch (Exception e) {
            throw new CustomException(SystemErrorEnum.GOODS_SHAPE3_WIDTH_ERROR);
        }
    }
}