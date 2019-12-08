package com.ryit.commons.base.po;

import com.google.common.collect.Lists;
import com.ryit.commons.exception.CustomException;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.List;

/**
 * Po基类
 *
 * @author samphin
 * @since 2019-9-27 18:30:41
 */
@Data
public class BasePo<PK extends Serializable, D, P> implements Serializable {

    private static final long serialVersionUID = 8979553397500543791L;

    /**
     * 主键ID（如果是自增主键GeneratedValue则生效）
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private PK id;

    /**
     * 控制提交版本，防止重复提交
     */
    @Version
    private Integer version;

    /**
     * dto->po
     *
     * @param dto
     * @return
     */
    public P buildPo(D dto) {
        try {
            P p = (P) this.getClass().newInstance();
            BeanUtils.copyProperties(dto, p);
            return p;
        } catch (InstantiationException e) {
            throw new CustomException(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new CustomException(e.getMessage(), e);
        }
    }

    /**
     * dtoList->poList
     *
     * @param dtoList
     * @return
     */
    public List<P> buildPoList(List<D> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return null;
        }

        return Lists.transform(dtoList, dto -> {
            return buildPo(dto);
        });
    }
}
