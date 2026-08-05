package com.pushkar.developerlifeos.service;

import com.pushkar.developerlifeos.dto.DSAStatisticsDTO;
import com.pushkar.developerlifeos.dto.ProblemRequestDTO;
import com.pushkar.developerlifeos.dto.ProblemResponseDTO;
import com.pushkar.developerlifeos.entity.Difficulty;
import com.pushkar.developerlifeos.entity.Problem;
import com.pushkar.developerlifeos.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProblemService {

    private final ProblemRepository problemRepository;
    private final ModelMapper modelMapper;


    // Create Problem
    public Problem createProblem(ProblemRequestDTO dto) {

        Problem problem = modelMapper.map(dto, Problem.class);

        log.info("Creating DSA Problem : {}", dto.getTitle());

        return problemRepository.save(problem);

    }

    // Get All Problems
    public List<ProblemResponseDTO> getAllProblems() {

        return problemRepository.findAll()

                .stream()

                .map(problem ->

                        modelMapper.map(
                                problem,
                                ProblemResponseDTO.class))

                .toList();

    }

    // Get Problem by ID
    public ProblemResponseDTO getProblemById(Long id) {

        Problem problem = problemRepository.findById(id)

                .orElseThrow(() ->

                        new RuntimeException("Problem not found"));

        return modelMapper.map(
                problem,
                ProblemResponseDTO.class);

    }

    // Update Problem
    public Problem updateProblem(Long id,
                                 ProblemRequestDTO dto) {

        Problem problem = problemRepository.findById(id)

                .orElseThrow(() ->
                        new RuntimeException("Problem not found"));

        problem.setTitle(dto.getTitle());
        problem.setPlatform(dto.getPlatform());
        problem.setDifficulty(dto.getDifficulty());
        problem.setTopic(dto.getTopic());
        problem.setSolved(dto.isSolved());
        problem.setProblemLink(dto.getProblemLink());
        problem.setSolutionLink(dto.getSolutionLink());
        problem.setNotes(dto.getNotes());
        problem.setSolvedDate(dto.getSolvedDate());

        log.info("Updating Problem : {}", id);

        return problemRepository.save(problem);

    }

    // Delete Problem
    public void deleteProblem(Long id) {

        Problem problem = problemRepository.findById(id)

                .orElseThrow(() ->
                        new RuntimeException("Problem not found"));

        problemRepository.delete(problem);

        log.info("Deleted Problem : {}", id);

    }

    public DSAStatisticsDTO getStatistics() {

        long total = problemRepository.count();

        long solved = problemRepository.countBySolved(true);

        long unsolved = problemRepository.countBySolved(false);

        long easy = problemRepository.countByDifficulty(Difficulty.EASY);

        long medium = problemRepository.countByDifficulty(Difficulty.MEDIUM);

        long hard = problemRepository.countByDifficulty(Difficulty.HARD);

        return new DSAStatisticsDTO(

                total,

                solved,

                unsolved,

                easy,

                medium,

                hard

        );

    }

}