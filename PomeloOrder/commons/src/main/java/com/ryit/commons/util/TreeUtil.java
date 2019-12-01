package com.ryit.commons.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: whsoso
 * @description: 树结构工具类
 * @author: Mr.Lu
 * @create: 2019-07-17 10:44
 **/
public class TreeUtil {

    public static List<Map<String,Object>> treeList(String idName , String pidName, List<Map<String, Object>> list, Object parentId, String subName) {
        List<Map<String,Object>> childMenu = new ArrayList<>();
        for (Map<String,Object> map : list) {
            Object menuId = map.get(idName);
            Object pid = map.get(pidName);
            if (String.valueOf(parentId).equals(String.valueOf(pid))) {
                List<Map<String,Object>> cNode = treeList(idName,pidName,list, menuId,subName);
                if(cNode!=null && cNode.size()>0){
                    map.put(subName, cNode);
                }
                childMenu.add(map);
            }
        }
        return childMenu;
    }

}
