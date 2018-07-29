package config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author guohezuzi
 * \* Date: 18-2-2
 * \* Time: 下午5:30
 * \* Description:
 */
@EnableWebMvc
@ComponentScan({"controller", "config"})
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 配置视图解析
     **/
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        //jsp视图解析
        registry.jsp("/WEB-INF/views/", ".jsp");
        //order值越低优先级越高
        registry.order(2);
        registry.tiles();
    }

    /**配置thymeleaf视图解析*/
    @Bean
    public SpringResourceTemplateResolver springResourceTemplateResolver(){
        SpringResourceTemplateResolver springResourceTemplateResolver=new SpringResourceTemplateResolver();
        springResourceTemplateResolver.setPrefix("/WEB-INF/template/");
        springResourceTemplateResolver.setSuffix(".html");
        springResourceTemplateResolver.setTemplateMode("HTML5");
        return springResourceTemplateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine(SpringResourceTemplateResolver springResourceTemplateResolver){
        SpringTemplateEngine springTemplateEngine=new SpringTemplateEngine();
        springTemplateEngine.setTemplateResolver(springResourceTemplateResolver);
        return springTemplateEngine;
    }

    @Bean
    public ThymeleafViewResolver thymeleafViewResolver(SpringTemplateEngine springTemplateEngine){
        ThymeleafViewResolver thymeleafViewResolver=new ThymeleafViewResolver();
        thymeleafViewResolver.setTemplateEngine(springTemplateEngine);
        thymeleafViewResolver.setCharacterEncoding("UTF-8");
        thymeleafViewResolver.setOrder(1);
        return thymeleafViewResolver;
    }


    /**配置apache tiles视图解析*/
    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions("/WEB-INF/layout/tiles.xml",
                "/WEB-INF/views/**/tiles.xml");
        tilesConfigurer.setCheckRefresh(true);
        return tilesConfigurer;
    }

    /**配置静态资源处理**/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }

    /**
     * 用于国际化
     **/
    @Bean
    public MessageSource messageSource() {
        /*//方法一:
        ResourceBundleMessageSource messageSource=new ResourceBundleMessageSource();
        messageSource.setBasename("messages");//设置属性文件的基本名称,(不加前缀)会在web应用的根路下的属性文件中解析信息
        return messageSource;*/
        //方法二:
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultEncoding("utf-8");
        //在类路径下的属性文件,在文件系统中配置为:(file:/...)无法使用[存疑!!!(已解决,可正常使用,message匹配错误)]
        messageSource.setBasename("file:/home/guohezuzi/temp/messages");
        messageSource.setCacheSeconds(10);
        return messageSource;
    }

    @Bean
    MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    /**properties配置 详见http://www.importnew.com/27997.html **/
    @Bean
    public PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer propertyConfigurer = new PropertySourcesPlaceholderConfigurer();
        Resource[] resources=new ClassPathResource[]{new ClassPathResource("application.properties")};
        propertyConfigurer.setLocations(resources);
        return propertyConfigurer;
    }
}
