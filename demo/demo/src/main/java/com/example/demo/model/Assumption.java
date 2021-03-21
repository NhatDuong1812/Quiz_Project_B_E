package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "assumption")
@Data
public class Assumption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String level;
    private String content;
    private String explanation;
    private Long guessNumber;
    private Long point;
    private String status;
    private boolean isCorrect;
    private boolean isFlag;
    private LocalDate created_at;
    private LocalDate updated_at;


    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "attempt_id")
    @JsonIgnore
    private Attempt attempt;

    @OneToMany(mappedBy = "assumption", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<UserAnswer> userAnswers = new ArrayList<>();

}
