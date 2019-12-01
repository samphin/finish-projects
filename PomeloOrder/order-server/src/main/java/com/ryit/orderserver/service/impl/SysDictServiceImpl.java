package com.ryit.orderserver.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.ryit.commons.entity.dto.SysDictDto;
import com.ryit.commons.entity.pojo.SysDict;
import com.ryit.commons.entity.vo.SysDictListVo;
import com.ryit.commons.entity.vo.SysDictVo;
import com.ryit.commons.util.StringUtil;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.orderserver.dao.SysDictMapper;
import com.ryit.orderserver.service.ISysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysDictServiceImpl implements ISysDictService {

    @Autowired
    private SysDictMapper sysDictMapper;

    @Override
    public List<SysDictVo> queryAllDict(boolean isShowAstrict,boolean isShowCamelCaseName, SysDict sysDict) {

        //查询出所有字典信息
        List<SysDict> sysDictList = sysDictMapper.selectAllDict(sysDict);

        List<SysDictVo> voList = new ArrayList<>();
        if (CollectionUtils.isEmpty(sysDictList)) {
            return voList;
        }

        //根据字典类型分组，查询出所有类型字典
        Map<String, List<SysDict>> dictTypeMap = sysDictList.stream().collect(Collectors.groupingBy(SysDict::getDictType));

        dictTypeMap.keySet().forEach(dictType -> {
            SysDictVo vo = new SysDictVo();
            //设置字典类型
            vo.setValue(dictType);
            //例如：value=house_property值添加驼峰显示houseProperty
            if(isShowCamelCaseName){
                vo.setValueCamelName(StringUtil.camelCaseName(dictType));
            }
            List<Map> contentList = new ArrayList<>();
            if (isShowAstrict) {
                Map item = new HashMap(2);
                item.put("label", "不限制");
                item.put("value", "0");
                contentList.add(0, item);
            }

            //筛选当前字典类型内容，并根据内容序号正序排列
            List<SysDict> sysDictContent = dictTypeMap.get(dictType).stream().sorted(Comparator.comparingInt(SysDict::getDictSort)).
                    collect(Collectors.toList());
            //通过字典类型从字典内容集合中任意取一条获得字典类型描述
            Optional<SysDict> dictOptional = sysDictContent.stream().filter(tempDict -> dictType.equals(tempDict.getDictType())).findFirst();
            dictOptional.ifPresent(dict -> {
                //设置字典类型描述
                vo.setLabel(dict.getRemark());
                //设置字典信息类型
                vo.setInfoType(dict.getInfoType());
            });

            sysDictContent.stream().forEach(dictContent -> {
                Map item = new HashMap(2);
                item.put("label", dictContent.getDictLabel());
                item.put("value", dictContent.getDictValue());
                item.put("score",dictContent.getCreditScore());
                contentList.add(item);
            });
            vo.setContent(contentList);

            voList.add(vo);
        });
        return voList;
    }

    @Override
    public List<SysDictVo> queryAllAptitude() {
        //返回不带有非限制字段的字典信息
        return queryAllDict(false,true,null);
    }

    @Override
    public List<SysDictListVo> queryAllAptitudeByOrderSetting() {
        return sysDictMapper.selectAllAptitudeByOrderSetting();
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean updateAptitude(SysDictDto dictDto) {
        try {
            SysDict po = new SysDict().buildPo(dictDto);
            int count = sysDictMapper.updateAptitude(po);
            return count > 0 ? true : false;
        } catch (Exception e) {
            throw new CustomException("修改字典失败");
        }
    }

    @Override
    public String getPomeloFlag(){
        return sysDictMapper.getPomeloFlag();
    }

    @Override
    public SysDict getMaxQuota() {
        return sysDictMapper.getMaxQuota();
    }
}
