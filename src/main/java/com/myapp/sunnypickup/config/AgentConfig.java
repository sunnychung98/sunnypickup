package com.myapp.sunnypickup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AgentConfig  {
    public static final String DATASOURCE_MAIN = "dataSource1";
    public static final String TRANSACTION_MANAGER_MAIN = "transactionManager1";
    public static final String TRANSACTION_SESSION_FACTORY_MAIN = "sqlSessionFactory1";

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

}
