package com.huameng.shortlink;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.huameng.shortlink.project.dao.mapper")
public class ShortLinkProjectApplication {

    public static void main(String[] args){

        SpringApplication.run(ShortLinkProjectApplication.class, args);
    }
}
