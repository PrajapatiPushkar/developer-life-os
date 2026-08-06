package com.pushkar.developerlifeos.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "educations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String collegeName;

    private String degree;

    private String fieldOfStudy;

    private Double cgpa;

    private Integer passingYear;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

}