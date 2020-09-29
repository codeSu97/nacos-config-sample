package com.alibaba.cloud.nacosconfigsample;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.cloud.nacosconfigsample.entity.User;
import com.alibaba.nacos.api.config.listener.AbstractListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@SpringBootApplication
@RefreshScope
@RestController
@EnableConfigurationProperties(User.class)
public class NacosConfigSampleApplication {

    @Value("${user.name}")
    private String userName;

    @Value("${user.age}")
    private int userAge;

    @Autowired
    private User user;

    @Autowired
    private NacosConfigManager nacosConfigManager;

    @PostConstruct
    public void init() {
        System.out.printf("[init] user name : %s , age : %d%n", userName, userAge);
    }

    @PreDestroy
    public void destroy() {
        System.out.printf("[destroy] user name : %s , age : %d%n", userName, userAge);
    }

    @RequestMapping(value = "/user")
    public String getUserName() {
        return "[HTTP]" + user;
    }

    @Bean
    public ApplicationRunner runner() {
        return args -> {
            String dataId = "nacos-config-sample.yaml";
            String group = "DEFAULT_GROUP";
            nacosConfigManager.getConfigService().addListener(dataId, group, new AbstractListener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    System.out.println("[Listener] " + configInfo);
                }
            });
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(NacosConfigSampleApplication.class, args);
    }

}
