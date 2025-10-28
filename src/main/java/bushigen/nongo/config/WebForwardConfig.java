package bushigen.nongo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
}
