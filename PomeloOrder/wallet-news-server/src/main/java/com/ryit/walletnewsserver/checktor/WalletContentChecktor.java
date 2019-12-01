package com.ryit.walletnewsserver.checktor;

import com.ryit.commons.entity.pojo.WalletContent;
import com.ryit.commons.web.exception.user.CustomException;
import org.apache.commons.lang.StringUtils;

/**
 * @author: zhangweixun
 * @Date: 2019/10/10 0010上午 10:51
 */
public class WalletContentChecktor {

    public static void insertCheck(WalletContent walletContent) {
        if (null == walletContent) {
            throw new CustomException("分类资讯信息不能为空");
        }
        if (StringUtils.isBlank(walletContent.getNewsTitle())) {
            throw new CustomException("分类资讯标题不能为空");
        }
        if (StringUtils.isBlank(walletContent.getTxt())) {
            throw new CustomException("分类资讯内容不能为空");
        }
        if (StringUtils.isBlank(walletContent.getOrigin())) {
            throw new CustomException("分类资讯来源信息不能为空");
        }
        if (null == walletContent.getTypeId()) {
            throw new CustomException("分类资讯所属分类不能为空");
        }
        if (null == walletContent.getParentTypeId()) {
            throw new CustomException("分类资讯所属顶级分类不能为空");
        }
        //限定金融百科的标题的长度 用于做关键字
        if (walletContent.getTypeId() == 3L && walletContent.getParentTypeId() == 3L
                && walletContent.getNewsTitle().length() > 25) {
            throw new CustomException("金融百科标题过长");
        }
        if (null == walletContent.getRecommendFlag()) {
            throw new CustomException("资讯是否属于是否推荐不能为空");
        }
        if (null == walletContent.getRecommendLevel()) {
            throw new CustomException("资讯推荐级别不能为空");
        }
    }

    public static void updateCheck(WalletContent walletContent) {
        insertCheck(walletContent);
        if (null == walletContent.getId()) {
            throw new CustomException("分类资讯id不能为空");
        }
    }
}
