package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private LocalDate created_at;
    private LocalDate updated_at;

    @OneToMany(mappedBy = "category", cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<Quiz> quizzes = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<Question> questions = new ArrayList<>();


}
