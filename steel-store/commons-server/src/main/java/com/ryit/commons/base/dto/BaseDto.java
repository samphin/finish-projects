package com.ryit.commons.base.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

/**
 * Dto基类
 *
 * @author samphin
 * @since 2019-10-22 16:54:29
 */
@Data
public class BaseDto<PK extends Serializable> implements Serializable {

    private static final long serialVersionUID = -3912129770720454589L;

    /**
     * 主键ID
     */
    @Null(groups = Save.class)
    @NotNull(message = "ID不能为空", groups = Update.class)
    private PK id;

    /**
     * 控制提交版本，防止重复提交
     */
    private int version = 1;

    /**
     * 定义新增校验组
     */
    public interface Save {


    }

    /**
     * 定义修改校验组
     */
    public interface Update {


    }
}
