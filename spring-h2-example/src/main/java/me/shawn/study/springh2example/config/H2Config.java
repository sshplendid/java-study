package me.shawn.study.springh2example.config;

import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class H2Config {
    static final String H2_WEB_CONSOLE_URL = "/h2console/*";

    @Bean
    ServletRegistrationBean h2ServletResgitration() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
        registrationBean.addUrlMappings(H2_WEB_CONSOLE_URL);
        return registrationBean;
    }
}
