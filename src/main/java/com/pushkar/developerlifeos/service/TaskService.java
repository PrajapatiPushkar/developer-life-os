package com.pushkar.developerlifeos.service;

import com.pushkar.developerlifeos.dto.DashboardSummaryDTO;
import com.pushkar.developerlifeos.dto.TaskRequestDTO;
import com.pushkar.developerlifeos.dto.TaskResponseDTO;
import com.pushkar.developerlifeos.entity.Priority;
import com.pushkar.developerlifeos.entity.Task;
import com.pushkar.developerlifeos.entity.TaskStatus;
import com.pushkar.developerlifeos.exception.TaskNotFoundException;
import com.pushkar.developerlifeos.repository.TaskRepository;
import com.pushkar.developerlifeos.specification.TaskSpecification;
import com.pushkar.developerlifeos.entity.User;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    private final CurrentUserService currentUserService;


    // Constructor Injection
    public TaskService(TaskRepository taskRepository,
                       ModelMapper modelMapper,
                       CurrentUserService currentUserService) {

        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
        this.currentUserService = currentUserService;
    }



    // Get All Tasks
    public
    Page<Task> getAllTasks(Pageable pageable){

        return taskRepository.findAll(pageable);

    }

    // Create New Task
    public Task createTask(TaskRequestDTO dto){

        log.info("Creating task with title: {}", dto.getTitle());

        User currentUser = currentUserService.getCurrentUser();

        Task task = modelMapper.map(dto, Task.class);

        task.setUser(currentUser);

        Task savedTask = taskRepository.save(task);

        log.info("Task created successfully. Task ID: {}", savedTask.getId());

        return savedTask;
    }

    // Get Task by ID
    public TaskResponseDTO getTaskById(Long id){

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found"));

        return convertToDTO(task);
    }

    // Update task
    public Task updateTask(Long id, Task updatedTask) {

        log.info("Updating task with id: {}", id);

        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found with id : " + id));

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setPriority(updatedTask.getPriority());
        existingTask.setCompleted(updatedTask.isCompleted());
        existingTask.setDueDate(updatedTask.getDueDate());

        log.info("Task updated successfully: {}", id);
        return taskRepository.save(existingTask);

    }

    // Delete task
    public void deleteTask(Long id) {
        log.info("Deleting task with id: {}", id);

        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found with id : " + id));

        taskRepository.delete(existingTask);
        log.info("Deleting task with id: {}", id);
    }

    private TaskResponseDTO convertToDTO(Task task){

        return modelMapper.map(task, TaskResponseDTO.class);

    }


    public Page<TaskResponseDTO> searchTasks(
            String keyword,
            Pageable pageable) {

        User currentUser = currentUserService.getCurrentUser();

        Page<Task> tasks =
                taskRepository.findByUserAndTitleContainingIgnoreCase(
                        currentUser,
                        keyword,
                        pageable
                );

        return tasks.map(task ->
                modelMapper.map(task,
                        TaskResponseDTO.class));
    }

    public List<TaskResponseDTO> filterTasks(
            String title,
            Priority priority,
            TaskStatus status,
            Boolean completed) {

        Specification<Task> specification =
                Specification.allOf();

        if (title != null) {

            specification = specification.and(
                    TaskSpecification.hasTitle(title));

        }

        if (priority != null) {

            specification = specification.and(
                    TaskSpecification.hasPriority(priority));

        }

        if (status != null) {

            specification = specification.and(
                    TaskSpecification.hasStatus(status));

        }

        if (completed != null) {

            specification = specification.and(
                    TaskSpecification.isCompleted(completed));

        }

        return taskRepository.findAll(specification)
                .stream()
                .map(task -> modelMapper.map(task,
                        TaskResponseDTO.class))
                .toList();

    }

    public DashboardSummaryDTO getDashboardSummary() {

        User currentUser = currentUserService.getCurrentUser();

        long completed =
                taskRepository.countByUserAndCompleted(
                        currentUser,
                        true
                );

        long pending =
                taskRepository.countByUserAndCompleted(
                        currentUser,
                        false
                );

        long high =
                taskRepository.countByUserAndPriority(
                        currentUser,
                        Priority.HIGH
                );

        long overdue =
                taskRepository.countByUserAndDueDateBeforeAndCompletedFalse(
                        currentUser,
                        LocalDate.now()
                );

        long today =
                taskRepository.countByUserAndDueDateAndCompletedFalse(
                        currentUser,
                        LocalDate.now()
                );

        long total = completed + pending;

        double progress =

                total == 0

                        ? 0

                        : ((double) completed / total) * 100;

        return new DashboardSummaryDTO(

                total,
                completed,
                pending,
                high,
                overdue,
                today,
                progress

        );

    }

    public List<TaskResponseDTO> getUpcomingTasks() {

        User currentUser =
                currentUserService.getCurrentUser();

        return taskRepository

                .findTop5ByUserOrderByDueDateAsc(currentUser)

                .stream()

                .map(task ->
                        modelMapper.map(task,
                                TaskResponseDTO.class))

                .toList();




    }


}