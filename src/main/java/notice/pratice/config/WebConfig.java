package notice.pratice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/save", "/auth/login", "/error","/swagger-resources/**","/swagger-ui.html","/v2/api-docs","/webjars/**" , "/" , "/csrf");
    }

    @Bean
    public JoinCheckInterceptor customInterceptor() {
        return new JoinCheckInterceptor();
    }
}
