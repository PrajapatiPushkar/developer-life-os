package com.pushkar.developerlifeos.controller;

import com.pushkar.developerlifeos.dto.ApiResponse;
import com.pushkar.developerlifeos.dto.DashboardSummaryDTO;
import com.pushkar.developerlifeos.dto.TaskRequestDTO;
import com.pushkar.developerlifeos.dto.TaskResponseDTO;
import com.pushkar.developerlifeos.entity.Priority;
import com.pushkar.developerlifeos.entity.Task;
import com.pushkar.developerlifeos.service.TaskService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Task APIs", description = "Operations related to tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Get All Tasks")
    @GetMapping
    public Page<Task> getAllTasks(
            Pageable pageable){

        return taskService.getAllTasks(pageable);

    }

    @PostMapping
    public ResponseEntity<ApiResponse<Task>> createTask(
            @Valid
            @RequestBody TaskRequestDTO dto) {

        Task task = taskService.createTask(dto);

        ApiResponse<Task> response =
                new ApiResponse<>(
                        true,
                        "Task created successfully",
                        task,
                        LocalDateTime.now()
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponseDTO>> getTaskById(
            @PathVariable Long id){

        TaskResponseDTO task = taskService.getTaskById(id);

        ApiResponse<TaskResponseDTO> response =
                new ApiResponse<>(
                        true,
                        "Task fetched successfully",
                        task,
                        LocalDateTime.now()
                );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @RequestBody Task task) {

        Task updatedTask = taskService.updateTask(id, task);

        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponseDTO>> deleteTask(@PathVariable Long id) {

        taskService.deleteTask(id);

        TaskResponseDTO task = taskService.getTaskById(id);

        ApiResponse<TaskResponseDTO> response =
                new ApiResponse<>(
                        true,
                        "Task fetched successfully",
                        task,
                        LocalDateTime.now()
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<TaskResponseDTO>>
    searchTasks(
            @RequestParam String keyword,
            Pageable pageable) {

        return ResponseEntity.ok(
                taskService.searchTasks(
                        keyword,
                        pageable
                )
        );
    }

    @GetMapping("/filter")
    public ResponseEntity<List<TaskResponseDTO>> filterTasks(

            @RequestParam(required = false)
            String title,

            @RequestParam(required = false)
            Priority priority,

            @RequestParam(required = false)
            Boolean completed){

        return ResponseEntity.ok(

                taskService.filterTasks(
                        title,
                        priority,
                        completed
                )

        );
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardSummaryDTO>
    dashboard(){

        return ResponseEntity.ok(

                taskService.getDashboardSummary()

        );

    }
}
