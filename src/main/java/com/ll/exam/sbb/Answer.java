package com.ll.exam.sbb;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.ManyToOne;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;
    @ManyToOne
    private Question question;
    //답변은 질문에 대해 N:1의 관계다.
}
