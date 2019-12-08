package com.ryit.quartz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 定时任务配置类
 *
 * @author samphin
 * @since 2019-10-22 10:27:44
 */
@Configuration
@EnableAsync
public class ScheduleJobConfig {

    /**最小任务线程个数*/
    @Value("${schedule.job.corePoolSize:3}")
    private int corePoolSize;

    /** 最大线程个数*/
    @Value("${schedule.job.maxPoolSize:20}")
    private int maxPoolSize;

    /** 等待队列大小*/
    @Value("${schedule.job.queueCapacity:30}")
    private int queueCapacity = 10;

    @Bean
    public Executor jobExecutor(){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(this.corePoolSize);
        taskExecutor.setMaxPoolSize(this.maxPoolSize);
        taskExecutor.setQueueCapacity(this.queueCapacity);
        return taskExecutor;
    }
}
