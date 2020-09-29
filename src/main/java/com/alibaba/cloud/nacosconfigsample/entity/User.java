package com.alibaba.cloud.nacosconfigsample.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@ConfigurationProperties(prefix = "user")
@Data
@ToString
public class User implements InitializingBean, DisposableBean {
    private String name;
    private int age;

    @Override
    public void destroy() {
        System.out.println("[destroy()] " + toString());
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("[afterPropertiesSet()] " + toString());
    }
}
