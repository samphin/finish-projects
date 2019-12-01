package com.ryit.credithelpserver.dao;

import com.ryit.commons.entity.pojo.CreditNotesImg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditNotesImgMapper {

    int insertBatch(List<CreditNotesImg> records);

    /**
     * 根据留言ID,查询相关图片
     * @param notesId
     * @return
     */
    List<CreditNotesImg> selectImageByNotesId(@Param("notesId")Long notesId);
}