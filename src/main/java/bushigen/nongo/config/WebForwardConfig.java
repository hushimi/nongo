package bushigen.nongo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebForwardConfig implements WebMvcConfigurer {

  public void addViewControllers(@NonNull ViewControllerRegistry registry) {
    // All SPA routes forward to index.html for client-side routing
    registry.addViewController("/login").setViewName("forward:/index.html");
    registry.addViewController("/signup").setViewName("forward:/index.html");
    registry.addViewController("/error").setViewName("forward:/index.html");
    registry.addViewController("/verify-email").setViewName("forward:/index.html");
    registry.addViewController("/software-engineer/**").setViewName("forward:/index.html");
  }

  @Override
  public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/**")
      .addResourceLocations("classpath:/static/")
      .resourceChain(false);
  }
}
