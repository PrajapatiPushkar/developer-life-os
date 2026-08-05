package com.pushkar.developerlifeos.service;

import com.pushkar.developerlifeos.dto.GoalRequestDTO;
import com.pushkar.developerlifeos.dto.GoalResponseDTO;
import com.pushkar.developerlifeos.dto.GoalStatisticsDTO;
import com.pushkar.developerlifeos.entity.Goal;
import com.pushkar.developerlifeos.entity.GoalStatus;
import com.pushkar.developerlifeos.exception.TaskNotFoundException;
import com.pushkar.developerlifeos.repository.GoalRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class GoalService {

    private final GoalRepository goalRepository;
    private final ModelMapper modelMapper;

    public GoalService(
            GoalRepository goalRepository,
            ModelMapper modelMapper) {

        this.goalRepository = goalRepository;
        this.modelMapper = modelMapper;
    }

    public Goal createGoal(GoalRequestDTO dto) {

        Goal goal = modelMapper.map(dto, Goal.class);

        return goalRepository.save(goal);

    }

    public List<GoalResponseDTO> getAllGoals() {

        return goalRepository.findAll()

                .stream()

                .map(goal -> modelMapper.map(
                        goal,
                        GoalResponseDTO.class))

                .toList();

    }

    public GoalResponseDTO getGoalById(Long id) {

        Goal goal = goalRepository.findById(id)

                .orElseThrow(() ->
                        new TaskNotFoundException(
                                "Goal not found"));

        return modelMapper.map(
                goal,
                GoalResponseDTO.class);

    }

    public Goal updateGoal(Long id, GoalRequestDTO dto) {

        Goal existingGoal = goalRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Goal not found"));

        existingGoal.setTitle(dto.getTitle());
        existingGoal.setDescription(dto.getDescription());
        existingGoal.setTargetDate(dto.getTargetDate());
        existingGoal.setProgress(dto.getProgress());
        existingGoal.setStatus(dto.getStatus());
        existingGoal.setCategory(dto.getCategory());

        return goalRepository.save(existingGoal);
    }

    public void deleteGoal(Long id) {

        Goal goal = goalRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Goal not found"));

        goalRepository.delete(goal);
    }

    public GoalStatisticsDTO getStatistics() {

        return GoalStatisticsDTO.builder()

                .totalGoals(goalRepository.count())

                .completedGoals(
                        goalRepository.countByStatus(GoalStatus.COMPLETED))

                .inProgressGoals(
                        goalRepository.countByStatus(GoalStatus.IN_PROGRESS))

                .notStartedGoals(
                        goalRepository.countByStatus(GoalStatus.NOT_STARTED))

                .build();

    }

}