package com.ryit.walletnewsserver.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.constant.RedisConstants;
import com.ryit.commons.entity.dto.WalletNewsDto;
import com.ryit.commons.entity.pojo.WalletNews;
import com.ryit.commons.entity.vo.WalletNewsVo;
import com.ryit.commons.util.RedisUtil;
import com.ryit.commons.util.RelativeDateFormat;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.walletnewsserver.dao.WalletNewsMapper;
import com.ryit.walletnewsserver.feign.SolrFeignClient;
import com.ryit.walletnewsserver.service.WalletNewsService;
import com.ryit.walletnewsserver.util.SensitiveWordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: zhangweixun
 * @Date: 2019/9/6 0006上午 9:46
 */
@Service
public class WalletNewsServiceImpl implements WalletNewsService {

    @Resource
    private SolrFeignClient solrFeignClient;

    @Autowired
    private WalletNewsMapper walletNewsMapper;

    @Autowired
    private RedisUtil redisUtil;

    private Logger log = LoggerFactory.getLogger(WalletNewsServiceImpl.class);

    private static final String READ_COUNT = "ids";

    private static final String BUZZ_WORD = "buzzword";

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Long insertWalletNews(WalletNews walletNews) {
        try {
            walletNews.setReleaseTime(new Date());
            walletNews.setDelFlag(0);
            walletNewsMapper.insertWalletNews(walletNews);
            //向redis中添加资讯访问量
            redisUtil.zAdd(String.format(RedisConstants.PREFIX_WALLET_NEWS, READ_COUNT), walletNews.getId(), 0D);
            //向solr中添加索引
            solrFeignClient.add(walletNews);
            return walletNews.getId();
        } catch (Exception e) {
            log.error("添加钱包资讯失败", e);
            throw new CustomException("添加钱包资讯失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteWalletNews(String ids) {
        try {
            String[] strs = ids.split("\\,");
            List<String> strList = Arrays.asList(strs);
            List<Long> id = strList.stream().map(a -> Long.parseLong(a)).collect(Collectors.toList());

            Integer num = walletNewsMapper.deleteWalletNews(id);
            String[] strings = strList.toArray(new String[]{});
            //删除solr中的索引
            solrFeignClient.delete(Arrays.asList(strings));
            //批量删除redis中的阅读量
            redisUtil.zRemove(String.format(RedisConstants.PREFIX_WALLET_NEWS, READ_COUNT), id);
            return num == id.size() ? true : false;
        } catch (Exception e) {
            log.error("删除钱包资讯失败", e);
            throw new CustomException("删除钱包资讯失败");
        }
    }

    @Override
    public WalletNewsVo queryNewsById(Long id) {
        try {
            //id查询数据库中的资讯详情
            WalletNewsVo walletNewsVo = walletNewsMapper.queryNewsById(id);
            //转换日期为 1天前之类格式
            walletNewsVo.setDate(RelativeDateFormat.format(walletNewsVo.getReleaseTime()));
            //阅读量+1后存入对象
            Double readCount = redisUtil.zIncrementScore(String.format(RedisConstants.PREFIX_WALLET_NEWS, READ_COUNT), id, 1D);
            walletNewsVo.setReadCount(readCount.intValue());
            return walletNewsVo;
        } catch (Exception e) {
            log.error("查询钱包资讯详情失败", e);
            throw new CustomException("查询钱包资讯详情失败");
        }
    }

    @Override
    public List<WalletNewsVo> queryNewsByCondition(WalletNewsDto dto) {
        try {
            List<WalletNewsVo> list = walletNewsMapper.queryNewsByCondition(dto);
            list.stream().forEach(vo -> {
                Double readCount = redisUtil.zGet(String.format(RedisConstants.PREFIX_WALLET_NEWS, READ_COUNT), vo.getId());
                if (readCount == null) {
                    redisUtil.zAdd(String.format(RedisConstants.PREFIX_WALLET_NEWS, READ_COUNT), vo.getId(), 0D);
                    readCount = 0D;
                }
                vo.setReadCount(readCount.intValue());
                vo.setDate(RelativeDateFormat.format(vo.getReleaseTime()));
            });
            return list;
        } catch (Exception e) {
            log.error("条件查询资讯列表失败", e);
            throw new CustomException("条件查询资讯列表失败");
        }
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Long updateWalletNews(WalletNews walletNews) {
        try {
            return walletNewsMapper.updateWalletNews(walletNews);
        } catch (Exception e) {
            log.error("修改钱包资讯失败", e);
            throw new CustomException("修改钱包资讯失败");
        }
    }

    @Override
    public List<WalletNewsVo> getRecommendNews() {
        try {
            //数据库查询热门资讯
            List<WalletNewsVo> list = walletNewsMapper.getRecommendNews();
            list.stream().forEach(vo -> {
                //从redis中读取访问量
                Double readCount = redisUtil.zGet(String.format(RedisConstants.PREFIX_WALLET_NEWS, READ_COUNT), vo.getId());
                if (readCount == null) {
                    redisUtil.zAdd(String.format(RedisConstants.PREFIX_WALLET_NEWS, READ_COUNT), vo.getId(), 0D);
                    readCount = 0D;
                }
                //将日期转换为 7天前之类的格式
                vo.setDate(RelativeDateFormat.format(vo.getReleaseTime()));
                vo.setReadCount(readCount.intValue());
            });
            return list;
        } catch (Exception e) {
            log.error("获取热门资讯失败", e);
            throw new CustomException("获取热门资讯失败");
        }
    }

    @Override
    public List<WalletNews> getNewsBanner() {
        try {
            return walletNewsMapper.getNewsBanner();
        } catch (Exception e) {
            log.error("查询资讯轮播图失败", e);
            throw new CustomException("查询资讯轮播图失败");
        }
    }

    @Override
    public Set<String> checkBuzzword() {
        try {
            //查询出redis中阅读量前10的资讯的id
            Set<Object> set = redisUtil.zReverseRange(String.format(RedisConstants.PREFIX_WALLET_NEWS, READ_COUNT), 0, 9);
            //查询出前10资讯的title
            List<String> wordList = walletNewsMapper.getRecommendNewsTitle(set);
            Set<String> result = new HashSet<>();
            wordList.stream().forEach(word -> {
                //对资讯进行分词
                AjaxResult ajaxResult = solrFeignClient.checkBuzzword(word);
                if (ajaxResult.getCode() == 1) {
                    List<String> words = (List<String>) ajaxResult.getData();
                    words.stream().forEach(w -> {
                        //对停词进行转换 转换为 *
                        String content = SensitiveWordUtil.startReplaceStr(w);
                        if (!content.contains("*")) {
                            result.add(content);
                        }
                    });
                }
            });
            //清空redis中所有的热词
//            redisUtil.zRemove(String.format(RedisConstants.PREFIX_WALLET_NEWS, BUZZ_WORD), set);
            for (String str : result) {
                redisUtil.zAdd(String.format(RedisConstants.PREFIX_WALLET_NEWS, BUZZ_WORD), str, 0D);
            }
            return result;
        } catch (Exception e) {
            log.error("对热门资讯分词失败", e);
            throw new CustomException("对热门资讯分词失败");
        }
    }

    /**
     * 判断字符串是否全为 *
     *
     * @param str
     * @return
     */
    public boolean checkStopWord(String str) {
        char[] chars = str.toCharArray();
        for (char c : chars) {
            if (c == '*') {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<Object> getBuzzword() {
        try {
            //获取点击量前十的热词
            return redisUtil.zReverseRange(String.format(RedisConstants.PREFIX_WALLET_NEWS, BUZZ_WORD), 0, 9);
        } catch (Exception e) {
            log.error("获取热词失败", e);
            throw new CustomException("获取热词失败");
        }
    }

    @Override
    public Long incrementBuzzword(String buzzword) {
        try {
            //每次点击浏览量+1
            Double readCount = redisUtil.zIncrementScore(String.format(RedisConstants.PREFIX_WALLET_NEWS, BUZZ_WORD), buzzword, 1D);
            //返回浏览量
            return readCount.longValue();
        } catch (Exception e) {
            log.error("热词点击量增加失败", e);
            throw new CustomException("热词点击量增加失败");
        }
    }

}
