package notice.pratice;

import com.querydsl.jpa.impl.JPAQueryFactory;
import notice.pratice.global.config.WebConfig;
import notice.pratice.utils.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@EnableJpaAuditing
@SpringBootApplication
public class PraticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PraticeApplication.class, args);
	}

	@Bean
	public AuditorAware<String> auditorProvider(HttpServletRequest request) {
		return new AuditorAware<String>() {
			@Override
			public Optional<String> getCurrentAuditor() {
				String userId = "";
				if (checkUrI(request)) {
					userId =  request.getAttribute("userId") != null ? String.valueOf(request.getAttribute("userId")) : JwtUtil.getUserId(JwtUtil.resolveToken(request));
				}
				return Optional.of(userId);
			}

			private Boolean checkUrI(HttpServletRequest request) {
				return !WebConfig.excludeArr.contains(request.getRequestURI()) ? true : false;
			}
		};
	}

	@Bean
	JPAQueryFactory queryFactory(EntityManager em) {
		return new JPAQueryFactory(em);
	}
}
