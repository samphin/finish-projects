package com.ryit.orderserver.service.impl;

import com.ryit.commons.entity.pojo.SysArea;
import com.ryit.orderserver.dao.AreaMapper;
import com.ryit.orderserver.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : 刘修
 * @Date : 2019/8/29 9:53
 */
@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaMapper areaMapper;

    static Pattern pattern = Pattern.compile("(市|自治区|特别行政区|台湾省)");

    @Override
    public List<SysArea> getAreaList() {
        List<SysArea> areas = areaMapper.getAreaList();
        List<SysArea> areaList = new ArrayList<>();
        
        for (SysArea area : areas) {
            Matcher matcher = pattern.matcher(area.getAreaName());
            if (area.getLevel() != 1 || area.getParentCode() != -1) {
                continue;
            }
            if (area.getParentCode() == -1 && matcher.find()) {
                List<SysArea> list = new ArrayList<>();
                SysArea sysArea = new SysArea();
                sysArea.setAreaName(area.getAreaName());
                sysArea.setCode(area.getCode());
                sysArea.setLevel(area.getLevel());
                sysArea.setParentCode(0);
                list.add(sysArea);
                area.setAreaList(list);
                areaList.add(area);
                continue;
            }
            List<SysArea> cityList = new ArrayList<>();
            for (SysArea area1 : areas) {
                if (area.getCode().equals(area1.getParentCode())) {
                    cityList.add(area1);
                }
            }
            area.setAreaList(cityList);
            areaList.add(area);

        }
        return areaList;
    }
}
