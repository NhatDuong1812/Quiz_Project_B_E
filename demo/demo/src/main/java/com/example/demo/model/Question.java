package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question")
@Data
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String type;
    private String level;
    private String content;
    private String explanation;
    private LocalDate created_at;
    private LocalDate updated_at;


    @OneToMany(mappedBy = "question", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<QuestionAnswer> questionAnswers = new ArrayList<>();

    @OneToMany(mappedBy = "question", cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<Assumption> assumptions = new ArrayList<>();


    @ManyToMany(mappedBy = "questions", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Quiz> quizzes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


}
