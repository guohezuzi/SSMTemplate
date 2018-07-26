package config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import util.MyMapper;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author guohezuzi
 * \* Date: 18-2-2
 * \* Time: 下午9:26
 * \* Description:数据库访问配置
 * \
 */
@Configuration
@PropertySource("classpath:application.properties")
@MapperScan(basePackages = "dao",markerInterface = MyMapper.class)
public class DateConfig{
    Logger logger=LoggerFactory.getLogger(DateConfig.class);

    @Value("${spring.datasource.driverClassName}")
    private String driver;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String name;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.initialSize:#{null}}")
    private Integer initialSize;

    @Value("${spring.datasource.minIdle:#{null}}")
    private Integer minIdle;

    @Value("${spring.datasource.maxActive:#{null}}")
    private Integer maxActive;

    @Value("${spring.datasource.maxWait:#{null}}")
    private Integer maxWait;

    @Value("${spring.datasource.timeBetweenEvictionRunsMillis:#{null}}")
    private Integer timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.minEvictableIdleTimeMillis:#{null}}")
    private Integer minEvictableIdleTimeMillis;

    @Value("${spring.datasource.validationQuery:#{null}}")
    private String validationQuery;

    @Value("${spring.datasource.testWhileIdle:#{null}}")
    private Boolean testWhileIdle;

    @Value("${spring.datasource.testOnBorrow:#{null}}")
    private Boolean testOnBorrow;

    @Value("${spring.datasource.testOnReturn:#{null}}")
    private Boolean testOnReturn;

    @Value("${spring.datasource.poolPreparedStatements:#{null}}")
    private Boolean poolPreparedStatements;

    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize:#{null}}")
    private Integer maxPoolPreparedStatementPerConnectionSize;

    @Value("${spring.datasource.filters:#{null}}")
    private String filters;

    @Bean
    @Primary
    public DataSource dateSource(){
        DruidDataSource dataSource=new DruidDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(name);
        dataSource.setPassword(password);

        if(initialSize != null) {
            dataSource.setInitialSize(initialSize);
        }
        if(minIdle != null) {
            dataSource.setMinIdle(minIdle);
        }
        if(maxActive != null) {
            dataSource.setMaxActive(maxActive);
        }
        if(maxWait != null) {
            dataSource.setMaxWait(maxWait);
        }
        if(timeBetweenEvictionRunsMillis != null) {
            dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        }
        if(minEvictableIdleTimeMillis != null) {
            dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        }
        if(validationQuery!=null) {
            dataSource.setValidationQuery(validationQuery);
        }
        if(testWhileIdle != null) {
            dataSource.setTestWhileIdle(testWhileIdle);
        }
        if(testOnBorrow != null) {
            dataSource.setTestOnBorrow(testOnBorrow);
        }
        if(testOnReturn != null) {
            dataSource.setTestOnReturn(testOnReturn);
        }
        if(poolPreparedStatements != null) {
            dataSource.setPoolPreparedStatements(poolPreparedStatements);
        }
        if(maxPoolPreparedStatementPerConnectionSize != null) {
            dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        }

        List<Filter> filters = new ArrayList<>();
        filters.add(statFilter());
        filters.add(wallFilter());
        dataSource.setProxyFilters(filters);

        return dataSource;

    }


    @Bean
    public StatFilter statFilter(){
        StatFilter statFilter = new StatFilter();
        //slowSqlMillis用来配置SQL慢的标准，执行时间超过slowSqlMillis的就是慢。
        statFilter.setLogSlowSql(true);
        //SQL合并配置
        statFilter.setMergeSql(true);
        //slowSqlMillis的缺省值为3000，也就是3秒。
        statFilter.setSlowSqlMillis(1000);
        return statFilter;
    }

    @Bean
    public WallFilter wallFilter(){
        WallFilter wallFilter = new WallFilter();
        //允许执行多条SQL
        WallConfig config = new WallConfig();
        config.setMultiStatementAllow(true);
        wallFilter.setConfig(config);
        return wallFilter;
    }


    /**mybatis sqlSessionFactory配置*/
    @Bean
    public SqlSessionFactoryBean sessionFactoryBean(DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean= new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        //PathMatchingResourcePatternResolver() 配置路径通配符
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("mapper/*.xml"));
        return sqlSessionFactoryBean;
    }

    /**mybatis sqlSession配置 可以替代sqlSessionFactory简化代码*/
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
