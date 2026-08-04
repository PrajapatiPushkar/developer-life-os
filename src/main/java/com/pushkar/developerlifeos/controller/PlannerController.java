package com.pushkar.developerlifeos.controller;

import com.pushkar.developerlifeos.dto.ApiResponse;
import com.pushkar.developerlifeos.dto.PlannerRequestDTO;
import com.pushkar.developerlifeos.dto.PlannerResponseDTO;
import com.pushkar.developerlifeos.entity.Planner;
import com.pushkar.developerlifeos.entity.TimeSlot;
import com.pushkar.developerlifeos.service.PlannerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    // update
    @PutMapping("/{id}")
    public ResponseEntity<Planner> updatePlanner(

            @PathVariable Long id,

            @RequestBody PlannerRequestDTO dto) {

        return ResponseEntity.ok(

                plannerService.updatePlanner(id, dto)

        );

    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlanner(

            @PathVariable Long id) {

        plannerService.deletePlanner(id);

        return ResponseEntity.noContent().build();

    }

    // Toggle Complete
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Planner> toggleCompleted(

            @PathVariable Long id) {

        return ResponseEntity.ok(

                plannerService.toggleCompleted(id)

        );

    }

    // Filter by Date
    @GetMapping("/date")
    public ResponseEntity<List<PlannerResponseDTO>>

    plannerByDate(

            @RequestParam LocalDate date) {

        return ResponseEntity.ok(

                plannerService.getPlannerByDate(date)

        );

    }

    // Filter by Time Slot
    @GetMapping("/timeslot")
    public ResponseEntity<List<PlannerResponseDTO>>

    plannerByTimeSlot(

            @RequestParam TimeSlot slot) {

        return ResponseEntity.ok(

                plannerService.getPlannerByTimeSlot(slot)

        );

    }

    // Focus
    @GetMapping("/focus")
    public ResponseEntity<List<PlannerResponseDTO>>
    todayFocus() {

        return ResponseEntity.ok(

                plannerService.getTodayFocus()

        );

    }

}