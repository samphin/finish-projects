package com.ryit.solrserver.util;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.params.ModifiableSolrParams;

/**
 * @program: whsoso
 * @description:
 * @author: Mr.Lu
 * @create: 2019-07-31 14:58
 **/
public class SolrUtil {

    public static HttpSolrClient getSolrClient(String solrUrl,String user,String password){
        ModifiableSolrParams params = new ModifiableSolrParams();
        //params.set(HttpClientUtil.PROP_BASIC_AUTH_USER, member);
        //params.set(HttpClientUtil.PROP_BASIC_AUTH_PASS, password);
        //CloseableHttpClient closeableHttpClient = HttpClientUtil.createClient(params);
        return new HttpSolrClient.Builder(solrUrl).withConnectionTimeout(10000).withSocketTimeout(60000).withInvariantParams(params).build();
    }

}
