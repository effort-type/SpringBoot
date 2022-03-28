package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
@Slf4j // 로깅을 위한 어노테이션
public class ArticleController {

    @Autowired // 스프링 부트가 미리 생성해놓은 객체를 가져다가 자동 연결, new 사용 할 필요 없음
    private ArticleRepository articleRepository;

    @GetMapping("articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        // System.out.println(form.toString()); -> 로깅 기능으로 대체
        log.info(form.toString());


        // 1. Dto를 Entity로 변환
        Article article = form.toEntity();
        log.info(article.toString());
        //System.out.println(article.toString());


        // 2. Repository에게 Entity를 DB에 저장하도록 해야함.
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
        //System.out.println(saved.toString());

        return "redirect:/articles/" + saved.getId();
    }


    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("id = " + id);

        // 1: id로 데이터를 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null); // id값을 찾았는데 없다면 orElse null을 반환해라

        // 2: 가져온 데이터를 모델에 등록하기
        model.addAttribute("article", articleEntity);

        // 3: 보여줄 페이지 설정하기
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {

        // 1: 모든 Article을 가져온다.
        List<Article> articleEntityList = articleRepository.findAll(); // repository에 있는 ArticleRepository에서 오버라이딩하여 ArrayList로 바꾸면 오류 해결

        // 2: 가져온 Article를 List 묶음으로 뷰에 전달
        model.addAttribute("articleList", articleEntityList);

        // 3: 뷰 페이지 설정
        return "articles/index";
    }


    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        // 수정할 데이터를 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 모델에 데이터 등록
        model.addAttribute("article", articleEntity);

        // 뷰 페이지 설정
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form) {
        log.info(form.toString());

        // 1: Dto를 Entity로 변환
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        // 2: Entity를 DB로 저장
        // 2-1: DB에서 기존 데이터를 가져온다
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        // 2-2: 기존 데이터이 있다면 값을 갱신한다.
        if(target != null) {
            articleRepository.save(articleEntity);
        }

        // 3: 수정 결과 페이지로 redirect
        return "redirect:/articles/" + articleEntity.getId();
    }

}
