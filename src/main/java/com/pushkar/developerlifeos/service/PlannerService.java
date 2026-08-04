package com.pushkar.developerlifeos.service;

import com.pushkar.developerlifeos.dto.PlannerRequestDTO;
import com.pushkar.developerlifeos.dto.PlannerResponseDTO;
import com.pushkar.developerlifeos.entity.Planner;
import com.pushkar.developerlifeos.entity.TimeSlot;
import com.pushkar.developerlifeos.exception.TaskNotFoundException;
import com.pushkar.developerlifeos.repository.PlannerRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    // update planner
    public Planner updatePlanner(Long id, PlannerRequestDTO dto) {

        Planner planner = plannerRepository.findById(id)

                .orElseThrow(() ->
                        new TaskNotFoundException(
                                "Planner not found"));

        planner.setTitle(dto.getTitle());
        planner.setDescription(dto.getDescription());
        planner.setTimeSlot(dto.getTimeSlot());
        planner.setPlannerDate(dto.getPlannerDate());

        return plannerRepository.save(planner);

    }

    // Delete Planner
    public void deletePlanner(Long id) {

        Planner planner = plannerRepository.findById(id)

                .orElseThrow(() ->
                        new TaskNotFoundException(
                                "Planner not found"));

        plannerRepository.delete(planner);

    }

    // Toggle Complete
    public Planner toggleCompleted(Long id) {

        Planner planner = plannerRepository.findById(id)

                .orElseThrow(() ->
                        new TaskNotFoundException(
                                "Planner not found"));

        planner.setCompleted(!planner.isCompleted());

        return plannerRepository.save(planner);

    }

    //  Filter by Date
    public List<PlannerResponseDTO> getPlannerByDate(LocalDate date) {

        return plannerRepository

                .findByPlannerDate(date)

                .stream()

                .map(planner ->
                        modelMapper.map(
                                planner,
                                PlannerResponseDTO.class))

                .toList();

    }

    // Filter By Time Slot
    public List<PlannerResponseDTO> getPlannerByTimeSlot(TimeSlot slot) {

        return plannerRepository

                .findByTimeSlot(slot)

                .stream()

                .map(planner ->
                        modelMapper.map(
                                planner,
                                PlannerResponseDTO.class))

                .toList();

    }

}