package fpt.aptech.pjs4.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/api/image/**")
                .addResourceLocations("classpath:/static/image/");
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/ws/**")
                .allowedOrigins("http://localhost:5173") // Chỉnh sửa nếu cần
                .allowedMethods("*")
                .allowCredentials(true);
    }
}




