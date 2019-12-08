package com.ryit.order.service.impl;

import com.codingapi.txlcn.tc.annotation.TxTransaction;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Joiner;
import com.ryit.commons.base.dto.BaseQueryDto;
import com.ryit.commons.base.service.impl.BaseServiceImpl;
import com.ryit.commons.base.vo.PageBean;
import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.entity.dto.*;
import com.ryit.commons.entity.enums.PayTypeEnum;
import com.ryit.commons.entity.pojo.BusiOrder;
import com.ryit.commons.entity.pojo.BusiOrderCertificate;
import com.ryit.commons.entity.pojo.BusiOrderGoods;
import com.ryit.commons.entity.pojo.BusiOrderReturn;
import com.ryit.commons.entity.vo.*;
import com.ryit.commons.enums.SystemErrorEnum;
import com.ryit.commons.exception.CustomException;
import com.ryit.commons.util.OrderNoWorker;
import com.ryit.commons.util.SnowflakeIdWorker;
import com.ryit.order.feign.IBusiGoodsFeignClient;
import com.ryit.order.feign.ISysUserFeignClient;
import com.ryit.order.mapper.BusiOrderCertificateMapper;
import com.ryit.order.mapper.BusiOrderGoodsMapper;
import com.ryit.order.mapper.BusiOrderMapper;
import com.ryit.order.mapper.BusiOrderReturnMapper;
import com.ryit.order.service.IBusiOrderService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BusiOrderServiceImpl extends BaseServiceImpl<Long, BusiOrderDto, BusiOrderVo> implements IBusiOrderService {

    @Autowired
    private BusiOrderMapper busiOrderMapper;

    @Autowired
    private BusiOrderReturnMapper busiOrderReturnMapper;

    @Resource
    private IBusiGoodsFeignClient busiGoodsFeignClient;

    @Resource
    private ISysUserFeignClient sysUserFeignClient;

    @Autowired
    private BusiOrderGoodsMapper busiOrderGoodsMapper;

    @Autowired
    private BusiOrderCertificateMapper busiOrderCertificateMapper;

    /**
     * 提交订单
     *
     * @return
     */
    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class)
    public Long submitOrders(BusiSubmitOrdersDto ordersDto, HttpServletRequest request) {
        //获取当前用户ID
        int userId = getCurrentUserId(request);
        //判断当前户公司信息是否存在
        if (!isExistCompany(userId)) {
            throw new CustomException(SystemErrorEnum.USER_COMPANY_EMPTY_ERROR);
        }

        //保存商品规格信息(并获取规格所有ID)
        ResponseData<List<BusiGoodsSkuInfoVo>> responseData = busiGoodsFeignClient.saveBatch(ordersDto.getSkuList());
        if (responseData.getCode() != HttpStatus.SC_OK) {
            throw new CustomException(responseData.getMsg());
        }
        try {
            //获取订单提交中保存成功的规格信息
            List<BusiGoodsSkuInfoVo> skuInfoVoList = responseData.getData();
            //设置订单ID
            long orderId = saveOrder(userId, ordersDto, skuInfoVoList);
            //保存订单商品关联
            saveOrderGoods(userId, orderId, skuInfoVoList);
            //汇总订单中所有商品ID
            Set<Long> goodsIdSet = skuInfoVoList.stream().map(BusiGoodsSkuInfoVo::getGoodsId).collect(Collectors.toSet());
            //下单成功后，清空对应购物车信息
            busiGoodsFeignClient.deleteTrolleyGoods(goodsIdSet);

            return orderId;
        } catch (Exception e) {
            throw new CustomException("提交订单失败", e);
        }
    }

    /**
     * 代理下单
     *
     * @param ordersDto
     * @return
     */
    @Override
    public boolean proxySubmitOrders(BusiProxySubmitOrdersDto ordersDto) {

        //获取代理人ID
        int userId = ordersDto.getProxyUserId();
        //判断当前户公司信息是否存在
        if (!isExistCompany(userId)) {
            throw new CustomException(SystemErrorEnum.USER_COMPANY_EMPTY_ERROR);
        }

        //保存商品规格信息(并获取规格所有ID)
        ResponseData<List<BusiGoodsSkuInfoVo>> responseData = busiGoodsFeignClient.saveBatch(ordersDto.getSkuList());
        if (responseData.getCode() != HttpStatus.SC_OK) {
            throw new CustomException(responseData.getMsg());
        }

        try {
            //获取订单提交中保存成功的规格信息
            List<BusiGoodsSkuInfoVo> skuInfoVoList = responseData.getData();
            //设置订单ID
            long orderId = saveOrder(userId, ordersDto, skuInfoVoList);
            //保存订单商品关联
            saveOrderGoods(userId, orderId, skuInfoVoList);
            return true;
        } catch (Exception e) {
            throw new CustomException("代理下单失败", e);
        }
    }

    /**
     * 保存订单并返回订单ID
     *
     * @param userId
     * @param ordersDto
     * @param skuInfoVoList
     * @return
     */
    private Long saveOrder(Integer userId, BusiSubmitOrdersDto ordersDto, List<BusiGoodsSkuInfoVo> skuInfoVoList) {
        //转换订单信息
        BusiOrder po = new BusiOrder().buildPo(ordersDto);

        //设置订单ID
        long orderId = SnowflakeIdWorker.generateId();
        //设置订单ID
        po.setId(orderId);
        //设置订单号
        po.setOrderNo(OrderNoWorker.generateOrderNo(userId));
        po.setCreateDate(new Date());
        po.setCreateUserId(userId);
        po.setPayCode(PayTypeEnum.CASH.getCode());
        po.setPayName(PayTypeEnum.CASH.getName());

        //根据规格计算订单总额
        double orderTotalAmount = skuInfoVoList.stream().mapToDouble(BusiGoodsSkuInfoVo::getTotalPrice).sum();
        //设置订单总金额
        po.setTotalAmount(orderTotalAmount);
        busiOrderMapper.insertSelective(po);

        return orderId;
    }

    /**
     * 保存订单商品关联
     *
     * @param userId
     * @param orderId
     * @param skuInfoVoList
     */
    private void saveOrderGoods(int userId, long orderId, List<BusiGoodsSkuInfoVo> skuInfoVoList) {
        List<BusiOrderGoods> orderGoodsList = new ArrayList<>();
        skuInfoVoList.forEach(skuInfoVo -> {
            //保存订单与商品关联信息
            BusiOrderGoods orderGoods = new BusiOrderGoods();
            orderGoods.setId(SnowflakeIdWorker.generateId());
            orderGoods.setOrderId(orderId);
            orderGoods.setGoodsId(skuInfoVo.getGoodsId());
            orderGoods.setGoodsName(skuInfoVo.getGoodsName());
            orderGoods.setGoodsImg(skuInfoVo.getGoodsImg());
            orderGoods.setSkuId(skuInfoVo.getId());
            orderGoods.setSkuNum(skuInfoVo.getAmount());
            orderGoods.setSkuPrice(skuInfoVo.getPrice());
            orderGoods.setCreateUserId(userId);
            orderGoods.setCreateDate(new Date());
            orderGoodsList.add(orderGoods);
        });

        busiOrderGoodsMapper.insertBatch(orderGoodsList);
    }

    /**
     * 查询当前用户是否存在公司信息
     *
     * @return
     */
    private boolean isExistCompany(int userId) {
        ResponseData<BusiCompanyVo> responseData = sysUserFeignClient.queryCompanyInfoByUserId(userId);
        if (responseData.getCode() != HttpStatus.SC_OK) {
            throw new CustomException(responseData.getMsg());
        }
        BusiCompanyVo data = responseData.getData();
        return null != data ? true : false;
    }

    /**
     * 修改订单
     *
     * @param dto
     * @return
     */
    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean updateByIdSelective(BusiOrderDto dto, HttpServletRequest request) {
        //获取当前用户ID
        int userId = getCurrentUserId(request);
        BusiOrder po = new BusiOrder().buildPo(dto);
        po.setLastUpdateUserId(userId);
        po.setLastUpdateDate(new Date());
        return busiOrderMapper.updateByPrimaryKeySelective(po) > 0;
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        return busiOrderMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public List<BusiOrderVo> queryListByCondition(BusiOrderDto dto) {
        try {
            BusiOrder po = new BusiOrder().buildPo(dto);
            List<BusiOrder> poList = busiOrderMapper.select(po);
            return new BusiOrderVo().buildVoList(poList);
        } catch (Exception e) {
            throw new CustomException(SystemErrorEnum.ORDER_NO_PAY_LIST_ERROR);
        }
    }

    /**
     * 查看订单详情(PC端)
     *
     * @param id
     * @return
     */
    @Override
    public BusiOrderVo queryById(Long id) {
        BusiOrder po = busiOrderMapper.selectByPrimaryKey(id);
        return new BusiOrderVo().buildVo(po);
    }

    /**
     * 查询我的订单列表
     *
     * @param queryDto
     * @param request
     * @return
     */
    @Override
    public PageBean queryMyOrderList(BaseQueryDto<Integer> queryDto, HttpServletRequest request) {
        Integer userId = getCurrentUserId(request);
        Page<Object> page = PageHelper.startPage(queryDto.getPageNum(), queryDto.getPageSize());
        BusiOrderQueryDto dto = new BusiOrderQueryDto();
        dto.setCreateUserId(userId);
        dto.setStatus(queryDto.getParam());
        List<BusiOrderListVo> orders = busiOrderMapper.selectOrderList(dto);
        List<BusiOrderMyListVo> voList = new BusiOrderMyListVo().buildVoList(orders);
        return getPageData(voList, page);
    }

    /**
     * 填写订单发票信息
     *
     * @author samphin
     * @since 2019-11-21 17:48:58
     */
    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean updateOrderInvoice(BusiOrderInvoiceDto dto, HttpServletRequest request) {
        //获取当前用户ID
        int userId = getCurrentUserId(request);
        BusiOrder po = new BusiOrder().buildPo(dto);
        po.setLastUpdateUserId(userId);
        po.setLastUpdateDate(new Date());
        return busiOrderMapper.updateByPrimaryKeySelective(po) > 0;
    }

    @Override
    public PageBean queryOrderList(BaseQueryDto<BusiOrderQueryDto> queryDto) {
        Page<Object> page = PageHelper.startPage(queryDto.getPageNum(), queryDto.getPageSize());
        BusiOrderQueryDto dto = queryDto.getParam();
        List<BusiOrderListVo> voList = busiOrderMapper.selectOrderList(dto);
        return getPageData(voList, page);
    }

    /**
     * 查询我的拼团订单详情
     *
     * @param queryDto
     * @return
     */
    @Override
    public List<BusiOrderListVo> queryMyGroupOrderList(BusiOrderGroupDetailDto queryDto) {
        //如果是已拼成，则查询出和我一起购买相同商品的最近两人的下单记录
        List<BusiOrderListVo> voList = busiOrderMapper.selectMyGroupOrderList(queryDto.getGoodsId(), queryDto.getCreateDate());
        return voList;
    }

    /**
     * APP查询订单详情
     *
     * @param id
     * @return
     */
    @Override
    public BusiOrderDetailVo queryOrderDetail(Long id) {
        //查询订单详情
        BusiOrderDetailVo detailVo = busiOrderMapper.selectOrderDetail(id);
        //查询订单对应的商品规格信息
        List<BusiOrderGoodsListVo> orderGoods = busiOrderGoodsMapper.queryOrderGoodsList(id);
        detailVo.setOrderGoods(orderGoods);

        //查询订单支付凭证
        Example example1 = new Example(BusiOrderCertificate.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("orderId", id);
        List<BusiOrderCertificate> orderCertificates = busiOrderCertificateMapper.selectByExample(example1);
        List<BusiOrderCertificateVo> certificatesVoList = new BusiOrderCertificateVo().buildVoList(orderCertificates);
        List<String> images = certificatesVoList.stream().map(BusiOrderCertificateVo::getImageId).collect(Collectors.toList());
        detailVo.setCertificates(images);
        return detailVo;
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean autoCancel(Long orderId) {
        BusiOrder po = new BusiOrder();
        po.setId(orderId);
        po.setStatus(3);
        return busiOrderMapper.updateByPrimaryKeySelective(po) > 0;
    }

    @Override
    public boolean uploadPayCertificate(BusiOrderCertificateDto dto) {
        try {
            List<String> images = dto.getImages();

            List<BusiOrderCertificate> poList = new ArrayList<>();
            images.forEach(imageId -> {
                BusiOrderCertificate po = new BusiOrderCertificate();
                po.setId(SnowflakeIdWorker.generateId());
                po.setOrderId(dto.getOrderId());
                po.setImageId(imageId);
                po.setCreateDate(new Date());
                poList.add(po);
            });

            busiOrderCertificateMapper.insertBatch(poList);

            //修改订单上传支付凭证状态
            BusiOrder orderPo = new BusiOrder();
            orderPo.setId(dto.getOrderId());
            orderPo.setCertificateStatus(1);
            //上传凭证并设置支付时间
            orderPo.setPayTime(new Date());
            busiOrderMapper.updateByPrimaryKeySelective(orderPo);
            return true;
        } catch (Exception e) {
            throw new CustomException(SystemErrorEnum.ORDER_CERTIFICATE_ERROR);
        }
    }

    @Override
    public Double queryTurnover(BusiOrderStatisticDto busiOrderStatisticDto) {
        return busiOrderMapper.selectTurnover(busiOrderStatisticDto);
    }

    @Override
    public Integer queryUserOrderNums(Integer userId) {
        return busiOrderMapper.selectOrderNumsByUserId(userId);
    }

    /**
     * 订单退款
     *
     * @param busiOrderReturndto
     * @return
     */
    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean insertRefundOrder(BusiOrderReturnDto busiOrderReturndto, HttpServletRequest request) {
        //获取当前用户ID
        int userId = getCurrentUserId(request);
        //转换退款订单信息
        BusiOrderReturn po = new BusiOrderReturn().buildPo(busiOrderReturndto);
        //设置退款ID
        long Id = SnowflakeIdWorker.generateId();
        po.setId(Id);
        if (!CollectionUtils.isEmpty(busiOrderReturndto.getImgs())) {
            //获取po图片字段
            String imgs = Joiner.on(";").join(busiOrderReturndto.getImgs());
            po.setImgs(imgs);
        }
        po.setCreateUserId(userId);
        po.setCreateDate(new Date());
        return busiOrderReturnMapper.insertSelective(po) > 0;
    }
}
