package com.ryit.commons.entity.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author samphin
 * @date 2019-8-29 17:18:24
 */
@Setter
@Getter
@NoArgsConstructor
public class CreditNotesImg implements Serializable {

    private static final long serialVersionUID = 9163848020965977102L;
    
    private Long id;

    private Long notesId;

    private String imgPath;

    /**
     * dto->po
     * @param  notesId 留言编号
     * @param images 留言图片
     * @return
     */
    public List<CreditNotesImg> buildPo(Long notesId, List<String> images){
        List<CreditNotesImg> notesImgList = new ArrayList<>();
        images.forEach(imagePath ->{
            CreditNotesImg notesImg = new CreditNotesImg();
            notesImg.setNotesId(notesId);
            notesImg.setImgPath(imagePath);
            notesImgList.add(notesImg);
        });
        return notesImgList;
    }
}