package com.ryit.solrserver;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDistributedTransaction
@EnableDiscoveryClient
@SpringBootApplication
public class SolrServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SolrServerApplication.class, args);
    }

}
