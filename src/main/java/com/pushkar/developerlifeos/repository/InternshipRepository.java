package com.pushkar.developerlifeos.repository;

import com.pushkar.developerlifeos.entity.Internship;
import com.pushkar.developerlifeos.entity.InternshipStatus;
import com.pushkar.developerlifeos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InternshipRepository
        extends JpaRepository<Internship, Long> {

    // ==========================
    // User Specific
    // ==========================

    List<Internship> findByUser(User user);

    Optional<Internship> findByIdAndUser(
            Long id,
            User user
    );

    // ==========================
    // Search / Filter
    // ==========================

    List<Internship> findByUserAndStatus(
            User user,
            InternshipStatus status
    );

    List<Internship> findByUserAndCompanyNameContainingIgnoreCase(
            User user,
            String companyName
    );

    List<Internship> findByUserAndRoleContainingIgnoreCase(
            User user,
            String role
    );

    // ==========================
    // Statistics
    // ==========================

    long countByUser(User user);

    long countByUserAndStatus(
            User user,
            InternshipStatus status
    );
}