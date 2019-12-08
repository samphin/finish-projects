package com.ryit.commons.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 评价回复Dto
 *
 * @author samphin
 * @since 2019-10-21 17:43:15
 */
@Data
public class BusiGoodsCommentsReplyDto implements Serializable {

    /**
     * 上级评价信息
     */
    private BusiGoodsCommentsDto parentComments;

    /**
     * 自己评价信息
     */
    private BusiGoodsCommentsDto myComments;
}