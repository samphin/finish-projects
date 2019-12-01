package com.ryit.commons.entity.vo;

import com.ryit.commons.entity.pojo.CreditMenu;
import com.ryit.commons.entity.pojo.CreditRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @Author 武汉软艺
 **/
@Getter
@Setter
@NoArgsConstructor
public class CreditRoleVo extends CreditRole implements Serializable {

    /**
     * 菜单权限
     */
    private List<Long> menus;


}
