package com.ryit.commons.entity.vo;

import com.ryit.commons.entity.pojo.WalletContent;
import com.ryit.commons.entity.pojo.WalletContentType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/10/9 0009下午 4:04
 */
@Getter
@Setter
@NoArgsConstructor
public class WalletContentTypeVo extends WalletContentType {

    /**
     * 资讯分类的子分类
     */
    private List<WalletContentTypeVo> list;

    /**
     * 资讯分类中的资讯
     */
    private List<WalletContentVo> contentList;

}
