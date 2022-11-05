package com.koorung.blog.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing  // JPA설정 따로 분리
public class JpaConfig {
    // Auditing 클래스에 최초작성자, 수정자 정보를 제공
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of("koorung");        //TODO) 추후 Member의 name이나 loginId로 교체
    }
}
