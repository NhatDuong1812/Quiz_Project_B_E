package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "attempt")
@Data
public class Attempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    private String username;
    private String email;
    private double averageScore;
    private LocalDate submittedAt;
    private LocalDate createdAt;
    private Long takingTime;


    @ManyToOne
    @JoinColumn(name = "study_id")
    @JsonIgnore
    private Study study;


    @OneToMany(mappedBy = "attempt")
    private List<Assumption> assumptions;

}
