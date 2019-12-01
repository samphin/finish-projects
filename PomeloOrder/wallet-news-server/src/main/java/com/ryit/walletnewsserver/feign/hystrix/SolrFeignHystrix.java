package com.ryit.walletnewsserver.feign.hystrix;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.entity.pojo.WalletNews;
import com.ryit.commons.web.exception.user.CustomException;
import com.ryit.walletnewsserver.feign.SolrFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author : 刘修
 * @Date : 2019/9/11 15:07
 */
@Component
public class SolrFeignHystrix implements SolrFeignClient {

    private Logger log = LoggerFactory.getLogger(SolrFeignHystrix.class);

    @Override
    public void add (WalletNews news) {
        log.error("更新资讯索引时服务中断");
        throw new CustomException("更新资讯索引时服务中断");
    }

    @Override
    public void delete (List<String> ids) {
        log.error("删除资讯索引时服务中断");
        throw new CustomException("删除资讯索引时服务中断");
    }

    @Override
    public AjaxResult checkBuzzword(String word) {
        log.error("获取分词结果时服务中断");
        throw new CustomException("获取分词结果时服务中断");
    }
}
