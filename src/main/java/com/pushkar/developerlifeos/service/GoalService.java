package com.pushkar.developerlifeos.service;

import com.pushkar.developerlifeos.dto.GoalRequestDTO;
import com.pushkar.developerlifeos.dto.GoalResponseDTO;
import com.pushkar.developerlifeos.entity.Goal;
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

}