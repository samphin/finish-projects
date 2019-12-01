package com.ryit.walletnewsserver.feign;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.entity.pojo.WalletNews;
import com.ryit.walletnewsserver.feign.hystrix.SolrFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "solr-server", fallback = SolrFeignHystrix.class)
public interface SolrFeignClient {

    @PostMapping("/solrNews/add")
    void add(@RequestBody WalletNews news);

    @DeleteMapping("/solrNews/delete")
    void delete(@RequestBody List<String> ids);

    @GetMapping("/solrNews/checkBuzz")
    AjaxResult checkBuzzword(@RequestParam("word") String word);
}
