package com.pushkar.developerlifeos.repository;

import com.pushkar.developerlifeos.entity.Internship;
import com.pushkar.developerlifeos.entity.InternshipStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InternshipRepository
        extends JpaRepository<Internship, Long> {

    List<Internship> findByStatus(InternshipStatus status);

    List<Internship> findByCompanyNameContainingIgnoreCase(String companyName);

    List<Internship> findByRoleContainingIgnoreCase(String role);

    long countByStatus(InternshipStatus status);


}