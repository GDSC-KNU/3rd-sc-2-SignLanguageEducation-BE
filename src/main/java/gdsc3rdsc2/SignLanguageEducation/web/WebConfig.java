package gdsc3rdsc2.SignLanguageEducation.web;

import gdsc3rdsc2.SignLanguageEducation.util.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getJwtInterceptor())
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/user/join")
                .excludePathPatterns("/api/user/login");

    }

    @Bean
    public JwtInterceptor getJwtInterceptor() {
        return new JwtInterceptor();
    }

}
