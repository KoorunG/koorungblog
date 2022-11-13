package com.koorung.blog.global.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * PackageName : com.koorung.blog.global.config
 * FileName : QuerydslConfig
 * Author : Koorung
 * Date : 2022년 11월 09일
 * Description : QueryDsl용 설정파일, JPAQueryFactory를 스프링 빈에 주입하는 용도
 */
@Configuration
public class QuerydslConfig {

    @PersistenceContext
    private EntityManager em;

    // querydsl용 JPAQueryFactory 빈 등록
    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(em);
    }
}
