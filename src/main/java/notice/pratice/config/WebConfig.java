package notice.pratice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final String[] strArr = {"/users/save", "/auths/login", "/error", "/swagger-resources/**", "/swagger-ui.html", "/v2/api-docs", "/webjars/**", "/", "/csrf"};
    public static final List<String> excludeArr = new ArrayList<>(Arrays.asList(strArr));
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns(excludeArr);
    }

    @Bean
    public JoinCheckInterceptor customInterceptor() {
        return new JoinCheckInterceptor();
    }
}
