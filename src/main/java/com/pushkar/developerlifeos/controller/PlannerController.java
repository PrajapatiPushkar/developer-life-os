package com.pushkar.developerlifeos.controller;

import com.pushkar.developerlifeos.dto.ApiResponse;
import com.pushkar.developerlifeos.dto.PlannerRequestDTO;
import com.pushkar.developerlifeos.dto.PlannerResponseDTO;
import com.pushkar.developerlifeos.entity.Planner;
import com.pushkar.developerlifeos.service.PlannerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/planner")
public class PlannerController {

    private final PlannerService plannerService;

    public PlannerController(
            PlannerService plannerService) {

        this.plannerService = plannerService;
    }

    // Create Planner
    @PostMapping
    public ResponseEntity<ApiResponse<Planner>> createPlanner(

            @Valid
            @RequestBody PlannerRequestDTO dto) {

        Planner planner =
                plannerService.createPlanner(dto);

        ApiResponse<Planner> response =
                new ApiResponse<>(

                        true,

                        "Planner created successfully",

                        planner,

                        LocalDateTime.now()

                );

        return ResponseEntity.ok(response);

    }

    // Get All Planners
    @GetMapping
    public ResponseEntity<List<PlannerResponseDTO>>
    getAllPlanners() {

        return ResponseEntity.ok(

                plannerService.getAllPlanners()

        );

    }

    // Get Planner By Id
    @GetMapping("/{id}")
    public ResponseEntity<PlannerResponseDTO>
    getPlannerById(

            @PathVariable Long id) {

        return ResponseEntity.ok(

                plannerService.getPlannerById(id)

        );

    }

}