package com.ryit.credithelpserver.service;

import com.ryit.commons.base.service.IBaseService;
import com.ryit.commons.entity.dto.CreditNotesDto;
import com.ryit.commons.entity.vo.CreditNotesVo;

public interface ICreditNotesService extends IBaseService<Long, CreditNotesDto, CreditNotesVo> {

    /**
     * 回复留言
     * @param account
     * @param dto
     * @return
     */
    boolean answer(Long account,CreditNotesDto dto);
}
