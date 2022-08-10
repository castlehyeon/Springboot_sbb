package com.ll.exam.sbb;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    //mappedBy는 참조 엔티티의 속성명을 의미
    //Answer 엔티티에서 Question 엔티티를 참조한 속성명 question을 mappedBy에 전달
    //질문을 삭제하면 그에 달린 답변들도 모두 함께 삭제하기 위해서 @OneToMany의 속성으로 cascade = CascadeType.REMOVE
    private List<Answer> answerList;
    //질문과 답변은 1:N의 관계다.
    //질문객체에서 답을 참조하기 위해
}

