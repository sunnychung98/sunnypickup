package kr.code.homes.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AgentConfig  {
    public static final String DATASOURCE_MAIN = "dataSource1";
    public static final String TRANSACTION_MANAGER_MAIN = "transactionManager1";
    public static final String TRANSACTION_SESSION_FACTORY_MAIN = "sqlSessionFactory1";

}
