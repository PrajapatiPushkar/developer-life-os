package com.pushkar.developerlifeos.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "internships")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Internship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String role;

    private String location;

    private LocalDate applicationDate;

    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    private InternshipStatus status;

    private String jobLink;

    private String salary;

    @Column(length = 2000)
    private String notes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // ==========================
    // User Relationship
    // ==========================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    public void onCreate() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

    }

    @PreUpdate
    public void onUpdate() {

        updatedAt = LocalDateTime.now();

    }
}