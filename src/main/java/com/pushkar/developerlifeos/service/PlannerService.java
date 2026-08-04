package com.pushkar.developerlifeos.service;

import com.pushkar.developerlifeos.dto.PlannerRequestDTO;
import com.pushkar.developerlifeos.dto.PlannerResponseDTO;
import com.pushkar.developerlifeos.entity.Planner;
import com.pushkar.developerlifeos.exception.TaskNotFoundException;
import com.pushkar.developerlifeos.repository.PlannerRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PlannerService {

    private final PlannerRepository plannerRepository;
    private final ModelMapper modelMapper;

    public PlannerService(
            PlannerRepository plannerRepository,
            ModelMapper modelMapper) {

        this.plannerRepository = plannerRepository;
        this.modelMapper = modelMapper;
    }

    // Create Planner
    public Planner createPlanner(PlannerRequestDTO dto) {

        Planner planner = modelMapper.map(dto, Planner.class);

        planner.setCompleted(false);

        return plannerRepository.save(planner);
    }

    // Get All Planners
    public List<PlannerResponseDTO> getAllPlanners() {

        return plannerRepository.findAll()

                .stream()

                .map(planner -> modelMapper.map(
                        planner,
                        PlannerResponseDTO.class))

                .toList();

    }

    // Get Planner By Id
    public PlannerResponseDTO getPlannerById(Long id) {

        Planner planner = plannerRepository.findById(id)

                .orElseThrow(() ->

                        new TaskNotFoundException(
                                "Planner Not Found"));

        return modelMapper.map(
                planner,
                PlannerResponseDTO.class);

    }

}