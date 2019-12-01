package com.ryit.commons.entity.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 代理下单Dto
 *
 * @author samphin
 * @since 2019-11-25 15:57:33
 */
@Data
@ApiModel(value = "代理下单Dto")
public class BusiProxySubmitOrdersDto extends BusiSubmitOrdersDto implements Serializable {

    /**
     * 代理人ID
     */
    private Integer proxyUserId;
}