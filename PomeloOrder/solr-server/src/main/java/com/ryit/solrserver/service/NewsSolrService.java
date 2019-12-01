package com.ryit.solrserver.service;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.base.PageData;
import com.ryit.commons.entity.pojo.WalletNews;
import com.ryit.commons.util.HttpClientUtil;
import com.ryit.commons.util.StringUtil;
import com.ryit.solrserver.entity.SolrNewsVo;
import com.ryit.solrserver.util.SolrUtil;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.FieldAnalysisRequest;
import org.apache.solr.client.solrj.response.AnalysisResponseBase;
import org.apache.solr.client.solrj.response.FieldAnalysisResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

/**
 * @author : 刘修
 * @Date : 2019/9/5 17:47
 */
@RestController
@RequestMapping("/solrNews")
public class NewsSolrService {

    private Logger log = LoggerFactory.getLogger(NewsSolrService.class);

    @Value("${spring.data.solr.news}")
    private String url;

    @Value("${default-page-size}")
    private Integer defaultPageSize;


    @GetMapping("/search")
    public AjaxResult searchWalletNews(@RequestParam String param, @RequestParam(value = "page", defaultValue = "1") Integer page) {
        HttpSolrClient solr = SolrUtil.getSolrClient(url, null, null);
        List<SolrNewsVo> list = null;
        PageData pageData = null;
        try {
            SolrQuery query = new SolrQuery();
            //设置查找的参数
            if (StringUtil.isEmpty(param)) {
                param = "*";
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("newsTitle:" + param);
            stringBuilder.append(" || origin:" + param);
            stringBuilder.append(" || txt:" + param);
            query.setQuery(stringBuilder.toString());
            query.addSort("recommendFlag", SolrQuery.ORDER.desc);
            query.addSort("recommendLevel", SolrQuery.ORDER.desc);
            query.addSort("releaseTime", SolrQuery.ORDER.desc);
            //每页条数固定为20
            query.setStart((page - 1) * defaultPageSize);
            query.setRows(defaultPageSize);

            QueryResponse response = solr.query(query);
            list = response.getBeans(SolrNewsVo.class);
            SolrDocumentList results = response.getResults();
            pageData = new PageData();
            pageData.setRows(list);
            pageData.setTotal(results.getNumFound());
        } catch (Exception e) {
            log.error("资讯搜索错误", e);
        } finally {
            try {
                if (null != solr) {
                    solr.close();
                }
            } catch (IOException e) {
                log.error("关闭solr错误", e);
            }
        }
        return AjaxResult.success(pageData);
    }

    @PostMapping("/add")
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public void add(WalletNews news) {
        HttpSolrClient solr = SolrUtil.getSolrClient(url, null, null);
        try {
            SolrNewsVo vo = SolrNewsVo.buildVo(news);
            solr.addBean(vo); //添加数据到solr服务器
            solr.commit(); //提交
        } catch (Exception e) {
            log.error("资讯添加索引错误", e);
        } finally {
            try {
                if (null != solr) {
                    solr.close();
                }
            } catch (IOException e) {
                log.error("关闭solr错误", e);
            }
        }
    }

    @DeleteMapping("/delete")
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<String> ids) {
        HttpSolrClient solr = SolrUtil.getSolrClient(url, null, null);
        try {
            solr.deleteById(ids);
            solr.commit(); //提交
            solr.close();
        } catch (Exception e) {
            log.error("资讯删除索引错误", e);
        } finally {
            try {
                if (null != solr) {
                    solr.close();
                }
            } catch (IOException e) {
                log.error("关闭solr错误", e);
            }
        }
    }

    @PostMapping("/update")
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public void updateSolr() {
        String path = url + "/dataimport?command=full-import&commit=true";
//        String path = url + "/dataimport?command=delta-import&commit=true&clean=true";
        String result = HttpClientUtil.doGet(path);
        System.out.println("======solr更新=====");
        System.out.println(result);
    }


    /**
     * 对语句进行分词
     *
     * @param word
     * @return
     */
    @GetMapping("/checkBuzz")
    public AjaxResult checkBuzzword(String word) {
        FieldAnalysisRequest request = new FieldAnalysisRequest("/analysis/field");
        HttpSolrClient solr = SolrUtil.getSolrClient(url, null, null);
        request.addFieldName("newsTitle");
        request.setFieldValue("");
        request.setQuery(word);
        FieldAnalysisResponse response = null;
        try {
            response = request.process(solr);
        } catch (Exception e) {
            log.error("获取查询分词语句的分词时遇到错误", e);
            return AjaxResult.error("获取查询分词语句的分词时遇到错误");
        }
        Set<String> result = new HashSet<>();
        Iterator<AnalysisResponseBase.AnalysisPhase> it = response.getFieldNameAnalysis("newsTitle")
                .getQueryPhases().iterator();
        while (it.hasNext()) {
            AnalysisResponseBase.AnalysisPhase phase = it.next();
            List<AnalysisResponseBase.TokenInfo> list = phase.getTokens();
            for (AnalysisResponseBase.TokenInfo info : list) {
                result.add(info.getText());
            }
        }
        if (!CollectionUtils.isEmpty(result)) {
            return AjaxResult.success(result);
        }
        return AjaxResult.error();
    }

}
