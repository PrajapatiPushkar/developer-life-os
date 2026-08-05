package com.pushkar.developerlifeos.controller;

import com.pushkar.developerlifeos.dto.ApiResponse;
import com.pushkar.developerlifeos.dto.DSAStatisticsDTO;
import com.pushkar.developerlifeos.dto.ProblemRequestDTO;
import com.pushkar.developerlifeos.dto.ProblemResponseDTO;
import com.pushkar.developerlifeos.entity.Platform;
import com.pushkar.developerlifeos.entity.Difficulty;
import com.pushkar.developerlifeos.entity.Problem;
import com.pushkar.developerlifeos.entity.Topic;
import com.pushkar.developerlifeos.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/problems")
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    @PostMapping
    public ResponseEntity<ApiResponse<Problem>> createProblem(
            @RequestBody ProblemRequestDTO dto) {

        Problem problem = problemService.createProblem(dto);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Problem created successfully",
                        problem,
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<ProblemResponseDTO>> getAllProblems() {

        return ResponseEntity.ok(
                problemService.getAllProblems()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProblemResponseDTO> getProblemById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                problemService.getProblemById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Problem> updateProblem(
            @PathVariable Long id,
            @RequestBody ProblemRequestDTO dto) {

        return ResponseEntity.ok(
                problemService.updateProblem(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProblem(
            @PathVariable Long id) {

        problemService.deleteProblem(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/statistics")
    public ResponseEntity<DSAStatisticsDTO> getStatistics() {

        return ResponseEntity.ok(

                problemService.getStatistics()

        );

    }

    @GetMapping("/filter")
    public ResponseEntity<List<ProblemResponseDTO>>
    filterProblems(

            @RequestParam(required=false)
            String title,

            @RequestParam(required=false)
            Difficulty difficulty,

            @RequestParam(required=false)
            Platform platform,

            @RequestParam(required=false)
            Topic topic,

            @RequestParam(required=false)
            Boolean solved){

        return ResponseEntity.ok(

                problemService.filterProblems(

                        title,

                        difficulty,

                        platform,

                        topic,

                        solved

                )

        );

    }

}