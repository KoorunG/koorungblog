package com.koorung.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@SpringBootApplication
public class KoorungblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(KoorungblogApplication.class, args);
	}

	// Auditing 클래스에 최초작성자, 수정자 정보를 제공
	@Bean
	public AuditorAware<String> auditorProvider() {
		return () -> Optional.of("koorung");		//TODO) 추후 Member의 name이나 loginId로 교체
	}
}
