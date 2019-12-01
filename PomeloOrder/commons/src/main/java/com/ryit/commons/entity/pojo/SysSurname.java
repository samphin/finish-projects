package com.ryit.commons.entity.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 复姓字典表
 *
 * @author samphin
 * @date 2019-10-15 16:15:26
 */
@Data
public class SysSurname implements Serializable {

    private static final long serialVersionUID = -5608321026847268388L;

    private Integer id;

    private String surname;
}