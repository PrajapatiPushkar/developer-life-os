package com.pushkar.developerlifeos.service;

import com.pushkar.developerlifeos.dto.TaskRequestDTO;
import com.pushkar.developerlifeos.dto.TaskResponseDTO;
import com.pushkar.developerlifeos.entity.Task;
import com.pushkar.developerlifeos.exception.TaskNotFoundException;
import com.pushkar.developerlifeos.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    // Constructor Injection
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Get All Tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Create New Task
    public Task createTask(TaskRequestDTO dto){

        Task task = new Task();

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCompleted(dto.isCompleted());
        task.setPriority(dto.getPriority());
        task.setDueDate(dto.getDueDate());

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

        TaskResponseDTO dto = new TaskResponseDTO();

        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setCompleted(task.isCompleted());
        dto.setPriority(task.getPriority().name());
        dto.setDueDate(task.getDueDate());

        return dto;
    }
}