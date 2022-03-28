package com.example.firstproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity // DB가 해당 객체를 인식 가능
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Article {

    @Id // Entity는 대표값을 하나 지정해줘야함. 주민등록번호 같은 것임
    @GeneratedValue // 1, 2, 3 ... 과 같이 자동 생성 어노테이션
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

}