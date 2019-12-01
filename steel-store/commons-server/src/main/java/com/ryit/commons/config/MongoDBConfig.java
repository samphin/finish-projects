package com.ryit.commons.config;

import com.mongodb.MongoClient;
import com.ryit.commons.support.BigDecimalToDecimal128Converter;
import com.ryit.commons.support.Decimal128ToBigDecimalConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * MongoDB自定义配置
 *
 * @author samphin
 * @since 2019-10-17 09:31:13
 */
@Configuration
public class MongoDBConfig extends AbstractMongoConfiguration {
    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.port}")
    private int port;

    @Value("${spring.data.mongodb.username}")
    private String username;

    @Value("${spring.data.mongodb.password}")
    private String password;

    @Value("${spring.data.mongodb.database}")
    private String database;

    /**
     * 重写父类方法，MongoDB转换去掉_class
     *
     * @return
     */
    @Bean
    @Override
    public MappingMongoConverter mappingMongoConverter() throws Exception {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(this.mongoDbFactory());
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, this.mongoMappingContext());
        converter.setCustomConversions(this.customConversions());
        // 不保存 _class to mongodb
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return converter;
    }

    /**
     * 指定集合库
     *
     * @return
     */
    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Override
    public MongoClient mongoClient() {
        MongoClient mongoClient = new MongoClient(host, port);
        return mongoClient;
    }

    @Bean
    @Override
    public MongoDbFactory mongoDbFactory() {
        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(new MongoClient(host, port),
                getDatabaseName());
        return mongoDbFactory;
    }

    /**
     * 自定义转换类
     *
     * @return
     */
    @Bean
    @Override
    public MongoCustomConversions customConversions() {
        List<Converter<?, ?>> converterList = new ArrayList<Converter<?, ?>>();
        converterList.add(new BigDecimalToDecimal128Converter());
        converterList.add(new Decimal128ToBigDecimalConverter());
        return new MongoCustomConversions(converterList);
    }
}
