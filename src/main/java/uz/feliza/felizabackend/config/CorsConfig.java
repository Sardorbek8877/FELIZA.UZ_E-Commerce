package uz.feliza.felizabackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("https://feliza-admin.netlify.app", "http://localhost:3000", "https://192.168.178.21")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }


}
