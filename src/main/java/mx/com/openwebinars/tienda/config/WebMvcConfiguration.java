package mx.com.openwebinars.tienda.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import mx.com.openwebinars.tienda.component.RequestTimeInterceptor;


@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer{

	@Autowired
	@Qualifier("requestTimeInterceptor")
	private RequestTimeInterceptor time;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(time);
	}

	@Bean
	WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("*")
				.allowedOrigins("*")
				.allowedMethods("GET", "POST", "PUT", "DELETE");
			}
		
		};
	}

}
