package com.petManage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/20 21:32
 **/
@SpringBootApplication
public class PetManageApplication {
    public static void main(String[] args){
        SpringApplication.run(PetManageApplication.class);
    }
    @Bean
    @LoadBalanced //负载均衡器
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
