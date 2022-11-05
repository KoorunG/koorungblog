package com.koorung.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;


@SpringBootApplication
public class KoorungblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(KoorungblogApplication.class, args);
	}
}
