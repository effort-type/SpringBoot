package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    // Repository를 직접 구현할 수 있지만 jpa에서 제공하는 crudRepository 사용
    // <>안에는 <관리대상Entity, 관리대상 Entity의 대표값 타입>
}
