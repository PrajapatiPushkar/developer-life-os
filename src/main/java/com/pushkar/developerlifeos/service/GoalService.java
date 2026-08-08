package com.pushkar.developerlifeos.service;

import com.pushkar.developerlifeos.dto.GoalRequestDTO;
import com.pushkar.developerlifeos.dto.GoalResponseDTO;
import com.pushkar.developerlifeos.dto.GoalStatisticsDTO;
import com.pushkar.developerlifeos.entity.Goal;
import com.pushkar.developerlifeos.entity.User;
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
    private final CurrentUserService currentUserService;

    public GoalService(
            GoalRepository goalRepository,
            ModelMapper modelMapper,
            CurrentUserService currentUserService) {

        this.goalRepository = goalRepository;
        this.modelMapper = modelMapper;
        this.currentUserService = currentUserService;
    }

    public Goal createGoal(GoalRequestDTO dto) {

        User currentUser = currentUserService.getCurrentUser();

        Goal goal = modelMapper.map(dto, Goal.class);

        goal.setUser(currentUser);

        return goalRepository.save(goal);

    }

    public List<GoalResponseDTO> getAllGoals() {

        User currentUser = currentUserService.getCurrentUser();

        return goalRepository.findByUser(currentUser)

                .stream()

                .map(goal -> modelMapper.map(
                        goal,
                        GoalResponseDTO.class))

                .toList();

    }

    public GoalResponseDTO getGoalById(Long id) {

        User currentUser = currentUserService.getCurrentUser();

        Goal goal = goalRepository

                .findByIdAndUser(id, currentUser)

                .orElseThrow(() ->
                        new TaskNotFoundException(
                                "Goal not found"));

        return modelMapper.map(
                goal,
                GoalResponseDTO.class);

    }

    public Goal updateGoal(Long id, GoalRequestDTO dto) {

        User currentUser = currentUserService.getCurrentUser();

        Goal existingGoal = goalRepository

                .findByIdAndUser(id, currentUser)

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

        User currentUser = currentUserService.getCurrentUser();

        Goal goal = goalRepository

                .findByIdAndUser(id, currentUser)

                .orElseThrow(() ->
                        new RuntimeException("Goal not found"));

        goalRepository.delete(goal);

    }

    public GoalStatisticsDTO getStatistics() {

        User currentUser = currentUserService.getCurrentUser();

        return GoalStatisticsDTO.builder()

                .totalGoals(
                        goalRepository.countByUser(currentUser))

                .completedGoals(
                        goalRepository.countByUserAndStatus(
                                currentUser,
                                GoalStatus.COMPLETED))

                .inProgressGoals(
                        goalRepository.countByUserAndStatus(
                                currentUser,
                                GoalStatus.IN_PROGRESS))

                .notStartedGoals(
                        goalRepository.countByUserAndStatus(
                                currentUser,
                                GoalStatus.NOT_STARTED))

                .build();

    }

}