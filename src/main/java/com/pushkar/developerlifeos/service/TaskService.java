package com.pushkar.developerlifeos.service;

import com.pushkar.developerlifeos.dto.TaskRequestDTO;
import com.pushkar.developerlifeos.dto.TaskResponseDTO;
import com.pushkar.developerlifeos.entity.Priority;
import com.pushkar.developerlifeos.entity.Task;
import com.pushkar.developerlifeos.exception.TaskNotFoundException;
import com.pushkar.developerlifeos.repository.TaskRepository;
import com.pushkar.developerlifeos.specification.TaskSpecification;

import org.modelmapper.ModelMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;


    // Constructor Injection
    public TaskService(TaskRepository taskRepository,
                       ModelMapper modelMapper) {

        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
    }



    // Get All Tasks
    public
    Page<Task> getAllTasks(Pageable pageable){

        return taskRepository.findAll(pageable);

    }

    // Create New Task
    public Task createTask(TaskRequestDTO dto){

        Task task = modelMapper.map(dto, Task.class);

        return taskRepository.save(task);
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

        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found with id : " + id));

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setPriority(updatedTask.getPriority());
        existingTask.setCompleted(updatedTask.isCompleted());
        existingTask.setDueDate(updatedTask.getDueDate());

        return taskRepository.save(existingTask);
    }

    // Delete task
    public void deleteTask(Long id) {

        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found with id : " + id));

        taskRepository.delete(existingTask);
    }

    private TaskResponseDTO convertToDTO(Task task){

        return modelMapper.map(task, TaskResponseDTO.class);

    }


    public Page<TaskResponseDTO> searchTasks(
            String keyword,
            Pageable pageable) {

        Page<Task> tasks =
                taskRepository.findByTitleContainingIgnoreCase(
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


}