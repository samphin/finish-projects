package com.ryit.credithelpserver.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.ryit.commons.entity.dto.CreditNotesDto;
import com.ryit.commons.entity.dto.PushMessageDto;
import com.ryit.commons.entity.pojo.CreditNotes;
import com.ryit.commons.entity.pojo.CreditNotesImg;
import com.ryit.commons.entity.pojo.CreditUser;
import com.ryit.commons.entity.vo.CreditNotesVo;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.credithelpserver.dao.CreditNotesImgMapper;
import com.ryit.credithelpserver.dao.CreditNotesMapper;
import com.ryit.credithelpserver.feign.CreditUserFeignClient;
import com.ryit.credithelpserver.feign.MessageFeignClient;
import com.ryit.credithelpserver.service.ICreditNotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class CreditNotesServiceImpl implements ICreditNotesService {

    @Autowired
    private CreditNotesMapper creditNotesMapper;

    @Autowired
    private CreditNotesImgMapper creditNotesImgMapper;


    @Resource
    private MessageFeignClient messageFeignClient;

    @Resource
    private CreditUserFeignClient creditUserFeignClient;

    /**
     * 回复留言
     *
     * @param account
     * @param dto
     * @return
     */
    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean answer(Long account, CreditNotesDto dto) {
        //先保存留言，再保存图片
        CreditNotes notesPo = new CreditNotes().buildPo(dto);
        notesPo.setAnswerStatus(true);
        notesPo.setAnswerTime(new Date());
        notesPo.setAnswerUserId(account);
        //回复留言后推送消息
        PushMessageDto messageDto = new PushMessageDto();
        //通过手机号查询用户信息
        CreditUser creditUser = creditUserFeignClient.getCreditUserByPhone(dto.getPhone());
        messageDto.setUsers(Arrays.asList(creditUser));
        messageDto.setTitle("留言提醒");
        messageDto.setContent("回复：" + notesPo.getAnswerContent());
        messageDto.setAppType("credit");
        messageFeignClient.push(messageDto);
        //回复留言
        return creditNotesMapper.updateByPrimaryKeySelective(notesPo) > 0 ? true : false;
    }

    /**
     * 保存留言信息及图片
     *
     * @param dto
     * @return
     * @author samphin
     * @date 2019-8-31 15:18:03
     */
    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean save(CreditNotesDto dto) {
        try {
            //先保存留言，再保存图片
            CreditNotes notesPo = new CreditNotes().buildPo(dto);

            //保存留言图片
            creditNotesMapper.insertSelective(notesPo);

            //如果上传图片，则保存图片信息
            if (!CollectionUtils.isEmpty(dto.getImages())) {
                List<CreditNotesImg> notesImgList = new CreditNotesImg().buildPo(notesPo.getId(), dto.getImages());
                this.creditNotesImgMapper.insertBatch(notesImgList);
            }
            return true;
        } catch (Exception e) {
            throw new CustomException("留言提交失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        try {
            int count = creditNotesMapper.deleteByPrimaryKey(id);
            return count > 0 ? true : false;
        } catch (Exception e) {
            throw new CustomException("删除留言信息失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(List<Long> ids) {
        try {
            int count = creditNotesMapper.deleteBatch(ids);
            return count > 0 ? true : false;
        } catch (Exception e) {
            throw new CustomException("批量删除留言信息失败");
        }
    }

    @Override
    public List<CreditNotesVo> queryAllByCondition(CreditNotesDto dto) {
        try {
            CreditNotes po = new CreditNotes().buildPo(dto);
            List<CreditNotes> poList = creditNotesMapper.selectAllByCondition(po);
            return CreditNotesVo.buildVoList(poList);
        } catch (Exception e) {
            throw new CustomException("条件查询留言信息失败");
        }
    }

    @Override
    public CreditNotesVo queryOne(Long id) {
        try {
            CreditNotes po = creditNotesMapper.selectByPrimaryKey(id);
            return CreditNotesVo.buildVo(po);
        } catch (Exception e) {
            throw new CustomException("查询留言信息失败");
        }
    }
}
