package com.ryit.solrserver.entity;

import com.ryit.commons.entity.pojo.WalletNews;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author : 刘修
 * @Date : 2019/9/6 16:02
 */
@Getter
@Setter
@NoArgsConstructor
public class SolrNewsVo {

    private static final long serialVersionUID = 1567679119452L;


    /**
     * 主键
     * <p>
     * isNullAble:0
     */
    @Field
    private String id;

    /**
     * 资讯标题
     * isNullAble:1
     */
    @Field
    private String newsTitle;

    /**
     * 标题图
     * isNullAble:1
     */
    @Field
    private String titleImg;

    /**
     * 是否推荐 0推荐 1不推荐
     * isNullAble:1
     */
    @Field
    private Integer recommendFlag;

    /**
     * 推荐级别 从大到小 级别越高越推荐
     * isNullAble:1,defaultVal:0
     */
    @Field
    private Integer recommendLevel;

    /**
     * 阅读数
     * isNullAble:1
     */
    @Field
    private Integer readCount;

    /**
     * 发布时间
     * isNullAble:1
     */
    @Field
    private Date releaseTime;

    /**
     * 来源
     * isNullAble:1
     */
    @Field
    private String origin;

    /**
     * 资讯内容
     * isNullAble:1
     */
    @Field
    private String txt;

    public Long getId () {
        return Long.valueOf(id);
    }

    /**
     * po->vo
     */
    public static SolrNewsVo buildVo(WalletNews po){
        SolrNewsVo vo = new SolrNewsVo();
        BeanUtils.copyProperties(po,vo);
        vo.setId(po.getId().toString());
        return vo;
    }
}
