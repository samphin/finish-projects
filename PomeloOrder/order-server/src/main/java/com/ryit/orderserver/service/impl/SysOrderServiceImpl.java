package com.ryit.orderserver.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.entity.dto.*;
import com.ryit.commons.entity.pojo.*;
import com.ryit.commons.entity.vo.*;
import com.ryit.commons.util.DateUtil;
import com.ryit.commons.util.IdCardUtils;
import com.ryit.commons.util.StringUtil;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.orderserver.dao.*;
import com.ryit.orderserver.feign.CreditCouponFeignClient;
import com.ryit.orderserver.feign.CreditUserFeignClient;
import com.ryit.orderserver.feign.MessageFeignClient;
import com.ryit.orderserver.feign.WalletUserFeignClient;
import com.ryit.orderserver.service.ISysOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author : 刘修
 * @Date : 2019/8/20 10:59
 */
@Service
@Slf4j
public class SysOrderServiceImpl implements ISysOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SysOrderTempMapper sysOrderTempMapper;

    @Autowired
    private SysOrderAptitudeMapper sysOrderAptitudeMapper;

    @Autowired
    private SysOrderAptitudeTempMapper sysOrderAptitudeTempMapper;

    @Autowired
    private SysOrderBackimgMapper sysOrderBackimgMapper;

    @Resource
    private CreditUserFeignClient creditUserFeignClient;

    @Resource
    private CreditCouponFeignClient creditCouponFeignClient;

    @Resource
    private WalletUserFeignClient walletUserFeignClient;

    @Resource
    private MessageFeignClient messageFeignClient;

    @Autowired
    private SysDictMapper sysDictMapper;

    @Autowired
    private SysSurnameMapper sysSurnameMapper;

    @Autowired
    private AreaMapper areaMapper;

    @Override
    public List<SysOrderVo> queryHomePageOrderList(SysOrderQueryDto sysOrderQueryDto) {
        Set<String> aptitudeCode = new HashSet<>();//资质编码
        if (null != sysOrderQueryDto.getSocialSecurity() && sysOrderQueryDto.getSocialSecurity() == 1) {
            aptitudeCode.add("social_security");
        } else if (null != sysOrderQueryDto.getAccumulatioinFund() && sysOrderQueryDto.getAccumulatioinFund() == 1) {
            aptitudeCode.add("accumulatioin_fund");
        } else if (null != sysOrderQueryDto.getHouseProperty() && sysOrderQueryDto.getHouseProperty() == 1) {
            aptitudeCode.add("house_property");
        } else if (null != sysOrderQueryDto.getCarProperty() && sysOrderQueryDto.getCarProperty() == 1) {
            aptitudeCode.add("car_property");
        } else if (null != sysOrderQueryDto.getLifeInsurance() && sysOrderQueryDto.getLifeInsurance() == 1) {
            aptitudeCode.add("life_insurance");
        } else if (null != sysOrderQueryDto.getSesameCredit() && sysOrderQueryDto.getSesameCredit() == 1) {
            aptitudeCode.add("sesame_credit");
        } else if (null != sysOrderQueryDto.getMicrofinance() && sysOrderQueryDto.getMicrofinance() == 1) {
            aptitudeCode.add("microfinance");
        } else if (null != sysOrderQueryDto.getCreditCardFlag() && sysOrderQueryDto.getCreditCardFlag() == 1) {
            aptitudeCode.add("credit_card");
        }
        //芝蔴信息用值匹配
        sysOrderQueryDto.setAptitudeCode(aptitudeCode);
        List<SysOrderVo> list = orderMapper.selectHomePageOrderList(sysOrderQueryDto);
        for (SysOrderVo vo : list) {
            vo.setReleaseTimeStr(null != vo.getReleaseTime() ? DateUtil.timeCala(vo.getReleaseTime()) : "");
        }
        return list;
    }

    @Override
    public SysOrderDetailVo queryHomePageOrderDetail(Long id) {
        //查询订单详情
        SysOrderDetailVo vo = orderMapper.selectOrderDetail(id);
        //查询订单用户资质信息和基本信息
        return queryOrderAptitudeInfo(vo);
    }

    @Override
    public Map<String, Object> countOrder() {
        return orderMapper.countOrder();
    }

    @Override
    public List<SysOrderVo> getOrderListByCreditUser(Long creditUserId) {
        return orderMapper.getOrderListByCreditUser(creditUserId);
    }

    @Override
    public SysOrderDetailVo getCreditUserOrderDetail(Long id, Long creditUserId) {
        //获取用户订单详情
        SysOrderDetailVo vo = orderMapper.getCreditUserOrderDetail(id);
        return queryOrderAptitudeInfo(vo);
    }

    /**
     * 获取用户资质信息
     *
     * @param vo
     * @author samphin
     * @date 2019-9-18 14:44:42
     */
    private SysOrderDetailVo queryOrderAptitudeInfo(SysOrderDetailVo vo) {
        //查询订单用户资质信息和基本信息
        List<SysOrderAptitude> aptitudes = sysOrderAptitudeMapper.selectAllByOrderId(vo.getId());
        /*//根据信息分组
        Map<Integer, List<SysOrderAptitude>> aptitudeGroupMap = aptitudes.stream().collect(Collectors.groupingBy(SysOrderAptitude::getInfoType));
        //筛选出订单用户资质信息
        List<SysOrderAptitude> aptitudeInfo = aptitudeGroupMap.get(1);*/
        //先根据优先级排序，再根据分值倒序
        Comparator<SysOrderAptitude> bya = Comparator.comparing(SysOrderAptitude::getPriority).reversed();//按照优先级降序
        Comparator<SysOrderAptitude> byb = Comparator.comparing(SysOrderAptitude::getScore).reversed();//按照分值降序

        Collections.sort(aptitudes, bya.thenComparing(byb));//将aptitudeInfo按照a字段先升序再按照B字段进行升序排列

        vo.setAptitudes(aptitudes);

        return vo;
    }

    @Override
    public List<SysOrderVo> getBackOrderListByCreditUser(Long creditUserId) {
        return orderMapper.getBackOrderListByCreditUser(creditUserId);
    }

    @Override
    public SysOrderVo getBackOrderDetail(Long id, Long creditUserId) {
        if (checkOrderUser(id, creditUserId)) {
            SysOrderVo order = orderMapper.getBackOrderDetail(id);
            if (null != order.getBackImg()) {
                order.setBackImgs(order.getBackImg().split(","));
            }
            return order;
        }
        return null;
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer operationOrder(SysOrderDto sysOrder) {
        if (checkOrderUser(sysOrder.getId(), sysOrder.getCreditUserId())) {
            SysOrder order = new SysOrder();
            order.setId(sysOrder.getId());
            order.setCallFlag(sysOrder.getCallFlag());
            order.setDelFlag(sysOrder.getDelFlag());
            order.setFinishFlag(sysOrder.getFinishFlag());
            order.setOrderRemark(sysOrder.getOrderRemark());
            return orderMapper.updateOrder(order);
        }
        return 0;
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer grabOrder(SysOrderDto sysOrder) throws CustomException {
        //获取订单金额
        double coin = orderMapper.getCoinById(sysOrder.getId());
        double couponCoin = 0D;
        if (null != sysOrder.getCouponId()) {
            //获取优惠券面值
            couponCoin = creditCouponFeignClient.queryDiscountCoin(sysOrder.getCouponId());
            if (couponCoin == -2) {
                throw new CustomException("优惠券不存在");
            }
            if (couponCoin == -1) {
                couponCoin = coin;
            }
        }
        //获取实际应扣金额
        double realcoin = couponCoin - coin;
        double remain = creditUserFeignClient.getCreditUserCoin(sysOrder.getCreditUserId());
        if (remain + realcoin < 0) {
            throw new CustomException("账户余额不足");
        }
        //生成账单
        CreditBill creditBill = new CreditBill();
        creditBill.setBillCoin(realcoin);
        creditBill.setUserId(sysOrder.getCreditUserId());
        creditBill.setBillType(1);
        creditBill.setCouponId(sysOrder.getCouponId());
        Long billid = creditUserFeignClient.saveBill(creditBill);
        if (billid == 0) {
            return 0;
        }
        //更新订单为已抢单
        SysOrder order = new SysOrder();
        order.setId(sysOrder.getId());
        order.setPayId(billid);
        order.setOrderStatus(1);
        order.setPayTime(new Date());
        if (orderMapper.updateOrder(order) <= 0) {
            return 0;
        }
        //设置优惠券状态为已使用
        if (null != sysOrder.getCouponId() && !creditCouponFeignClient.updateCouponUseStatus(sysOrder.getCouponId())) {
            return 0;
        }
        //扣减信贷员账户余额并计算是否增加退单机会
        creditUserFeignClient.addBackOrderNum(sysOrder.getCreditUserId());
        return creditUserFeignClient.updateCreditUserCoin(sysOrder.getCreditUserId(), realcoin);
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer applyBackOrder(SysOrderDto sysOrder) {
        if (!checkOrderUser(sysOrder.getId(), sysOrder.getCreditUserId())) {
            return 0;
        }
        //校验是否有退单机会
        Integer backOrderNum = creditUserFeignClient.getBackOrderNum(sysOrder.getCreditUserId());
        if (null == backOrderNum || backOrderNum < 1) {
            return 0;
        }
        orderMapper.deleteBackOrderByOrder(sysOrder.getId());
        SysOrderReturn orderReturn = new SysOrderReturn();
        orderReturn.setOrderId(sysOrder.getId());
        for (String path : sysOrder.getBackImgs()) {
            orderReturn.setRetuenImgPath(path);
            orderReturn.setId(null);
            orderMapper.insertBackOrder(orderReturn);
        }
        SysOrder order = new SysOrder();
        order.setId(sysOrder.getId());
        order.setBackReason(sysOrder.getBackReason());
        order.setBackTime(new Date());
        order.setBackStatus(1);
        return orderMapper.updateOrder(order);
    }

    @Override
    public Integer checkBackOrder(Long id) {
        SysOrderVo order = orderMapper.getOrderForBack(id);
        Double hour = (System.currentTimeMillis() - order.getPayTime().getTime()) / 1000.0 / 60.0 / 60.0;
        if (hour < 24) {
            throw new CustomException("抢单后未超过24小时不能申请退单");
        }
        if (hour > 120) {
            throw new CustomException("抢单后超过120小时不能申请退单");
        }
        if (order.getBackOrderNum() < 1) {
            throw new CustomException("可用退单次数不足");
        }
        return order.getBackOrderNum();
    }

    /**
     * @author: 刘修
     * @date: 2019/8/22 20:23
     * Description:查询订单是否属于当前用户
     */
    public boolean checkOrderUser(Long oId, Long cId) {
        if (null == oId || null == cId) {
            return false;
        }
        return cId.equals(orderMapper.getOrderCreditUserById(oId));
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteOrderById(List ids) {
        try {
            return orderMapper.deleteOrderById(ids);
        } catch (Exception e) {
            throw new CustomException("删除订单失败");
        }
    }

    @Override
    public List<SysOrderListVo> queryOrderList(SysOrderListQueryDto queryDto) {

        List<SysOrder> poList = orderMapper.selectAllOrders(queryDto);

        List<SysOrderListVo> voList = SysOrderListVo.buildVoList(poList);

        return voList;
    }

    @Override
    public String queryLastGrabOrderPayTime(Long orderId, Long userId) {
        Map queryParam = new HashMap(2);
        queryParam.put("orderId", orderId);
        queryParam.put("userId", userId);
        Date lastPayTime = orderMapper.selectLastGrabOrderPayTime(queryParam);
        if (null == lastPayTime) {
            return "";
        }
        return DateFormatUtils.format(lastPayTime, "yyyy年MM月dd日");
    }

    @Override
    public Integer getTodayOrderCount() {
        return orderMapper.getTodayOrderCount();
    }

    /**
     * 保存订单及资质、基本信息
     *
     * @param sysOrderDto
     * @author samphin
     * @date 2019-9-19 14:14:44
     */
    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveSysOrder(Long account, SysOrderDto sysOrderDto) {
        try {
            //获取贷款人资质信息
            List<SysOrderAptitude> aptitudes = sysOrderDto.getAptitudes();
            //如果钱包用户ID为空，说明是后台保存用户信息
            saveWalletUser(sysOrderDto);
            //订单总分值
            int orderTotalScore = 0;
            //资质信息和基本信息关联订单id
            //资质信息分值总和
            orderTotalScore += aptitudes.stream().mapToInt(SysOrderAptitude::getScore).sum();
            //基本信息分值总和(去字典表根据label去查询对应分值)
            //orderTotalScore += userInfo.stream().filter(info -> null != info.getScore()).mapToInt(SysOrderAptitude::getScore).sum();
            //查询出所有字典信息
            SysDict sysDict = new SysDict();
            sysDict.setCreditFlag(1);
            List<SysDict> allDict = sysDictMapper.selectAllDict(sysDict);
            //==========根据反射解析出基本信息字段对应的分值================
            Class clazz = sysOrderDto.getClass();
            //学历,婚姻状况,职业类型,单位性质,月收入,工资发放形式,当前单位工龄,月经营流水,经营年限,营业执照
            Set<String> allowGetScoreFields = Stream.of("eduLevel", "maritalStatus", "careersType",
                    "companyType", "monthCash", "wagesWay", "workAge",
                    "managerFlow", "managerYear", "businessLicense").collect(Collectors.toSet());
            //得到所有字段
            Field[] declaredFields = clazz.getDeclaredFields();
            //遍历字段并通过字段信息从字典表中获取字段分值
            for (Field field : declaredFields) {
                //求订单字段中出现基本信息字段时就返回true
                boolean isHas = allowGetScoreFields.stream().anyMatch(content -> content.equals(field.getName()));
                if (isHas) {
                    //允许访问私有方法
                    field.setAccessible(true);
                    //根据字段名称从字典结果集中查询基本信息分值
                    Object value = field.get(sysOrderDto);
                    if (null != value) {
                        Optional<SysDict> dictOptional = allDict.stream().
                                filter(dict -> value.equals(dict.getDictLabel()) &&
                                        StringUtil.underscoreName(field.getName()).equals(dict.getDictType())).
                                findFirst();
                        //如果有值，则进行分数计算
                        if (dictOptional.isPresent()) {
                            SysDict dict = dictOptional.get();
                            Integer infoScore = dict.getCreditScore();
                            if (null != infoScore && infoScore != 0) {
                                orderTotalScore += infoScore;
                            }
                        }
                    }
                }
            }
            //绑定发布人ID
            sysOrderDto.setReleaseUserId(account);
            //生成发布时间
            sysOrderDto.setReleaseTime(new Date());
            //计算抢单价格
            sysOrderDto.setOrderCoin(getCreditCoin(orderTotalScore));
            //dto->po
            SysOrder sysOrder = new SysOrder().buildPo(sysOrderDto);
            //保存订单
            orderMapper.insertSelective(sysOrder);
            //汇总订单资质信息
            aptitudes.stream().forEach(aptitude -> {
                if (aptitude != null) {
                    aptitude.setOrderId(sysOrder.getId());
                    int value = aptitude.getValue();
                    aptitude.setPriority(value > 0 ? 1 : value);
                }
            });
            //批量保存订单资质信息
            sysOrderAptitudeMapper.batchInsertAptitude(aptitudes);

            //清空订单草稿信息
            clearDrafts(sysOrderDto);

            //订单保存成功后，极光推送消息给用户
            WalletUser walletUser = sysOrderDto.getUserInfo();
            StringBuffer content = new StringBuffer();
            content.append("贷款人：" + getWalletAliasName(walletUser.getRealname()) + "，");
            content.append(getCityName(sysOrderDto.getCity()) + "，");
            content.append("借款：" + sysOrderDto.getOrderMoney() + "万，期限：" + sysOrderDto.getOrderTerm() + "。");
            content.append("工资发放形式：" + sysOrderDto.getWagesWay() + "，月收入：" + sysOrderDto.getMonthCash() + "。");
            //芝麻信用
            Optional<SysOrderAptitude> sesameCreditOptional = aptitudes.stream().filter(ap -> "sesame_credit".equals(ap.getCode())).findFirst();
            sesameCreditOptional.ifPresent(ap -> {
                content.append("芝麻信用：" + ap.getLabel() + "，");
            });
            //房产
            Optional<SysOrderAptitude> housePropertyOptional = aptitudes.stream().filter(ap -> "house_property".equals(ap.getCode())).findFirst();
            housePropertyOptional.ifPresent(ap -> {
                content.append("房产：" + ap.getLabel() + "。");
            });
            List<WalletUser> users = Stream.of(walletUser).collect(Collectors.toList());
            PushMessageDto pushMessageDto = new PushMessageDto();
            pushMessageDto.setUsers(users);
            pushMessageDto.setTitle("柚子抢单");
            pushMessageDto.setContent(Objects.toString(content));
            pushMessageDto.setAppType("credit");
            messageFeignClient.push(pushMessageDto);
            return true;
        } catch (IllegalAccessException e) {
            throw new CustomException("订单新增失败", e);
        }
    }

    /**
     * 将真实姓名转换成XX先生或XX女士
     *
     * @author samphin
     * @date 2019-10-15 16:28:36
     */
    private String getWalletAliasName(String realName) {
        //截取真实姓名前2位
        String surname = realName.substring(0, 2);
        //获取性别雅称
        //String sexAlias = IdCardUtils.getSexDescription(sex);
        String sexAlias = "先生/女士";
        SysSurname sysSurname = this.sysSurnameMapper.selectBySurname(surname);
        if (null == sysSurname) {
            return surname.substring(0, 1) + sexAlias;
        } else {
            return sysSurname.getSurname() + sexAlias;
        }
    }

    /**
     * 通过城市编码获取城市名称
     *
     * @param cityCode
     * @author samphin
     * @date 2019-10-15 16:38:52
     */
    private String getCityName(int cityCode) {
        return areaMapper.selectNameByCode(cityCode);
    }

    /**
     * 清空草稿信息
     *
     * @author samphin
     * @date 2019-10-10 09:35:33
     */
    private void clearDrafts(SysOrderDto sysOrderDto) {
        //如果当前订单信息不包含订单ID，说明是第一次新增订单，此时没有草稿。
        if (null == sysOrderDto.getId()) {
            return;
        }
        //删除订单草稿信息
        sysOrderTempMapper.deleteByPrimaryKey(sysOrderDto.getId());
        sysOrderAptitudeTempMapper.clearOrderAptitude(sysOrderDto.getId());
    }

    /**
     * 保存草稿箱
     *
     * @author samphin
     * @date 2019-10-9 19:30:40
     */
    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveDrafts(Long userId, SysOrderDto sysOrderDto) {
        try {
            //如果钱包用户ID为空，说明是后台保存用户信息
            saveWalletUser(sysOrderDto);
            //将订单信息转换成订单草稿对象
            SysOrderTemp sysOrderTemp = new SysOrderTemp().buildPo(sysOrderDto);
            sysOrderTemp.setCreateUserId(userId);
            //新增草稿
            if (null == sysOrderDto.getId()) {
                sysOrderTempMapper.insertSelective(sysOrderTemp);
            } else {
                //先清空原来旧资质信息
                sysOrderAptitudeTempMapper.clearOrderAptitude(sysOrderTemp.getId());
                //修改草稿箱信息
                sysOrderTempMapper.updateByPrimaryKeySelective(sysOrderTemp);
            }

            //资质信息不为空则进行保存
            if (!CollectionUtils.isEmpty(sysOrderDto.getAptitudes())) {
                //获取贷款人资质信息
                List<SysOrderAptitude> aptitudes = sysOrderDto.getAptitudes();
                //将资质信息转换成草稿箱数据
                List<SysOrderAptitudeTemp> aptitudeTemps = SysOrderAptitudeTemp.buildPoList(aptitudes);

                //汇总订单资质信息
                aptitudeTemps.forEach(aptitude -> {
                    if (aptitude != null) {
                        aptitude.setOrderId(sysOrderTemp.getId());
                        int value = aptitude.getValue();
                        aptitude.setPriority(value > 0 ? 1 : value);
                    }
                });

                //批量保存订单资质信息
                sysOrderAptitudeTempMapper.batchInsertAptitude(aptitudeTemps);
            }
            return true;
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), e);
        }
    }

    /**
     * 查询草稿箱列表
     *
     * @author samphin
     * @date 2019-10-10 14:01:44
     */
    @Override
    public List<SysOrderTempListVo> queryOrderDraftsList(SysOrderTempQueryDto queryDto) {
        List<SysOrderTempListVo> voList = sysOrderTempMapper.selectAllDraftsList(queryDto);
        return voList;
    }

    /**
     * 根据订单草稿信息条件查询草稿箱信息
     *
     * @param params
     * @return
     */
    @Override
    public SysOrderTempDetailVo queryDrafts(Map<String, Object> params) {
        SysOrderTempDetailVo sysOrderTempDetailVo = sysOrderTempMapper.selectOrderTempDetail(params);
        //如果通过手机号查询订单草稿信息时，如果为空，直接返回草稿信息
        if (sysOrderTempDetailVo == null) {
            return null;
        }
        List<SysOrderAptitudeTemp> aptitudeTemps = sysOrderAptitudeTempMapper.selectAllByOrderId(sysOrderTempDetailVo.getId());
        aptitudeTemps.forEach(ap -> {
            ap.setCodeCamelCaseName(StringUtil.camelCaseName(ap.getCode()));
        });
        sysOrderTempDetailVo.setAptitudes(aptitudeTemps);
        return sysOrderTempDetailVo;
    }

    /**
     * 保存钱包用户信息
     *
     * @param sysOrderDto
     * @author samphin
     * @date 2019-10-10 09:27:04
     */
    private void saveWalletUser(SysOrderDto sysOrderDto) {
        //如果贷款人ID为空，说明此时需要新增贷款人，否则说明贷款人已存在，不需要再新增
        if (null == sysOrderDto.getWalletUserId()) {
            //获取贷款人基本信息
            WalletUser userInfo = sysOrderDto.getUserInfo();
            //设置性别
            String idCard = userInfo.getCreditorIdcard();
            if (StringUtils.isNotBlank(idCard)) {
                userInfo.setSex(IdCardUtils.getSexByIdCard(idCard));
                userInfo.setAge(IdCardUtils.getAgeByIdCard(idCard));
            }
            //注册时间
            userInfo.setReleaseTime(new Date());
            //保存贷款人信息，并返回贷款用户ID
            AjaxResult respData = walletUserFeignClient.insertWalletUser(userInfo);
            //如果返回错误码为0，代码调用失败
            if (respData.getCode() == 0) {
                throw new CustomException(respData.getMsg());
            }
            //设置订单贷款用户ID
            sysOrderDto.setWalletUserId(Long.parseLong(Objects.toString(respData.getData())));
        }
    }

    @Override
    public Boolean updateOrder(SysOrderDto sysOrderDto) {
        SysOrder sysOrder = new SysOrder().buildPo(sysOrderDto);
        return this.orderMapper.updateOrder(sysOrder) > 0 ? true : false;
    }

    @Override
    public Boolean updateOrderWalletor(UpdateOrderWalletorDto updateIdCardDto) {
        WalletUser walletUser = new WalletUser();
        BeanUtils.copyProperties(updateIdCardDto, walletUser);
        AjaxResult result = walletUserFeignClient.updateOrderWalletor(walletUser);
        if (0 == result.getCode()) {
            throw new CustomException(result.getMsg());
        }
        return true;
    }

    @Override
    public WalletUser queryOrderWalletor(Long id) {
        AjaxResult result = walletUserFeignClient.queryOrderWalletor(id);
        if (result.getCode() == 0) {
            throw new CustomException("订单贷款人信息查询失败");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(result.getData(), WalletUser.class);
    }

    /**
     * 用户电话查询24小时内提交的订单的数量
     *
     * @param phone
     * @return
     */
    @Override
    public Integer queryOrderWithinDay(String phone) {
        return orderMapper.queryOrderWithinDay(phone);
    }

    /**
     * X>=60	79
     * 40≤X<60	59
     * 20≤X<40	39
     * <20	29
     * 根据分值计算抢单价格
     *
     * @param score
     * @return
     * @author samphin
     * @date 2019-9-19 12:05:43
     */
    private double getCreditCoin(int score) {
        if (score < 20) {
            return 29D;
        } else if (20 <= score && score < 40) {
            return 39D;
        } else if (40 <= score && score < 60) {
            return 59D;
        } else if (60 <= score) {
            return 79D;
        }
        return 0D;
    }

    @Override
    public SysOrderDetailVo queryOrderDetail(Long id) {
        SysOrderDetailVo vo = orderMapper.getCreditUserOrderDetail(id);
        //返回退单订单待审核时图片信息
        if (null != vo && 1 == vo.getBackStatus()) {
            List<SysOrderBackimg> sysOrderBackimgs = sysOrderBackimgMapper.selectOrderBackImage(id);
            //如果退单凭证存在
            if (!CollectionUtils.isEmpty(sysOrderBackimgs)) {
                List<String> imgPathList = sysOrderBackimgs.stream().map(SysOrderBackimg::getRetuenImgPath).collect(Collectors.toList());
                vo.setBackImgs(imgPathList.toArray(new String[]{}));
            }
        }
        return queryOrderAptitudeInfo(vo);
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Boolean reviewBackOrders(OrderBackDto orderBackDto) {
        SysOrder orderPo = new SysOrder();
        orderPo.setId(orderBackDto.getId());
        //查询出当前订单详情
        SysOrder orderDetail = orderMapper.selectOrderById(orderBackDto.getId());
        //通过订单支付人ID查询支付流水信息
        CreditBillVo billVo = creditUserFeignClient.getPayBill(orderDetail.getPayId());
        //退单审核消息标题
        String title = "";
        //退单审核消息内容
        String content = "";
        switch (orderBackDto.getIsPass()) {
            //审核通过
            case 0:
                //退单成功
                orderPo.setBackStatus(2);
                //将支付信息作为退单流水依据，然后退单
                CreditBill billDto = new CreditBill();
                BeanUtils.copyProperties(billVo, billDto);
                //求出退还支付的金额
                double billCoin = billVo.getBillCoin();
                billDto.setBillCoin(Math.abs(billCoin));
                //设置退单类型
                billDto.setBillType(2);
                //退单时间
                billDto.setBillTime(new Date());
                //添加一条退单流水信息
                creditUserFeignClient.saveBill(billDto);
                //将用户退单次数减1，并退还用户支付金额
                creditUserFeignClient.subtractBackOrderNum(billVo.getUserId(), Math.abs(billCoin));
                title = "审核已通过，退单成功";
                content = "退款金额：" + Math.abs(billCoin) + "元，一般1~3工作日到账！";
                break;
            //审核不通过
            case 1:
                //退单失败
                orderPo.setBackStatus(3);
                //退单失败原因
                orderPo.setErrorReason(orderBackDto.getErrorReason());
                title = "审核未通过，退单失败";
                content = "失败原因：" + orderBackDto.getErrorReason();
                break;
        }
        //退单审核后，向用户推送消息
        List<CreditUser> users = this.creditUserFeignClient.queryCreditUserListById(billVo.getUserId());
        PushMessageDto messageDto = new PushMessageDto();
        messageDto.setTitle(title);
        messageDto.setContent(content);
        messageDto.setUsers(users);
        //柚子抢单消息
        messageDto.setAppType("credit");
        //极光推送消息给支付用户
        messageFeignClient.push(messageDto);
        return this.orderMapper.updateOrder(orderPo) > 0 ? true : false;
    }

    @Override
    public HomePageFieldVo queryMaxQuotaAndOrderCount() {
        HomePageFieldVo vo = new HomePageFieldVo();
        SysDict sysDict = sysDictMapper.getMaxQuota();
        Integer orderCount = orderMapper.getTodayOrderCount();
        vo.setMaxQuota(sysDict.getDictValue());
        vo.setOrderCount(orderCount);
        return vo;
    }
}
