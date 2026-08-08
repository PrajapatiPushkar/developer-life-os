package com.pushkar.developerlifeos.service;

import com.pushkar.developerlifeos.dto.ProblemRequestDTO;
import com.pushkar.developerlifeos.dto.ProblemResponseDTO;
import com.pushkar.developerlifeos.dto.ProblemStatisticsDTO;
import com.pushkar.developerlifeos.entity.Difficulty;
import com.pushkar.developerlifeos.entity.Platform;
import com.pushkar.developerlifeos.entity.Problem;
import com.pushkar.developerlifeos.entity.Topic;
import com.pushkar.developerlifeos.entity.User;
import com.pushkar.developerlifeos.repository.ProblemRepository;
import com.pushkar.developerlifeos.specification.ProblemSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProblemService {

    private final ProblemRepository problemRepository;

    private final ModelMapper modelMapper;

    private final CurrentUserService currentUserService;


    // ==========================
    // Create Problem
    // ==========================

    public Problem createProblem(ProblemRequestDTO dto) {

        User currentUser =
                currentUserService.getCurrentUser();

        Problem problem =
                modelMapper.map(dto, Problem.class);

        problem.setUser(currentUser);

        log.info(
                "Creating DSA Problem: {} for user: {}",
                dto.getTitle(),
                currentUser.getUsername()
        );

        return problemRepository.save(problem);
    }


    // ==========================
    // Get All Problems
    // ==========================

    public List<ProblemResponseDTO> getAllProblems() {

        User currentUser =
                currentUserService.getCurrentUser();

        return problemRepository
                .findByUser(currentUser)

                .stream()

                .map(problem ->
                        modelMapper.map(
                                problem,
                                ProblemResponseDTO.class
                        )
                )

                .toList();
    }


    // ==========================
    // Get Problem By ID
    // ==========================

    public ProblemResponseDTO getProblemById(Long id) {

        User currentUser =
                currentUserService.getCurrentUser();

        Problem problem =
                problemRepository
                        .findByIdAndUser(
                                id,
                                currentUser
                        )

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Problem not found"
                                )
                        );

        return modelMapper.map(
                problem,
                ProblemResponseDTO.class
        );
    }


    // ==========================
    // Update Problem
    // ==========================

    public Problem updateProblem(
            Long id,
            ProblemRequestDTO dto) {

        User currentUser =
                currentUserService.getCurrentUser();

        Problem problem =
                problemRepository
                        .findByIdAndUser(
                                id,
                                currentUser
                        )

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Problem not found"
                                )
                        );

        problem.setTitle(dto.getTitle());

        problem.setPlatform(
                dto.getPlatform()
        );

        problem.setDifficulty(
                dto.getDifficulty()
        );

        problem.setTopic(
                dto.getTopic()
        );

        problem.setSolved(
                dto.isSolved()
        );

        problem.setProblemLink(
                dto.getProblemLink()
        );

        problem.setSolutionLink(
                dto.getSolutionLink()
        );

        problem.setNotes(
                dto.getNotes()
        );

        problem.setSolvedDate(
                dto.getSolvedDate()
        );

        log.info(
                "Updating Problem: {} for user: {}",
                id,
                currentUser.getUsername()
        );

        return problemRepository.save(problem);
    }


    // ==========================
    // Delete Problem
    // ==========================

    public void deleteProblem(Long id) {

        User currentUser =
                currentUserService.getCurrentUser();

        Problem problem =
                problemRepository
                        .findByIdAndUser(
                                id,
                                currentUser
                        )

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Problem not found"
                                )
                        );

        problemRepository.delete(problem);

        log.info(
                "Deleted Problem: {} for user: {}",
                id,
                currentUser.getUsername()
        );
    }


    // ==========================
    // Statistics
    // ==========================

    public ProblemStatisticsDTO getStatistics() {

        User currentUser =
                currentUserService.getCurrentUser();

        long total =
                problemRepository.countByUser(
                        currentUser
                );

        long solved =
                problemRepository.countByUserAndSolved(
                        currentUser,
                        true
                );

        long unsolved =
                problemRepository.countByUserAndSolved(
                        currentUser,
                        false
                );

        long easy =
                problemRepository.countByUserAndDifficulty(
                        currentUser,
                        Difficulty.EASY
                );

        long medium =
                problemRepository.countByUserAndDifficulty(
                        currentUser,
                        Difficulty.MEDIUM
                );

        long hard =
                problemRepository.countByUserAndDifficulty(
                        currentUser,
                        Difficulty.HARD
                );

        return new ProblemStatisticsDTO(

                total,

                solved,

                unsolved,

                easy,

                medium,

                hard
        );
    }


    // ==========================
    // Filter Problems
    // ==========================

    public List<ProblemResponseDTO> filterProblems(

            String title,

            Difficulty difficulty,

            Platform platform,

            Topic topic,

            Boolean solved) {

        User currentUser =
                currentUserService.getCurrentUser();

        Specification<Problem> specification =
                Specification.allOf();


        // ==========================
        // IMPORTANT:
        // Always filter by current user
        // ==========================

        specification = specification.and(
                (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(
                                root.get("user"),
                                currentUser
                        )
        );


        if (title != null && !title.isBlank()) {

            specification =
                    specification.and(
                            ProblemSpecification.hasTitle(
                                    title
                            )
                    );
        }


        if (difficulty != null) {

            specification =
                    specification.and(
                            ProblemSpecification.hasDifficulty(
                                    difficulty
                            )
                    );
        }


        if (platform != null) {

            specification =
                    specification.and(
                            ProblemSpecification.hasPlatform(
                                    platform
                            )
                    );
        }


        if (topic != null) {

            specification =
                    specification.and(
                            ProblemSpecification.hasTopic(
                                    topic
                            )
                    );
        }


        if (solved != null) {

            specification =
                    specification.and(
                            ProblemSpecification.isSolved(
                                    solved
                            )
                    );
        }


        return problemRepository
                .findAll(specification)

                .stream()

                .map(problem ->
                        modelMapper.map(
                                problem,
                                ProblemResponseDTO.class
                        )
                )

                .toList();
    }

}