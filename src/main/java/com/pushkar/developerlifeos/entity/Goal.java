package com.pushkar.developerlifeos.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "goals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    private LocalDate targetDate;

    private Integer progress = 0;

    @Enumerated(EnumType.STRING)
    private GoalStatus status;

    @Enumerated(EnumType.STRING)
    private GoalCategory category;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

    }

    @PreUpdate
    public void onUpdate() {

        updatedAt = LocalDateTime.now();

    }

    @OneToMany(
            mappedBy = "goal",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Milestone> milestones = new ArrayList<>();

}