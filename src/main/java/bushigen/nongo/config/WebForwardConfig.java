package bushigen.nongo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class WebForwardConfig implements WebMvcConfigurer {

    public void addViewControllers(@NonNull ViewControllerRegistry registry) {
        // All SPA routes except /api/** go to index.html
        registry.addViewController("/software-engineer/**").setViewName("forward:/index.html");
        // you can add other top-level SPA route prefixes here
    }

    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(false);
    }

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:5173")
            .allowedMethods("GET","POST")
            .allowedHeaders("*")
            .allowCredentials(true);
    }

}
