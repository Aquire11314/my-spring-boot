package com.example.demo.filter;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DruidConfiguration {

    @Bean
    public ServletRegistrationBean druidStatViewServle(){
        ServletRegistrationBean servletRegistrationBean=
                new ServletRegistrationBean(new StatViewServlet(),"/druid/*");

        //白名单
        servletRegistrationBean.addInitParameter("allow","127.0.0.1");

        //黑名单
        //servletRegistrationBean.addInitParameter("deny","192.168.1.73");

        servletRegistrationBean.addInitParameter("loginUsername","admin");
        servletRegistrationBean.addInitParameter("loginPassword","123456");

        //是否能够reset数据
        servletRegistrationBean.addInitParameter("resetEnable","false");
        return servletRegistrationBean;

    }

    @Bean
    public FilterRegistrationBean druidStatFilter(){
        FilterRegistrationBean filterRegistrationBean=
                new FilterRegistrationBean(new WebStatFilter());

        //添加过滤规则
        filterRegistrationBean.addUrlPatterns("/*");
        //添加需要忽略的格式
        filterRegistrationBean.addInitParameter("excludsions","*.js,*.gif,*.png,*.jpg,*.css,*.icon,/druid/*");
        return filterRegistrationBean;
    }
}
