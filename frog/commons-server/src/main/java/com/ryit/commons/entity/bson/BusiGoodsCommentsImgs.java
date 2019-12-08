package com.ryit.commons.entity.bson;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * 商品评价图片表
 *
 * @author samphin
 * @since 2019-10-21 17:43:45
 */
@Data
@Document(collection = "Busi_Goods_Comments_Imgs")
public class BusiGoodsCommentsImgs implements Serializable {

    private static final long serialVersionUID = -8316054524797504872L;

    /**
     * 主键id
     */
    @Id
    private Long id;

    /**
     * 图片/或者视频路径
     */
    private String filePath;

    /**
     * 文件类型 1:图片；2:视频
     */
    private Integer type;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 评价id
     */
    private Long commentId;
}