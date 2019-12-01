package com.ryit.commons.entity.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
public class SysOrderBackimg implements Serializable {

    private static final long serialVersionUID = -5195162188948565069L;

    private Integer id;

    private Integer orderId;

    private String retuenImgPath;


}