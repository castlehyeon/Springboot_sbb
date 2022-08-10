package com.ll.exam.sbb;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
//컨트롤러에 @Controller 애너테이션을 적용하는 것과 마찬가지로 엔티티는 @Entity 애너테이션을 적용
public class Question {
    @Id
    //고유 번호 id 속성에 적용한 @Id 애너테이션은 id 속성을 기본 키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GeneratedValue 애너테이션을 적용하면 데이터를 저장할 때 해당 속성에 값을 따로 세팅하지 않아도 1씩 자동으로 증가하여 저장
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    //columnDefinition = "TEXT"은 "내용"처럼 글자 수를 제한할 수 없는 경
    private String content;
    //엔티티의 속성은 @Column 애너테이션을 사용하지 않더라도 테이블 컬럼으로 인식한다. 테이블 컬럼으로 인식하고 싶지 않은 경우에만 @Transient 애너테이션을 사용
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    //mappedBy는 참조 엔티티의 속성명을 의미
    //Answer 엔티티에서 Question 엔티티를 참조한 속성명 question을 mappedBy에 전달
    //질문을 삭제하면 그에 달린 답변들도 모두 함께 삭제하기 위해서 @OneToMany의 속성으로 cascade = CascadeType.REMOVE
    private List<Answer> answerList;
    //질문과 답변은 1:N의 관계다.
    //질문객체에서 답을 참조하기 위해
}

