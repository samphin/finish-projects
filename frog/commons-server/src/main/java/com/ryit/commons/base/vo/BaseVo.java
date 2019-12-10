package com.ryit.commons.base.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.google.common.collect.Lists;
import com.ryit.commons.exception.CustomException;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Vo基类
 *
 * @author samphin
 * @since 2019-9-27 18:30:41
 */
@Data
public class BaseVo<PK extends Serializable, P, V> implements Serializable {

    private static final long serialVersionUID = 8290753776671439473L;

    /**
     * 主键，解决前端JS获取long型时出现精度丢失的解决方案
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private PK id;

    /**
     * po->vo
     *
     * @param po
     * @return
     */
    public V buildVo(P po) {
        try {
            V v = (V) this.getClass().newInstance();
            if (po == null) {
                return v;
            }
            BeanUtils.copyProperties(po, v);
            return v;
        } catch (InstantiationException e) {
            throw new CustomException(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new CustomException(e.getMessage(), e);
        }
    }

    /**
     * poList->voList
     *
     * @param poList
     * @return
     */
    public List<V> buildVoList(List<P> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return Lists.newArrayList();
        }

        return Lists.transform(poList, po -> {
            return buildVo(po);
        });
    }
}
