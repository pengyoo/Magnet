package com.pengyu.magnet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableAsync
public class MagnetApplication {

    public static void main(String[] args) {
        System.out.println("test web hook");
        SpringApplication.run(MagnetApplication.class, args);
    }

    /**
     * Create TestTemplate for external API calling
     * @return
     */
    @Bean
    public RestTemplate restTemplate(){
        RestTemplate template = new RestTemplate();
        return template;
    }


    /**
     * Enable Async Task
     * @return
     */
    @Bean
    public AsyncTaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // 设置核心线程池大小
        executor.setMaxPoolSize(10); // 设置最大线程池大小
        executor.setThreadNamePrefix("MyAsyncThread-"); // 设置线程名前缀
        executor.initialize();
        return executor;
    }

}
