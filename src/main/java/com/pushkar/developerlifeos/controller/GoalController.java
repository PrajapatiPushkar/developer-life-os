package com.pushkar.developerlifeos.controller;

import com.pushkar.developerlifeos.dto.ApiResponse;
import com.pushkar.developerlifeos.dto.GoalRequestDTO;
import com.pushkar.developerlifeos.dto.GoalResponseDTO;
import com.pushkar.developerlifeos.entity.Goal;
import com.pushkar.developerlifeos.service.GoalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/goals")
public class GoalController {

    private final GoalService goalService;

    public GoalController(
            GoalService goalService) {

        this.goalService = goalService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Goal>>
    createGoal(

            @RequestBody GoalRequestDTO dto) {

        Goal goal =
                goalService.createGoal(dto);

        return ResponseEntity.ok(

                new ApiResponse<>(

                        true,

                        "Goal Created",

                        goal,

                        LocalDateTime.now()

                )

        );

    }

    @GetMapping
    public ResponseEntity<List<GoalResponseDTO>>
    getAllGoals() {

        return ResponseEntity.ok(

                goalService.getAllGoals()

        );

    }

    @GetMapping("/{id}")
    public ResponseEntity<GoalResponseDTO>
    getGoalById(

            @PathVariable Long id) {

        return ResponseEntity.ok(

                goalService.getGoalById(id)

        );

    }

    @PutMapping("/{id}")
    public ResponseEntity<Goal> updateGoal(
            @PathVariable Long id,
            @RequestBody GoalRequestDTO dto) {

        return ResponseEntity.ok(
                goalService.updateGoal(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoal(
            @PathVariable Long id) {

        goalService.deleteGoal(id);

        return ResponseEntity.noContent().build();
    }

}