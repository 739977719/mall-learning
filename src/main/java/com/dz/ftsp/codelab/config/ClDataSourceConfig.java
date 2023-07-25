package com.dz.ftsp.codelab.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.dz.ftsp.codelab.constant.ClConst.SUB_SYS_CODE;

@Configuration
@MapperScan(basePackages = ClDataSourceConfig.PACKAGE, sqlSessionFactoryRef = ClDataSourceConfig.SESSION_FACTORY_NAME)
public class ClDataSourceConfig {
    public static final String MODULE = "cl";
    public static final String DATA_SOURCE_NAME = MODULE + "DataSource";
    public static final String TRANSACTION_MANAGER_NAME = MODULE + "TransactionManager";
    public static final String SESSION_FACTORY_NAME = MODULE + "SessionFactory";

    public static final String PACKAGE = "com.dz.ftsp." + SUB_SYS_CODE + ".dao." + MODULE;
    public static final String MAPPER_LOCATION = "classpath*:com/dz/ftsp/" + SUB_SYS_CODE + "/dao/" + MODULE + "/xml/*.xml";

    @Value("${dzconfig.datasource." + SUB_SYS_CODE + ".jdbcUrl}")
    private String url;

    @Value("${dzconfig.datasource." + SUB_SYS_CODE + ".driver}")
    private String driver;

    @Value("${dzconfig.datasource." + SUB_SYS_CODE + ".username}")
    private String username;

    @Value("${dzconfig.datasource." + SUB_SYS_CODE + ".password}")
    private String password;

    @Bean(name = DATA_SOURCE_NAME)
    public DataSource buildDataSource() {
        HikariDataSource datasource = new HikariDataSource();
        datasource.setDriverClassName(driver);
        datasource.setJdbcUrl(url);
        datasource.setUsername(username);
        datasource.setPassword(password);
        return datasource;
    }

    @Bean(name = TRANSACTION_MANAGER_NAME)
    public DataSourceTransactionManager TransactionManager(@Qualifier(value = DATA_SOURCE_NAME) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = SESSION_FACTORY_NAME)
    public SqlSessionFactory SqlSessionFactory(@Qualifier(value = DATA_SOURCE_NAME) DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(resolveMapperLocations());
        return sessionFactory.getObject();
    }

    private Resource[] resolveMapperLocations() throws IOException {
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        List<Resource> resources = new ArrayList<>();
        Resource[] mappers = resourceResolver.getResources(MAPPER_LOCATION);
        resources.addAll(Arrays.asList(mappers));
        return resources.toArray(new Resource[resources.size()]);
    }
}
