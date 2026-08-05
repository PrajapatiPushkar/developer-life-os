package com.pushkar.developerlifeos.controller;

import com.pushkar.developerlifeos.dto.ApiResponse;
import com.pushkar.developerlifeos.dto.InternshipRequestDTO;
import com.pushkar.developerlifeos.dto.InternshipResponseDTO;
import com.pushkar.developerlifeos.dto.InternshipStatisticsDTO;
import com.pushkar.developerlifeos.entity.Internship;
import com.pushkar.developerlifeos.service.InternshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/internships")
@RequiredArgsConstructor
public class InternshipController {

    private final InternshipService internshipService;

    @PostMapping
    public ResponseEntity<ApiResponse<Internship>> createInternship(

            @RequestBody InternshipRequestDTO dto) {

        Internship internship = internshipService.createInternship(dto);

        return ResponseEntity.ok(

                new ApiResponse<>(

                        true,

                        "Internship Created Successfully",

                        internship,

                        LocalDateTime.now()

                )

        );

    }

    @GetMapping
    public ResponseEntity<List<InternshipResponseDTO>> getAllInternships() {

        return ResponseEntity.ok(

                internshipService.getAllInternships()

        );

    }

    @GetMapping("/{id}")
    public ResponseEntity<InternshipResponseDTO> getInternshipById(

            @PathVariable Long id) {

        return ResponseEntity.ok(

                internshipService.getInternshipById(id)

        );

    }

    @PutMapping("/{id}")
    public ResponseEntity<Internship> updateInternship(

            @PathVariable Long id,

            @RequestBody InternshipRequestDTO dto) {

        return ResponseEntity.ok(

                internshipService.updateInternship(id, dto)

        );

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInternship(

            @PathVariable Long id) {

        internshipService.deleteInternship(id);

        return ResponseEntity.noContent().build();

    }

    @GetMapping("/statistics")
    public ResponseEntity<InternshipStatisticsDTO>
    getStatistics() {

        return ResponseEntity.ok(

                internshipService.getStatistics()

        );

    }

}