package com.pushkar.developerlifeos.service;

import com.pushkar.developerlifeos.dto.InternshipRequestDTO;
import com.pushkar.developerlifeos.dto.InternshipResponseDTO;
import com.pushkar.developerlifeos.dto.InternshipStatisticsDTO;
import com.pushkar.developerlifeos.entity.Internship;
import com.pushkar.developerlifeos.entity.InternshipStatus;
import com.pushkar.developerlifeos.entity.User;
import com.pushkar.developerlifeos.repository.InternshipRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InternshipService {

    private final InternshipRepository internshipRepository;

    private final ModelMapper modelMapper;

    private final CurrentUserService currentUserService;


    // ==========================
    // Create Internship
    // ==========================

    public Internship createInternship(
            InternshipRequestDTO dto) {

        User currentUser =
                currentUserService.getCurrentUser();

        Internship internship =
                modelMapper.map(
                        dto,
                        Internship.class
                );

        internship.setUser(currentUser);

        log.info(
                "Creating Internship: {} for user: {}",
                dto.getCompanyName(),
                currentUser.getUsername()
        );

        return internshipRepository.save(internship);
    }


    // ==========================
    // Get All Internships
    // ==========================

    public List<InternshipResponseDTO> getAllInternships() {

        User currentUser =
                currentUserService.getCurrentUser();

        return internshipRepository
                .findByUser(currentUser)

                .stream()

                .map(internship ->
                        modelMapper.map(
                                internship,
                                InternshipResponseDTO.class
                        )
                )

                .toList();
    }


    // ==========================
    // Get Internship By ID
    // ==========================

    public InternshipResponseDTO getInternshipById(
            Long id) {

        User currentUser =
                currentUserService.getCurrentUser();

        Internship internship =
                internshipRepository
                        .findByIdAndUser(
                                id,
                                currentUser
                        )

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Internship not found"
                                )
                        );

        return modelMapper.map(
                internship,
                InternshipResponseDTO.class
        );
    }


    // ==========================
    // Update Internship
    // ==========================

    public Internship updateInternship(
            Long id,
            InternshipRequestDTO dto) {

        User currentUser =
                currentUserService.getCurrentUser();

        Internship internship =
                internshipRepository
                        .findByIdAndUser(
                                id,
                                currentUser
                        )

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Internship not found"
                                )
                        );

        internship.setCompanyName(
                dto.getCompanyName()
        );

        internship.setRole(
                dto.getRole()
        );

        internship.setLocation(
                dto.getLocation()
        );

        internship.setApplicationDate(
                dto.getApplicationDate()
        );

        internship.setDeadline(
                dto.getDeadline()
        );

        internship.setStatus(
                dto.getStatus()
        );

        internship.setJobLink(
                dto.getJobLink()
        );

        internship.setSalary(
                dto.getSalary()
        );

        internship.setNotes(
                dto.getNotes()
        );

        log.info(
                "Updating Internship: {} for user: {}",
                id,
                currentUser.getUsername()
        );

        return internshipRepository.save(internship);
    }


    // ==========================
    // Delete Internship
    // ==========================

    public void deleteInternship(Long id) {

        User currentUser =
                currentUserService.getCurrentUser();

        Internship internship =
                internshipRepository
                        .findByIdAndUser(
                                id,
                                currentUser
                        )

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Internship not found"
                                )
                        );

        internshipRepository.delete(internship);

        log.info(
                "Deleted Internship: {} for user: {}",
                id,
                currentUser.getUsername()
        );
    }


    // ==========================
    // Statistics
    // ==========================

    public InternshipStatisticsDTO getStatistics() {

        User currentUser =
                currentUserService.getCurrentUser();

        return InternshipStatisticsDTO.builder()

                .totalApplications(
                        internshipRepository.countByUser(
                                currentUser
                        )
                )

                .applied(
                        internshipRepository
                                .countByUserAndStatus(
                                        currentUser,
                                        InternshipStatus.APPLIED
                                )
                )

                .onlineAssessment(
                        internshipRepository
                                .countByUserAndStatus(
                                        currentUser,
                                        InternshipStatus.ONLINE_ASSESSMENT
                                )
                )

                .interview(
                        internshipRepository
                                .countByUserAndStatus(
                                        currentUser,
                                        InternshipStatus.INTERVIEW
                                )
                )

                .hrRound(
                        internshipRepository
                                .countByUserAndStatus(
                                        currentUser,
                                        InternshipStatus.HR_ROUND
                                )
                )

                .selected(
                        internshipRepository
                                .countByUserAndStatus(
                                        currentUser,
                                        InternshipStatus.SELECTED
                                )
                )

                .rejected(
                        internshipRepository
                                .countByUserAndStatus(
                                        currentUser,
                                        InternshipStatus.REJECTED
                                )
                )

                .offerAccepted(
                        internshipRepository
                                .countByUserAndStatus(
                                        currentUser,
                                        InternshipStatus.OFFER_ACCEPTED
                                )
                )

                .build();
    }
}